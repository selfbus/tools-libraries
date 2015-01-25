package org.selfbus.sbtools.knxcom.internal;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.DataConnection;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiFrameFactory;
import org.selfbus.sbtools.knxcom.emi.EmiTelegramFrame;
import org.selfbus.sbtools.knxcom.emi.EmiVersion;
import org.selfbus.sbtools.knxcom.emi.L_Data_req;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.event.CloseEvent;
import org.selfbus.sbtools.knxcom.event.FrameEvent;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.link.LinkListener;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramListener;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link BusInterface} implementation. Use {@link BusInterfaceFactory} to get a
 * bus interface object.
 */
public class BusInterfaceImpl implements BusInterface
{
   private final static Logger LOGGER = LoggerFactory.getLogger(BusInterfaceImpl.class);

   // Timeout for telegram confirmation in milliseconds
   private static final int CONFIRM_TIMEOUT_MS = 3000;

   // Minimum link idle time in milliseconds.
   private static final long MIN_IDLE_TIME_MS = 100;

   // Wait time in milliseconds if the minimum link idle time does not hold
   private static final long IDLE_WAIT_MS = 100;

   protected final CopyOnWriteArraySet<TelegramListener> listeners = new CopyOnWriteArraySet<TelegramListener>();
   protected final Map<PhysicalAddress, DataConnection> connections = new ConcurrentHashMap<PhysicalAddress, DataConnection>();
   private final Semaphore replySemaphore = new Semaphore(0);
   private volatile long lastFrameReceive;
   private Telegram waitConTelegram;
   private Link link;
   private Receiver receiver;

   /**
    * Create a bus-interface object.
    *
    * @see {@link #setLink(Link)}
    */
   public BusInterfaceImpl()
   {
      this(null);
   }

   /**
    * Create a bus-interface object that uses the given connection for the bus
    * communication.
    *
    * @param con - the connection to use.
    */
   public BusInterfaceImpl(Link con)
   {
      this.link = con;

      (new Thread(receiver)).start();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void addListener(TelegramListener listener)
   {
      listeners.add(listener);
   }

   /**
    * Set the link.
    *
    * @param link - the link to set.
    * @throws IOException if the link cannot be opened.
    */
   public void setLink(Link link) throws IOException
   {
      LinkMode mode = link.getLinkMode();
      boolean opened = link.isConnected();

      if (opened) link.close();
      this.link = link;
      if (opened) link.open(mode);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void open(LinkMode mode) throws IOException
   {
      receiver = new Receiver();

      link.open(mode);

      link.addListener(receiver);
      receiver.start();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
      close(true, "normal close");
   }

   /**
    * Close the connection.
    *
    * @param normal
    * @param reason
    */
   private void close(boolean normal, String reason)
   {
      LOGGER.info("Closing bus interface - " + reason);

      link.removeListener(receiver);
      receiver.quit();
      link.close();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLinkMode(LinkMode mode) throws IOException
   {
      LOGGER.debug("Switching to " + mode + " link mode");
      link.setLinkMode(mode);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LinkMode getLinkMode()
   {
      return link.getLinkMode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public DataConnection connect(PhysicalAddress addr, Priority priority) throws IOException
   {
      if (link == null)
         throw new IOException("Not open");

      if (getLinkMode() == LinkMode.BusMonitor)
         throw new IllegalAccessError("bus monitor link mode is read only");

      final DataConnection dataCon = new DataConnectionImpl(addr, priority, this);
      dataCon.open();

      connections.put(addr, dataCon);
      return dataCon;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Link getConnection()
   {
      return link;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PhysicalAddress getPhysicalAddress()
   {
      return link.getPhysicalAddress();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isConnected()
   {
      return link != null && link.isConnected();
   }

   /**
    * Notify all listeners that the given telegram was received.
    *
    * @param telegram - the telegram that was received.
    */
   protected void notifyListenersReceived(final Telegram telegram)
   {
      for (TelegramListener listener : listeners)
         listener.telegramReceived(telegram);
   }

   /**
    * Notify all listeners that the given telegram was sent.
    *
    * @param telegram - the telegram that was sent.
    */
   protected void notifyListenersSent(final Telegram telegram)
   {
      for (TelegramListener listener : listeners)
         listener.telegramSent(telegram);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void removeListener(TelegramListener listener)
   {
      listeners.remove(listener);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public synchronized void send(Telegram telegram) throws IOException
   {
      if (link == null)
         throw new IOException("Not open");

      if (getLinkMode() == LinkMode.BusMonitor)
         throw new IOException("bus monitor link mode is read only");

      if (System.currentTimeMillis() - lastFrameReceive < MIN_IDLE_TIME_MS)
      {
         try
         {
            Thread.sleep(IDLE_WAIT_MS);
         }
         catch (InterruptedException e)
         {
            LOGGER.error("send interrupted", e);
         }
      }

      final PhysicalAddress from = telegram.getFrom();
      if (from == null || PhysicalAddress.NULL.equals(from))
         telegram.setFrom(getPhysicalAddress());

      // LOGGER.debug("SEND: " + telegram);

      replySemaphore.drainPermits();
      waitConTelegram = telegram;

      final int waitTimeMS = CONFIRM_TIMEOUT_MS;
      try
      {
         link.send(new L_Data_req(EmiFrameType.EMI2_L_DATA_REQ, telegram), true);

         if (replySemaphore.tryAcquire(waitTimeMS, TimeUnit.MILLISECONDS))
            return;
      }
      catch (InterruptedException e)
      {
         LOGGER.warn("Interrupted while waiting for send confirmation", e);
      }
      finally
      {
         waitConTelegram = null;
      }

      throw new IOException("Sent telegram was not confirmed by the link / BAU within " + waitTimeMS + "ms");
   }

   /**
    * Telegram receiver thread.
    */
   private final class Receiver extends Thread implements LinkListener
   {
      private final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

      private final Queue<FrameEvent> receiveQueue = new ConcurrentLinkedQueue<FrameEvent>();
      private final Semaphore received = new Semaphore(0, true);
      private volatile boolean active;

      Receiver()
      {
         super("BusInterface-Receiver");
         setDaemon(true);
      }

      /**
       * The receiver's main loop.
       */
      @Override
      public void run()
      {
         active = true;

         try
         {
            while (active)
            {
               try
               {
                  received.acquire();
               }
               catch (InterruptedException e)
               {
                  if (active)
                     LOGGER.warn("interrupted", e);
               }

               if (active)
                  processFrameEvent(receiveQueue.poll());
            }
         }
         catch (Exception e)
         {
            LOGGER.error("Uncaught exception during bus frame processing", e);
         }
         finally
         {
            if (active)
               close(false, "Receiver communication failure");
         }

         LOGGER.info("Receiver thread terminated");
      }

      /**
       * Process a frame-event.
       *
       * @param e - the frame event to process.
       * @return true if the frame was valid, false if not.
       */
      boolean processFrameEvent(FrameEvent e)
      {
         lastFrameReceive = System.currentTimeMillis();
         final EmiFrame frame = e.getFrame();

         if (frame instanceof EmiTelegramFrame)
         {
            final Telegram telegram = ((EmiTelegramFrame) frame).getTelegram();

            if (frame.getType().isConfirmation())
            {
               LOGGER.debug("SENT: " + telegram);
               notifyListenersSent(telegram);
            }
            else
            {
               LOGGER.debug("RECV: " + telegram);
               notifyListenersReceived(telegram);
            }
         }

         return true;
      }

      /**
       * Terminate the receiver thread.
       */
      void quit()
      {
         active = false;
         received.release(1);

         interrupt();

         if (currentThread() == this)
            return;

         try
         {
            join(1000);
         }
         catch (final InterruptedException e)
         {
         }
      }

      /**
       * A frame-event is received. This method is called by the link's thread
       * and must not contain expensive code.
       */
      @Override
      public void frameReceived(FrameEvent e)
      {
         EmiFrame frame = e.getFrame();
         if (frame == null)
         {
            try
            {
               frame = EmiFrameFactory.createFrame(e.getData(), EmiVersion.EMI2);
               e.setFrame(frame);
            }
            catch (IOException ex)
            {
               final byte[] data = e.getData();
               LOGGER.warn("invalid EMI frame, discarding " + data.length + " bytes:" + HexString.toString(data), ex);
               return;
            }
         }

         if (frame instanceof EmiTelegramFrame)
         {
            final Telegram telegram = ((EmiTelegramFrame) frame).getTelegram();

            if (frame.getType().isConfirmation() && telegram.isSimilar(waitConTelegram))
            {
               replySemaphore.release();
            }
         }

         receiveQueue.add(e);
         received.release();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void linkClosed(CloseEvent e)
      {
         quit();
      }
   };
}
