package org.selfbus.sbtools.knxcom.application.interfaceobject;


/**
 * Type of an interface object.
 */
public enum InterfaceObjectType
{
   /**
    * Device object.
    */
   DEVICE,

   /**
    * Address table object.
    */
   ADDR_TABLE,

   /**
    * Association table object.
    */
   ASSOC_TABLE,

   /**
    * Application program object.
    */
   APPLICATION,

   /**
    * Interface program object.
    */
   INTERFACE_PROGRAM,

   /**
    * KNX-object association table object.
    */
   KNX_OBJECT_ASSOC_TABLE,

   /**
    * Router object.
    */
   ROUTER,

   /**
    * LTE address routing table object.
    */
   LTE_ADDR_ROUTING_TABLE,

   /**
    * cEMI server object.
    */
   CEMI_SERVER,

   /**
    * Group object table object.
    */
   GROUP_OBJECT_TABLE,

   /**
    * Polling master.
    */
   POLLING_MASTER,

   /**
    * KNXnet/IP parameter object.
    */
   KNXNET_PARAMS,

   /**
    * Reserved.
    */
   RESERVED,

   /**
    * File server object.
    */
   FILE_SERVER;

   /**
    * Get an interface object type by number.
    */
   public static InterfaceObjectType valueOf(int ordinal)
   {
      for (InterfaceObjectType t : values())
      {
         if (t.ordinal() == ordinal)
            return t;
      }

      return null;
   }
}
