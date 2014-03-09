package org.selfbus.sbtools.vdio.internal;

/**
 * Information about a column of a VD table.
 */
public class VDTableColumn
{
   /**
    * The ID of the column.
    */
   public final int id;

   /**
    * The name of the column.
    */
   public final String name;

   /**
    * The data type of the column.
    */
   public final int type;

   /**
    * The length of the column's data in bytes.
    */
   public final int length;

   /**
    * True if the value may be empty.
    */
   public final boolean nullAllowed;

   /**
    * Create a VD-table column-information object
    */
   public VDTableColumn(int id, String name, int type, int length, boolean nullAllowed)
   {
      this.id = id;
      this.name = name;
      this.type = type;
      this.length = length;
      this.nullAllowed = nullAllowed;
   }
}
