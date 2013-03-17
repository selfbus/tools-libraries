package org.selfbus.sbtools.knxcom.telegram;

/**
 * The types of telegrams on the transport layer.
 */
public enum Transport
{
   /**
    * A transmission to a single physical address or a single group address.
    */
   Individual(false, 0x00, 0xfc, false, true),

   /**
    * A group transmission, or a broadcast (depending on the contents of the
    * telegram's destination address).
    */
   Group(true, 0x00, 0xfc, false, true),

   /**
    * A group transmission of transport data.
    */
   TagGroup(true, 0x01, 0xfc, false, true),

   /**
    * A transmission in connected-data mode.
    */
   Connected(false, 0x40, 0xc0, true, true),

   /**
    * A connection attempt for connected-data mode.
    */
   Connect(false, 0x80, 0xff, false, false),

   /**
    * A disconnect from connected-data mode.
    */
   Disconnect(false, 0x81, 0xff, false, false),

   /**
    * Connected-data mode acknowledge.
    */
   ConnectedAck(false, 0xc2, 0xc3, true, false),

   /**
    * Connected-data mode not-acknowledged.
    */
   ConnectedNak(false, 0xc3, 0xc3, true, false);

   /**
    * The contents of the address-type field (AT).
    */
   public final boolean isGroup;

   /**
    * The contents of the transport-control field.
    */
   public final int value;

   /**
    * The bit-mask for the transport-control field.
    */
   public final int mask;

   /**
    * True if a sequence number is contained.
    */
   public final boolean hasSequence;

   /**
    * True if a telegram with this transport type usually has an application.
    */
   public final boolean hasApplication;

   /**
    * Find the corresponding transport type.
    *
    * @param isGroup - true if the telegram destination is a group address.
    * @param tpci - the contents of the transport control field.
    *
    * @return the transport type for the given values, or null if no transport
    *         type matches.
    */
   public static Transport valueOf(boolean isGroup, int tpci)
   {
      for (Transport t: values())
         if (t.isGroup == isGroup && (tpci & t.mask) == t.value) return t;

      return null;
   }

   /**
    * @return the transport type in human readable form.
    */
   @Override
   public String toString()
   {
      return name();
   }

   /*
    * Internal constructor
    */
   private Transport(boolean isGroup, int value, int mask, boolean hasSequence, boolean hasApplication)
   {
      this.isGroup = isGroup;
      this.value = value;
      this.mask = mask;
      this.hasSequence = hasSequence;
      this.hasApplication = hasApplication;
   }
}
