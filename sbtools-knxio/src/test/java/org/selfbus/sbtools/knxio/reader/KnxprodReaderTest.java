package org.selfbus.sbtools.knxio.reader;

import java.io.File;
import java.io.InputStream;

import net.lingala.zip4j.exception.ZipException;

import org.junit.Assert;
import org.junit.Test;
import org.selfbus.sbtools.knxio.model.KnxDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnxprodReaderTest
{
   private static final Logger LOGGER = LoggerFactory.getLogger(KnxprodReaderTest.class);
   private static final ClassLoader CLASS_LOADER = KnxprodReaderTest.class.getClassLoader();

   @Test
   public void readXml_Master()
   {
      KnxprodReader reader = new KnxprodReader();
      InputStream in = CLASS_LOADER.getResourceAsStream("KnxprodReaderTest.master.xml");
      KnxDocument doc = reader.readXml(in);
      Assert.assertNotNull(doc);

      LOGGER.info("Master: {}", doc);
   }

 @Test
 public void readXml_Catalog()
 {
    KnxprodReader reader = new KnxprodReader();
    InputStream in = CLASS_LOADER.getResourceAsStream("KnxprodReaderTest.Catalog.xml");
    KnxDocument doc = reader.readXml(in);
    Assert.assertNotNull(doc);

    LOGGER.info("Catalog: {}", doc);
 }

   @Test
   public void readXml_Hardware()
   {
      KnxprodReader reader = new KnxprodReader();
      InputStream in = CLASS_LOADER.getResourceAsStream("KnxprodReaderTest.Hardware.xml");
      KnxDocument doc = reader.readXml(in);
      Assert.assertNotNull(doc);

      LOGGER.info("Hardware: {}", doc);
   }

   // You need to copy a knxprod file to src/test/resources/KnxprodReaderTest.knxprod
   // before you can run this test. Please do not commit an existing knxprod file.
   @Test
   public void read() throws ZipException
   {
      KnxprodReader reader = new KnxprodReader();
      reader.read(new File("src/test/resources/KnxprodReaderTest.knxprod"));
   }
}
