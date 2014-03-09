package example;

import java.io.IOException;

import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.knxcom.event.CloseEvent;
import org.selfbus.sbtools.knxcom.event.FrameEvent;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.link.LinkListener;
import org.selfbus.sbtools.knxcom.link.serial.Ft12SerialLink;
import org.selfbus.sbtools.knxcom.link.serial.SerialPortUtil;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Open a serial link, switch to bus monitor mode,
 * and log the received frames.
 */
public final class SerialLinkExample
{
   private final static Logger LOGGER = LoggerFactory.getLogger(SerialLinkExample.class);

   public static void main(String[] args) throws InterruptedException, IOException
   {
      final Link link = new Ft12SerialLink(SerialPortUtil.getPortNames()[0]);

      link.addListener(new LinkListener()
      {
         @Override
         public void linkClosed(CloseEvent e)
         {
            LOGGER.info("*** exit ***");
            System.exit(0);
         }
         
         @Override
         public void frameReceived(FrameEvent e)
         {
            LOGGER.debug("received: " + HexString.toString(e.getData()));
         }
      });

      link.open(LinkMode.BusMonitor);

      while (true)
      {
         Thread.sleep(10000);
      }
   }
}
