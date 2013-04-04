package org.selfbus.sbtools.common.gui.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * Factory for {@link BasicAction actions}.
 */
public class BasicActionFactory implements ActionFactory
{
   private static final ActionFactory INSTANCE = new BasicActionFactory();
   
   private final Map<String,BasicAction> actions = new HashMap<String,BasicAction>();
   private String[] packageNames;

   /**
    * Create a action factory.
    * 
    * @see #getInstance()
    */
   public BasicActionFactory()
   {
      setPackageNames(new String[] { "", getClass().getCanonicalName().replaceAll("\\.[^\\.]+$", "") });
   }

   /**
    * @return The action factory instance.
    */
   public static ActionFactory getInstance()
   {
      return INSTANCE;
   }

   /**
    * Set the package names to try when searching for actions.
    * 
    * @param packageNames - the package names
    */
   public void setPackageNames(String... packageNames)
   {
      this.packageNames = packageNames;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BasicAction getAction(final String id)
   {
      Validate.notNull(packageNames, "the action package names must be set");

      synchronized (actions)
      {
         BasicAction action = actions.get(id);
         if (action == null)
         {
            final StringBuilder failedMsg = new StringBuilder();
            String className = null;
            Class<?> clazz = null;

            for (String packageName : packageNames)
            {
               if (packageName.isEmpty())
                  className = id;
               else className = packageName + '.' + id.substring(0, 1).toUpperCase() + id.substring(1);

               try
               {
                  clazz = getClass().getClassLoader().loadClass(className);
                  break;
               }
               catch (ClassNotFoundException e)
               {
                  failedMsg.append(" ").append(className);
               }
            }

            if (clazz == null)
            {
               throw new ActionCreationException("No class found for action " + id + ". Tried " + failedMsg.toString());
            }

            try
            {
               action = (BasicAction) clazz.newInstance();
            }
            catch (InstantiationException e)
            {
               throw new ActionCreationException("Could not instantiate class " + clazz);
            }
            catch (IllegalAccessException e)
            {
               throw new ActionCreationException("Could not instantiate class " + clazz + ": illegal access");
            }

            actions.put(id, action);
         }

         return action;
      }
   }
}
