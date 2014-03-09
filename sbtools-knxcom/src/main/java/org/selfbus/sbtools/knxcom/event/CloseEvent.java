package org.selfbus.sbtools.knxcom.event;

import java.util.EventObject;

/**
 * The link was closed.
 */
public class CloseEvent extends EventObject
{
   private static final long serialVersionUID = -3360748102724381256L;

   /**
    * Create a new close event.
    * 
    * @param source - the source of the event.
    */
   public CloseEvent(Object source)
   {
      super(source);
   }
}
