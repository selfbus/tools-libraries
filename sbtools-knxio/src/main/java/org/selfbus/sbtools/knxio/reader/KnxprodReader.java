package org.selfbus.sbtools.knxio.reader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.knxio.model.KnxDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * A reader for KNXPROD ZIP files.
 *
 * The contents of a KNXPROD file is e.g.:
 *
 * knx_master.xml
 * M-0083.signature
 * M-0083/
 * M-0083/Catalog.xml
 * M-0083/Hardware.xml
 * M-0083/M-0083_A-001F-11-0EA6.xml
 */
public class KnxprodReader
{
   private static final Logger LOGGER = LoggerFactory.getLogger(KnxprodReader.class);

   /**
    * The name of the schema file
    */
   public static final String SCHEMA_FILE_NAME = "org.selfbus.sbtools.knxio/knx_all.xsd";

   /**
    * Read a ZIP file.
    *
    * @param file The ZIP file to read
    * @throws ZipException
    */
   public void read(File file) throws ZipException
   {
      ZipFile zip = new ZipFile(file);

      @SuppressWarnings("unchecked")
      List<FileHeader> fileHeaders = zip.getFileHeaders();

      for (FileHeader fileHeader : fileHeaders)
      {
         LOGGER.info("ZIP contains: {}", fileHeader.getFileName());

         if (fileHeader.isDirectory() && fileHeader.getFileName().toUpperCase().matches("^M-\\d+\\/*$"))
         {
            readManufacturerFiles(zip, fileHeader.getFileName().replace("/", ""));
         }
      }
   }

   /**
    * Read the files of a manufacturer.
    *
    * @param zip The ZIP file to read from
    * @param manufacturerKey The zip file key of the manufacturer, e.g. "M-0004"
    * @throws ZipException
    */
   void readManufacturerFiles(ZipFile zip, String manufacturerKey) throws ZipException
   {
      LOGGER.info("Reading files of manufacturer {}", manufacturerKey);

      String catalogFileName = manufacturerKey + "/Catalog.xml";
      FileHeader catalogFileHeader = zip.getFileHeader(catalogFileName);
      if (catalogFileHeader == null)
      {
         LOGGER.info("Skipping {}: no catalog file {} found", manufacturerKey, catalogFileName);
         return;
      }

      String hardwareFileName = manufacturerKey + "/Hardware.xml";
      FileHeader hardwareFileHeader = zip.getFileHeader(hardwareFileName);
      if (hardwareFileHeader == null)
      {
         LOGGER.info("Skipping {}: no hardware file {} found", manufacturerKey, hardwareFileName);
         return;
      }

      LOGGER.info("Reading {}", catalogFileName);
      KnxDocument catalogDocument = readXml(zip.getInputStream(catalogFileHeader), catalogFileName);

      LOGGER.info("Reading {}", hardwareFileName);
      KnxDocument hardwareDocument = readXml(zip.getInputStream(hardwareFileHeader), hardwareFileName);

      // Process the product files of the manufacturer
      String filePrefix = manufacturerKey + '/' + manufacturerKey + '_';
      @SuppressWarnings("unchecked")
      List<FileHeader> fileHeaders = zip.getFileHeaders();
      for (FileHeader fileHeader : fileHeaders)
      {
         if (fileHeader.getFileName().startsWith(filePrefix))
         {
            LOGGER.info("Reading {}", fileHeader.getFileName());
            KnxDocument productsDocument = readXml(zip.getInputStream(fileHeader), fileHeader.getFileName());
         }
      }
   }

   /**
    * Read an XML document from an input stream, with schema validation.
    * The schema file is searched on the class path.
    *
    * @param in The input stream to read the XML document from
    * @return The root {@link KnxDocument} object of the newly created object tree
    */
   public KnxDocument readXml(InputStream in)
   {
      return readXml(in, "<stream>");
   }

   /**
    * Read an XML document from an input stream, with schema validation.
    * The schema file is searched on the class path.
    *
    * @param in The input stream to read the XML document from
    * @param name The name of the input stream, for error reporting
    * @return The root {@link KnxDocument} object of the newly created object tree
    */
   public KnxDocument readXml(InputStream in, String name)
   {
      Validate.notNull(in, "input stream is null");
      Validate.notNull(name, "name is null");

      try
      {
         final JAXBContext context = JAXBContext.newInstance("org.selfbus.sbtools.knxio.model");

         final URL schemaUrl = getClass().getClassLoader().getResource(SCHEMA_FILE_NAME);
         if (schemaUrl == null)
            throw new RuntimeException("Schema file not found in class path: " + SCHEMA_FILE_NAME);

         Unmarshaller unmarshaller = context.createUnmarshaller();
         unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl));

         SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
         saxParserFactory.setNamespaceAware(true);
         XMLReader reader = saxParserFactory.newSAXParser().getXMLReader();
         SAXSource source = new SAXSource(reader, new InputSource(in));

         JAXBElement<KnxDocument> root = unmarshaller.unmarshal(source, KnxDocument.class);
         return root.getValue();
      }
      catch (JAXBException|SAXException|ParserConfigurationException e)
      {
         throw new RuntimeException("failed to read " + name, e);
      }
   }
}
