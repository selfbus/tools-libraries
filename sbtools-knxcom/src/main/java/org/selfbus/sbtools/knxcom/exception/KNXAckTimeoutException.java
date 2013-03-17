package org.selfbus.sbtools.knxcom.exception;

/**
 * Timeout while waiting for an acknowledgment.
 */
public final class KNXAckTimeoutException extends KNXException
{
   private static final long serialVersionUID = -427540522334689128L;

   /**
    * Create an acknowledgment timeout exception. 
    * 
    * @param message - The detail message.
    */
   public KNXAckTimeoutException(String message)
   {
      super(message);
   }

   /**
    * Create an acknowledgment timeout exception. 
    * 
    * @param message - The detail message.
    * @param cause - The cause.
    */
   public KNXAckTimeoutException(String message, Throwable cause)
   {
      super(message, cause);
   }

   /**
    * Create an acknowledgment timeout exception. 
    * 
    * @param cause - The cause.
    */
   public KNXAckTimeoutException(Throwable cause)
   {
      super(cause);
   }
}
