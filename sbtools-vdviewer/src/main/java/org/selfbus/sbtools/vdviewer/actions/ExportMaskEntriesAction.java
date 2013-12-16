package org.selfbus.sbtools.vdviewer.actions;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.vdviewer.VdViewer;
import org.selfbus.sbtools.vdviewer.internal.I18n;
import org.selfbus.sbtools.vdviewer.tabs.TableTab;
import org.selfbus.sbtools.vdviewer.vdx.VdxFile;
import org.selfbus.sbtools.vdviewer.vdx.VdxTable;

/**
 * Export the mask entries.
 */
public class ExportMaskEntriesAction extends BasicAction
{
   private static final long serialVersionUID = -736816890851568466L;

   /**
    * Create an action object.
    */
   public ExportMaskEntriesAction()
   {
      super(I18n.getMessage("ExportMaskEntriesAction.name"), I18n.getMessage("ExportMaskEntriesAction.toolTip"),
         ImageCache.getIcon("icons/filesaveas"));
   }

   /**
    * Create an action object.
    *
    * @param name - The name of the action. A "&" marks the mnemonic.
    * @param toolTipText - The text for the tool-tip.
    * @param icon - The icon for the action.
    */
   ExportMaskEntriesAction(String name, String toolTipText, Icon icon)
   {
      super(name, toolTipText, icon);
   }

   /**
    * Perform the action.
    */
   @Override
   public void actionEvent(ActionEvent e)
   {
      VdViewer app = VdViewer.getInstance();
      final JFrame mainWin = app.getMainFrame();

      VdxTable table = null;
      int[] selectedRows = null;

      Component c = app.getCurrentTab();
      if (c instanceof TableTab)
      {
         TableTab tableTab = (TableTab) c;
         table = tableTab.getTable();
         if ("mask".equals(table.getName()))
         {
            selectedRows = tableTab.getSelectedRows();
         }
      }

      if (selectedRows == null || selectedRows.length < 1)
      {
         Dialogs.showErrorDialog(I18n.getMessage("ExportMaskEntriesAction.howto"));
         return;
      }

      final int maskVersion = table.getIntValue(selectedRows[0], "mask_version");
      final String maskId = table.getValue(selectedRows[0], "mask_id");
      final int type = (maskVersion >> 8) & 255;
      final int version = (maskVersion >> 4) & 15;
      final int subVersion = maskVersion & 15;

      final String maskFileName = String.format("memory-addresses-%1$d-%2$d.%3$d.properties",
         new Object[] { type, version, subVersion });

      final Config cfg = Config.getInstance();
      String lastDir = cfg.getStringValue("exportMaskEntries.lastDir");

      final JFileChooser dlg = new JFileChooser();
      dlg.setCurrentDirectory(new File(lastDir));
      dlg.setSelectedFile(new File(lastDir + '/' + maskFileName));
      dlg.setDialogTitle(I18n.getMessage("ExportMaskEntriesAction.title"));

      if (dlg.showOpenDialog(mainWin) != JFileChooser.APPROVE_OPTION)
         return;

      File file = dlg.getSelectedFile();
      if (file == null) return;

      cfg.put("exportMaskEntries.lastDir", file.getParent());

      try
      {
         mainWin.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         Properties props = createMaskProperties(maskId);
         props.store(new FileOutputStream(file), maskFileName);
         app.setStatusMessage(I18n.formatMessage("ExportMaskEntriesAction.saved", file.getName()));
      }
      catch (IOException ex)
      {
         throw new RuntimeException(ex);
      }
      finally
      {
         mainWin.setCursor(Cursor.getDefaultCursor());
      }
   }

   /**
    * Create a {@link Properties} object containing the mask entries of the given mask.
    *
    * @param maskId - the ID of the mask to process.
    *
    * @return The properties object containing the mask entries.
    * @throws IOException when there is an error reading the VdxFile
    */
   public Properties createMaskProperties(String maskId) throws IOException
   {
      VdxFile vdxFile = VdViewer.getInstance().getProducts();
      VdxTable maskEntryTable = vdxFile.getTable("mask_entry");
      Validate.notNull(maskEntryTable, "VD contains no table \"mask_entry\"");

      Properties props = new Properties();

      int maskIdCol = maskEntryTable.getHeader().getIndexOf("mask_id");
      int maskEntryNameCol = maskEntryTable.getHeader().getIndexOf("mask_entry_name");
      int maskEntryAddrCol = maskEntryTable.getHeader().getIndexOf("mask_entry_address");
      
      for (int row = maskEntryTable.getRowCount() - 1; row >= 0; --row)
      {
         if (maskId.equals(maskEntryTable.getValue(row, maskIdCol)))
         {
            props.setProperty(maskEntryTable.getValue(row, maskEntryNameCol),
                              maskEntryTable.getValue(row, maskEntryAddrCol));
         }
      }

      return props;
   }
}
