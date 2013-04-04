package org.selfbus.sbtools.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * System specific environment settings.
 */
public final class Environment
{
   private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);
   static Environment instance;

   private final String osname, tempDir, homeDir;
   private String appDir = null;
   private String appName = "sbtools";

   static
   {
      // Adds a SLF4JBridgeHandler instance to java.util.logging root logger
      SLF4JBridgeHandler.install();

      LOGGER.debug("slf4j bridge handlers installed");
   }
   
   /**
    * Initialize the environment. Usually programmers do not need to create
    * environment objects, as the global environment object is created
    * automatically on demand when the static methods are used.
    */
   Environment()
   {
      final String os = System.getProperty("os.name", "").toLowerCase();
      String envHomeDir;

      if (os.startsWith("windows"))
      {
         osname = "windows";
         tempDir = "c:/windows/temp";
         // C:\Dokumente und Einstellungen\joe
         envHomeDir = System.getenv("USERPROFILE");
      }
      else if (os.startsWith("linux"))
      {
         osname = "linux";
         tempDir = "/tmp";
         envHomeDir = System.getenv("HOME");
      }
      else
      {
         osname = "other";
         tempDir = "/tmp";
         envHomeDir = System.getenv("HOME");
      }

      if (envHomeDir != null && !envHomeDir.isEmpty())
         homeDir = envHomeDir;
      else homeDir = tempDir;
   }

   /**
    * Ensure that the environment is properly loaded.
    */
   public static void init()
   {
      getInstance();
   }

   /**
    * Returns the lower-case name of the operation system: "windows" for Windows
    * systems, "linux" for Linux systems, "other" for unknown systems.
    *
    * @return the name of the operating system
    */
   public static String getOS()
   {
      return getInstance().osname;
   }

   /**
    * @return the temporary directory (/tmp for Unix, c:\temp for Windows).
    */
   public static String getTempDir()
   {
      return getInstance().tempDir;
   }

   /**
    * @return the user's home directory.
    */
   public static String getHomeDir()
   {
      return getInstance().homeDir;
   }

   /**
    * Returns the application's user-data directory. The name of the directory
    * depends on the platform and uses the {@link #setAppName application name}.
    *
    * The user-data directory is created if it does not exist.
    *
    * @return the directory for application specific user data.
    *
    * @see #setAppName
    */
   public static String getAppDir()
   {
      final Environment env = getInstance();

      if (env.appDir == null)
      {
         env.appDir = getAppDir(env.homeDir, env.appName);
         System.setProperty("app.dir", env.appDir);
      }

      return env.appDir;
   }

   /**
    * Returns the application's user-data directory for the given home directory
    * and application name.
    *
    * @param homeDir - the home directory to use.
    * @param appName - the name of the application.
    *
    * @return the application directory
    */
   public static String getAppDir(final String homeDir, final String appName)
   {
      if (getInstance().osname.startsWith("linux"))
         return homeDir + "/.config/" + appName;
      else return homeDir + "/" + appName;
   }

   /**
    * Set the name of the application (default is "fts"). Slashes, semicolons,
    * and double-colons are replaced with dashes, white-spaces are replaced with
    * underlines.
    *
    * Creates the user-data directory if it does not exist.
    *
    * @param appName - the name of the application.
    *
    * @see #getAppDir()
    */
   public static void setAppName(String appName)
   {
      final Environment env = getInstance();

      appName = appName.replace('\\', '-').replace('/', '-').replace(':', '-').replace(';', '-').replace(' ', '_');
      env.appName = appName;

      final String dirName = getAppDir();
      final File appDirFile = new File(dirName);
      if (!appDirFile.isDirectory())
      {
         appDirFile.delete();

         if (!appDirFile.mkdir())
            throw new RuntimeException("Cannot create application directory: " + dirName);
      }
   }

   /**
    * @return the name of the application.
    */
   public static String getAppName()
   {
      return getInstance().appName;
   }

   /**
    * Returns the global environment object. The object is created if it does
    * not yet exist.
    *
    * @return the global environment object.
    */
   public static Environment getInstance()
   {
      if (instance == null)
         instance = new Environment();

      return instance;
   }

   /**
    * Dispose the global environment object.
    */
   public static void disposeInstance()
   {
      instance = null;
   }
}
