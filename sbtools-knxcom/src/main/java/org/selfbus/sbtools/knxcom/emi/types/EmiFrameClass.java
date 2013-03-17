package org.selfbus.sbtools.knxcom.emi.types;

/**
 * The frame class categorizes the {@link EmiFrameType EMI frame types}
 * into sending, receiving, send confirmation, and possibly others.
 */
public enum EmiFrameClass
{
   /**
    * A frame that is used for sending, usually named with "req" at
    * the end of the name.
    */
   SEND,

   /**
    * A frame that is used for receiving, usually named with "ind" at
    * the end of the name.
    */
   RECEIVE,

   /**
    * A frame that is a send confirmation or direct reply, usually
    * named with "con" at the end of the name.
    */
   CONFIRM;
}
