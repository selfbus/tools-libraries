package org.selfbus.sbtools.knxcom.link;

import java.io.IOException;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.selfbus.sbtools.knxcom.types.LinkType;

/**
 * A KNX/EIB (low level) bus connection link. For communication with the bus,
 * the {@link BusInterface} is usually used. The bus interface gets a
 * {@link Link link} which it uses to do the actual communication with the
 * bus. But you most definitely want to use {@link BusInterface} and not this
 * class for doing the bus communication.
 * 
 * @see BusInterface An interface for talking with the KNX/EIB bus.
 * @see LinkType
 */
public interface Link
{
   /**
    * Open the connection to the bus.
    * 
    * @param mode - the link mode to set for the connection.
    * 
    * @throws IOException if the open fails
    */
   public void open(LinkMode mode) throws IOException;

   /**
    * Close the bus connection.
    */
   public void close();

   /**
    * @return true if the bus connection is opened.
    */
   public boolean isConnected();

   /**
    * Switch the link mode.
    * 
    * @param mode - the link mode to switch to
    */
   public void setLinkMode(LinkMode mode) throws IOException;

   /**
    * @return the currently active link mode.
    */
   public LinkMode getLinkMode();

   /**
    * Send an EMI frame to the bus.
    * 
    * @param frame - the frame to send.
    * @param blocking - enable to wait for an acknowledge.
    * 
    * @throws IOException
    */
   public void send(EmiFrame frame, boolean blocking) throws IOException;

   /**
    * Add a link listener. Link listeners get called when {@link EmiFrame}s
    * arrive or the link is closed.
    */
   public void addListener(LinkListener listener);

   /**
    * Remove a link listener.
    */
   public void removeListener(LinkListener listener);

   /**
    * @return the physical address of the bus interface.
    */
   public PhysicalAddress getPhysicalAddress();
}
