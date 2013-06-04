package org.selfbus.sbtools.vdio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import test.model.Manufacturer;
import test.model.VD;

public class TestVdReader
{
   @Test
   public void test() throws JAXBException, FileNotFoundException, SAXException
   {
      final JAXBContext context = JAXBContext.newInstance("test.model");

      final String schemaFileName = "org/selfbus/sbtools/vdio/vd.xsd";
      final URL schemaUrl = getClass().getClassLoader().getResource(schemaFileName);
      if (schemaUrl == null)
         throw new RuntimeException("Schema file not found in class path: " + schemaFileName);

      Unmarshaller unmarshaller = context.createUnmarshaller();
      unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl));
//      unmarshaller.setSchema(null);

      VDReader vdReader = new VDReader();
      vdReader.setFeature("debug", true);

      InputStream in = new FileInputStream("src/test/resources/test-device-short.vd_");
      SAXSource source = new SAXSource(vdReader, new InputSource(in));

      @SuppressWarnings("unchecked")
      JAXBElement<VD> root = (JAXBElement<VD>) unmarshaller.unmarshal(source);
      assertNotNull(root);

      VD vd = root.getValue();
      assertNotNull(vd);

      assertEquals("3.19", vd.version);
      assertEquals("/ETS/ETS_PDB/ets2.vd_", vd.name);

      List<Manufacturer> manufacturers = vd.manufacturers;
      assertNotNull("manufacturers!=null", manufacturers);
      assertEquals(2, manufacturers.size());
      assertEquals(1, manufacturers.get(0).id);
      assertEquals("Siemens", manufacturers.get(0).name);
   }
}
