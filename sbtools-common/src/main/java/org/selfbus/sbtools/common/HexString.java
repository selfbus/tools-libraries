package org.selfbus.sbtools.common;

/**
 * Utility methods to convert byte arrays from / to hex strings in the format
 * "12 a0 01 00 7c".
 */
public final class HexString
{
   /**
    * Convert a hex string to a byte array. The hex string is expected to have
    * the format "12 a0 01 00 7c". The hex numbers may be one or two hex digits
    * in size.
    *
    * @param str - the string to process
    *
    * @return the byte array
    */
   public static byte[] valueOf(final String str)
   {
      final String[] values = str.split("  *");
      final byte[] data = new byte[values.length];

      if (values.length == 1 && values[0].equals(""))
         return new byte[0];

      for (int i = 0; i < values.length; ++i)
         data[i] = (byte) Integer.parseInt(values[i], 16);

      return data;
   }

   /**
    * Convert the byte array into a hex string in the format "12 a0 01 00 7c".
    *
    * @param data - the byte array to process.
    *
    * @return the hex string
    */
   public static String toString(byte[] data)
   {
      return toString(data, 0, data.length);
   }

   /**
    * Convert a part of the byte array into a hex string in the format
    * "12 a0 01 00 7c".
    *
    * @param data - the byte array to process.
    * @param start - the index of the first byte to process.
    * @param length - the number of bytes to process.
    *
    * @return the hex string
    */
   public static String toString(byte[] data, int start, int length)
   {
      final StringBuffer sb = new StringBuffer(data.length * 3);

      for (int i = start; length > 0; ++i, --length)
      {
         if (i > start)
            sb.append(String.format(" %02x", data[i] & 255, 16));
         else sb.append(String.format("%02x", data[i] & 255, 16));
      }

      return sb.toString();
   }

   /**
    * Disabled constructor.
    */
   private HexString()
   {
   }
}
