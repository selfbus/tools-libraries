package org.selfbus.sbtools.knxcom.exception;

import java.io.IOException;

/**
 * The basic exception for the knxcomm library.
 */
public class KNXException extends IOException
{
   private static final long serialVersionUID = -74840523465123218L;

   /**
    * Create a KNX exception.
    * 
    * @param message - The detail message.
    */
   public KNXException(String message)
   {
      super(message);
   }

   /**
    * Create a KNX exception.
    * 
    * @param message - The detail message.
    * @param cause - The cause.
    */
   public KNXException(String message, Throwable cause)
   {
      super(message, cause);
   }

   /**
    * Create a KNX exception.
    * 
    * @param cause - The cause.
    */
   public KNXException(Throwable cause)
   {
      super(cause);
   }
}
