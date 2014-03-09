package org.selfbus.sbtools.knxcom.emi;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Link data indicator. This message contains a telegram that
 * was received from the EIB bus by the bus-interface.
 */
public class L_Data_ind extends EmiTelegramFrame
{
   /**
    * Create a link data indicator with the given telegram.
    */
   public L_Data_ind(EmiFrameType type, Telegram telegram)
   {
      super(type, telegram);
   }

   /**
    * Create an empty link data indicator message.
    */
   public L_Data_ind(EmiFrameType type)
   {
      super(type);
   }
}