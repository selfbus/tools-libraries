package org.selfbus.sbtools.knxcom.types;

/**
 * The type of a KNX medium.
 */
public enum MediumType
{
   /**
    * Unspecified medium type.
    */
   UNKNOWN(0, null),

   /**
    * Twisted pair.
    */
   TWISTED_PAIR(0x02, "TP"),

   /**
    * Power line.
    */
   POWER_LINE(0x04, "PL"),

   /**
    * Radio frequency.
    */
   RADIO_FREQ(0x10, "RF"),

   /**
    * Reserved.
    */
   RESERVED_01(0x01, null),

   /**
    * Reserved.
    */
   RESERVED_08(0x08, null);

   /**
    * The code of the type.
    */
   public final int code;

   /**
    * The short name of the type.
    */
   public final String shortName;

   /**
    * @return the medium type for the given value.
    * @throws IllegalArgumentException if no matching medium type is found.
    */
   static public MediumType valueOf(int code)
   {
      for (MediumType v : values())
      {
         if (v.code == code)
            return v;
      }

      throw new IllegalArgumentException("Invalid KNXnet/IP medium type: " + code);
   }

   /*
    * Internal constructor
    */
   private MediumType(int code, String shortName)
   {
      this.code = code;
      this.shortName = shortName;
   }
}
