package org.selfbus.sbtools.knxcom.link.netip.types;

/**
 * Status codes for various responses.
 */
public enum StatusCode
{
   /**
    * The operation completed successful.
    */
   OK(0),

   /**
    * The requested host protocol is not supported by the KNXnet/IP server.
    */
   E_HOST_PROTOCOL_TYPE(0x01),

   /**
    * The requested protocol version is not supported by the KNXnet/IP server.
    */
   E_VERSION_NOT_SUPPORTED(0x02),

   /**
    * The received sequence number is out of order.
    */
   E_SEQUENCE_NUMBER(0x04),

   /**
    * There is no active data connection with the specified ID.
    */
   E_CONNECTION_ID(0x21),

   /**
    * The requested connection type is not supported by the KNXnet/IP server.
    * This code can occur in a response to a connect request.
    */
   E_CONNECTION_TYPE(0x22),

   /**
    * One or more requested connection options are not supported by the
    * KNXnet/IP server. This code can occur in a response to a connect request.
    */
   E_CONNECTION_OPTION(0x23),

   /**
    * The KNXnet/IP server cannot handle more connections as its maximum number
    * of connections is reached. This code can occur in a response to a connect
    * request.
    */
   E_NO_MORE_CONNECTIONS(0x24),

   /**
    * There is an error concerning the data connection with the specified ID.
    */
   E_DATA_CONNECTION(0x26),

   /**
    * There is an error concerning the KNX subnetwork connection with the
    * specified ID.
    */
   E_KNX_CONNECTION(0x27),

   /**
    * The requested tunneling layer is not supported by the KNXnet/IP server.
    * This code can occur in a response to a connect request.
    */
   E_TUNNELLING_LAYER(0x29),

   /**
    * An unknown status code. This is valid as the list of status codes might
    * not be complete. Treat it as an error.
    */
   E_CUSTOM(0x100);

   /**
    * The numeric status code
    */
   public final int code;

   /**
    * @return the status code for the given value, or {@link #E_CUSTOM} if the
    *         status code is unknown.
    */
   static public StatusCode valueOf(int code)
   {
      for (StatusCode v : values())
      {
         if (v.code == code)
            return v;
      }

      return E_CUSTOM;
   }

   /*
    * Internal constructor.
    */
   private StatusCode(int code)
   {
      this.code = code;
   }
}
