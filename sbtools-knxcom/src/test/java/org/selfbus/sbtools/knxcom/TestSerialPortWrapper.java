package org.selfbus.sbtools.knxcom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.util.TooManyListenersException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.serial.SerialPortUtil;
import org.selfbus.sbtools.knxcom.link.serial.SerialPortWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * These tests fail if your system has no serial port
 */
public class TestSerialPortWrapper
{
   private final static Logger LOGGER = LoggerFactory.getLogger(TestSerialPortWrapper.class);

   private static String portName;
   private SerialPortWrapper wrapper;

   static
   {
      final String[] portNames = SerialPortUtil.getPortNames();
      if (portNames == null || portNames.length == 0)
      {
         portName = null;
         LOGGER.info("Cannot run some unit tests: no serial port found");
      }
      else
      {
         portName = portNames[0];

         try
         {
            // Test if the port is usable

            final SerialPortWrapper wrapper = new SerialPortWrapper();
            wrapper.open(portName, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
            wrapper.close();
         }
         catch (Exception e)
         {
            LOGGER.info("Cannot run some unit tests: serial port " + portName + " cannot be opened");
            portName = null;
         }
      }
   }

   @Before
   public void setUp() throws InterruptedException
   {
      wrapper = new SerialPortWrapper();
   }

   @After
   public void tearDown()
   {
      if (wrapper != null)
      {
         wrapper.close();
         wrapper = null;
      }
   }

   @Test
   public final void testOpenClose() throws IOException
   {
      if (portName == null)
         return;

      assertFalse(wrapper.isOpened());

      wrapper.open(portName, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
      assertTrue(wrapper.isOpened());

      wrapper.notifyOnDataAvailable(true);
      wrapper.notifyOnDataAvailable(false);

      wrapper.close();
      assertFalse(wrapper.isOpened());

      wrapper.close();
      assertFalse(wrapper.isOpened());
   }

   @Test(expected = IOException.class)
   public final void testOpenNullPortName() throws IOException
   {
      wrapper.open(null, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
   }

   @Test(expected = IOException.class)
   public final void testOpenEmptyPortName() throws IOException
   {
      wrapper.open("", 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
   }

   @Test(expected = IOException.class)
   public final void testOpenTwice() throws IOException
   {
      if (portName == null)
         throw new IOException(); // the test expects an exception...

      wrapper.open(portName, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
      wrapper.open(portName, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
   }

   @Test
   public final void testGetStreams() throws IOException
   {
      if (portName == null)
         return;

      assertNull(wrapper.getInputStream());
      assertNull(wrapper.getOutputStream());

      wrapper.open(portName, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);

      assertNotNull(wrapper.getInputStream());
      assertNotNull(wrapper.getOutputStream());
   }

   @Test
   public final void testAddRemoveEventListener() throws TooManyListenersException, IOException
   {
      if (portName == null)
         return;

      wrapper.open(portName, 9600, SerialPortWrapper.DATABITS_8, SerialPortWrapper.STOPBITS_1, SerialPortWrapper.PARITY_NONE);
      wrapper.addEventListener(new MyListener());
      wrapper.removeEventListener();
      wrapper.addEventListener(new MyListener());
   }

   private static class MyListener implements SerialPortEventListener
   {
      @Override
      public void serialEvent(SerialPortEvent arg0)
      {
      }
   }
}
