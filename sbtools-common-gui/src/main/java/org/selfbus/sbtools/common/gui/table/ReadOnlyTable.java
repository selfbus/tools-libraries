package org.selfbus.sbtools.common.gui.table;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * A {@link JTable} that disallows editing of the cells.
 */
public class ReadOnlyTable extends JTable
{
   private static final long serialVersionUID = 1444448663563604142L;

   /**
    * Create a read-only table object.
    */
   public ReadOnlyTable()
   {
      super();
   }

   /**
    * Create a read-only table object.
    * 
    * @param dm - the table model.
    */
   public ReadOnlyTable(TableModel dm)
   {
      super(dm);
   }

   /**
    * Create a read-only table object.
    * 
    * @param dm - the table model.
    * @param cm - the column model.
    */
   public ReadOnlyTable(TableModel dm, TableColumnModel cm)
   {
      super(dm, cm);
   }

   /**
    * Create a read-only table object.
    * 
    * @param numRows - the number of rows of the table.
    * @param numColumns - the number of columns of the table.
    */
   public ReadOnlyTable(int numRows, int numColumns)
   {
      super(numRows, numColumns);
   }

   /**
    * Create a read-only table object.
    *
    * @param rowData - the initial table data.
    * @param columnNames - the names of the columns.
    */
   public ReadOnlyTable(Vector<?> rowData, Vector<?> columnNames)
   {
      super(rowData, columnNames);
   }

   /**
    * Create a read-only table object.
    * 
    * @param rowData - the initial table data.
    * @param columnNames - the names of the columns.
    */
   public ReadOnlyTable(Object[][] rowData, Object[] columnNames)
   {
      super(rowData, columnNames);
   }

   /**
    * Create a read-only table object.
    * 
    * @param dm - the table model.
    * @param cm - the column model.
    * @param sm - the list selection model.
    */
   public ReadOnlyTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm)
   {
      super(dm, cm, sm);
   }

   /**
    * Create a read-only table object.
    * 
    * @param row - the row of the cell to test.
    * @param column - the column of the cell to test.
    * 
    * @return False.
    */
   public final boolean isCellEditable(int row, int column)
   {
      return false;
   }
}
