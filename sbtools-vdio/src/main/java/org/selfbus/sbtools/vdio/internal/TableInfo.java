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
    * The table fields.
    */
   public final List<String> fields = new ArrayList<String>(64);

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
