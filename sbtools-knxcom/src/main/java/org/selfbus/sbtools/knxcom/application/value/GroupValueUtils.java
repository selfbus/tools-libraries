package org.selfbus.sbtools.knxcom.application.value;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.common.HexString;

/**
 * Utility functions for converting group values to/from byte arrays.
 */
public final class GroupValueUtils
{
   /**
    * Convert the value object to a byte array.
    * 
    * @param value - the value to convert
    * @param type - the type of the data
    * 
    * @return The byte array containing the value. The first byte contains the APCI byte, if the
    *         data type uses the APCI byte.
    * 
    * @throws IllegalArgumentException if the type is not supported or any argument is null
    */
   public static byte[] toBytes(Object value, DataPointType type)
   {
      if (value == null)
         throw new IllegalArgumentException("the value must not be null");
      if (type == null)
         throw new IllegalArgumentException("the type must not be null");

      Class<?> valueClass = type.getValueClass();
      if (valueClass != null && value.getClass() != valueClass)
      {
         throw new IllegalArgumentException("the value is of the type " + value.getClass()
            + " but must be of the type " + valueClass);
      }

      switch (type)
      {
         case BOOL:
            return new byte[] { ((Boolean) value) ? (byte) 1 : (byte) 0 };

         case UNSIGNED_BYTE:
            return new byte[] { (byte) ((Integer) value).intValue() };

         case UNSIGNED_SHORT:
            return intToBytes((Integer) value, 2);

         case SHORT_FLOAT:
            float f = 100f * (float) value; 
            int exp = 0;
            while (f > 2047 || f < -2048)
            {
               ++exp;
               f /= 2;
            }
            Validate.isTrue(exp <= 15, "value too large for SHORT_FLOAT");
            int ival = (int) f;
            if (ival < 0) exp |= 16;
            return intToBytes((exp << 11) | (ival & 2047), 2);

         default:
            throw new IllegalArgumentException("Unsupported datapoint type " + type + " for value " + value);
      }
   }

   /**
    * Convert the byte array to a value object.
    * 
    * @param data - the byte array to convert
    * @param type - the type of the data
    * 
    * @return A suitable object with the value, e.g. Boolean for {@link DataPointType#BOOL}.
    * 
    * @throws IllegalArgumentException if the type is not supported or any argument is null
    */
   public static Object fromBytes(byte[] data, DataPointType type)
   {
      if (data == null)
         throw new IllegalArgumentException("the value must not be null");
      if (type == null)
         throw new IllegalArgumentException("the type must not be null");

      switch (type)
      {
         case BOOL:
            return Boolean.valueOf(data[0] != (byte) 0);

         case UNSIGNED_BYTE:
            return Integer.valueOf(data[1] & 255);

         case UNSIGNED_SHORT:
            return Integer.valueOf(bytesToInt(data, 1, 2));

         case SHORT_FLOAT:
            int ival = bytesToInt(data, 1, 2);
            int m = ival & 2047;
            if ((ival & 0x8000) == 0x8000) m = -m;
            int exp = (ival >> 11) & 15;
            return m * 0.01f * (1 << exp);

         default:
            throw new IllegalArgumentException("Unsupported datapoint type " + type + " for raw-data "
               + HexString.toString(data));
      }
   }

   /**
    * Convert the string to a value object.
    * 
    * @param str - the string to convert
    * @param type - the type of the data
    * 
    * @return A suitable object with the value, e.g. Boolean for {@link DataPointType#BOOL}.
    * 
    * @throws IllegalArgumentException if the type is not supported or any argument is null
    */
   public static Object fromString(String str, DataPointType type)
   {
      if (str == null)
         throw new IllegalArgumentException("the string must not be null");
      if (type == null)
         throw new IllegalArgumentException("the type must not be null");

      switch (type)
      {
         case BOOL:
            if (str.matches("^\\d+$"))
               return Integer.valueOf(str) != 0;
            return Boolean.valueOf(str);

         case UNSIGNED_BYTE:
         case UNSIGNED_SHORT:
            return Integer.valueOf(str);

         default:
            throw new IllegalArgumentException("Unsupported datapoint type " + type + " for string \"" + str + "\"");
      }
   }

   /**
    * Assemble an integer number from count bytes at an offset. The returned number is e.g. for
    * count==2: (data[offset] << 8) + data[offset+1].
    * 
    * @param data - the byte data to process.
    * @param offset - the index of the first byte to process.
    * @param count - the number of bytes to process.
    * 
    * @return The assembled number.
    */
   public static int bytesToInt(byte[] data, int offset, int count)
   {
      int result = 0;

      while (count > 0)
      {
         result = (result << 8) | (data[offset++] & 255);
         --count;
      }

      return result;
   }

   /**
    * Convert an integer value into an array of count bytes.
    * 
    * @param value - the value to convert
    * @param count - the number of bytes to create
    * @return The created byte array
    */
   public static byte[] intToBytes(int value, int count)
   {
      byte[] result = new byte[count];

      for (int i = count - 1; i >= 0; --i)
      {
         result[i] = (byte) (value & 255);
         value >>= 8;
      }

      return result;
   }
}
