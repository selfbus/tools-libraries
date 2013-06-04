package org.selfbus.sbtools.sniffer.actions;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import org.jdesktop.application.SingleFrameApplication;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.sniffer.Sniffer;
import org.selfbus.sbtools.sniffer.filter.RecordingFileFilter;
import org.selfbus.sbtools.sniffer.internal.I18n;

/**
 * Open a recording file.
 */
public final class OpenRecordingAction extends BasicAction
{
   private static final long serialVersionUID = -8293395896565603621L;

   /**
    * Create an action object.
    */
   public OpenRecordingAction()
   {
      super(I18n.getMessage("OpenRecordingAction.name"), I18n.getMessage("OpenRecordingAction.toolTip"),
            ImageCache.getIcon("icons/fileopen"));

      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
   }

   /**
    * Perform the action.
    */
   @Override
   public void actionEvent(ActionEvent e)
   {
      SingleFrameApplication app = (SingleFrameApplication) SingleFrameApplication.getInstance();
      final JFrame mainWin = app.getMainFrame();

      final Config cfg = Config.getInstance();
      String lastDir = cfg.getStringValue("recording.lastDir");

      final JFileChooser dlg = new JFileChooser();
      dlg.setCurrentDirectory(new File(lastDir));
      final FileFilter fileFilter = new RecordingFileFilter();
      dlg.addChoosableFileFilter(fileFilter);
      dlg.addChoosableFileFilter(dlg.getAcceptAllFileFilter());
      dlg.setFileFilter(fileFilter);
      dlg.setDialogTitle(I18n.getMessage("OpenRecordingAction.title"));

      if (dlg.showOpenDialog(mainWin) != JFileChooser.APPROVE_OPTION)
         return;

      final File file = dlg.getSelectedFile();
      if (file == null) return;

      cfg.put("recording.lastDir", file.getParent());

      try
      {
         mainWin.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         Sniffer.getInstance().loadRecording(file);
      }
      finally
      {
         mainWin.setCursor(Cursor.getDefaultCursor());
      }
   }
}
