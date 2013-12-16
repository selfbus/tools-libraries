package org.selfbus.sbtools.vdviewer.vdx;

/**
 * Header information about a table in a vd_ file.
 */
public final class VdxTableHeader
{
   /**
    * The name of the table.
    */
   public final String name;

   /**
    * The id of the table.
    */
   public final int id;

   /**
    * The offset of the table in the vd_ file.
    * This is the position of the first header line.
    */
   public final long offset;

   /**
    * The names of the columns of the table entries.
    */
   public final String[] columnNames;

   /**
    * The types of the columns of the table entries.
    */
   public final VdxFieldType[] types;

   /**
    * The sizes of the columns of the table entries.
    */
   public final int[] sizes;

   /**
    * Create a header object.
    */
   VdxTableHeader(String name, int id, long offset, String[] columns, VdxFieldType[] types, int[] sizes)
   {
      this.name = name;
      this.id = id;
      this.offset = offset;
      this.columnNames = columns;
      this.types = types;
      this.sizes = sizes;
   }

   /**
    * Get the index of a field.
    * 
    * @param name - the name of the searched field.
    * 
    * @return the index of the field with the given name, or -1 if not found.
    */
   public int getIndexOf(String name)
   {
      int i;
      for (i=columnNames.length-1; i>=0; --i)
         if (columnNames[i].equalsIgnoreCase(name)) break;
      return i;
   }

   /**
    * @return a human readable representation of the object
    */
   @Override
   public String toString()
   {
      return Integer.toString(id) + " " + name + " offset " + Long.toString(offset);
   }
}
