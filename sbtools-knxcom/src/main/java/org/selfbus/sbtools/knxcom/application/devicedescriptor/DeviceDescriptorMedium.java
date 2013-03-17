package org.selfbus.sbtools.knxcom.application.devicedescriptor;

/**
 * Known medium types for the {@link DeviceDescriptor0 device descriptor
 * type 0}.
 */
public enum DeviceDescriptorMedium
{
   /**
    * Twisted pair type 1.
    */
   TP1(0),

   /**
    * Power line type 110.
    */
   PL110(1),

   /**
    * Radio frequency.
    */
   RF(2),

   /**
    * Twisted pair type 0.
    */
   TP0(3),

   /**
    * Power line type 132.
    */
   PL132(4),

   /**
    * An unused / custom code.
    */
   CUSTOM_5(5),

   /**
    * An unused / custom code.
    */
   CUSTOM_6(6),

   /**
    * An unused / custom code.
    */
   CUSTOM_7(7),

   /**
    * An unused / custom code.
    */
   CUSTOM_8(8),

   /**
    * An unused / custom code.
    */
   CUSTOM_9(9),

   /**
    * An unused / custom code.
    */
   CUSTOM_10(10),

   /**
    * An unused / custom code.
    */
   CUSTOM_11(11),

   /**
    * An unused / custom code.
    */
   CUSTOM_12(12),

   /**
    * An unused / custom code.
    */
   CUSTOM_13(13),

   /**
    * An unused / custom code.
    */
   CUSTOM_14(14),

   /**
    * An unused / custom code.
    */
   CUSTOM_15(15);

   /**
    * The type code.
    */
   public final int code;

   /**
    * Returns the medium type for a specific code.
    *
    * @param code - the code to lookup
    * @return the medium type.
    *
    * @throws IllegalArgumentException if the code is unknown
    */
   static DeviceDescriptorMedium valueOf(int code)
   {
      for (DeviceDescriptorMedium v : values())
      {
         if (v.code == code)
            return v;
      }

      throw new IllegalArgumentException("No matching medium type found for code " + code);
   }

   /*
    * Internal constructor
    */
   private DeviceDescriptorMedium(int code)
   {
      this.code = code;
   }
}
