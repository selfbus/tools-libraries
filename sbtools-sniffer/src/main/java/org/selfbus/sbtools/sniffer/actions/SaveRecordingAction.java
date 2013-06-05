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
import org.selfbus.sbtools.sniffer.misc.I18n;

/**
 * Save a recording file.
 */
public final class SaveRecordingAction extends BasicAction
{
   private static final long serialVersionUID = -156959508941638583L;

   /**
    * Create an action object.
    */
   public SaveRecordingAction()
   {
      super(I18n.getMessage("SaveRecordingAction.name"), I18n.getMessage("SaveRecordingAction.toolTip"),
            ImageCache.getIcon("icons/filesave"));

      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
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
      dlg.setDialogTitle(I18n.getMessage("SaveRecordingAction.title"));

      if (dlg.showOpenDialog(mainWin) != JFileChooser.APPROVE_OPTION)
         return;

      File file = dlg.getSelectedFile();
      if (file == null) return;

      cfg.put("recording.lastDir", file.getParent());

      if (!file.getName().toLowerCase().endsWith(".snif"))
         file = new File(file.getPath() + ".snif");

      try
      {
         mainWin.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         Sniffer.getInstance().saveRecording(file);
      }
      finally
      {
         mainWin.setCursor(Cursor.getDefaultCursor());
      }
   }
}
