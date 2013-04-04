package org.selfbus.sbtools.common.gui.utils;

import java.io.File;

/**
 * Utility functions for file operations.
 */
public final class FileUtils
{
   /**
    * Extract the extension of a file. E.g. "Example.PNG" returns "png".
    * The returned extension is lower-case.
    *
    * @param file - the file to process.
    * @return the lower-case extension of the file.
    */
   public static String getExtension(File file)
   {
      String ext = null;
      String s = file.getName();
      int i = s.lastIndexOf('.');

      if (i > 0 && i < s.length() - 1) ext = s.substring(i + 1).toLowerCase();

      return ext;
   }
}
