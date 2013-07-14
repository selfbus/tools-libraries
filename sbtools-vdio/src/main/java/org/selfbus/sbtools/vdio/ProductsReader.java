package org.selfbus.sbtools.vdio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.vdio.internal.AbstractZipPasswordHandler;
import org.selfbus.sbtools.vdio.model.VD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Read a ETS products VD or ZIP file.
 */
public class ProductsReader extends AbstractZipPasswordHandler
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ProductsReader.class);
   private boolean parsingStarted;

   /**
    * Create a products reader.
    */
   public ProductsReader()
   {
      super();
   }

   /**
    * Create a products reader. The parent frame is used for the ZIP password dialog.
    * 
    * @param parentFrame - the frame of the window invoking the reader
    */
   public ProductsReader(JFrame parentFrame)
   {
      super(parentFrame);
   }

   /**
    * Read the VD file and create a {@link VD} from it. If the file is a ZIP file, the .vd_ file is
    * extracted from it.
    * 
    * @param file - the VD file or ZIP archive to read.
    * @return The created VD, or null if the user canceled loading
    * 
    * @throws FileNotFoundException if the file does not exist
    * @throws VdioException in case of parse or read errors
    */
   public VD read(File file) throws FileNotFoundException, VdioException
   {
      if (file.getName().toLowerCase().matches(".*\\.vd[0-9]"))
      {
         LOGGER.debug("Reading ZIP file {}", file);
         return readZip(file);
      }
      else
      {
         LOGGER.debug("Reading plain file {}", file);
         return read(new FileInputStream(file));
      }
   }

   /**
    * Read the ZIP file, handle password protection.
    * 
    * @param file - the ZIP file to read
    * @return The created VD, or null if the user canceled loading
    * 
    * @throws VdioException in case of unzip problems
    */
   protected VD readZip(File file) throws VdioException
   {
      boolean wrongPassword = false;
      boolean isEncrypted = false;

      while (true)
      {
         parsingStarted = false;
         ZipFile zipFile;

         try
         {
            zipFile = new ZipFile(file);
            isEncrypted = zipFile.isEncrypted();

            if (isEncrypted)
            {
               if (zipPasswd == null)
               {
                  if (interactive)
                  {
                     String newPasswd = askZipPassword(wrongPassword);
                     if (newPasswd == null)
                        return null;
                     zipPasswd = newPasswd;
                  }
                  else
                  {
                     LOGGER.info("ZIP file is password protected, but no password is given");
                     return null;
                  }
               }

               if (zipPasswd != null && !zipPasswd.isEmpty())
                  zipFile.setPassword(zipPasswd);
            }

            @SuppressWarnings("unchecked")
            List<FileHeader> fileHeaders = zipFile.getFileHeaders();

            VD vd = null;
            for (FileHeader fileHeader : fileHeaders)
            {
               String name = fileHeader.getFileName().toLowerCase(); 
               if (name.endsWith(".vd_"))
               {
                  LOGGER.debug("ZIP: reading {}", fileHeader.getFileName());
                  vd = read(zipFile.getInputStream(fileHeader));
               }
//               else LOGGER.debug("ZIP: ignoring {}", fileHeader.getFileName());
            }

            return vd;
         }
         catch (VdioException e)
         {
            if (interactive && e.getMessage().toLowerCase().indexOf("wrong password") >= 0 && isEncrypted && !parsingStarted)
            {
               wrongPassword = true;
               zipPasswd = null;
               continue;
            }
            throw e;
         }
         catch (ZipException e)
         {
            throw new VdioException(e);
         }
      }
   }

   /**
    * Read the VD from the input stream.
    * 
    * @param in - the stream to read from.
    * @return The read project.
    * 
    * @throws VdioException in case of parse errors
    */
   public VD read(InputStream in) throws VdioException
   {
      Validate.notNull(in, "input stream is null");

      VDReader vdReader = new VDReader();
      JAXBContext context;
      parsingStarted = false;

      try
      {
         context = JAXBContext.newInstance("org.selfbus.sbtools.vdio.model");

         final String schemaFileName = "org/selfbus/sbtools/vdio/vd.xsd";
         final URL schemaUrl = getClass().getClassLoader().getResource(schemaFileName);
         if (schemaUrl == null)
            throw new VdioException("Schema file not found in class path: " + schemaFileName);

         Unmarshaller unmarshaller = context.createUnmarshaller();
         unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl));

//         vdReader.setFeature("debug", true);

         SAXSource source = new SAXSource(vdReader, new InputSource(new InputStreamReader(in, "ISO-8859-15")));

         @SuppressWarnings("unchecked")
         JAXBElement<VD> root = (JAXBElement<VD>) unmarshaller.unmarshal(source);
         Validate.notNull(root);

         return root.getValue();
      }
      catch (JAXBException | SAXException | UnsupportedEncodingException e)
      {
         parsingStarted = vdReader.isStarted();
         throw new VdioException("Error near line " + vdReader.getLineNo(), e);
      }
   }
}
