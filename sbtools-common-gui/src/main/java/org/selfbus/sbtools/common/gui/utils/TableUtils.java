package org.selfbus.sbtools.common.gui.utils;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Utility methods for {@link JTree} trees.
 */
public final class TableUtils
{
   /**
    * Pack the table such that all columns are just as wide as their contents
    * requires.
    * 
    * @param table - the table to pack.
    * @param margin - the extra space to add to both sides of every column.
    */
   public static void pack(JTable table, int margin)
   {
      final TableColumnModel colModel = table.getColumnModel();

      for (int colIdx = colModel.getColumnCount() - 1; colIdx >= 0; --colIdx)
      {
         final TableColumn col = colModel.getColumn(colIdx);
         int width = 0;

         TableCellRenderer renderer = col.getHeaderRenderer();
         if (renderer == null)
            renderer = table.getTableHeader().getDefaultRenderer();

         Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
         width = comp.getPreferredSize().width;

         // Get maximum width of column data
         for (int r = 0; r < table.getRowCount(); r++)
         {
            renderer = table.getCellRenderer(r, colIdx);
            comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, colIdx), false, false, r, colIdx);
            width = Math.max(width, comp.getPreferredSize().width);
         }

         col.setPreferredWidth(width + (margin << 1));
      }
   }

   /**
    * Create a string that contains the names of the table's columns in their
    * current order, separated by comma.
    * 
    * @param table - the table to process.
    * @return a string containing the comma separated names of the columns.
    */
   public static String getColumnOrderString(final JTable table)
   {
      final StringBuilder sb = new StringBuilder();

      final TableColumnModel columnModel = table.getColumnModel();
      final int numColumns = columnModel.getColumnCount();
      for (int i = 0; i < numColumns; ++i)
      {
         if (i > 0) sb.append(',');
         sb.append(columnModel.getColumn(i).getHeaderValue().toString());
      }

      return sb.toString();
   }

   /**
    * Apply the order of the columns to the table.
    * 
    * @param table - the table to process.
    * @param orderStr - the string with the column names, separated by comma.
    */
   public static void applyColumnOrder(final JTable table, final String orderStr)
   {
      final TableColumnModel columnModel = table.getColumnModel();
      final String[] colNames = orderStr.split(",");

      for (int i = 0; i < colNames.length; ++i)
      {
         try
         {
            final int idx = columnModel.getColumnIndex(colNames[i]);
            if (idx >= 0) columnModel.moveColumn(idx, i);
         }
         catch (IllegalArgumentException e)
         {
         }
      }
   }

   /**
    * Disabled constructor.
    */
   private TableUtils()
   {
   }
}
