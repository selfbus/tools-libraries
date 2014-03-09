package example;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.DataConnection;
import org.selfbus.sbtools.knxcom.application.ADCRead;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An example program that shows the connected data transfer methods.
 */
public final class ConnectedDataTransferExample
{
   // The physical address of the device to which this test program connects.
   private final PhysicalAddress deviceAddress = new PhysicalAddress(1, 1, 6);

   private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedDataTransferExample.class);
   private final BusInterface bus;

   /**
    * Create the example main object.
    *
    * @throws Exception
    */
   public ConnectedDataTransferExample() throws Exception
   {
      LOGGER.info("*** Opening bus connection");
      // bus = BusInterfaceFactory.newKNXnetInterface("localhost", KNXnetConnection.defaultPortUDP);
      bus = BusInterfaceFactory.newSerialInterface("/dev/ttyUSB0");
      bus.open(LinkMode.LinkLayer);
   }

   /**
    * Close the resources.
    */
   public void dispose()
   {
      if (bus != null && bus.isConnected())
      {
         LOGGER.info("*** Closing bus connection");
         bus.close();
      }
   }

   /**
    * Do the real (example) work.
    *
    * @throws InterruptedException
    * @throws TimeoutException
    */
   public void run() throws IOException, InterruptedException, TimeoutException
   {
      LOGGER.info("*** Opening data-connection to " + deviceAddress);

      DataConnection con = null;
      try
      {
         con = bus.connect(deviceAddress, Priority.SYSTEM);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      LOGGER.debug("Data-connection to " + deviceAddress + " established");
      con.receiveMultiple(500);

      Application replyApp;

      LOGGER.info("*** Querying ADC channel #0");
      con.receiveMultiple(0);
      replyApp = con.query(new ADCRead(0, 5));
      LOGGER.debug("*** Reply: " + replyApp);
      if (replyApp == null)
         throw new RuntimeException("No reply received");

      LOGGER.info("*** Sending memory-read telegram");

      con.receiveMultiple(0);
      con.send(new ADCRead(1, 10));

      LOGGER.info("*** Waiting for reply");
      List<Application> replies = con.receiveMultiple(2000);
      for (Application reply : replies)
         LOGGER.debug("*** Reply: " + reply);

      LOGGER.info("*** Closing data-connection");
      con.close();
   }

   /**
    * The main.
    */
   public static void main(String[] args) throws Exception
   {
      Environment.init();

      ConnectedDataTransferExample cdt = null;
      try
      {
         cdt = new ConnectedDataTransferExample();
         cdt.run();

         Thread.sleep(500);
         LOGGER.info("*** Done, waiting some seconds before terminating");
         Thread.sleep(7000);
      }
      finally
      {
         if (cdt != null)
            cdt.dispose();
      }
   }
}
