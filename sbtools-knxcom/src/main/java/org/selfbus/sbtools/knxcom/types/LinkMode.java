package org.selfbus.sbtools.knxcom.types;

/**
 * The mode for the bus connection.
 */
public enum LinkMode
{
   /**
    * Link layer mode.
    */
   LinkLayer,

   /**
    * Bus monitor mode. Read only, at least for KNXnet/IP.
    */
   BusMonitor;
}
