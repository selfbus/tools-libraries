package org.selfbus.sbtools.knxcom.internal;

import java.util.concurrent.CopyOnWriteArrayList;

import org.selfbus.sbtools.knxcom.event.CloseEvent;
import org.selfbus.sbtools.knxcom.event.FrameEvent;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.link.LinkListener;
import org.slf4j.LoggerFactory;

/**
 * An abstract {@link Link} class that has methods for handling listeners.
 */
public abstract class AbstractListenableLink implements Link
{
   private final CopyOnWriteArrayList<LinkListener> listeners = new CopyOnWriteArrayList<LinkListener>();

   /**
    * {@inheritDoc}
    */
   @Override
   public final void addListener(LinkListener listener)
   {
      listeners.add(listener);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final void removeListener(LinkListener listener)
   {
      listeners.remove(listener);
   }

   /**
    * Notify all listeners that a frame was received.
    *
    * @param e - the corresponding frame event.
    */
   public final void fireFrameReceived(FrameEvent e)
   {
      for (LinkListener listener : listeners)
      {
         try
         {
            listener.frameReceived(e);
         }
         catch (final RuntimeException ex)
         {
            removeListener(listener);
            LoggerFactory.getLogger(AbstractListenableLink.class).error("removed event listener", ex);
         }
      }
   }

   /**
    * Notify all listeners that the link was closed.
    *
    * @param e - the corresponding frame event.
    */
   public final void fireLinkClosed(CloseEvent e)
   {
      for (LinkListener listener : listeners)
      {
         try
         {
            listener.linkClosed(e);
         }
         catch (final RuntimeException ex)
         {
            removeListener(listener);
            LoggerFactory.getLogger(AbstractListenableLink.class).error("removed event listener", ex);
         }
      }
   }
}
