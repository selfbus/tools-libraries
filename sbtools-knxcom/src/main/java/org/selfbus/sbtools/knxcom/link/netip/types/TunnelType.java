package org.selfbus.sbtools.knxcom.link.netip.types;

/**
 * Types for KNXnet/IP tunneling.
 */
public enum TunnelType
{
   /**
    * A data link layer tunnel to the KNX network.
    */
   LINK_LAYER(0x02),

   /**
    * A cEMI raw tunnel to the KNX network.
    */
   RAW(0x04),

   /**
    * A bus monitor tunnel to the KNX network.
    */
   BUS_MONITOR(0x80);

   /**
    * The numeric tunnel code
    */
   public final int code;

   /**
    * @return the tunnel type for the given value.
    * @throws IllegalArgumentException if the code is invalid
    */
   static public TunnelType valueOf(int code)
   {
      for (TunnelType v : values())
      {
         if (v.code == code)
            return v;
      }

      throw new IllegalArgumentException("Invalid KNXnet/IP tunnel type: " + code);
   }

   /*
    * Internal constructor.
    */
   private TunnelType(int code)
   {
      this.code = code;
   }
}
