package org.selfbus.sbtools.knxcom.exception;

/**
 * Thrown when a closed port is used.
 */
public final class KNXPortClosedException extends KNXException
{
   private static final long serialVersionUID = -127640533974652158L;

   /**
    * Create a port is closed exception.
    * 
    * @param message - The detail message.
    */
   public KNXPortClosedException(String message)
   {
      super(message);
   }

   /**
    * Create a port is closed exception.
    * 
    * @param message - The detail message.
    * @param cause - The cause.
    */
   public KNXPortClosedException(String message, Throwable cause)
   {
      super(message, cause);
   }

   /**
    * Create a port is closed exception.
    * 
    * @param cause - The cause.
    */
   public KNXPortClosedException(Throwable cause)
   {
      super(cause);
   }
}
