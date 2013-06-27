package org.selfbus.sbtools.prodedit.model.enums;

import org.selfbus.sbtools.prodedit.internal.I18n;

/**
 * Types of communication objects.
 * 
 * The {@link #ordinal() ordinal} of the communication object gives the ID for
 * KNX devices, e.g. in the EEPROM communication table.
 */
public enum ObjectType
{
   /**
    * 1 bit.
    */
   BITS_1(1),

   /**
    * 2 bits.
    */
   BITS_2(2),

   /**
    * 3 bits.
    */
   BITS_3(3),

   /**
    * 4 bits.
    */
   BITS_4(4),

   /**
    * 5 bits.
    */
   BITS_5(5),

   /**
    * 6 bits.
    */
   BITS_6(6),

   /**
    * 7 bits.
    */
   BITS_7(7),

   /**
    * 1 byte.
    */
   BYTES_1(8),

   /**
    * 1 byte.
    */
   BYTES_2(16),

   /**
    * 3 bytes.
    */
   BYTES_3(24),

   /**
    * 4 bytes float.
    */
   BYTES_4(32),

   /**
    * 6 bytes.
    */
   BYTES_6(48),

   /**
    * 8 bytes.
    */
   BYTES_8(64),

   /**
    * 10 bytes.
    */
   BYTES_10(80),

   /**
    * 14 bytes.
    */
   BYTES_14(112),

   /**
    * 1..14 bytes.
    */
   VARBYTES_14(112);

   private final String name;
   private final int bitLength;

   /**
    * @return the id
    */
   public int getId()
   {
      return ordinal();
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * @return the bitLength
    */
   public int getBitLength()
   {
      return bitLength;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return name;
   }

   /**
    * Get the object type from the object type's ordinal number.
    * 
    * @param ordinal - the ordinal number to process.
    * @return The object type.
    */
   static public ObjectType valueOf(int ordinal)
   {
      for (ObjectType t : values())
      {
         if (t.ordinal() == ordinal)
            return t;
      }

      return null;
   }

   /**
    * Constructor.
    * 
    * @param bitLength - the number of bits of the object type.
    */
   private ObjectType(int bitLength)
   {
      this.name = I18n.getMessage(name());
      this.bitLength = bitLength;
   }
}
