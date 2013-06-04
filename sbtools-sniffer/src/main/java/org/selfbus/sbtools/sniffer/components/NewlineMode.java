package org.selfbus.sbtools.sniffer.components;


/**
 * A new line will be started when the newline mode matches the end of
 * the buffer.
 */
public enum NewlineMode
{
   /**
    * No newline detection.
    */
   NONE("", null),

   /**
    * Newline at CR (carriage return).
    */
   CR("CR", new byte[] { 0x0d }),
   
   /**
    * Newline at LF (line feed).
    */
   LF("LF", new byte[] { 0x0a }),

   /**
    * Newline at CR+LF.
    */
   CR_LF("CR+LF", new byte[] { 0x0d, 0x0a }),
   
   /**
    * Newline at FF (form feed).
    */
   FF("FF", new byte[] { 0x0c }),

   /**
    * Newline at ETX (end of transmission).
    */
   ETX("ETX", new byte[] { 0x03 });

   public final String label;
   
   private final byte[] match;

   /**
    * Test if the given bytes up to length match the newline mode.
    *
    * @param bytes - the bytes to match
    * @param length - the number of bytes to match
    */
   public boolean matches(byte[] bytes, int length)
   {
      if (match == null || length < match.length)
         return false;

      int offs = length - match.length;
      for (int i = match.length - 1; i >= 0; --i)
      {
         if (bytes[offs + i] != match[i])
            return false;
      }

      return true;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return label;
   }

   /*
    * Private constructor.
    */
   NewlineMode(String label, byte[] match)
   {
      this.label = label;
      this.match = match;
   }
}
