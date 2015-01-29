package org.selfbus.sbtools.vdio;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.vdio.internal.AbstractXmlReader;
import org.selfbus.sbtools.vdio.internal.I18n;
import org.selfbus.sbtools.vdio.internal.TableFieldType;
import org.selfbus.sbtools.vdio.internal.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

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

   private final VdioAttributes recordAtts = new VdioAttributes();
   private final VdioAttributes tableAtts = new VdioAttributes();
   private BufferedReader reader;
   private String documentName;
   private String currentLine;
   private TableInfo tableInfo;
   private boolean debug, started;

   /**
    * @return True if at least parseDocument was called.
    */
   public boolean isStarted()
   {
      return started;
   }

   /**
    * @return The current line number of reading.
    */
   public int getLineNo()
   {
      return lineNo;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void parseDocument(BufferedReader reader) throws SAXException, IOException
   {
      this.reader = reader;

      started = true;
      lineNo = 0;

      debug = getFeature("debug");

      try
      {
         contentHandler.startDocument();
         parseHeader();

         while (!atEnd())
         {
            parseTable();
         }

         contentHandler.endElement(null, documentName, documentName);
         contentHandler.endDocument();
      }
      finally
      {
         readRest();
      }
   }

   /**
    * Parse the header.
    *
    * @throws SAXParseException
    * @throws IOException
    */
   private void parseHeader() throws SAXException, IOException
   {
      String version = "";
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
         {
            version = value;
            recordAtts.addAttribute("", "version", "version", "xs:string", value);
         }
         else if ("D".equals(type))
            recordAtts.addAttribute("", "created", "created", "xs:string", value);
         else if ("N".equals(type))
            recordAtts.addAttribute("", "name", "name", "xs:string", value);
         else if ("H".equals(type))
            documentName = value.toLowerCase().intern();
         else if (line.length() > 2 && !" ".equals(line.substring(1, 2)))
            throw new SAXParseException("Malformed header line", locator);
      }

      Validate.notNull(documentName, "document name is undefined in the file header");
      LOGGER.debug("Document is \"{}\", version {}", documentName, version);

      contentHandler.startElement("", documentName, documentName, recordAtts);
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
      String tableName = tableInfo.name.intern();
      contentHandler.startElement(null, tableName, tableName, tableAtts);

      while (currentLine != null && !TABLE_SEPARATOR.equals(currentLine) && !END_INDICATOR.equals(currentLine))
      {
         String[] parts = currentLine.split(" ", 5);
         if (!"R".equals(parts[0]) || !"T".equals(parts[2]) || tableInfo.id != Integer.parseInt(parts[3]))
            throw new SAXParseException("Malformed line, expected a R record start line for table " + tableInfo.id,
               locator);

         parseTableRecord();
      }

      contentHandler.endElement(null, tableName, tableName);
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
         tableInfo.fieldNames.add(parts[5].toLowerCase().intern());

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

      int startLineNo = lineNo;
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

         if (i > 0 && !value.isEmpty())
         {
            String name = tableInfo.fieldNames.get(i - 1);
            String xsType = tableInfo.fieldTypes.get(i - 1).xsType;

            // Work around bogous field contents (happens e.g. when importing from VD Builder)
            if ("xs:hexBinary".equals(xsType) && value.length() == 1)
               value = "0" + value;

            recordAtts.addAttribute("", name, name, xsType, value);
         }

         if (i == numFields)
            break;

         value = line;
         ++i;
      }

      String recordName = tableInfo.name.intern();

      if (startLineNo == 3359)
      {
         LOGGER.debug("debug point");
      }

      try
      {
         contentHandler.startElement(null, recordName, recordName, recordAtts);
         contentHandler.endElement(null, recordName, recordName);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         readRest();

         String fieldName = recordAtts.getQName(recordAtts.getLastIndex());
         LOGGER.warn("line " + startLineNo + ": annotation missing in target Java class for table {} field {} (class in package org.selfbus.sbtools.vdio.model)", tableInfo.name, fieldName);
         throw e; //new SAXException(I18n.formatMessage("VDReader.unknownFieldError", Integer.toString(startLineNo), tableInfo.name, fieldName), e);
      }
      catch (Exception e)
      {
         LOGGER.warn("Exception while parsing " + recordName + " near line " + startLineNo + ". Parameters: " + attsToString(recordAtts), e);
         throw new SAXException(I18n.formatMessage("VDReader.parseError", Integer.toString(startLineNo)), e);
      }

      currentLine = line;
   }

   /**
    * Convert the attributes to a human readable string
    *
    * @param atts - the attributes
    * @return A string with the attributes
    */
   private String attsToString(VdioAttributes atts)
   {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < atts.getLength(); ++i)
      {
         if (i > 0)
            builder.append(", ");
         builder.append(atts.getType(i)).append(" ").append(atts.getQName(i)).append("=\"").append(atts.getValue(i)).append("\"");
      }
      return builder.toString();
   }

   /**
    * Read with the reader to the end of the file. This is required because the ZipFile library will else throw
    * an exception on close().
    */
   private void readRest()
   {
      try
      {
         while (reader.readLine() != null)
            ;
      }
      catch (IOException e)
      {
         // Ignore IO exceptions, this method is called when the reader is about to being closed
         // anyways.
      }
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
