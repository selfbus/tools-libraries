package org.selfbus.sbtools.vdviewer.vdx;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * A table of a vd_ file.
 */
public class VdxTable extends AbstractTableModel
{
   private static final long serialVersionUID = 3545345846318251056L;

   private final VdxTableHeader header;
   private final Vector<String[]> records = new Vector<String[]>(1000, 1000);

   /**
    * Create a new table object.
    * @param header
    */
   VdxTable(VdxTableHeader header)
   {
      this.header = header;
   }

   /**
    * @return The name of the table.
    */
   public String getName()
   {
      return header.name;
   }

   /**
    * @return The table header.
    */
   public VdxTableHeader getHeader()
   {
      return header;
   }
   /**
    *  Returns the name for the column.
    *
    * @param columnIndex - the column being queried
    * @return a string containing the default name of <code>column</code>
    */
   @Override
   public String getColumnName(int columnIndex)
   {
      return header.columnNames[columnIndex];
   }

   /**
    *  Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
    *
    *  @param columnIndex - the column being queried
    *  @return the Object.class
    */
   @Override
   public Class<?> getColumnClass(int columnIndex)
   {
      return header.types[columnIndex].clazz;
   }

   /**
    * @return the number of records that the table contains.
    */
   @Override
   public int getRowCount()
   {
      return records.size();
   }

   /**
    * @return The number of columns of the table.
    */
   @Override
   public int getColumnCount()
   {
      return header.columnNames.length;
   }

   /**
    * Add a record to the table.
    * 
    * @param values - the values to add.
    */
   public void addRecord(String[] values)
   {
      records.add(values);
   }

   /**
    * Get a record.
    * 
    * @param idx - the index of the record.
    * 
    * @return the field's values of the idx-th record.
    */
   public String[] getRecord(int idx)
   {
      return records.get(idx);
   }

   /**
    * Returns the value for the cell at <code>columnIndex</code> and
    * <code>rowIndex</code>.
    *
    * @param   row - the row whose value is to be queried
    * @param   col - the column whose value is to be queried
    * @return  the value Object at the specified cell
    */
   @Override
   public Object getValueAt(int row, int col)
   {
      return records.get(row)[col];
   }

   /**
    * Get the value of a single field.
    * 
    * @param row - the index of the element.
    * @param col - the index of the column within the element.
    * 
    * @return the fieldIdx-th field of the idx-th record.
    */
   public String getValue(int row, int col)
   {
      return records.get(row)[col];
   }

   /**
    * Get the integer value of a single field.
    * 
    * @param row - the index of the record.
    * @param col - the index of the column within the element.

    * @return the fieldIdx-th field of the idx-th record as an integer.
    *         Zero is returned if the field is empty.
    */
   public int getIntValue(int row, int col)
   {
      final String val = records.get(row)[col];
      if (val.isEmpty()) return 0;
      return Integer.parseInt(val);
   }

   /**
    * Get the value of a single field.
    * 
    * @param row - the index of the record.
    * @param colName - the name of the column.
    * 
    * @return the field of the record with the name columnName.
    */
   public String getValue(int row, String colName)
   {
      final int fieldIdx = header.getIndexOf(colName);
      return records.get(row)[fieldIdx];
   }

   /**
    * Get the integer value of a single field.
    * 
    * @param row - the index of the record.
    * @param colName - the name of the column.

    * @return the field of the record with the name columnName
    *         as an integer. Zero is returned if the field is empty.
    */
   public int getIntValue(int row, String colName)
   {
      final int fieldIdx = header.getIndexOf(colName);
      final String val = records.get(row)[fieldIdx];
      if (val.isEmpty()) return 0;
      return Integer.parseInt(val);
   }

   /**
    * Clear the records in the table.
    */
   public void clear()
   {
      records.clear();
   }
}
