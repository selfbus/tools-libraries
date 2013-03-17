package org.selfbus.sbtools.knxcom.telegram;

import java.io.IOException;

/**
 * The exception indicates that a field of an EMI message or telegram
 * contains an invalid value.
 */
public final class InvalidDataException extends IOException
{
   private static final long serialVersionUID = -427540522334652158L;

   /**
    * The illegal contents.
    */
   public final int contents;

   /**
    * Create a EMI/telegram invalid field contents exception.
    */
   public InvalidDataException(String message, int contents)
   {
      super(message);
      this.contents = contents;
   }

   /**
    * Create a EMI/telegram invalid field contents exception.
    */
   public InvalidDataException(String message, int contents, Throwable cause)
   {
      super(message, cause);
      this.contents = contents;
   }

   /**
    * @return the exception in human readable form.
    */
   @Override
   public String toString()
   {
      return super.toString() + ", contents=0x" + Integer.toHexString(contents);
   }
}
