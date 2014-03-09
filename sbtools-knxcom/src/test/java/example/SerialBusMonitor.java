package example;

import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramListener;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple bus monitor that opens a bus interface, and prints the received
 * telegrams to the console.
 */
public class SerialBusMonitor implements TelegramListener
{
   final static Logger LOGGER = LoggerFactory.getLogger(SerialBusMonitor.class);
   private final BusInterface iface;

   /**
    * Create the bus monitor.
    * 
    * @throws Exception
    */
   public SerialBusMonitor() throws Exception
   {
      //iface = BusInterfaceFactory.newSerialInterface(SerialPortUtil.getPortNames()[0]);
      iface = BusInterfaceFactory.newSerialInterface("/dev/ttyUSB0");
//      iface = BusInterfaceFactory.newSerialInterface("/dev/ttyS0");

      iface.addListener(this);
      iface.open(LinkMode.BusMonitor);
      LOGGER.debug("Bus connection opened");
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
      System.out.println(telegram.toString());
   }

   /**
    * A telegram was sent by our application.
    */
   @Override
   public void telegramSent(Telegram telegram)
   {
   }

   /**
    * Start the application.
    * 
    * @throws Exception
    */
   public static void main(String[] args) throws Exception
   {
      Environment.init();

      SerialBusMonitor mon = null;
      try
      {
         mon = new SerialBusMonitor();

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
