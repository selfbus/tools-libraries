package org.selfbus.sbtools.knxcom.link.serial;

/**
 * An exception that is thrown when there are fatal errors with the serial port.
 */
public class SerialPortException extends RuntimeException
{
   private static final long serialVersionUID = -7922219584957802545L;

   /**
    * Create an exception.
    * 
    * @param message - The detail message.
    */
   public SerialPortException(String message)
   {
      super(message);
   }

   /**
    * Create an exception.
    * 
    * @param message - The detail message.
    * @param cause - The cause.
    */
   public SerialPortException(String message, Throwable cause)
   {
      super(message, cause);
   }

   /**
    * Create an exception.
    * 
    * @param cause - The cause.
    */
   public SerialPortException(Throwable cause)
   {
      super(cause);
   }
}
