package org.selfbus.sbtools.common;

/**
 * Utility functions for byte handling.
 */
public final class ByteUtils
{
   /**
    * Assemble an integer number from count bytes at an offset. The returned
    * number is e.g. for count==2: (data[offset] << 8) + data[offset+1].
    *
    * @param data - the byte data to process.
    * @param offset - the index of the first byte to process.
    * @param count - the number of bytes to process.
    *
    * @return The assembled number.
    */
   public static int toInteger(byte[] data, int offset, int count)
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
    * Disabled constructor.
    */
   private ByteUtils()
   {
   }
}
