package org.selfbus.sbtools.vdio;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.vdio.internal.AbstractXmlReader;
import org.selfbus.sbtools.vdio.internal.TableFieldType;
import org.selfbus.sbtools.vdio.internal.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

/**
 * A {@link XMLReader XML reader} for VD files.
 * <p>
 * Table and field names are converted to lower case.
 * <p>
 * Available {@link #setFeature(String, boolean) features}:
 * <ul>
 * <li>debug - enable debug log output
 * </ul>
 */
public class VDReader extends AbstractXmlReader
{
   private static final Logger LOGGER = LoggerFactory.getLogger(VDReader.class);

   private static final String TABLE_SEPARATOR = "-------------------------------------";
   private static final String START_INDICATOR = "EX-IM";
   private static final String END_INDICATOR = "XXX";

   private final AttributesImpl recordAtts = new AttributesImpl();
   private final AttributesImpl tableAtts = new AttributesImpl();
   private BufferedReader reader;
   private String documentName;
   private String currentLine;
   private TableInfo tableInfo;
   private boolean debug;

   /**
    * {@inheritDoc}
    */
   @Override
   protected void parseDocument(BufferedReader reader) throws SAXException, IOException
   {
      this.reader = reader;
      lineNo = 0;

      debug = features.get("debug");

      contentHandler.startDocument();

      parseHeader();

      while (!atEnd())
      {
         parseTable();
      }

      contentHandler.endElement("", documentName, documentName);
      contentHandler.endDocument();
   }

   /**
    * Parse the header.
    * 
    * @throws SAXParseException
    * @throws IOException
    */
   private void parseHeader() throws SAXException, IOException
   {
      documentName = null;
      recordAtts.clear();

      String line = reader.readLine();
      ++lineNo;

      if (!START_INDICATOR.equals(line))
         throw new SAXParseException("Document does not start with \"" + START_INDICATOR + "\"", locator);

      while (true)
      {
         line = readLine();

         if (line == null)
            break;

         String type = line.substring(0, 1);
         String value = line.substring(2);

         if ("V".equals(type))
            recordAtts.addAttribute("", "version", "version", "xs:string", value);
         else if ("D".equals(type))
            recordAtts.addAttribute("", "created", "created", "xs:string", value);
         else if ("N".equals(type))
            recordAtts.addAttribute("", "name", "name", "xs:string", value);
         else if ("H".equals(type))
            documentName = value.toLowerCase();
         else if (line.length() > 2 && !" ".equals(line.substring(1, 2)))
            throw new SAXParseException("Malformed header line", locator);
      }

      Validate.notNull(documentName, "document name is undefined in the file header");
      LOGGER.debug("Document is \"{}\"", documentName);

      // OpenJDK bug?  Replacing the string constant "virtual_device" with
      // the variable documentName breaks the reader.
      contentHandler.startElement("", "virtual_device", documentName, recordAtts);
   }

   /**
    * Parse a table section.
    * 
    * @throws SAXParseException
    * @throws IOException
    */
   private void parseTable() throws SAXException, IOException
   {
      parseTableHeader();

      if (debug)
         LOGGER.debug("Parsing table {} {}", tableInfo.id, tableInfo.name);

      tableAtts.clear();
      String tableName = tableInfo.name;
      contentHandler.startElement("", tableName, tableName, tableAtts);

      while (currentLine != null && !TABLE_SEPARATOR.equals(currentLine) && !END_INDICATOR.equals(currentLine))
      {
         String[] parts = currentLine.split(" ", 5);
         if (!"R".equals(parts[0]) || !"T".equals(parts[2]) || tableInfo.id != Integer.parseInt(parts[3]))
            throw new SAXParseException("Malformed line, expected a R record start line for table " + tableInfo.id,
               locator);

         parseTableRecord();
      }

      contentHandler.endElement("", tableName, tableName);
   }

   /**
    * Parse a table header.
    */
   private void parseTableHeader() throws SAXException, IOException
   {
      String line = readLine();
      if (line == null)
         return;

      String[] parts = line.split(" ", 3);

      if (!"T".equals(parts[0]))
         throw new SAXParseException("Malformed line, expected a T table start line", locator);

      tableInfo = new TableInfo(Integer.parseInt(parts[1]), parts[2].toLowerCase());

      while (true)
      {
         line = readLine();
         if (line == null || !line.startsWith("C"))
            break;

         parts = line.split(" ", 6);
         tableInfo.fieldNames.add(parts[5].toLowerCase());

         TableFieldType fieldType = TableFieldType.valueOf(Integer.parseInt(parts[2]));
         if (fieldType == null)
            throw new SAXParseException("Unknown field type number: " + parts[2], locator);
         tableInfo.fieldTypes.add(fieldType);
      }
   }

   /**
    * Parse a table record. Assumes that the R line was already read.
    */
   private void parseTableRecord() throws SAXException, IOException
   {
      int numFields = tableInfo.fieldNames.size();
      String value = "";
      String line = null;

      recordAtts.clear();

      for (int i = 0; i <= numFields; )
      {
         line = reader.readLine();
         ++lineNo;

         if (line == null)
            break;

         if (line.startsWith("\\\\"))
         {
            value += line.substring(2);
            continue;
         }

         if (i == numFields)
            break;

         if (i > 0 && !value.isEmpty())
         {
            String name = tableInfo.fieldNames.get(i - 1);
            recordAtts.addAttribute("", name, name, tableInfo.fieldTypes.get(i - 1).xsType, value);
         }

         value = line;
         ++i;
      }

      String recordName = tableInfo.name;
      contentHandler.startElement("", recordName, recordName, recordAtts);
      contentHandler.endElement("", recordName, recordName);

      currentLine = line;
   }

   /**
    * Read the next line. {@link #currentLine} also contains the read line.
    * 
    * @return The next line or null if the line is a separator line or we are at the end of the
    *         file.
    * @throws IOException
    */
   private String readLine() throws IOException
   {
      currentLine = reader.readLine();
      ++lineNo;

      if (currentLine == null || TABLE_SEPARATOR.equals(currentLine) || END_INDICATOR.equals(currentLine))
         return null;

      return currentLine;
   }

   /**
    * @return True if we are at end of file or have read the {@link #END_INDICATOR end indicator}.
    */
   private boolean atEnd()
   {
      return currentLine == null || END_INDICATOR.equals(currentLine);
   }
}
