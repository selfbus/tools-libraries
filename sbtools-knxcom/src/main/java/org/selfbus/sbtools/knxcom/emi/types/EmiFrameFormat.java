package org.selfbus.sbtools.knxcom.emi.types;

public enum EmiFrameFormat
{
   /**
    * RAW means the raw format on the medium
    */
   RAW,

   /**
    * Default frame format for cEMI:
    * ctrl:        2 octets
    * src:         2 octets
    * dst:         2 octets
    * npdu length: 1 octet
    * npdu data:   [length] octets
    */
   cEMI;
}
