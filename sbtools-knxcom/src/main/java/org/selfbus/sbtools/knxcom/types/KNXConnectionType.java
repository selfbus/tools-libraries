package org.selfbus.sbtools.knxcom.types;

/**
 * Types for KNX/EIB bus connections.
 */
public enum KNXConnectionType
{
   /**
    * No bus connection.
    */
   NONE("No connection"),

   /**
    * Serial port connection, using FT1.2
    */
   SERIAL_FT12("Serial FT1.2"),

   /**
    * KNXnet/IP network connection
    */
   KNXNET_IP("KNXnet/IP");

   /**
    * A human readable, English label.
    */
   public final String label;

   /*
    * Internal constructor.
    */
   private KNXConnectionType(String label)
   {
      this.label = label;
   }
}
