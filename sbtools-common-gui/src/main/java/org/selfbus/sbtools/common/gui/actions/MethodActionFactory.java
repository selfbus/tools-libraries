package org.selfbus.sbtools.common.gui.actions;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.Action;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.common.exception.SbToolsRuntimeException;
import org.selfbus.sbtools.common.gui.misc.ImageCache;

/**
 * An action factory for actions that call methods of a single object.
 */
public class MethodActionFactory implements ActionFactory
{
   private final Object actionObject;

   /**
    * Create an action factory.
    * 
    * @param actionObject - the object that contains the action methods.
    */
   public MethodActionFactory(Object actionObject)
   {
      Validate.notNull(actionObject, "the action object must be set");
      this.actionObject = actionObject;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Action getAction(final String id)
   {
      try
      {
         final Method method = actionObject.getClass().getMethod(id, ActionEvent.class);
         final ActionMethod actionMethod = method.getAnnotation(ActionMethod.class);
         Validate.notNull(actionMethod,
            "method " + id + " has no @ActionMethod annotation in class " + actionObject.getClass());

         return new BasicAction(actionMethod.name(), ImageCache.getIcon(actionMethod.icon()))
         {
            private static final long serialVersionUID = -4727012180995879416L;

            @Override
            public void actionEvent(ActionEvent event)
            {
               try
               {
                  method.invoke(actionObject, event);
               }
               catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
               {
                  throw new SbToolsRuntimeException(e);
               }
            }
         };
      }
      catch (NoSuchMethodException e)
      {
         throw new SbToolsRuntimeException("Cannot find method " + id + " in class " + actionObject.getClass(), e);
      }
      catch (SecurityException e)
      {
         throw new SbToolsRuntimeException(e);
      }
   }
}
