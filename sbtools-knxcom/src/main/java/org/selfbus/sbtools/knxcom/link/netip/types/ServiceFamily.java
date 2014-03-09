package org.selfbus.sbtools.knxcom.link.netip.types;

/**
 * KNXnet/IP service families.
 */
public enum ServiceFamily
{
   /**
    * KNXnet/IP core.
    */
   CORE(0x02),

   /**
    * KNXnet/IP device management.
    */
   DEVICE_MGMT(0x03),

   /**
    * KNXnet/IP tunnelling.
    */
   TUNNEL(0x04),

   /**
    * KNXnet/IP device management.
    */
   ROUTING(0x05);

   // TODO To be completed ...
   

   /**
    * The service type code as used in the data frames.
    */
   public final int code;

   /**
    * @return the service family for the given value.
    * @throws IllegalArgumentException if no matching service type is found.
    */
   static public ServiceFamily valueOf(int code)
   {
      for (ServiceFamily v : values())
      {
         if (v.code == code)
            return v;
      }

      throw new IllegalArgumentException("Invalid KNXnet/IP service family: " + code);
   }

   /*
    * Internal constructor.
    */
   private ServiceFamily(int code)
   {
      this.code = code;
   }
}
