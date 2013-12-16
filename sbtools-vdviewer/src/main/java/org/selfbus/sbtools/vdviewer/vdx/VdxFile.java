package org.selfbus.sbtools.vdviewer.vdx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

/**
 * A class that reads vd_ files. The table names are all converted to lower
 * case.
 */
public final class VdxFile
{
   private static final String table_SEPARATOR = "---------------------------";
   private final File file;
   private String format = null;
   private String version = null;
   private RandomAccessFile in = null;
   private int languageId = 0;
   private final FileBlockReader reader;
   private final Map<String, VdxTableHeader> tableHeaders = new LinkedHashMap<String, VdxTableHeader>();
   private final Map<String, VdxTable> tables = new HashMap<String, VdxTable>(100);
   private Map<String, Integer> languages;

   /**
    * Create a new VDX-file reader object. The file <code>fileName</code> is
    * read upon creation.
    *
    * @param file is the file that shall be read.
    *
    * @throws IOException if the file cannot be read.
    */
   public VdxFile(File file) throws IOException
   {
      this.file = file;

      try
      {
         // final ZipFileType zipFileType = ZipFileType.inspectFile(fileName);

         in = new RandomAccessFile(file, "r");
         reader = new FileBlockReader(in, 1049600);

         scanHeader();
         scanFile();
      }
      catch (IOException e)
      {
         if (in != null)
            in.close();
         throw new IOException("Failed to read file " + file + ": " + e.getMessage(), e);
      }

      try
      {
         setLanguage("German");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Create a new VDX-file reader object. The file <code>fileName</code> is
    * read upon creation.
    *
    * @param fileName - the name of the file that shall be read.
    *
    * @throws IOException if the file cannot be read.
    */
   public VdxFile(String fileName) throws IOException
   {
      this(new File(fileName));
   }

   /**
    * Destructor.
    *
    * @throws RuntimeException if the file was not closed.
    */
   @Override
   protected void finalize() throws RuntimeException
   {
      if (in != null)
      {
         close();
         throw new RuntimeException("Internal error: file " + file + " was not properly closed");
      }
   }

   /**
    * Close the file.
    */
   public void close()
   {
      if (in != null)
         try
         {
            in.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
         finally
         {
            in = null;
         }
   }

   /**
    * @return the file that the file-reader processes.
    */
   public File getFile()
   {
      return file;
   }

   /**
    * @return the names of all found tables.
    */
   public Set<String> getTableNames()
   {
      return tableHeaders.keySet();
   }

   /**
    * Get a header table.
    * 
    * @param name - the name of the header table.
    * 
    * @return the header of the table with the given name.
    */
   public VdxTableHeader getTableHeader(String name)
   {
      return tableHeaders.get(name.toLowerCase());
   }

   /**
    * Test if a header table exists.
    * 
    * @param name - the name of the header table.
    *
    * @return true if the table with the given name exists.
    */
   public boolean hastable(String name)
   {
      return tableHeaders.containsKey(name.toLowerCase());
   }

   /**
    * Get a specific table of the file. The table is read, if required.
    *
    * @param name - the name of the table.
    *
    * @return the table with the given name or null if no such table exists
    *         or if the table contains no records.
    *
    * @throws IOException in case of I/O problems.
    */
   public VdxTable getTable(String name) throws IOException
   {
      name = name.toLowerCase();
      if (tables.containsKey(name))
         return tables.get(name);

      final VdxTableHeader header = tableHeaders.get(name);
      if (header == null)
         return null;

      final VdxTable table = new VdxTable(header);
      final int numFields = header.columnNames.length;
      String line;

      reader.seek(header.offset);
      // We are at the "T" title line now
      // Skip to the first entry. An entry starts with such a line:
      // R 1 T 3 manufacturer
      line = reader.readLine();
      while (line != null)
      {
         line = reader.readLine();
         if (line == null || !line.startsWith("C"))
            break;
      }

      // Read the entries of the table
      while (line != null && !line.startsWith(table_SEPARATOR) && !line.equals("XXX"))
      {
         final String[] values = new String[numFields];
         for (int i = 0; i <= numFields; ++i)
         {
            line = reader.readLine();
            if (line == null)
            {
               if (i > 0 && i < numFields)
                  throw new IOException("file is truncated");
               break;
            }

            if (line.startsWith("\\\\") && i > 0)
            {
               values[--i] += line.substring(2).replace("//", "/");
               continue;
            }
            if (i == numFields)
               break;

            values[i] = line.replace("//", "/");
         }

         table.addRecord(values);
      }

      tables.put(name, table);
      return table;
   }

   /**
    * Remove a specific table contents from the reader. Only the contents of
    * the table is removed, the table header stays loaded.
    *
    * You can call this method free some memory, which is recommended when
    * working with large files.
    * 
    * @param name - the name of the table.
    */
   public void removetableContents(String name)
   {
      tables.remove(name);
   }

   /**
    * Read the vdx file header. The read-pointer stands on the T line of the
    * first table afterwards.
    *
    * @throws IOException in case of I/O problems.
    */
   private void scanHeader() throws IOException
   {
      String line;
      char lineType;

      format = null;
      setVersion(null);

      in.seek(0);

      line = reader.readLine();
      if (line != null)
         line = line.trim();
      if (!"EX-IM".equals(line))
         throw new IOException("not a vd_ file");

      while (true)
      {
         line = reader.readLine();
         if (line == null)
            break;
         lineType = line.charAt(0);
         if (lineType == '-' && line.startsWith(table_SEPARATOR))
            break;
         if (lineType == 'K')
         {
            if (format == null || format.isEmpty())
               format = line.substring(2).trim();
         }
         else if (lineType == 'V')
            setVersion(line.substring(2).trim());
      }
   }

   /**
    * Find the tables and their offsets within the file.
    *
    * @throws IOException
    */
   private void scanFile() throws IOException
   {
      VdxTableHeader tableHeader;
      final Vector<String> fieldNames = new Vector<String>(32);
      final Vector<VdxFieldType> fieldTypes = new Vector<VdxFieldType>(32);
      final Vector<Integer> fieldSizes = new Vector<Integer>(32);
      String line;
      long offset;

      tableHeaders.clear();

      while (!reader.atEnd())
      {
         // Read the table title
         offset = reader.getFilePointer();
         line = reader.readLine();
         if (line == null)
            break;

         String[] words = line.split(" ");
         if (words[0].equals("XXX"))
            break;
         else if (!words[0].equals("T"))
            throw new IOException("No table start at byte-pos " + Long.toString(reader.getFilePointer()));

         final int tableId = Integer.parseInt(words[1]);
         String tableName = words[2];

         if (tableName != null)
            tableName = tableName.trim().toLowerCase();
         if (tableHeaders.containsKey(tableName))
         {
            throw new IOException("Duplicate table \"" + tableName + "\" at byte-pos "
                  + Long.toString(reader.getFilePointer()));
         }

         // Read the fields of the table
         fieldNames.clear();
         fieldTypes.clear();
         fieldSizes.clear();
         while (!reader.atEnd() && reader.read() == 'C')
         {
            // A field definition line looks like this:
            // C1 T3 1 4 N MANUFACTURER_ID

            // Skip the field-id "1" (we already read the 'C')
            reader.readWord();

            // Skip the table name "T3"
            reader.readWord(); 

            // the field data type
            try
            {
               fieldTypes.add(VdxFieldType.valueOfTypeId(Integer.parseInt(reader.readWord())));
            }
            catch (Exception e)
            {
               throw new IOException("failed to get field type, table \"" + tableName + "\" at byte-pos "
                     + Long.toString(reader.getFilePointer()), e);
            }

            // The size of the field in bytes
            fieldSizes.add(Integer.valueOf(reader.readWord()));

            // Skip the null-allowed Y|N switch
            reader.readWord();

            // the field name
            line = reader.readLine();
            if (line == null)
               break;
            fieldNames.add(line.trim().toLowerCase());
         }

         final String[] fieldNamesArr = new String[fieldNames.size()];
         fieldNames.toArray(fieldNamesArr);

         final VdxFieldType[] fieldTypesArr = new VdxFieldType[fieldTypes.size()];
         fieldTypes.toArray(fieldTypesArr);

         final int[] fieldSizesArr = new int[fieldSizes.size()];
         for (int i = fieldSizes.size() - 1; i >= 0; --i)
            fieldSizesArr[i] = fieldSizes.get(i);

         // Create the table header object
         tableHeader = new VdxTableHeader(tableName, tableId, offset, fieldNamesArr, fieldTypesArr, fieldSizesArr);
         tableHeaders.put(tableName, tableHeader);

         // Skip the table entries
         final int numFields = fieldNamesArr.length;
         line = reader.readLine();
         while (line != null && !line.startsWith(table_SEPARATOR) && !line.equals("XXX"))
         {
            for (int i = 0; i <= numFields; ++i)
            {
               line = reader.readLine();
               if (line == null)
               {
                  if (i > 0 && i < numFields)
                     throw new IOException("file is truncated");
                  break;
               }

               if (line.startsWith("\\\\") && i > 0)
               {
                  --i;
                  continue;
               }
               if (i == numFields)
                  break;
            }
         }

      }
   }

   /**
    * @param version the version to set
    */
   public void setVersion(String version)
   {
      this.version = version;
   }

   /**
    * @return the version
    */
   public String getVersion()
   {
      return version;
   }

   /**
    * Set the language that shall be used for multi-lingual texts. Use
    * {@link #getLanguages()} to obtain the list of available languages in a VD_
    * file.
    * 
    * @param language - the language to set.
    */
   public synchronized void setLanguage(String language)
   {
      if (languages == null)
         getLanguages();

      if (languages.containsKey(language))
         languageId = languages.get(language);
      else languageId = 0;
   }

   /**
    * Set the language-id of the language that shall be used for multi-lingual
    * texts.
    * 
    * @param languageId - the language ID to set.
    *
    * @see #setLanguage(String)
    */
   public synchronized void setLanguageId(int languageId)
   {
      this.languageId = languageId;
   }

   /**
    * @return the language-id that is currently set.
    */
   public synchronized int getLanguageId()
   {
      return languageId;
   }

   /**
    * @return the list of available languages.
    */
   public synchronized Set<String> getLanguages()
   {
      if (languages == null)
      {
         languages = new TreeMap<String, Integer>();
         VdxTable table;
         try
         {
            table = getTable("ete_language");
            if (table == null)
               return null;

            final int langIdIdx = table.getHeader().getIndexOf("language_id");
            final int langNameIdx = table.getHeader().getIndexOf("language_name");

            for (int idx = table.getRowCount() - 1; idx >= 0; --idx)
            {
               languages.put(table.getValue(idx, langNameIdx), table.getIntValue(idx, langIdIdx));
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      return languages.keySet();
   }
}
