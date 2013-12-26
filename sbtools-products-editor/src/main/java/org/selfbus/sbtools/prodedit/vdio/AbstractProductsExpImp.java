package org.selfbus.sbtools.prodedit.vdio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.selfbus.sbtools.vdio.VdioException;

/**
 * Abstract base class for products exporter and importer.
 */
abstract public class AbstractProductsExpImp
{
   /**
    * Load a properties file that is searched in the directory "org.selfbus.sbtools.prodedit"
    * in the class path.
    * 
    * @param name - name of the properties file.
    * @return The properties
    * @throws VdioException if the properties file could not be loaded
    */
   Properties getProperties(String name) throws VdioException
   {
      String fileName = "org/selfbus/sbtools/prodedit/" + name;
      Properties prop = new Properties();
      try
      {
         InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
         if (in == null)
            throw new VdioException("property file not found in class path: " + fileName);

         prop.load(in);
      }
      catch (IOException e)
      {
         throw new VdioException("failed to read property file: " + fileName, e);
      }
      return prop;
   }
}
