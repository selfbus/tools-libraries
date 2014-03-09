package org.selfbus.sbtools.common.gui.misc;

import java.util.HashSet;
import java.util.Set;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A helper class that gets the class names for a bunch of custom look&feels.
 * The look&feels can then be loaded and installed.
 */
public final class LookAndFeelManager
{
   private static final Logger LOGGER = LoggerFactory.getLogger(LookAndFeelManager.class);
   private final Set<Class<? extends LookAndFeel>> lafClasses = new HashSet<Class<? extends LookAndFeel>>();

   /**
    * Set the default look and feel for the current platform.
    */
   static public void setDefaultLookAndFeel()
   {
      for (String lafName : new String[]
      { 
         "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
         "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
      })
      {
         try
         {
            UIManager.setLookAndFeel(lafName);
            break;
         }
         catch (Exception e)
         {
            try
            {
               // use the system look and feel instead
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e1)
            {
               // Never mind if this does not work
            }
         }
      }
   }

   /**
    * Add a look&feel by it's class-name.
    * 
    * @param lookAndFeelClassName - the name of the look&feel class to add.
    * 
    * @throws ClassNotFoundException if the look&feel class cannot be loaded
    */
   @SuppressWarnings("unchecked")
   public void add(String lookAndFeelClassName) throws ClassNotFoundException
   {
      final Class<? extends LookAndFeel> lafClass = (Class<? extends LookAndFeel>) getClass().getClassLoader()
            .loadClass(lookAndFeelClassName);

      lafClasses.add(lafClass);
   }

   /**
    * Install all look&feel classes that were previously added with
    * {@link #add(String)}. Must be called from within Swing's UI thread.
    */
   public void install()
   {
      for (Class<? extends LookAndFeel> lafClass : lafClasses)
      {
         try
         {
            LOGGER.info("Installing look&feel plugin: " + lafClass.getSimpleName());
            UIManager.installLookAndFeel(lafClass.newInstance().getName(), lafClass.getCanonicalName());
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }
}
