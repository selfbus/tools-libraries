package org.selfbus.sbtools.knxcom.application.value;

import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.selfbus.sbtools.knxcom.application.value.TestGroupValueUtils;

/**
 * Data point types for group value data exchange, e.g. with
 * 
 * {@link ApplicationType#GroupValue_Read} or {@link ApplicationType#GroupValue_Write} telegrams.
 * 
 * TODO Not complete!
 * 
 * @see TestGroupValueUtils
 */
public enum DataPointType
{
   /**
    * No data point type. Internal use.
    */
   NONE(0),

   /**
    * 1 bit data point type. Used for switch, boolean, enable, open/close, alarm, ...
    * <p>
    * 0 - off 1 - on
    */
   BOOL(1, Boolean.class, true),

   /**
    * 2 bit data point type. This is a {@link #BOOL boolean} type with an additional control bit.
    * Used for switch control, step control, ...
    * <p>
    * 00 - no control 01 - no control 10 - control, off 11 - control, on
    */
   BOOL_CONTROL(2, null, true),

   /**
    * 4 bit data point type: dimming, control blinds.
    * <p>
    * Bit 3 - direction: 0 decrease, 1 increase. <br>
    * Bit 0..2 - step code: 0 is break, >0 is 2^(value-1) intervals.
    */
   DIMMING(4, Integer.class, true),

   /**
    * Character (1 byte). 7 bit ASCII or 8 bit ISO-8859-1
    */
   CHAR(8, Character.class),

   /**
    * 8-bit unsigned value. For scaling, angle, percent, etc.
    */
   UNSIGNED_BYTE(8, Integer.class),

   /**
    * 8-bit signed, relative, value. For percent, value difference.
    */
   SIGNED_BYTE(8, Integer.class),

   /**
    * 2 byte unsigned value. For counter, time period, length, etc.
    */
   UNSIGNED_SHORT(16, Integer.class),

   /**
    * 2 byte signed value. For counter, percent, delta time, rotation angle, etc.
    */
   SIGNED_SHORT(16, Integer.class),

   /**
    * 2 byte float value. For temperature, humidity, lux, etc.
    * <p>
    * Bit 15 - sign <br>
    * Bit 14..11 - exponent <br>
    * Bit 10..0 - mantissa, in two's complement notation
    * <p>
    * float_value = 0.01 * mantissa * 2 ^ exponent
    */
   SHORT_FLOAT(16, Float.class),

   /**
    * Time (3 byte).
    * <p>
    * Byte 0: 3 bit day, 5 bit hour <br>
    * Byte 1: minutes <br>
    * Byte 2: seconds
    */
   TIME(24),

   /**
    * Date (3 byte).
    * <p>
    * Byte 0: day, byte 1: month, byte 2: year (0..99)
    */
   DATE(24),

   /**
    * Unsigned long value (4 bytes).
    */
   UNSIGNED_LONG(32, Long.class),

   /**
    * Signed long value (4 bytes).
    */
   SIGNED_LONG(32, Long.class),

   /**
    * Float value (4 bytes). Encoded in the IEEE 754 floating point format.
    */
   FLOAT(32, Float.class),

   /**
    * Access data (4 bytes).
    */
   ACCESS(32),

   /**
    * String. 14 bytes, unused bytes are zero.
    */
   STRING(14 * 8, String.class),

   /**
    * Scene number (1 byte)
    * <p>
    * 6 bit scene number, the 2 high bits are reserved.
    */
   SCENE_NUMBER(8),

   /**
    * Scene control (1 byte).
    * <p>
    * Bit 7: control - 0 to activate the scene, 1 - to learn the scene <br>
    * Bit 6: reserved <br>
    * Bit 5..0: scene number
    */
   SCENE_CONTROL(8),

   /**
    * Date time (8 bytes).
    * 
    * Byte 0: year <br>
    * Byte 1: month <br>
    * Byte 2: day of month <br>
    * Byte 3: 3 bit day of week + 5 bit hour <br>
    * Byte 4: minutes <br>
    * Byte 5: seconds <br>
    * Byte 6: status <br>
    * Byte 7: bit 7: clock quality, bits 6..0 reserved
    */
   DATE_TIME(64),

   /**
    * Enum (1 byte). Used for various enumerations.
    */
   ENUM(8),

   /**
    * General status (1 byte)
    * <p>
    * Bit 7..5: reserved, <br>
    * bit 4: datapoint alarm status not acknowledged, <br>
    * bit 3: datapoint is in alarm, <br>
    * bit 2: datapoint main value is overridden, <br>
    * bit 1: datapoint main value is corrupted due to failure, <br>
    * bit 0: datapoint value is out of service.
    */
   STATUS(8),

   /**
    * Device control (1 byte)
    * <p>
    * Bit 7..3: reserved, <br>
    * bit 2: verify mode enabled, <br>
    * bit 1: datagram with the own address as source has been received, <br>
    * bit 0: user application is stopped.
    */
   DEVICE_CONTROL(8),

   /**
    * 2 bit enum.
    */
   SHORT_ENUM(2),

   /**
    * String of variable length. The string is zero terminated. No length information is contained
    * in the telegram.
    */
   VAR_STRING(-1, String.class),

   /**
    * Scene info (1 byte).
    * <p>
    * Bit 7: reserved <br>
    * Bit 6: scene is active flag <br>
    * Bit 5..0: scene number
    */
   SCENE_INFO(8),

   /**
    * UTF-8 String of variable length. The string is zero terminated. No length information is
    * contained in the telegram.
    */
   UTF8_STRING(-1, String.class),

   /**
    * Combined on/off info (4 bytes).
    * <p>
    * 16 bit on/off for output 1..16, <br>
    * 16 bit mask bit for on/off output 1..16.
    */
   COMBINED_INFO_ONOFF(32),

   //
   // TODO not complete
   //

   /**
    * No data point type. FTS internal use.
    */
   _END(0);

   private final int bits;
   private final boolean usesApci;
   private final Class<?> valueClass;

   /**
    * @return the number of bits that the data point type requires. -1 if variable.
    */
   public int getBits()
   {
      return bits;
   }

   /**
    * @return True if the data type uses the APCI bits for the first byte, false if not.
    */
   public boolean isUsingApci()
   {
      return usesApci;
   }

   /**
    * @return The class for Java value objects for the type, or null if no default Java class
    *         exists.
    * 
    * @see TestGroupValueUtils
    */
   public Class<?> getValueClass()
   {
      return valueClass;
   }

   /**
    * @return A new value object for this type, or null if no default Java class exists.
    * 
    * @throws RuntimeException in case of problems with the object creation.
    */
   public Object newValueObject()
   {
      if (valueClass == Boolean.class)
         return Boolean.FALSE;
      if (valueClass == Integer.class)
         return Integer.valueOf(0);
      if (valueClass == Float.class)
         return Float.valueOf(0f);

      try
      {
         return valueClass.newInstance();
      }
      catch (InstantiationException | IllegalAccessException e)
      {
         throw new RuntimeException(e.getMessage(), e);
      }
   }

   /*
    * Internal constructor.
    */
   private DataPointType(int bits)
   {
      this.bits = bits;
      this.usesApci = false;
      this.valueClass = null;
   }

   /*
    * Internal constructor.
    */
   private DataPointType(int bits, Class<?> valueClass)
   {
      this.bits = bits;
      this.usesApci = false;
      this.valueClass = valueClass;
   }

   /*
    * Internal constructor.
    */
   private DataPointType(int bits, Class<?> valueClass, boolean usesAcpi)
   {
      this.bits = bits;
      this.usesApci = usesAcpi;
      this.valueClass = valueClass;
   }
}
