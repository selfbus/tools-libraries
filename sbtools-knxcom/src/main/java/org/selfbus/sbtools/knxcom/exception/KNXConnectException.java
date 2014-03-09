package org.selfbus.sbtools.knxcom.exception;

/**
 * The exception indicates that the connection to the EIB bus interface failed
 * for some reason.
 */
public final class KNXConnectException extends KNXException
{
   private static final long serialVersionUID = -427540522334652158L;

   /**
    * Create a EIB bus-interface connect-failed exception.
    * 
    * @param message - The detail message.
    */
   public KNXConnectException(String message)
   {
      super(message);
   }

   /**
    * Create a EIB bus-interface connect-failed exception.
    * 
    * @param message - The detail message.
    * @param cause - The cause.
    */
   public KNXConnectException(String message, Throwable cause)
   {
      super(message, cause);
   }

   /**
    * Create a EIB bus-interface connect-failed exception.
    * 
    * @param cause - The cause.
    */
   public KNXConnectException(Throwable cause)
   {
      super(cause);
   }
}
