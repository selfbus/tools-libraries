package example;

import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.DataConnection;
import org.selfbus.sbtools.knxcom.MemoryConnection;
import org.selfbus.sbtools.knxcom.link.netip.KNXnetLink;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.types.LinkMode;

/**
 * Toggle the programming LED of a device on the bus.
 */
public class ToggleProgLed
{
   // The host name for KNXnet/IP connection
   private static final String KNX_NET_HOST = "localhost";

   // The address of the device to toggle
   private static final PhysicalAddress TARGET_ADDR = PhysicalAddress.valueOf("1.1.50");

   // ----

   private static final int STATUS_ADDR = 0x60;
   private final BusInterface bus;

   /**
    * Create the bus monitor.
    *
    * @throws Exception
    */
   public ToggleProgLed() throws Exception
   {
      bus = BusInterfaceFactory.newKNXnetInterface(KNX_NET_HOST, KNXnetLink.defaultPortUDP);
   }

   /**
    * Run the example.
    *
    * @throws Exception
    */
   public void run() throws Exception
   {
      bus.open(LinkMode.BusMonitor);

      System.out.println("*** Opening connection");
      DataConnection con = bus.connect(TARGET_ADDR, Priority.SYSTEM);
      MemoryConnection memCon = new MemoryConnection(con);

      System.out.println("*** Read status byte");
      byte[] resp = memCon.read(STATUS_ADDR, 1);
      System.out.println("*** Read status byte is 0x" + Integer.toHexString(resp[0]));

      resp[0] ^= 0x01;

      System.out.println("*** Write status byte 0x" + Integer.toHexString(resp[0]));
      memCon.write(STATUS_ADDR, resp);

      System.out.println("*** Closing connection");
      con.close();

      System.out.println("*** Done");
   }

   /**
    * Close the resources
    */
   public void dispose()
   {
      if (bus != null && bus.isConnected())
         bus.close();
   }

   /**
    * Start the application.
    *
    * @throws Exception
    */
   public static void main(String[] args) throws Exception
   {
      Environment.init();

      ToggleProgLed example = null;
      try
      {
         example = new ToggleProgLed();
         example.run();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (example != null)
            example.dispose();
         System.exit(0);
      }
   }
}
