package org.selfbus.sbtools.common.gui.misc;

/**
 * A {@link RuntimeException} of FTS.
 */
public class SbToolsRuntimeException extends RuntimeException
{
   private static final long serialVersionUID = 1311874561716525815L;

   /**
    * Create an empty exception.
    */
   public SbToolsRuntimeException()
   {
      super();
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    */
   public SbToolsRuntimeException(String message)
   {
      super(message);
   }

   /**
    * Create an exception.
    * 
    * @param cause - the cause for the exception.
    */
   public SbToolsRuntimeException(Throwable cause)
   {
      super(cause);
   }

   /**
    * Create an exception.
    * 
    * @param message - the message.
    * @param cause - the cause for the exception.
    */
   public SbToolsRuntimeException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
