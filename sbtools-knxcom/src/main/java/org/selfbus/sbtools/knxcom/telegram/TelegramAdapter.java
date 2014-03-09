package org.selfbus.sbtools.knxcom.telegram;


/**
 * Abstract implementation of a {@link TelegramListener telegram listener}.
 * The methods are empty.
 */
public abstract class TelegramAdapter implements TelegramListener
{
   /**
    * {@inheritDoc}
    */
   @Override
   public void telegramReceived(Telegram telegram)
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void telegramSent(Telegram telegram)
   {
   }
}
