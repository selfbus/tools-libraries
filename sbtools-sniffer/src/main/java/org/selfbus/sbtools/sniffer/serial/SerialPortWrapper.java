package org.selfbus.sbtools.sniffer.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper class for accessing serial ports.
 */
public class SerialPortWrapper
{
   private static final Logger LOGGER = LoggerFactory.getLogger(SerialPortWrapper.class);

   /**
    * Constants for the number of data bits.
    */
   public static final int DATABITS_5 = SerialPort.DATABITS_5;
   public static final int DATABITS_6 = SerialPort.DATABITS_6;
   public static final int DATABITS_7 = SerialPort.DATABITS_7;
   public static final int DATABITS_8 = SerialPort.DATABITS_8;

   /**
    * Constants for the number of stop bits.
    */
   public static final int STOPBITS_1 = SerialPort.STOPBITS_1;
   public static final int STOPBITS_2 = SerialPort.STOPBITS_2;
   public static final int STOPBITS_1_5 = SerialPort.STOPBITS_1_5;

   /**
    * Constants for the parity.
    */
   public static final int PARITY_NONE = SerialPort.PARITY_NONE;
   public static final int PARITY_ODD = SerialPort.PARITY_ODD;
   public static final int PARITY_EVEN = SerialPort.PARITY_EVEN;
   public static final int PARITY_MARK = SerialPort.PARITY_MARK;
   public static final int PARITY_SPACE = SerialPort.PARITY_SPACE;

   private SerialPort serialPort;
   private InputStream inputStream;
   private OutputStream outputStream;
   private String portName;

   /**
    * Throw a runtime exception if the serial port was not properly closed.
    */
   @Override
   protected void finalize()
   {
      if (serialPort != null)
         throw new RuntimeException("Internal error: serial port " + portName + " was not properly closed");
   }

   /**
    * Connect to the serial port.
    * 
    * @param portName - the name of the port.
    * @param baudRate - the baud rate, e.g. 19200
    * @param dataBits - the number of data bits, e.g. 8
    * @param stopBits - the number of stop bits, e.g. 1
    * @param parity - the parity, e.g. {@link SerialPortWrapper#PARITY_EVEN}.
    * 
    * @throws IOException if the port could not be opened or is already open.
    * 
    * @see SerialPortUtil#getPortNames() obtaining the names of the available
    *      serial ports.
    */
   public void open(String portName, int baudRate, int dataBits, int stopBits, int parity) throws IOException
   {
      if (serialPort != null)
         throw new IOException("port is open");

      try
      {
         LOGGER.debug("Opening serial port " + portName);

         inputStream = null;
         outputStream = null;
         serialPort = null;

         final CommPortIdentifier portIdent = CommPortIdentifier.getPortIdentifier(portName);
         serialPort = (SerialPort) portIdent.open("sbtools", 500);

         serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
         serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
         serialPort.setInputBufferSize(2048);
         serialPort.setOutputBufferSize(2048);

         // Taken from Calimero's RxtxAdapter:
//         serialPort.enableReceiveThreshold(1024);
      }
      catch (Exception e)
      {
         serialPort = null;
         throw new IOException("cannot open port: " + portName, e);
      }

      inputStream = serialPort.getInputStream();
      outputStream = serialPort.getOutputStream();

      this.portName = portName;
   }

   /**
    * Close the serial port.
    */
   public void close()
   {
      if (serialPort == null)
         return;

      LOGGER.debug("Closing serial port " + portName);

      try
      {
         // Enable a receive timeout, to avoid hangs during close
         serialPort.enableReceiveTimeout(250);
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

      try
      {
         if (outputStream != null)
            outputStream.close();
      }
      catch (IOException e)
      {
      }

      if (serialPort != null)
      {
         serialPort.removeEventListener();
         serialPort.close();
         serialPort = null;
      }

      inputStream = null;
      outputStream = null;
      portName = null;
   }

   /**
    * @return true if the serial port is opened.
    */
   public boolean isOpened()
   {
      return serialPort != null;
   }

   /**
    * @return the input stream
    */
   public InputStream getInputStream()
   {
      return inputStream;
   }

   /**
    * @return the output stream
    */
   public OutputStream getOutputStream()
   {
      return outputStream;
   }

   /**
    * Set the receive timeout.
    * 
    * @param time - the timeout to set, in milliseconds.
    * @throws UnsupportedCommOperationException 
    */
   public void setReceiveTimeout(int time) throws UnsupportedCommOperationException
   {
      serialPort.enableReceiveTimeout(time);
      serialPort.enableReceiveThreshold(1024);
   }

   /**
    * Add a port listener to the serial port. Only one listener is allowed at
    * the same time. The port must be opened before a listener can be added.
    * 
    * @param listener is the listener to add.
    * @throws TooManyListenersException is thrown if another listener was
    *            previously added.
    */
   public void addEventListener(SerialPortEventListener listener) throws TooManyListenersException
   {
      serialPort.addEventListener(listener);
   }

   /**
    * Remove the serial port listener.
    */
   public void removeEventListener()
   {
      serialPort.removeEventListener();
   }

   /**
    * Notify the listeners when data is available on the serial port.
    * 
    * @param enable
    */
   public void notifyOnDataAvailable(boolean enable)
   {
      serialPort.notifyOnDataAvailable(enable);
   }
}
