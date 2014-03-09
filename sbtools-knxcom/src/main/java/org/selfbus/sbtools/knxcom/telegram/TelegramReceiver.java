package org.selfbus.sbtools.knxcom.telegram;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.selfbus.sbtools.common.address.Address;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.slf4j.LoggerFactory;

/**
 * A telegram receiver is an object for receiving telegrams from the KNX/EIB
 * bus. The received telegrams can be filtered with various rules to select the
 * telegrams that shall be received.
 * <p>
 * Per default, telegrams are filtered to only contain those that have the
 * physical address of the bus interface as destination.
 * <p>
 * You can override the {@link #filter(Telegram, boolean)} method in subclasses
 * to do your own telegram filtering.
 * <p>
 * Call {@link #close()} after using the TelegramReceiver.
 */
public class TelegramReceiver extends TelegramAdapter
{
   private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TelegramReceiver.class);

   private final LinkedList<Telegram> telegrams = new LinkedList<Telegram>();
   private final Semaphore waitSem = new Semaphore(0);
   private final WeakReference<BusInterface> busInterface;

   private Address destAddr;
   private ApplicationType applicationType;
   private boolean confirmations = false;

   /**
    * Create a telegram receiver.
    * <p>
    * Call {@link #close()} after using the TelegramReceiver.
    *
    * @param busInterface - the bus interface to receive telegrams from.
    */
   public TelegramReceiver(BusInterface busInterface)
   {
      this.busInterface = new WeakReference<BusInterface>(busInterface);
      destAddr = busInterface.getPhysicalAddress();

      busInterface.addListener(this);
   }

   /**
    * Create a telegram receiver that uses the
    * {@link BusInterfaceFactory#getBusInterface() default bus interface}.
    * <p>
    * Call {@link #close()} after using the TelegramReceiver.
    */
   public TelegramReceiver()
   {
      this(BusInterfaceFactory.getBusInterface());
   }

   /**
    * @return the {@link BusInterface bus interface} that is used
    */
   public BusInterface getBusInterface()
   {
      return busInterface.get();
   }

   /**
    * Close the telegram receiver.
    */
   public void close()
   {
      BusInterface iface = busInterface.get();

      if (iface != null)
         iface.removeListener(this);
   }

   /**
    * Empty the list of telegrams. This discards all telegrams that were
    * collected in the internal list.
    */
   public void clear()
   {
      synchronized (telegrams)
      {
         telegrams.clear();
      }
   }

   /**
    * Receive a telegram that matches the filter criteria. Returns the next
    * telegram of the internal list, or waits a specified time for a telegram to
    * arrive. If multiple telegrams are available, the oldest telegram is
    * returned.
    *
    * @param timeout - time to wait for a telegram to arrive, in milliseconds.
    *
    * @return the (next) received telegram, or null if no telegram arrived
    *         within the wait time.
    *
    * @see #clear()
    * @see #receiveMultiple(int)
    */
   public Telegram receive(int timeout)
   {
      try
      {
         if (!waitSem.tryAcquire(timeout, TimeUnit.MILLISECONDS))
            return null;
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }

      synchronized (telegrams)
      {
         return telegrams.isEmpty() ? null : telegrams.poll();
      }
   }

   /**
    * Receive a telegram that matches the filter criteria. Returns the next
    * telegram of the internal list, or waits until a matching telegram arrives.
    * If multiple telegrams are available, the oldest telegram is returned.
    *
    * @return the (next) received telegram.
    *
    * @see #clear()
    * @see #receiveMultiple(int)
    */
   public Telegram receive()
   {
      try
      {
         waitSem.acquire();

         synchronized (telegrams)
         {
            return telegrams.poll();
         }
      }
      catch (InterruptedException e)
      {
         throw new RuntimeException(e);
      }
   }

   /**
    * Receive multiple telegrams that match the filter criteria. Wait the full
    * timeout time for telegrams to arrive. The timeout may be zero. In that
    * case the queued telegrams are returned but it is not waited for more
    * telegrams.
    *
    * @param timeout - the timeout to wait for telegrams in milliseconds, 0 to
    *           not wait.
    *
    * @see #clear()
    * @see #receive(int)
    */
   public List<Telegram> receiveMultiple(int timeout)
   {
      if (timeout > 0)
      {
         try
         {
            Thread.sleep(timeout);
         }
         catch (InterruptedException e)
         {
            throw new RuntimeException(e);
         }
      }

      final List<Telegram> result = new LinkedList<Telegram>();

      synchronized (telegrams)
      {
//         LOGGER.debug("receiveMultiple: " + telegrams.size() + " telegrams received");

         if (!waitSem.tryAcquire(telegrams.size()))
            throw new RuntimeException("internal error");

         result.addAll(telegrams);
         telegrams.clear();
      }

      return result;
   }

   /**
    * @return the accepted destination address, or null if any destination
    *         address is accepted.
    */
   public Address getDest()
   {
      return destAddr;
   }

   /**
    * Set the accepted destination address for telegrams. Set to null for not
    * filtering on destination addresses.
    *
    * @param addr - the accepted destination address
    */
   public void setDest(Address addr)
   {
      this.destAddr = addr;
   }

   /**
    * @return the accepted application type, or null if no filtering on
    *         application type occurs.
    */
   public ApplicationType getApplicationType()
   {
      return applicationType;
   }

   /**
    * Set the application type for accepted telegrams. Can be null to not filter
    * on application type.
    *
    * @param type - the application type to set
    */
   public void setApplicationType(ApplicationType type)
   {
      this.applicationType = type;
   }

   /**
    * @return true if confirmation telegrams are accepted.
    */
   public boolean isConfirmations()
   {
      return confirmations;
   }

   /**
    * Decide if confirmation telegrams are accepted.
    *
    * @param enable - to accept confirmation telegrams.
    */
   public void setConfirmations(boolean enable)
   {
      this.confirmations = enable;
   }

   /**
    * Decide whether a telegram shall be further processed by the receiver
    * methods or not. The default implementation filters the telegram depending
    * on the set filter conditions: for system broadcasts, destination address,
    * application type, and send-confirmations.
    * <p>
    * This method can be overridden in subclasses to do other filtering.
    *
    * @param telegram - the telegram to filter.
    * @param isConfirmation - true if the telegram is a send-confirmation.
    *
    * @return true if the telegram shall be used.
    */
   public boolean filter(Telegram telegram, boolean isConfirmation)
   {
      final Address dest = telegram.getDest();

      if (!confirmations && isConfirmation)
         return false;

      if (destAddr != null && !destAddr.equals(dest))
         return false;

      if (applicationType != null && applicationType != telegram.getApplicationType())
         return false;

      return true;
   }

   /**
    * Process a received telegram. Call the {@link #filter(Telegram, boolean)}
    * to decide whether the telegram shall be further processed or not.
    *
    * @param telegram - the received telegram
    * @param isConfirmation - true if the telegram is a send-confirmation.
    */
   public void processTelegram(Telegram telegram, boolean isConfirmation)
   {
      if (!filter(telegram, isConfirmation))
         return;

      if (isConfirmation)
         LOGGER.debug("SENT filtered: " + telegram);
      else LOGGER.debug("RECV filtered: " + telegram);

      synchronized (telegrams)
      {
         telegrams.add(telegram);
      }

      waitSem.release();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void telegramReceived(Telegram telegram)
   {
      processTelegram(telegram, false);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void telegramSent(Telegram telegram)
   {
      processTelegram(telegram, true);
   }
}
