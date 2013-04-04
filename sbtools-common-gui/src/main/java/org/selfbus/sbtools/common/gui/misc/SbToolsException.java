package org.selfbus.sbtools.common.gui.misc;

/**
 * A generic FTS exception.
 */
public class SbToolsException extends Exception
{
   private static final long serialVersionUID = 249956496800905100L;

   /**
    * Create an empty exception.
    */
   public SbToolsException()
   {
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    */
   public SbToolsException(String message)
   {
      super(message);
   }

   /**
    * Create an exception.
    * 
    * @param cause - the cause for the exception.
    */
   public SbToolsException(Throwable cause)
   {
      super(cause);
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    * @param cause - the cause for the exception.
    */
   public SbToolsException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
