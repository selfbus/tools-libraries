package org.selfbus.sbtools.knxcom.emi;

public enum AdditionalInfoType
{

   PL_MEDIUM_INFORMATION(0x01, 2),

   RF_MEDIUM_INFORMATION(0x02, 8),

   BUSMONITOR_STATUS_INFO(0x03, 1),

   TIMESTAMP_RELATIVE(0x04, 2),

   TIME_DELAY_UNTIL_SEND(0x05, 4),

   EXT_RELATIVE_TIMESTAMP(0x06, 4),

   BIBAT_INFORMATION(0x07, 2);

   /**
    * The message-type code.
    */
   public final int code;

   /**
    * The message-type code.
    */
   public final int length;

   public static AdditionalInfoType valueOf(int code)
   {
      for (AdditionalInfoType e : values())
         if (e.code == code)
            return e;

      throw new IllegalArgumentException("Unknown Additional Info type code 0x" + Integer.toHexString(code));
   }
   
   private AdditionalInfoType(int code, int length)
   {
      this.code = code;
      this.length = length;
   }

}
