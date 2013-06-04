package org.selfbus.sbtools.knxcom.link.dummy;

import java.io.IOException;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.event.CloseEvent;
import org.selfbus.sbtools.knxcom.internal.AbstractListenableLink;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.selfbus.sbtools.knxcom.types.LinkType;

/**
 * A dummy KNX connection that does nothing.
 */
public class DummyLink extends AbstractListenableLink implements Link
{
   private PhysicalAddress busAddr = PhysicalAddress.NULL;
   private boolean connected = false;
   private LinkMode mode;

   /**
    * {@inheritDoc}
    */
   @Override
   public void open(LinkMode mode) throws IOException
   {
      this.mode = mode;
      connected = true;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
      this.mode = null;
      connected = false;
      fireLinkClosed(new CloseEvent(this));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isConnected()
   {
      return connected;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLinkMode(LinkMode mode) throws IOException
   {
      this.mode = mode;
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
      return LinkType.NONE;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void send(EmiFrame frame, boolean blocking) throws IOException
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PhysicalAddress getPhysicalAddress()
   {
      return busAddr;
   }

}
