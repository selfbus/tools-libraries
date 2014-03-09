package org.selfbus.sbtools.knxcom.types;

import org.selfbus.sbtools.knxcom.internal.I18n;

/**
 * Types for KNX/EIB bus connections.
 */
public enum LinkType
{
   /**
    * No bus connection.
    */
   NONE,

   /**
    * Serial port connection, using FT1.2
    */
   SERIAL_FT12,

   /**
    * KNXnet/IP network connection
    */
   KNXNET_IP;

   /**
    * A human readable, English label.
    */
   public final String label;

   /*
    * Internal constructor.
    */
   private LinkType()
   {
      this.label = I18n.getMessage("LinkType." + name().toLowerCase());
   }
}
