package org.selfbus.sbtools.sniffer.filter;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.selfbus.sbtools.common.gui.utils.FileUtils;
import org.selfbus.sbtools.sniffer.internal.I18n;

/**
 * A {@link JFileChooser} file-filter for project files.
 */
public final class RecordingFileFilter extends FileFilter
{
   @Override
   public boolean accept(File file)
   {
      if (file.isDirectory()) return true;

      final String ext = FileUtils.getExtension(file);
      return "snif".equalsIgnoreCase(ext);
   }

   @Override
   public String getDescription()
   {
      return I18n.getMessage("RecordingFileFilter.description");
   }
}
