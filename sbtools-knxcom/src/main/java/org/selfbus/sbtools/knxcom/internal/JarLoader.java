package org.selfbus.sbtools.knxcom.internal;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Load a JAR by dynamically extending the class-path.
 */
public final class JarLoader
{
   /**
    * Load JARs by dynamically extending the class-path.
    * 
    * @param pathNames - the paths of the JARs to load.
    * @throws Exception on error
    */
   public static void loadJar(String[] pathNames) throws Exception
   {
      try
      {
         final Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
         addURL.setAccessible(true);
   
         final ClassLoader cl = ClassLoader.getSystemClassLoader();

         for (int i = 0; i < pathNames.length; ++i)
            addURL.invoke(cl, new Object[] { new URL("file", null, pathNames[i]) });

         addURL.setAccessible(false);
      }
      catch (NoSuchMethodException e)
      {
         throw new Exception(e);
      }
   }
}
