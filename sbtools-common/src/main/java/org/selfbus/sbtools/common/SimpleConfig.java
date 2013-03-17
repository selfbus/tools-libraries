package org.selfbus.sbtools.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * A simple configuration class.
 */
public class SimpleConfig
{
   private static SimpleConfig instance;
   private final Properties props = new Properties();

   /**
    * Returns the global configuration object. A {@link SimpleConfig} object is
    * created if no global configuration object exists.
    * 
    * @return The global configuration object instance.
    */
   public static SimpleConfig getInstance()
   {
      if (instance == null)
         return new SimpleConfig();
      return instance;
   }

   /**
    * Dispose the global configuration object.
    */
   public static void disposeInstance()
   {
      instance = null;
   }

   /**
    * Create an empty configuration object.
    * 
    * The global instance is set to the created configuration object, if the
    * global instance is null.
    * 
    * @see #getInstance()
    */
   public SimpleConfig()
   {
      if (instance == null)
         instance = this;
   }

   /**
    * Test if the configuration with the given key exists.
    * 
    * @param key - the key to search for.
    * @return True if the config contains the key, false if not.
    */
   public boolean containsKey(String key)
   {
      return props.containsKey(key);
   }

   /**
    * Get a string value from the configuration.
    * 
    * @param key - the configuration key to get.
    * 
    * @return the configuration value for the given key as String. Returns an
    *         empty string if the key does not exist in the config object.
    */
   public String getStringValue(String key)
   {
      String val = props.getProperty(key);
      if (val == null)
         return "";
      return val;
   }

   /**
    * Get a string value from the configuration.
    * 
    * @param key - the configuration key to get.
    * @param defaultValue - the default value.
    * 
    * @return The configuration value for the given key as String. Returns the
    *         default value if the key does not exist in the config object.
    */
   public String getStringValue(String key, String defaultValue)
   {
      String val = props.getProperty(key);
      if (val == null)
         return defaultValue;
      return val;
   }

   /**
    * Get an integer value from the configuration.
    * 
    * @param key - the configuration key to get.
    * 
    * @return the configuration value for the given key as Integer. Returns zero
    *         if the key does not exist in the configuration object.
    */
   public int getIntValue(String key)
   {
      String val = props.getProperty(key);
      if (val == null)
         return 0;
      return Integer.parseInt(val);
   }

   /**
    * Get an integer value from the configuration.
    * 
    * @param key - the configuration key to get.
    * @param defaultValue - the default value.
    * 
    * @return the configuration value for the given key as Integer. Return the
    *         default value if the key does not exist in the configuration
    *         object.
    */
   public int getIntValue(String key, int defaultValue)
   {
      String val = props.getProperty(key);
      if (val == null)
         return defaultValue;
      return Integer.parseInt(val);
   }

   /**
    * Get the configuration value for the given key.
    * 
    * @param key - the key to get the value for.
    * 
    * @return the configuration value for the given key. Returns null if the key
    *         does not exist in the configuration object.
    */
   public String get(String key)
   {
      return props.getProperty(key);
   }

   /**
    * Set the configuration value for the given key.
    * 
    * @param key - the key.
    * @param value - the string value.
    */
   public void put(String key, String value)
   {
      props.setProperty(key, value);
   }

   /**
    * Set the configuration value for the given key.
    * 
    * @param key - the key.
    * @param value - the integer value.
    */
   public void put(String key, int value)
   {
      props.setProperty(key, Integer.toString(value));
   }

   /**
    * Clear the configuration.
    * 
    * @see #init()
    */
   public void clear()
   {
      props.clear();
   }

   /**
    * Initialize the configuration with default values. This default
    * implementation does nothing.
    * 
    * @see #clear()
    */
   public void init()
   {
   }

   /**
    * Load the configuration from the file fileName. The configuration is
    * cleared before loading.
    * 
    * @param fileName - the name of the file to load.
    * 
    * @throws FileNotFoundException if the file exists but is a directory rather
    *            than a regular file, does not exist but cannot be created, or
    *            cannot be opened for any other reason.
    * @throws IOException if an error occurred when reading from the input
    *            stream.
    * @throws IllegalArgumentException if the configuration file contains a
    *            malformed Unicode escape sequence.
    */
   public void load(String fileName) throws IOException, FileNotFoundException
   {
      InputStream in = null;

      try
      {
         in = new FileInputStream(fileName);
         load(in);
      }
      finally
      {
         if (in != null)
            in.close();
      }
   }

   /**
    * Load the configuration from the input stream <code>in</code>. The
    * configuration is cleared before loading.
    * 
    * @param in - the input stream to read.
    * 
    * @throws IOException if an error occurred when reading from the input
    *            stream.
    * @throws IllegalArgumentException if the input stream contains a malformed
    *            Unicode escape sequence.
    */
   public void load(InputStream in) throws IOException
   {
      props.load(in);
   }

   /**
    * Save the configuration to the file fileName.
    * 
    * @param fileName - the name of the file to save to.
    *
    * @throws SecurityException if a security manager exists and its
    *            <code>checkWrite</code> method denies write access to the file.
    * @throws IOException if writing the configuration list to the specified
    *            output stream throws an <tt>IOException</tt>.
    */
   public void save(String fileName) throws IOException
   {
      FileOutputStream out = null;
      try
      {
         out = new FileOutputStream(fileName);
         save(out);
      }
      finally
      {
         if (out != null)
            out.close();
      }
   }

   /**
    * Save the configuration to the output stream <code>out</code>.
    *
    * @param out - the stream to write to.
    *
    * @throws IOException if writing the configuration list to the specified
    *            output stream throws an <tt>IOException</tt>.
    * @throws NullPointerException if <code>out</code> is null.
    */
   public void save(OutputStream out) throws IOException
   {
      props.store(out, Environment.getAppName() + " configuration");
   }
}
