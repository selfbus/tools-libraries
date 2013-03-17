package example;

import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.common.address.GroupAddress;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.application.IndividualAddressRead;
import org.selfbus.sbtools.knxcom.link.netip.KNXnetLink;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramListener;
import org.selfbus.sbtools.knxcom.telegram.Transport;
import org.selfbus.sbtools.knxcom.types.LinkMode;

/**
 * A simple bus monitor that opens a bus interface using the KNXnet/IP server on
 * the local host. The received telegrams are printed to the console.
 */
public class KNXnetBusMonitor implements TelegramListener
{
//   private static final String KNX_NET_HOST = "localhost";
   private static final String KNX_NET_HOST = "omiai";

   private final BusInterface iface;

   /**
    * Create the bus monitor.
    *
    * @throws Exception
    */
   public KNXnetBusMonitor() throws Exception
   {
      iface = BusInterfaceFactory.newKNXnetInterface(KNX_NET_HOST, KNXnetLink.defaultPortUDP);
      iface.addListener(this);
      iface.open(LinkMode.BusMonitor);

      final Telegram telegram = new Telegram();
      telegram.setFrom(PhysicalAddress.NULL);
      telegram.setPriority(Priority.SYSTEM);
      telegram.setTransport(Transport.Individual);
      telegram.setDest(GroupAddress.BROADCAST);
      telegram.setApplication(new IndividualAddressRead());
      iface.send(telegram);
   }

   /**
    * Close the resources
    */
   public void dispose()
   {
      if (iface != null && iface.isConnected())
         iface.close();
   }

   /**
    * A telegram was received.
    */
   @Override
   public void telegramReceived(Telegram telegram)
   {
      System.out.println("Telegram received: " + telegram.toString());
   }

   /**
    * A telegram was sent by our application.
    */
   @Override
   public void telegramSent(Telegram telegram)
   {
      System.out.println("Telegram sent: " + telegram.toString());
   }

   /**
    * Start the application.
    *
    * @throws Exception
    */
   public static void main(String[] args) throws Exception
   {
      Environment.init();

      KNXnetBusMonitor mon = null;
      try
      {
         mon = new KNXnetBusMonitor();

         while (true)
            Thread.sleep(1000);

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (mon != null)
            mon.dispose();
         System.exit(0);
      }
   }
}
