package org.selfbus.sbtools.knxcom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.selfbus.sbtools.knxcom.application.Memory;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor0;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapper;

/**
 * A direct connection to a device on the KNX/EIB bus. Use
 * {@link BusInterface#connect(PhysicalAddress, org.selfbus.sbtools.knxcom.telegram.Priority)}
 * to open a data connection.
 * <p>
 * The data connection is {@link Application application} oriented. Proper
 * telegrams for transporting the applications and its data are created as
 * needed internally.
 * <p>
 * Always close the connection with {@link #close} when done.
 *
 * @see BusInterface#connect(PhysicalAddress, org.selfbus.sbtools.knxcom.telegram.Priority)
 */
public interface DataConnection
{
   /**
    * Open the connection.
    *
    * @throws IOException if the connection is not closed
    */
   public void open() throws IOException;

   /**
    * @return true if the connection is opened.
    */
   public boolean isOpened();

   /**
    * Close the connection.
    */
   public void close();

   /**
    * Destroy the connection without closing it. Use with care!
    * @see #close()
    */
   public void dispose();

   /**
    * Query the device by sending a telegram containing the given application,
    * and then wait for the answer from the device. The answer is then returned.
    *
    * @param application - the application to send.
    *
    * @return the application of the received reply telegram, or null if no
    *         reply telegram was received within 6 seconds.
    *
    * @throws IOException if there was a technical problem sending the telegram.
    * @throws TimeoutException if the telegram was not acknowledged by the
    *            remote device.
    */
   public Application query(Application application) throws IOException, TimeoutException;

   /**
    * Send a telegram to the device. The given application is wrapped into a
    * proper telegram and sent to the device. Send waits for an acknowledge from
    * the device.
    *
    * @param application - the application to send
    *
    * @throws IOException if there was a technical problem sending the telegram.
    * @throws TimeoutException if the telegram was not acknowledged by the
    *            remote device.
    */
   public void send(Application application) throws IOException, TimeoutException;

   /**
    * Send a telegram to the device. The given application is wrapped into a
    * proper telegram and sent to the device. This method does *not* wait for an
    * acknowledge from the remote device, as {@link #send(Application)} does.
    *
    * This method is e.g. for sending a {@link ApplicationType#Restart restart},
    * which is not confirmed.
    *
    * @param application - the application to send
    *
    * @throws IOException if there was a technical problem sending the telegram
    */
   public void sendUnconfirmed(Application application) throws IOException;

   /**
    * Receive an {@link Application application} from the device. Waits until a
    * telegram is received and extracts the application from it.
    *
    * @param timeout - how long to wait, in milliseconds, -1 waits infinitely.
    *
    * @return the received application
    *
    * @throws IOException in case of receive problems.
    */
   public Application receive(int timeout) throws IOException;

   /**
    * Receive multiple {@link Application application}s from the device. Waits
    * until the timeout is over and returns all applications that arrived within
    * the time, and that were in the receive queue.
    *
    * @param timeout - how long to wait, in milliseconds, 0 to not wait at all.
    *
    * @return a list with the received applications.
    *
    * @throws IOException in case of receive problems.
    */
   public List<Application> receiveMultiple(int timeout) throws IOException;

   /**
    * Get the device descriptor #0.
    *
    * @return The device descriptor #0.
    */
   public DeviceDescriptor0 getDeviceDescriptor0();

   /**
    * Install a memory address mapper that enables {@link Memory memory
    * applications} of telegrams to map logical to physical addresses. Usually
    * the connected remote device is queried for the device descriptor to load
    * the correct mapping.
    *
    * The installed mapper is automatically applied to all sent and received
    * {@link Memory memory applications}.
    */
   public void installMemoryAddressMapper();

   /**
    * Get the memory address mapper. A mapper is installed if required.
    *
    * @return The memory address mapper.
    */
   public MemoryAddressMapper getMemoryAddressMapper();
}
