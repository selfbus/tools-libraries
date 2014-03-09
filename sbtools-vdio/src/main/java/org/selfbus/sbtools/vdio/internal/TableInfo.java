package org.selfbus.sbtools.vdio.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * Table information, used by the VDReader.
 */
public class TableInfo
{
   /**
    * The index of the table.
    */
   public final int id;

   /**
    * The name of the table.
    */
   public final String name;

   /**
    * The table field names.
    */
   public final List<String> fieldNames = new ArrayList<String>(32);

   /**
    * The table field types.
    */
   public final List<TableFieldType> fieldTypes = new ArrayList<TableFieldType>(32);

   /**
    * Create a table information.
    *
    * @param id - the ID of the table
    * @param name - the name of the table
    */
   public TableInfo(int id, String name)
   {
      this.id = id;
      this.name = name;
   }
}
