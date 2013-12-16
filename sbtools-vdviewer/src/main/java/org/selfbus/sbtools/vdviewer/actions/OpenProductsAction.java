package org.selfbus.sbtools.vdviewer.actions;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.vdviewer.VdViewer;
import org.selfbus.sbtools.vdviewer.filter.ProductsFileFilter;
import org.selfbus.sbtools.vdviewer.internal.I18n;

/**
 * Open a products (VD) file.
 */
public final class OpenProductsAction extends BasicAction
{
   private static final long serialVersionUID = -2251750143937511022L;

   /**
    * Create an action object.
    */
   public OpenProductsAction()
   {
      super(I18n.getMessage("OpenProductsAction.name"), I18n.getMessage("OpenProductsAction.toolTip"), ImageCache
            .getIcon("icons/fileopen"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
   }

   /**
    * Perform the action.
    */
   @Override
   public void actionEvent(ActionEvent ev)
   {
      VdViewer app = VdViewer.getInstance();
      final JFrame mainWin = app.getMainFrame();

      final Config cfg = Config.getInstance();
      String lastFile = cfg.getStringValue("products.lastFile");

      final JFileChooser dlg = new JFileChooser();
      dlg.setCurrentDirectory(new File(lastFile).getParentFile());
      final FileFilter fileFilter = new ProductsFileFilter();
      dlg.addChoosableFileFilter(fileFilter);
      dlg.addChoosableFileFilter(dlg.getAcceptAllFileFilter());
      dlg.setFileFilter(fileFilter);
      dlg.setDialogTitle(I18n.getMessage("OpenProductsAction.title"));

      if (dlg.showOpenDialog(mainWin) != JFileChooser.APPROVE_OPTION)
         return;

      final File file = dlg.getSelectedFile();
      if (file == null) return;

      cfg.put("products.lastFile", file.getPath());

      try
      {
         mainWin.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

         app.openProducts(file);
      }
      catch (IOException e)
      {
         Dialogs.showExceptionDialog(e, I18n.formatMessage("Error.read", file.getName()));
      }
      finally
      {
         mainWin.setCursor(Cursor.getDefaultCursor());
      }
   }
}
