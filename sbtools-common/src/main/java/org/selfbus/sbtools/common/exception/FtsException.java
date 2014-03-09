package org.selfbus.sbtools.common.exception;

/**
 * A generic FTS exception.
 */
public class FtsException extends Exception
{
   private static final long serialVersionUID = 2499564968009057010L;

   /**
    * Create an empty exception.
    */
   public FtsException()
   {
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    */
   public FtsException(String message)
   {
      super(message);
   }

   /**
    * Create an exception.
    * 
    * @param cause - the cause for the exception.
    */
   public FtsException(Throwable cause)
   {
      super(cause);
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    * @param cause - the cause for the exception.
    */
   public FtsException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
