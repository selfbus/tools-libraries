package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;
import org.selfbus.sbtools.knxio.model.common.Labeled;

/**
 * Types of communication objects.
 *
 * The {@link #ordinal() ordinal} of the communication object gives the ID for KNX devices, e.g. in
 * the EEPROM communication table.
 */
@XmlType
@XmlEnum
public enum ComObjectSize implements Labeled
{
   /**
    * 1 bit.
    */
   @XmlEnumValue("1 Bit")
   BIT_1(1),

   /**
    * 2 bits.
    */
   @XmlEnumValue("2 Bit")
   BIT_2(2),

   /**
    * 3 bits.
    */
   @XmlEnumValue("3 Bit")
   BIT_3(3),

   /**
    * 4 bits.
    */
   @XmlEnumValue("4 Bit")
   BIT_4(4),

   /**
    * 5 bits.
    */
   @XmlEnumValue("5 Bit")
   BIT_5(5),

   /**
    * 6 bits.
    */
   @XmlEnumValue("6 Bit")
   BIT_6(6),

   /**
    * 7 bits.
    */
   @XmlEnumValue("7 Bit")
   BIT_7(7),

   /**
    * 1 byte.
    */
   @XmlEnumValue("1 Byte")
   BYTES_1(8),

   /**
    * 1 byte.
    */
   @XmlEnumValue("2 Bytes")
   BYTES_2(16),

   /**
    * 3 bytes.
    */
   @XmlEnumValue("3 Bytes")
   BYTES_3(24),

   /**
    * 4 bytes float.
    */
   @XmlEnumValue("4 Bytes")
   BYTES_4(32),

   /**
    * 6 bytes.
    */
   @XmlEnumValue("6 Bytes")
   BYTES_6(48),

   /**
    * 8 bytes.
    */
   @XmlEnumValue("8 Bytes")
   BYTES_8(64),

   /**
    * 10 bytes.
    */
   @XmlEnumValue("10 Bytes")
   BYTES_10(80),

   /**
    * 14 bytes.
    */
   @XmlEnumValue("14 Bytes")
   BYTES_14(112),

   /**
    * 1..14 bytes.
    */
   @XmlEnumValue("LegacyVarData")
   VARBYTES_14(112, "0..14 Bytes");

   private final String label, vdName;
   private final int bitLength;

   /**
    * @return The Id
    */
   public int getId()
   {
      return ordinal();
   }

   /**
    * @return The label
    */
   @Override
   public String getLabel()
   {
      return label;
   }

   /**
    * @return The name
    */
   public String getVdName()
   {
      return vdName;
   }

   /**
    * @return The size in bits
    */
   public int getBitSize()
   {
      return bitLength;
   }

   /**
    * Get the enum value from the ordinal number.
    *
    * @param ordinal The ordinal number to process.
    * @return The enum value.
    */
   static public ComObjectSize valueOf(int ordinal)
   {
      for (ComObjectSize o : values())
      {
         if (o.ordinal() == ordinal)
            return o;
      }

      return null;
   }

   /**
    * Get the enum value for the specified bit size.
    *
    * @param bitSize The bit size
    * @return The enum value, null if no matching value was found.
    */
   static public ComObjectSize valueForBitSize(int bitSize)
   {
      for (ComObjectSize o : ComObjectSize.values())
      {
         if (o.getBitSize() == bitSize)
            return o;
      }

      return null;
   }

   /**
    * Constructor.
    *
    * @param bitLength - the number of bits of the object type.
    */
   private ComObjectSize(int bitLength)
   {
      this.label = I18n.getMessage("ComObjectSize." + name());
      this.vdName = bitLength > 7 ? Integer.toString(bitLength >> 3) + " Byte" : Integer.toString(bitLength) + " Bit";
      this.bitLength = bitLength;
   }

   /**
    * Constructor.
    *
    * @param bitLength - the number of bits of the object type.
    */
   private ComObjectSize(int bitLength, String vdName)
   {
      this.label = I18n.getMessage("ComObjectSize." + name());
      this.vdName = vdName;
      this.bitLength = bitLength;
   }
}
