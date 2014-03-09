package org.selfbus.sbtools.knxcom.emi;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Link data request confirmation.
 */
public class L_Data_con extends EmiTelegramFrame
{
   /**
    * Create a link data confirmation with the given telegram.
    */
   public L_Data_con(EmiFrameType type, Telegram telegram)
   {
      super(type, telegram);
   }

   /**
    * Create an empty link data confirmation message.
    */
   public L_Data_con(EmiFrameType type)
   {
      super(type);
   }
}