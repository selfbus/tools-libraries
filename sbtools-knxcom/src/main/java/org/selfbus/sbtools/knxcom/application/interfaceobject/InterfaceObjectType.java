package org.selfbus.sbtools.knxcom.application.interfaceobject;

/**
 * Types of KNX / BCU interface objects.
 */
public enum InterfaceObjectType
{
   /**
    * Group address table.
    */
   AddressTableObject(1),

   

   ;

   final int id;
   
   /*
    * Internal constructor
    */
   private InterfaceObjectType(int id)
   {
      this.id = id;
   }
}
