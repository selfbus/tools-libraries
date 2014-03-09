package org.selfbus.sbtools.vdio.internal;

import java.util.Vector;

/**
 * Information about a VD table.
 */
public class VDTableInfo
{
   /**
    * The ID of the table.
    */
   public final int id;

   /**
    * The name of the table.
    */
   public final String name;

   /**
    * The table columns.
    */
   public final Vector<VDTableColumn> columns = new Vector<VDTableColumn>();

   /**
    * Create a VD-table information object
    */
   public VDTableInfo(int id, String name)
   {
      this.id = id;
      this.name = name;
   }
}
