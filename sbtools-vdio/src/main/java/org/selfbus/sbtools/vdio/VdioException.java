package org.selfbus.sbtools.vdio;

/**
 * An exception for VDIO problems.
 */
public class VdioException extends Exception
{
   private static final long serialVersionUID = -1540145948157465061L;

   public VdioException()
   {
      super();
   }

   public VdioException(String message)
   {
      super(message);
   }

   public VdioException(Throwable cause)
   {
      super(cause);
   }

   public VdioException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public VdioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
   {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
