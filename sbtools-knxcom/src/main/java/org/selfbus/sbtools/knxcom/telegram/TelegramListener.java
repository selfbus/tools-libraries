package org.selfbus.sbtools.knxcom.telegram;

import java.util.EventListener;

import org.selfbus.sbtools.knxcom.BusInterface;

/**
 * Interface for classes that want to be notified when a telegram is sent or received.
 *
 * @see BusInterface - this is where telegram listeners can be registered.
 */
public interface TelegramListener extends EventListener
{
   /**
    * A telegram was received.
    * The called object must not change the frame.
    *
    * @param telegram - the received telegram
    */
   public void telegramReceived(Telegram telegram);

   /**
    * A telegram was sent. This method is usually called when the send confirmation
    * is received from the BAU.
    *
    * The called object must not change the frame.
    *
    * @param telegram - the sent telegram
    */
   public void telegramSent(Telegram telegram);
}
