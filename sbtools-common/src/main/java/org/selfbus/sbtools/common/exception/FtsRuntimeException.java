package org.selfbus.sbtools.common.exception;

/**
 * A {@link RuntimeException} of FTS.
 */
public class FtsRuntimeException extends RuntimeException
{
   private static final long serialVersionUID = 7341874561716525815L;

   /**
    * Create an empty exception.
    */
   public FtsRuntimeException()
   {
      super();
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    */
   public FtsRuntimeException(String message)
   {
      super(message);
   }

   /**
    * Create an exception.
    * 
    * @param cause - the cause for the exception.
    */
   public FtsRuntimeException(Throwable cause)
   {
      super(cause);
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    * @param cause - the cause for the exception.
    */
   public FtsRuntimeException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
