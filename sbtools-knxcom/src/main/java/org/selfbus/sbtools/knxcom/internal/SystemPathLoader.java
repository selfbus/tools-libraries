package org.selfbus.sbtools.knxcom.internal;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Load a library by changing Java's system path and using
 * {@link System#loadLibrary}.
 * 
 * To make it clear: this is a hack. But it is the only way to get some JARs to
 * find their native libraries, like for RXTX.
 */
public final class SystemPathLoader
{
   /**
    * Load a library by changing Java's system path and using
    * {@link System#loadLibrary}.
    * 
    * @param libPath - the path to the library that shall be loaded.
    * @param libName - the name of the library, without system specific extensions like ".dll"
    * @throws Exception on error
    */
   public static void loadLibrary(String libPath, String libName) throws Exception
   {
      // Reset the "sys_paths" field of the ClassLoader to null.
      Class<?> clazz = ClassLoader.class;
      Field field;
      try
      {
         field = clazz.getDeclaredField("sys_paths");
         boolean accessible = field.isAccessible();
         if (!accessible)
            field.setAccessible(true);
         Object original = field.get(clazz);

         // Reset the "sys_paths" to null so that whenever "System.loadLibrary"
         // is called, it
         // will be reconstructed with the changed value.
         field.set(clazz, null);

         try
         {
            System.setProperty("java.library.path", new File(libPath).getAbsolutePath());
            System.loadLibrary(libName);
         }
         finally
         {
            // Revert back the changes
            field.set(clazz, original);
            field.setAccessible(accessible);
         }
      }
      catch (Exception e)
      {
         throw new Exception("failed to load library " + libName + " from " + libPath, e);
      }
   }
}
