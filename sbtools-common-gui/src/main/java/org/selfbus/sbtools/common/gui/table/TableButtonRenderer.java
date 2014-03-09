package org.selfbus.sbtools.common.gui.table;

import java.awt.Component;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * A {@link TableCellRenderer} that renders a button in the table cells.
 */
public class TableButtonRenderer implements TableCellRenderer
{
   private final Component emptyComponent = new Label();

   /**
    * Create a table button renderer.
    */
   public TableButtonRenderer()
   {
      emptyComponent.setVisible(false);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
         int row, int column)
   {
      JButton button = (JButton) value;
      if (button == null)
      {
         return emptyComponent;
      }

      if (isSelected)
      {
         button.setForeground(table.getSelectionForeground());
         button.setBackground(table.getSelectionBackground());
      }
      else
      {
         button.setForeground(table.getForeground());
         button.setBackground(UIManager.getColor("Button.background"));
      }
      return button;
   }
}
