package org.selfbus.sbtools.vdio;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.vdio.internal.VDTableColumn;
import org.selfbus.sbtools.vdio.internal.VDTableInfo;
import org.selfbus.sbtools.vdio.internal.VDTableInfoManager;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A {@link ContentHandler content handler} for writing VD format streams.
 */
public class VDWritingContentHandler extends DefaultHandler
{
   private static final String INFO_FILE_NAME = "org/selfbus/sbtools/vdio/vd-tables.txt";
   private static final String START_MARKER = "EX-IM";
   private static final String END_MARKER = "XXX";
   private static final String TABLE_SEPARATOR = "-------------------------------------";

   private final VDTableInfoManager infoMgr;
   private final PrintWriter out;

   private enum ElementType
   {
      NONE, DOCUMENT, TABLE, RECORD
   }

   private ElementType elemType;
   private VDTableInfo table;
   private int recordId;

   /**
    * Create a VD format stream writer.
    * 
    * @param outStream - the stream to write
    */
   public VDWritingContentHandler(OutputStream outStream)
   {
      InputStream infoStream = ClassLoader.getSystemResourceAsStream(INFO_FILE_NAME);
      Validate.notNull(infoStream, "VD tables info not found in class path: " + INFO_FILE_NAME);

      infoMgr = new VDTableInfoManager(infoStream);
      out = new PrintWriter(outStream);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void startDocument() throws SAXException
   {
      out.println(START_MARKER);

      elemType = ElementType.NONE;
      table = null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void endDocument() throws SAXException
   {
      out.println(END_MARKER);
      out.flush();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
   {
      if (elemType == ElementType.NONE)
      {
         elemType = ElementType.DOCUMENT;

         out.println("N " + atts.getValue("name"));
         out.println("K " + atts.getValue("comment"));
         out.println("D " + atts.getValue("created"));
         out.println("V " + atts.getValue("version"));
         out.println("H " + qName);
      }
      else if (elemType == ElementType.DOCUMENT)
      {
         elemType = ElementType.TABLE;

         table = infoMgr.getTable(qName);
         Validate.notNull(table, "unknown VD table: " + qName);
         recordId = 0;

         out.println(TABLE_SEPARATOR);
         out.println("T " + table.id + " " + table.name);
         writeColumnsHeader(table);
      }
      else if (elemType == ElementType.TABLE)
      {
         elemType = ElementType.RECORD;
         ++recordId;

         Validate.isTrue(table.name.equals(qName), "element name \"" + qName + "\" does not match table name \""
            + table.name + "\"");

         out.println("R " + recordId + " T " + table.id + " " + table.name);
         writeRecord(table, atts);

      }
      else
      {
         throw new SAXException("cannot have sub elements below records");
      }
   }

   /**
    * Write a table record.
    * 
    * @param table - the table to write the record for
    * @param atts - the attributes to write
    */
   private void writeRecord(VDTableInfo table, Attributes atts)
   {
      for (int i = 0; i < table.columns.size(); ++i)
      {
         VDTableColumn col = table.columns.get(i);
         String value = atts.getValue(col.name);

         Validate.isTrue(value != null || col.nullAllowed,
            "Mandatory column " + col.name + " in table " + table.name + " has no value for record #" + recordId);

         if (value == null)
         {
            value = "";
         }
         else if (value.indexOf('\n') >= 0)
         {
            value = value.replaceAll("\n", "\n\\\\\\\\");
         }

         out.println(value);
      }
   }

   /**
    * Write the columns of the table.
    * 
    * @param table - the table to write the columns.
    */
   private void writeColumnsHeader(VDTableInfo table)
   {
      for (int i = 0; i < table.columns.size(); ++i)
      {
         VDTableColumn col = table.columns.get(i);

         out.println("C" + i + " T" + table.id + " " + col.type + " " + col.length + " "
            + (col.nullAllowed ? "Y" : "N") + " " + col.name);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException
   {
      if (elemType == ElementType.TABLE)
      {
         elemType = ElementType.DOCUMENT;
         table = null;
      }
      else if (elemType == ElementType.RECORD)
      {
         elemType = ElementType.TABLE;
      }
      else if (elemType == ElementType.DOCUMENT)
      {
      }
      else
      {
         throw new SAXException("Internal error: encountered element type " + elemType.toString() + " in endElement");
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void characters(char[] ch, int start, int length) throws SAXException
   {
   }
}
