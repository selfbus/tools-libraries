package org.selfbus.sbtools.knxcom.link.netip.types;

import org.selfbus.sbtools.knxcom.link.netip.blocks.EndPoint;

/**
 * Protocol types of {@link EndPoint}.
 */
public enum ProtocolType
{
   /**
    * UDP over IPv4
    */
   IPv4_UDP(0x01, TransportType.UDP, AddressFamily.IPv4),

   /**
    * TCP over IPv4.
    */
   IPv4_TCP(0x02, TransportType.TCP, AddressFamily.IPv4);

   /**
    * The service type code as used in the data frames.
    */
   public final int code;

   /**
    * The transport type on the IP network.
    */
   public final TransportType transportType;

   /**
    * The address family.
    */
   public final AddressFamily family;

   /**
    * @return the protocol type for the given value.
    *
    * @throws IllegalArgumentException if no matching protocol type is found.
    */
   static public ProtocolType valueOf(int code)
   {
      for (ProtocolType v : values())
      {
         if (v.code == code)
            return v;
      }

      throw new IllegalArgumentException("Invalid KNXnet/IP protocol type: 0x" + Integer.toHexString(code));
   }

   /**
    * @return the protocol type for the given value.
    *
    * @throws IllegalArgumentException if no matching protocol type is found.
    */
   static public ProtocolType valueOf(TransportType transportType, AddressFamily family)
   {
      for (ProtocolType v : values())
      {
         if (v.transportType == transportType && v.family == family)
            return v;
      }

      throw new IllegalArgumentException("No matching KNXnet/IP protocol type found for transport " + transportType
            + " address family " + family);
   }

   /*
    * Internal constructor.
    */
   private ProtocolType(int code, TransportType transportType, AddressFamily family)
   {
      this.code = code;
      this.transportType = transportType;
      this.family = family;
   }
}
