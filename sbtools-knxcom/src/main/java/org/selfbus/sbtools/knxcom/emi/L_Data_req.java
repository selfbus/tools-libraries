package org.selfbus.sbtools.knxcom.emi;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Link data request. This message type is used to send telegrams to the EIB
 * bus.
 */
public class L_Data_req extends EmiTelegramFrame
{
   /**
    * Create a link data request with the given telegram.
    */
   public L_Data_req(EmiFrameType type, Telegram telegram)
   {
      super(type, telegram);
   }

   /**
    * Create an empty link data request message.
    */
   public L_Data_req(EmiFrameType type)
   {
      super(type);
   }
}