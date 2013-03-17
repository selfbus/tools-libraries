package org.selfbus.sbtools.knxcom;

import java.io.IOException;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramListener;
import org.selfbus.sbtools.knxcom.types.LinkMode;

/**
 * Interface for classes that do the communication with the KNX/EIB bus.
 */
public interface BusInterface
{
   /**
    * Open the connection to the bus, using the preferred connection type and
    * settings, taken from the application's configuration.
    * 
    * @param mode - the link mode to open the connection with.
    *
    * @throws IOException in case of I/O problems.
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
    *
    * @throws IOException in case of I/O problems.
    */
   public void setLinkMode(LinkMode mode) throws IOException;

   /**
    * @return the currently active link mode.
    */
   public LinkMode getLinkMode();

   /**
    * Send a telegram to the bus. Sending is not available in
    * {@link LinkMode#BusMonitor bus monitor} link mode.
    * 
    * @param telegram - the telegram to send.
    * 
    * @throws IOException in case of I/O problems.
    */
   public void send(Telegram telegram) throws IOException;

   /**
    * Returns an direct, {@link DataConnection#open() opened} data connection to
    * a specific device on the KNX/EIB bus. The connection can be used for
    * "connected" data transfer between the application and the KNX/EIB device.
    * <p>
    * Always {@link DataConnection#close() close the connection} after using it.
    * <p>
    * Opening a connection is not available in {@link LinkMode#BusMonitor bus
    * monitor} link mode.
    * 
    * @param addr - the physical address of the target device.
    * @param priority - the priority of the telegrams.
    * 
    * @return The new, {@link DataConnection#open() opened} connection.
    * 
    * @throws IOException in case of I/O problems.
    */
   public DataConnection connect(PhysicalAddress addr, Priority priority) throws IOException;

   /**
    * @return the internal KNX/EIB bus connection.
    */
   public Link getConnection();

   /**
    * Add a listener that gets informed when telegrams are sent or received.
    * Listeners are not informed about confirmations
    * {@link EmiFrameType#isConfirmation()}.
    * 
    * @param listener - the listener object.
    */
   public void addListener(TelegramListener listener);

   /**
    * Remove a listener.
    * 
    * @param listener - the listener object.
    */
   public void removeListener(TelegramListener listener);

   /**
    * Returns the physical address of the BCU that is used to access the KNX/EIB
    * bus. Valid after a successful {@link #open(LinkMode)}.
    * 
    * @return the physical address of the BCU.
    */
   public PhysicalAddress getPhysicalAddress();
}
