package org.selfbus.sbtools.knxcom.link.serial;

import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiFrameFactory;
import org.selfbus.sbtools.knxcom.emi.EmiVersion;
import org.selfbus.sbtools.knxcom.emi.PEI_Identify_con;
import org.selfbus.sbtools.knxcom.emi.PEI_Identify_req;
import org.selfbus.sbtools.knxcom.emi.PEI_Switch_req;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.emi.types.PEISwitchMode;
import org.selfbus.sbtools.knxcom.event.CloseEvent;
import org.selfbus.sbtools.knxcom.event.FrameEvent;
import org.selfbus.sbtools.knxcom.exception.KNXAckTimeoutException;
import org.selfbus.sbtools.knxcom.exception.KNXPortClosedException;
import org.selfbus.sbtools.knxcom.internal.AbstractListenableLink;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.selfbus.sbtools.knxcom.types.LinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A link to the KNX bus via serial port with FT1.2 protocol.
 *
 * @see BusInterfaceFactory#newSerialInterface(String)
 */
public class Ft12SerialLink extends AbstractListenableLink
{
   private static final Logger LOGGER = LoggerFactory.getLogger(Ft12SerialLink.class);

   // Log sent and received FT1.2 messages
   private static final boolean LOG_FT12_MESSAGES = true;

   // Timeout for end of frame exchange in bits
   private static final int EXCHANGE_TIMEOUT = 512;

   // Maximum time between two frame characters, minimum time to indicate error
   // in bits
   private static final int IDLE_TIMEOUT = 33;

   // Limit for retransmissions of discarded frames
   private static final int REPEAT_LIMIT = 3;

   // FT1.2 acknowledgment
   private static final int FT12_ACK = 0xe5;

   // FT1.2 frame delimiter characters
   private static final int FT12_START_VARIABLE = 0x68;
   private static final int FT12_START_FIXED = 0x10;
   private static final int FT12_END = 0x16;

   // FT1.2 control byte flags
   private static final int DIR_FROM_BAU = 0x80;
   private static final int INITIATOR = 0x40;
   private static final int FRAMECOUNT_BIT = 0x20;
   private static final int FRAMECOUNT_VALID = 0x10;

   private final SerialPortWrapper port = new SerialPortWrapper();
   private final Object ackLock = new Object();
   private volatile Object openLock;
   private final String portName;

   private volatile Ft12State state = Ft12State.CLOSED;
   private PhysicalAddress bcuAddress = PhysicalAddress.NULL;
   private Receiver receiver;
   private OutputStream outputStream;
   private InputStream inputStream;
   private int exchangeTimeout;
   private int idleTimeout;
   private int sendFrameCount;
   private int rcvFrameCount;
   private LinkMode mode;

   /**
    * Create a connection for the serial port
    *
    * @param portName - the name of the serial port to open
    */
   public Ft12SerialLink(String portName)
   {
      this.portName = portName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void open(LinkMode mode) throws IOException
   {
      // RS-Interface: 115200, SerialPort.DATABITS_8,
      // SerialPort.STOPBITS_1, SerialPort.PARITY_NONE.
      //
      // FT-1.2: 19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
      // SerialPort.PARITY_EVEN.
      final int baudRate = 19200;

      setTimeouts(baudRate);

      port.open(portName, baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_EVEN);
      try
      {
         port.setReceiveTimeout(idleTimeout);
      }
      catch (UnsupportedCommOperationException e)
      {
         throw new IOException(e);
      }

      inputStream = new BufferedInputStream(port.getInputStream());
      outputStream = port.getOutputStream();

      // Reset the BCU
      send(Ft12Function.RESET);
      msleep(500);

      // Start the receiver thread
      receiver = new Receiver();
      receiver.start();
      msleep(100);

      openLock = new Object();
      synchronized (openLock)
      {
         try
         {
            // Identify the BCU
            send((new PEI_Identify_req(EmiFrameType.EMI2_PEI_IDENTIFY_REQ)).toByteArray(), true);

            // Wait for the Identify reply
            openLock.wait(1000);
         }
         catch (InterruptedException e)
         {
            LOGGER.error("interrupted waiting for PEI_Identify reply from the BCU", e);
         }
      }

      // Set the link mode
      setLinkMode(mode);
      msleep(500);

      LOGGER.debug("FT1.2 link opened");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
      close(true, "normal close");
   }

   /**
    * End the communication with the BCU as specified by the FT1.2 protocol.
    * <p>
    * All registered event listeners get notified. The close event is the last
    * event the listeners receive. If this link is already closed, no action is
    * performed.
    *
    * @param normal - true if it was an intended close.
    * @param reason - a message describing the reason of the close.
    */
   private void close(boolean normal, String reason)
   {
      if (state == Ft12State.CLOSED)
         return;

      LOGGER.info("Closing serial port " + portName + " - " + reason);
      try
      {
         send(new PEI_Switch_req(EmiFrameType.EMI2_PEI_SWITCH_REQ, PEISwitchMode.NORMAL), false);
      }
      catch (IOException ex)
      {
      }

      state = Ft12State.CLOSED;

      if (receiver != null)
         receiver.quit();

      try
      {
         inputStream.close();
         outputStream.close();
         port.close();
      }
      catch (final Exception e)
      {
         LOGGER.warn("failed to close all serial I/O resources", e);
      }

      fireLinkClosed(new CloseEvent(this));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isConnected()
   {
      return state != Ft12State.CLOSED;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLinkMode(LinkMode mode) throws IOException
   {
      PEISwitchMode switchMode;

      if (mode == LinkMode.LinkLayer)
         switchMode = PEISwitchMode.LINK;
      else if (mode == LinkMode.BusMonitor)
         switchMode = PEISwitchMode.BUSMON;
      else throw new IllegalArgumentException("invalid link mode: " + mode);

      LOGGER.debug("Activating " + mode + " link mode");
      this.mode = mode;
      send((new PEI_Switch_req(EmiFrameType.EMI2_PEI_SWITCH_REQ, switchMode)).toByteArray(), true);
   }

   /**
    * @return The name of the serial port.
    */
   public String getPortName()
   {
      return portName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LinkMode getLinkMode()
   {
      return mode;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LinkType getLinkType()
   {
      return LinkType.SERIAL_FT12;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void send(EmiFrame frame, boolean blocking) throws IOException
   {
      send(frame.toByteArray(), blocking);
   }

   /**
    * Send a byte buffer to the BCU. The data is wrapped into a variable length
    * FT1.2 frame.
    *
    * @param data - the data to send
    * @param blocking - shall we wait for an acknowledge from the BCU?
    *
    * @throws KNXAckTimeoutException
    * @throws KNXPortClosedException
    */
   public void send(byte[] data, boolean blocking) throws KNXAckTimeoutException, KNXPortClosedException
   {
      boolean ack = false;
      try
      {
         for (int i = 0; i <= REPEAT_LIMIT; ++i)
         {
            if (LOG_FT12_MESSAGES)
            {
               LOGGER.debug("FT1.2 sent (" + (blocking ? "" : "non-") + "blocking, attempt " + (i + 1) + "): "
                     + HexString.toString(data));
            }

            sendData(data);
            if (!blocking || waitForAck())
            {
               ack = true;
               break;
            }
         }

         sendFrameCount ^= FRAMECOUNT_BIT;
         if (state == Ft12State.ACK_PENDING)
            state = Ft12State.OK;

         if (!ack)
            throw new KNXAckTimeoutException("no acknowledge reply received within " + Integer.toString(exchangeTimeout) + " msec");
      }
      catch (final IOException e)
      {
         close(false, e.getMessage());
         throw new KNXPortClosedException(e.getMessage());
      }
   }

   /**
    * Send a fixed-length FT1.2 frame of the type {@link Ft12FrameFormat#FIXED}.
    * The message is sent 3 times before sending fails if it is not
    * acknowledged.
    *
    * @param func - the function of the frame, see {@link Ft12Function}.
    *
    * @throws IOException
    */
   public void send(final Ft12Function func) throws IOException
   {
      final byte[] buf = new byte[4];
      buf[0] = (byte) Ft12FrameFormat.FIXED.code;
      buf[1] = (byte) func.code;
      buf[2] = buf[1];
      buf[3] = (byte) FT12_END;

      if (LOG_FT12_MESSAGES)
         LOGGER.debug("FT1.2 sent: " + func.toString());

      state = Ft12State.ACK_PENDING;

      synchronized (outputStream)
      {
         outputStream.write(buf);
         outputStream.flush();
      }

      waitForAck();
   }

   /**
    * Send data to the BCU and wait for an {@link #waitForAck() acknowledge}.
    *
    * @param data - the data to send.
    *
    * @throws IllegalArgumentException if the data is longer than 255 bytes.
    * @throws KNXPortClosedException if the port is closed.
    * @throws IOException on any I/O related problems.
    */
   private void sendData(byte[] data) throws IOException, KNXPortClosedException
   {
      if (data.length > 255)
         throw new IllegalArgumentException("data length > 255 bytes");

      if (state == Ft12State.CLOSED)
         throw new KNXPortClosedException("connection closed");

      final byte[] buf = new byte[data.length + 7];
      int i = -1;
      buf[++i] = FT12_START_VARIABLE;
      buf[++i] = (byte) (data.length + 1);
      buf[++i] = (byte) (data.length + 1);
      buf[++i] = FT12_START_VARIABLE;
      buf[++i] = (byte) (INITIATOR | sendFrameCount | FRAMECOUNT_VALID | Ft12Function.DATA.code);
      for (int k = 0; k < data.length; ++k)
         buf[++i] = data[k];
      buf[++i] = calcChecksum(buf, 4, data.length + 1);
      buf[++i] = FT12_END;

      state = Ft12State.ACK_PENDING;

      synchronized (outputStream)
      {
         outputStream.write(buf);
         outputStream.flush();
      }
   }

   /**
    * Send a FT1.2 acknowledge frame.
    *
    * @throws IOException
    */
   private void sendAck() throws IOException
   {
      synchronized (outputStream)
      {
         outputStream.write(FT12_ACK);
         outputStream.flush();
      }

      if (LOG_FT12_MESSAGES)
         LOGGER.debug("FT1.2 ACK sent");
   }

   /**
    * Wait for a FT1.2 acknowledge frame.
    *
    * @return true if the acknowledge was received, false if no acknowledge was
    *         received within the timeout.
    */
   private boolean waitForAck()
   {
      final long end = System.currentTimeMillis() + exchangeTimeout;
      long remaining = exchangeTimeout;

      synchronized (ackLock)
      {
         while (state == Ft12State.ACK_PENDING && remaining > 0)
         {
            try
            {
               ackLock.wait(remaining);
            }
            catch (final InterruptedException e)
            {
            }

            remaining = end - System.currentTimeMillis();
         }
      }

      return remaining > 0;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PhysicalAddress getPhysicalAddress()
   {
      return bcuAddress;
   }

   /**
    * Set the timeouts for the communication.
    *
    * @param baudrate - the baud rate of the serial communication.
    */
   private void setTimeouts(int baudrate)
   {
      // with some serial driver/BCU/OS combinations, the calculated
      // timeouts are just too short, so add some milliseconds just as it fits
      // this little extra time usually doesn't hurt
      final int xTolerance = 5;
      final int iTolerance = 15;
      exchangeTimeout = Math.round((1000f * EXCHANGE_TIMEOUT) / baudrate) + xTolerance;
      idleTimeout = Math.round((1000f * IDLE_TIMEOUT) / baudrate) + iTolerance;
   }

   /**
    * Calculate the FT1.2 checksum for the given data.
    *
    * @param data - the data to process
    * @param offset - the offset within data to start with
    * @param length - the number of bytes to process.
    * @return The checksum;
    */
   private static byte calcChecksum(byte[] data, int offset, int length)
   {
      byte chk = 0;
      for (int i = 0; i < length; ++i)
         chk += data[offset + i];
      return chk;
   }

   /**
    * Sleep a moment.
    *
    * @param msec - time in milliseconds to sleep.
    */
   private void msleep(int msec)
   {
      try
      {
         Thread.sleep(msec);
      }
      catch (InterruptedException e)
      {
      }
   }

   /**
    * A PEI-Identify.con frame was received.
    *
    * @param data - the raw data of the frame.
    */
   private void peiIdentifyCon(byte[] data)
   {
      try
      {
         final PEI_Identify_con frame = (PEI_Identify_con) EmiFrameFactory.createFrame(data, EmiVersion.EMI2);
         bcuAddress = frame.getAddr();
         LOGGER.debug("BCU address is " + bcuAddress);

         if (openLock != null)
         {
            synchronized (openLock)
            {
               openLock.notify();
            }
         }
      }
      catch (IOException e)
      {
      }
   }

   /**
    * FT1.2 receiver thread for the {@link Ft12SerialLink FT1.2 serial
    * communication link}.
    */
   private final class Receiver extends Thread
   {
      private volatile boolean active;
      private int lastChecksum = -1;

      Receiver()
      {
         super("FT1.2-Receiver");
         setDaemon(true);
      }

      /**
       * The main loop of the receiver thread.
       */
      @Override
      public void run()
      {
         active = true;

         try
         {
            while (active)
            {
               final int c = inputStream.read();
               if (c > -1)
               {
                  if (c == FT12_ACK)
                  {
                     if (state == Ft12State.ACK_PENDING)
                     {
                        if (LOG_FT12_MESSAGES)
                           LOGGER.debug("FT1.2 ACK received");

                        synchronized (ackLock)
                        {
                           state = Ft12State.OK;
                           ackLock.notify();
                        }
                     }
                  }
                  else if (c == FT12_START_VARIABLE)
                  {
                     readVariableFrame();
                  }
                  else if (c == FT12_START_FIXED)
                  {
                     readFixedFrame();
                  }
                  else LOGGER.trace("received unexpected start byte 0x" + Integer.toHexString(c) + " (ignored)");
               }
            }
         }
         catch (final IOException e)
         {
            if (active)
               close(false, "receiver communication failure");
         }
      }

      /**
       * Read a fixed length FT1.2 frame
       *
       * @return true if the FT1.2 frame was valid, false if the frame had bit
       *         errors.
       * @throws IOException
       */
      private boolean readFixedFrame() throws IOException
      {
         final byte[] buf = new byte[3];

         if (inputStream.read(buf) == 3 && buf[0] == buf[1] && (buf[2] & 0xff) == FT12_END)
         {
            final int funcCode = buf[0] & 255;
            final Ft12Function func = Ft12Function.valueOf(funcCode & 15);

            if (LOG_FT12_MESSAGES && LOGGER.isTraceEnabled())
               LOGGER.trace("FT1.2: received "
                     + (func == null ? "0x" + Integer.toHexString(funcCode) : func.toString()));

            if ((funcCode & 0x30) == 0)
            {
               sendAck();
               return true;
            }
         }

         return false;
      }

      /**
       * Receive a variable length FT1.2 frame
       *
       * @return true if the FT1.2 frame was valid, false if the frame had bit
       *         errors.
       * @throws IOException
       */
      private boolean readVariableFrame() throws IOException
      {
         final int len = inputStream.read();
         final byte[] buf = new byte[len + 4];

         // Read the rest of frame (in single bytes, or the read timeout might
         // interrupt us)
         int c, read = 0;
         while (read < buf.length && (c = inputStream.read()) >= 0)
            buf[read++] = (byte) c;

         // Check header, control field, and end tag
         if (read == (len + 4) && (buf[0] & 0xff) == len && (buf[1] & 0xff) == FT12_START_VARIABLE
               && (buf[len + 3] & 0xff) == FT12_END)
         {
            final byte chk = buf[buf.length - 2];
            if (!checkCtrlField(buf[2] & 0xff, chk))
            {
               LOGGER.warn("... in received frame: "
                     + HexString.toString(new byte[] { FT12_START_VARIABLE, (byte) len }) + ' '
                     + HexString.toString(buf));
            }
            else if (calcChecksum(buf, 2, len) != chk)
            {
               LOGGER.warn("FT1.2 invalid checksum in frame: "
                     + HexString.toString(new byte[] { FT12_START_VARIABLE, (byte) len }) + ' '
                     + HexString.toString(buf));
            }
            else
            {
               lastChecksum = chk;
               rcvFrameCount ^= FRAMECOUNT_BIT;

               final byte[] data = new byte[len - 1];
               for (int i = 0; i < data.length; ++i)
                  data[i] = buf[3 + i];

               if (LOG_FT12_MESSAGES && LOGGER.isDebugEnabled())
                  LOGGER.debug("FT1.2 received: " + HexString.toString(data));

               sendAck();

               final FrameEvent e = new FrameEvent(this, data);

               if ((data[0] & 255) == EmiFrameType.EMI2_PEI_IDENTIFY_CON.code)
                  peiIdentifyCon(data);

               fireFrameReceived(e);

               return true;
            }
         }
         else
         {
            LOGGER.warn("FT1.2 invalid frame, discarded " + (read + 2) + " bytes: "
                  + HexString.toString(new byte[] { FT12_START_VARIABLE, (byte) len }) + ' ' + HexString.toString(buf));
         }

         return false;
      }

      /**
       * Check the control field of a variable FT1.2 frame
       *
       * @param c - the control field
       * @param chk - the checksum
       * @return true if the frame shall be further processed.
       */
      private boolean checkCtrlField(int c, byte chk)
      {
         if ((c & (DIR_FROM_BAU | INITIATOR)) != (DIR_FROM_BAU | INITIATOR))
         {
            LOGGER.warn("FT1.2 unexpected control field 0x" + Integer.toHexString(c));
            return false;
         }
         if ((c & FRAMECOUNT_VALID) == FRAMECOUNT_VALID)
         {
            if ((c & FRAMECOUNT_BIT) != rcvFrameCount)
            {
               // ignore repeated frame
               if (chk == lastChecksum)
               {
                  LOGGER.debug("FT1.2 framecount and checksum indicate a repeated " + "frame - ignored");
                  return false;
               }
               // protocol discrepancy (merten instabus coupler)
               LOGGER.warn("FT1.2 toggle frame count bit");
               rcvFrameCount ^= FRAMECOUNT_BIT;
            }
         }

         final Ft12Function func = Ft12Function.valueOf(c & 15);
         if (func == Ft12Function.DATA && (c & FRAMECOUNT_VALID) == 0)
         {
            LOGGER.warn("FT1.2 data, framecount not valid");
            return false;
         }

         return true;
      }

      /**
       * Terminate the receiver thread.
       */
      void quit()
      {
         active = false;
         interrupt();

         if (currentThread() == this)
            return;

         try
         {
            join(100);
         }
         catch (final InterruptedException e)
         {
         }
      }
   }
}
