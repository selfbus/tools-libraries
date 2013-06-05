package org.selfbus.sbtools.sniffer.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

import org.selfbus.sbtools.sniffer.model.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reader for serial port with an own reader thread.
 */
public class PortReader implements Runnable
{
   private static final Logger LOGGER = LoggerFactory.getLogger(PortReader.class);

   private final Queue<ReceivedByte> readQueue;
   private final Direction direction;

   private SerialPort port;
   private String portName;
   private Thread readerThread;
   private InputStream inputStream;

   /**
    * Create a reader thread that connects to the serial port.
    * 
    * @param direction - the direction of the port
    * @param readQueue - the queue to add the read bytes to
    */
   public PortReader(Direction direction, Queue<ReceivedByte> readQueue)
   {
      this.direction = direction;
      this.readQueue = readQueue;
   }

   /**
    * Throw a runtime exception if the serial port was not properly closed.
    */
   @Override
   protected void finalize()
   {
      if (port != null)
         throw new RuntimeException("Internal error: port " + portName + " was not properly closed");
   }

   /**
    * Connect to the serial port and start the reader thread.
    * 
    * @param portName - the name of the port.
    * @param baudRate - the baud rate, e.g. 19200
    * @param dataBits - the number of data bits, e.g. 8
    * @param stop - the number of stop bits, e.g. 1
    * @param parity - the parity, e.g. {@link SerialPortWrapper#PARITY_EVEN}.
    * 
    * @throws IOException if the port could not be opened or is already open.
    */
   public void open(String portName, int baudRate, int dataBits, Stop stop, Parity parity) throws IOException
   {
      if (port != null)
         throw new IOException("port is open");

      try
      {
         LOGGER.debug("Opening serial port " + portName);

         final CommPortIdentifier portIdent = CommPortIdentifier.getPortIdentifier(portName);
         port = (SerialPort) portIdent.open("sniffer", 500);

         port.setSerialPortParams(baudRate, dataBits, stop.id, parity.id);
         port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

         port.setInputBufferSize(2048);
         port.setOutputBufferSize(2048);

         port.enableReceiveThreshold(1);
         port.enableReceiveTimeout(250);
      }
      catch (Exception e)
      {
         port = null;
         throw new IOException("cannot open port: " + portName, e);
      }

      this.portName = portName;
      inputStream = port.getInputStream();

      readerThread = new Thread(this, direction.toString().toLowerCase() + "-reader");
      readerThread.start();
   }

   /**
    * Close the serial port.
    */
   public void close()
   {
      if (port == null)
         return;

      LOGGER.debug("Closing serial port " + portName);

      readerThread.interrupt();
      portName = null;

      try
      {
         // Enable a receive timeout, to avoid hangs during close
         port.enableReceiveTimeout(100);
      }
      catch (Exception e)
      {
      }

      try
      {
         if (inputStream != null)
            inputStream.close();
      }
      catch (IOException e)
      {
      }

      if (port != null)
      {
         port.removeEventListener();
         port.close();
         port = null;
      }
   }

   /**
    * @return true if the serial port is opened.
    */
   public boolean isOpened()
   {
      return port != null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void run()
   {
      LOGGER.debug("reader thread started on " + portName);

      try
      {
         int ch;

         while (portName != null)
         {
            ch = inputStream.read();
            if (ch >= 0)
            {
               readQueue.add(new ReceivedByte(direction, ch));
            }
         }
      }
      catch (IOException e)
      {
         LOGGER.error("failed to read from " + portName, e);
      }

      LOGGER.debug("reader thread terminated");
   }
}
