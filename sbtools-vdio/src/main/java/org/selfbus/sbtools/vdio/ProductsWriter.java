package org.selfbus.sbtools.vdio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXResult;
import javax.xml.validation.SchemaFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.vdio.internal.AbstractZipPasswordHandler;
import org.selfbus.sbtools.vdio.internal.I18n;
import org.selfbus.sbtools.vdio.model.ObjectFactory;
import org.selfbus.sbtools.vdio.model.VD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Write a ETS products VD or ZIP file.
 */
public class ProductsWriter extends AbstractZipPasswordHandler
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ProductsReader.class);

   /**
    * Create a products writer.
    */
   public ProductsWriter()
   {
      super();
   }

   /**
    * Create a products writer. The parent frame is used for the ZIP password dialog.
    *
    * @param parentFrame - the frame of the window invoking the reader
    */
   public ProductsWriter(JFrame parentFrame)
   {
      super(parentFrame);
   }

   /**
    * Write the {@link VD} to the file. If the file ends with ".vd[0-9]", a ZIP file is created
    * containing the VD. The created ZIP file will be password protected with the ZIP password, if
    * set.
    *
    * @param file - the VD file or ZIP archive to write.
    * @param vd - the VD to write.
    *
    * @throws FileNotFoundException if the file does not exist
    * @throws VdioException in case of parse or read errors
    */
   public void write(File file, VD vd) throws FileNotFoundException, VdioException
   {
      Validate.notNull(file, "file is null");
      Validate.notNull(vd, "VD is null");

      if (file.getName().toLowerCase().matches(".*\\.vd[0-9]"))
      {
         LOGGER.debug("Writing ZIP file {}", file);
         writeZip(file, vd);
      }
      else
      {
         LOGGER.debug("Writing plain VD file {}", file);
         write(new FileOutputStream(file), vd);
      }
   }

   /**
    * Write a ZIP file, handle password protection.
    *
    * @param file - the ZIP file to write
    * @param vd - the VD to write.
    *
    * @throws VdioException in case of zip problems
    */
   protected void writeZip(File file, VD vd) throws VdioException
   {
      File vdFile = null;

      if (!haveZipPassword() && isInteractive())
      {
         int ret = JOptionPane.showOptionDialog(parentFrame, I18n.getMessage("ProductsWriter.askZipWithoutPass"),
            I18n.getMessage("ProductsWriter.confirm"), JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, null, Boolean.TRUE);
         if (ret == JOptionPane.YES_OPTION)
         {
            zipPasswd = askZipPassword(false);
         }
         else if (ret == JOptionPane.CANCEL_OPTION)
         {
            return;
         }
      }

      try
      {
         file.delete();

         ZipFile zipFile = new ZipFile(file);

         vdFile = File.createTempFile("ets_", ".vd_", file.getParentFile());
         write(new FileOutputStream(vdFile), vd);

         ZipParameters zipParams = new ZipParameters();
         zipParams.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
         if (haveZipPassword())
         {
            zipParams.setEncryptFiles(true);
            zipParams.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParams.setPassword(getZipPassword());
         }

         zipParams.setFileNameInZip("ets.vd_");
         zipFile.createZipFile(vdFile, zipParams);
      }
      catch (ZipException | IOException e)
      {
         throw new VdioException(e);
      }
      finally
      {
         if (vdFile != null)
            vdFile.delete();
      }
   }

   /**
    * Write the VD to the output stream in VD format.
    *
    * @param in - the stream to write to.
    * @param vd - the VD to write.
    *
    * @throws VdioException in case of write errors
    */
   public void write(OutputStream out, VD vd) throws VdioException
   {
      Validate.notNull(out, "output stream is null");
      Validate.notNull(vd, "VD is null");

      JAXBContext context;
      try
      {
         context = JAXBContext.newInstance("org.selfbus.sbtools.vdio.model");

         final String schemaFileName = "org/selfbus/sbtools/vdio/vd.xsd";
         final URL schemaUrl = getClass().getClassLoader().getResource(schemaFileName);
         if (schemaUrl == null)
            throw new VdioException("Schema file not found in class path: " + schemaFileName);

         final Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
         marshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl));

         final ObjectFactory objectFactory = new ObjectFactory();
         JAXBElement<VD> jaxbVD = objectFactory.createVD(vd);

         SAXResult result = new SAXResult(new VDWritingContentHandler(out));
         marshaller.marshal(jaxbVD, result);
      }
      catch (JAXBException | SAXException e)
      {
         throw new VdioException(e);
      }
   }
}
