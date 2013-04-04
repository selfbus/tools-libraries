package org.selfbus.sbtools.common.gui.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;

/**
 * This mouse listener will have to take the click, work out in which cell it
 * occurred and if that cell contains a JButton, click it.
 * 
 * See http://www.cordinc.com/blog/2010/01/jbuttons-in-a-jtable.html
 */
public class TableButtonMouseListener extends MouseAdapter
{
   private final JTable table;

   public TableButtonMouseListener(JTable table)
   {
      this.table = table;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void mouseClicked(MouseEvent e)
   {
      int column = table.getColumnModel().getColumnIndexAtX(e.getX());
      int row = e.getY() / table.getRowHeight();

      if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0)
      {
         Object value = table.getValueAt(row, column);
         if (value instanceof JButton)
         {
            ((JButton) value).doClick();
         }
      }
   }
}
