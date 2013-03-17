package org.selfbus.sbtools.knxcom.link.serial;

/**
 * Types of FT1.2 message function codes.
 */
public enum Ft12Function
{
   /**
    * Positive acknowledgment.
    */
   // ACK must be ordered before RESET in this enum
   ACK(0, Ft12FrameFormat.FIXED),

   /**
    * Reset the remote device (usually the BCU).
    */
   RESET(0, Ft12FrameFormat.FIXED),

   /**
    * Negative acknowledgment. The message was not accepted, probably due to
    * overload. Not used by the BAU.
    */
   NACK(1, Ft12FrameFormat.FIXED),

   /**
    * Data transfer.
    */
   DATA(3, Ft12FrameFormat.VARIABLE),

   /**
    * Status request.
    */
   STATUS_REQ(9, Ft12FrameFormat.FIXED),

   /**
    * Status reply.
    */
   STATUS_RESP(0xb, Ft12FrameFormat.FIXED);

   /**
    * The function code.
    */
   final public int code;

   /**
    * Format of the FT1.2 message.
    */
   final public Ft12FrameFormat format;

   /**
    * @return the function code symbol for the given code value, or null if the code is invalid.
    */
   static public Ft12Function valueOf(int code)
   {
      for (Ft12Function fc : values())
      {
         if (fc.code == code)
            return fc;
      }

      return null;
   }

   /*
    * Internal constructor
    */
   private Ft12Function(int code, Ft12FrameFormat messageType)
   {
      this.code = code;
      this.format = messageType;
   }
}
