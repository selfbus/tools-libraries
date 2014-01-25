package org.selfbus.sbtools.knxcom.gui.busmonitor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.selfbus.sbtools.common.gui.utils.FileUtils;
import org.selfbus.sbtools.knxcom.gui.internal.I18n;

/**
 * A {@link JFileChooser} file-filter for *.trx files.
 */
public final class BusTraceFileFilter extends FileFilter
{
   @Override
   public boolean accept(File file)
   {
      if (file.isDirectory()) return true;

      final String ext = FileUtils.getExtension(file);
      return "trx".equalsIgnoreCase(ext);
   }

   @Override
   public String getDescription()
   {
      return I18n.getMessage("BusTraceFileFilter.description");
   }
}
