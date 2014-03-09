package org.selfbus.sbtools.vdio.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * Manager that holds the meta informations about VD tables.
 */
public class VDTableInfoManager
{
   private final Map<String, VDTableInfo> tables = new HashMap<String, VDTableInfo>();

   /**
    * Create a VD-Tables-Info object.
    *
    * @param infoStream - the stream of the tables info file
    */
   public VDTableInfoManager(InputStream infoStream)
   {
      try
      {
         readTablesInfo(infoStream);
      }
      catch (IOException e)
      {
         throw new RuntimeException("Failed to read VD tables info from " + infoStream);
      }
   }

   /**
    * Get the info for a table.
    *
    * @param name - the name of the table
    * @return The info object, or null if not found.
    */
   public VDTableInfo getTable(String name)
   {
      return tables.get(name);
   }

   /**
    * Read the tables info
    *
    * @param in - the input stream to read from.
    * @throws IOException 
    */
   void readTablesInfo(InputStream in) throws IOException
   {
      Validate.notNull(in, "input stream is null");

      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      VDTableInfo table = null;
      tables.clear();

      while (true)
      {
         String line = reader.readLine();
         if (line == null) break;

         if (line.startsWith("T "))
         {
            // Format: T 1 table_name

            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[1]);
            String name = parts[2].toLowerCase().intern();

            table = new VDTableInfo(id, name);
            tables.put(table.name, table);
         }
         else if (line.startsWith("C"))
         {
            // Format: C1 T7 1 4 N field_name

            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0].substring(1));
            int typeId = Integer.parseInt(parts[2]);
            int length = Integer.parseInt(parts[3]);
            boolean nullAllowed = "Y".equals(parts[4]);
            String name = parts[5].toLowerCase().intern();

            VDTableColumn col = new VDTableColumn(id, name, typeId, length, nullAllowed);
            table.columns.add(col);
         }
      }
   }
}
