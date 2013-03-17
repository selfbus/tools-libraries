package org.selfbus.sbtools.knxcom.internal;

import java.io.IOException;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.DataConnection;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramListener;
import org.selfbus.sbtools.knxcom.types.LinkMode;

/**
 * A bus interface that is used for (unit) tests only.
 */
public class SimulatedBusInterface implements BusInterface
{
   @Override
   public void addListener(TelegramListener listener)
   {
   }

   @Override
   public void open(LinkMode mode) throws IOException
   {
   }

   @Override
   public void close()
   {
   }

   @Override
   public DataConnection connect(PhysicalAddress addr, Priority priority) throws IOException
   {
      return null;
   }

   @Override
   public Link getConnection()
   {
      return null;
   }

   @Override
   public PhysicalAddress getPhysicalAddress()
   {
      return PhysicalAddress.ONE;
   }

   @Override
   public boolean isConnected()
   {
      return true;
   }

   @Override
   public void removeListener(TelegramListener listener)
   {
   }

   @Override
   public void send(Telegram telegram) throws IOException
   {
   }

   @Override
   public void setLinkMode(LinkMode mode) throws IOException
   {
   }

   @Override
   public LinkMode getLinkMode()
   {
      return null;
   }

}
