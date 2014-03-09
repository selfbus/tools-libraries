package org.selfbus.sbtools.common;

/**
 * A variation of the ROT-13 algorithm for string obfuscation. This is no
 * password security, of course.
 */
public final class Rot13
{
   /**
    * A variation of the ROT-13 algorithm for string obfuscation. This is no
    * password security, of course.
    * 
    * @param str - the string to rotate.
    * @return The rotated string.
    */
   public static String rotate(String str)
   {
      final int len = str.length();
      StringBuilder result = new StringBuilder(len);

      for (int i = 0; i < len; ++i)
      {
         char ch = str.charAt(i);
         if ((ch >= 'a' && ch <= 'm') || (ch >= 'A' && ch <= 'M'))
            ch += 13;
         else if ((ch >= 'n' && ch <= 'z') || (ch >= 'N' && ch <= 'Z'))
            ch -= 13;
         else if (ch >= '0' && ch <= '4')
            ch += 5;
         else if (ch >= '5' && ch <= '9')
            ch -= 5;
         result.append(ch);
      }

      return result.toString();
   }

   /**
    * Disabled constructor.
    */
   private Rot13()
   {
   }
}
