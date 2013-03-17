package org.selfbus.sbtools.knxcom.link.netip.types;

/**
 * KNXnet/IP connection types.
 */
public enum ConnectionType
{
   /**
    * Connection for configuring a KNXnet/IP device.
    */
   DEVICE_MGMT(3),

   /**
    * Connection for forwarding KNX telegrams between
    * two KNXnet/IP devices.
    */
   TUNNEL(4),

   /**
    * Connection for configuration and data transfer
    * with a remote logging server.
    */
   REMOTE_LOGGING(6),

   /**
    * Connection for configuration and data transfer
    * with a remote configuration server.
    */
   REMOTE_CONFIG(7),

   /**
    * Connection for configuration and data transfer
    * with an object server in a KNXnet/IP device.
    */
   OBJECT_SERVER(8);

   /**
    * The code byte.
    */
   public final int code;

   /**
    * @return the connection type for the given value.
    * @throws IllegalArgumentException if no matching connection type is found.
    */
   static public ConnectionType valueOf(int code)
   {
      for (ConnectionType v : values())
      {
         if (v.code == code)
            return v;
      }

      throw new IllegalArgumentException("Invalid KNXnet/IP connection type: " + code);
   }

   /*
    * Internal constructor
    */
   private ConnectionType(int code)
   {
      this.code = code;
   }
}
