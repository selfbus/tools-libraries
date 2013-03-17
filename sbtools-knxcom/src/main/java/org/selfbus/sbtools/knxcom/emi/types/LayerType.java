package org.selfbus.sbtools.knxcom.emi.types;

/**
 * PEI Layer types.
 */
public enum LayerType
{
   /**
    * Unused / discard.
    */
   NONE(0),

   /**
    * Link layer.
    */
   LL(1),

   /**
    * Network layer.
    */
   NL(2),

   /**
    * Transport layer, group oriented.
    */
   TLG(3),

   /**
    * Transport layer, connection oriented.
    */
   TLC(4),

   /**
    * Transport layer, local.
    */
   TLL(5),

   /**
    * Application layer.
    */
   AL(6),

   /*
    * ALG - Group oriented part of the application layer
    */
   // ALG(6),

   /**
    * Application layer, management part.
    */
   MAN(7),

   /**
    * Physical external interface layer.
    */
   PEI(8),

   /**
    * Application running in the BAU. If the User layer is not running, the
    * messages are directed to the PEI.
    */
   USR(9),

   /**
    * Reserved, do not use.
    */
   _RESERVED(10);

   /**
    * The layer id.
    */
   public final int id;

   /*
    * Internal constructor.
    */
   private LayerType(int id)
   {
      this.id = id;
   }

   /**
    * Get the layer type for a layer id.
    * 
    * @param id - the layer id to lookup.
    * 
    * @return the layer type for the given layer id.
    *
    * @throws IllegalArgumentException if the layer id is invalid.
    */
   static public LayerType valueOf(int id)
   {
      for (LayerType e : values())
         if (e.id == id)
            return e;

      throw new IllegalArgumentException("None layer 0x" + Integer.toHexString(id));
   }
}
