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
import org.selfbus.sbtools.vdio.model.VD;
import org.selfbus.sbtools.vdio.model.VdManufacturer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestVdReader
{
   @Test
   public void testRead() throws JAXBException, FileNotFoundException, SAXException
   {
      final JAXBContext context = JAXBContext.newInstance("org.selfbus.sbtools.vdio.model");

      final String schemaFileName = "org/selfbus/sbtools/vdio/vd.xsd";
      final URL schemaUrl = getClass().getClassLoader().getResource(schemaFileName);
      if (schemaUrl == null)
         throw new RuntimeException("Schema file not found in class path: " + schemaFileName);

      Unmarshaller unmarshaller = context.createUnmarshaller();
      unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl));
//      unmarshaller.setSchema(null);

      VDReader vdReader = new VDReader();
      vdReader.setFeature("debug", true);

      InputStream in = new FileInputStream("src/test/resources/test-device.vd_");
      SAXSource source = new SAXSource(vdReader, new InputSource(in));

      @SuppressWarnings("unchecked")
      JAXBElement<VD> root = (JAXBElement<VD>) unmarshaller.unmarshal(source);
      assertNotNull(root);

      VD vd = root.getValue();
      assertNotNull(vd);

      assertEquals("3.19", vd.version);
      assertEquals("/ETS/ETS_PDB/ets2.vd_", vd.name);

      List<VdManufacturer> manufacturers = vd.manufacturers;
      assertNotNull("manufacturers!=null", manufacturers);
      assertEquals(2, manufacturers.size());
      assertEquals(1, manufacturers.get(0).getId());
      assertEquals("Siemens", manufacturers.get(0).getName());

      assertEquals(2, vd.functionalEntities.size());
      assertEquals("Phys. Sensoren", vd.functionalEntities.get(0).getName());

      assertEquals(1, vd.bcuTypes.size());
      assertEquals(1, vd.symbols.size());
      assertEquals(1, vd.products.size());
      assertEquals(1, vd.catalogEntries.size());
      assertEquals(1, vd.mediumTypes.size());

      assertEquals(1, vd.masks.size());
      assertEquals(497, vd.masks.get(0).getId());

      assertEquals(1, vd.assemblers.size());
      assertEquals("IASM", vd.assemblers.get(0).getName());

      assertEquals(1, vd.programs.size());
      assertEquals("12 S2 Helligkeit und Temperatur 221C01", vd.programs.get(0).getName());

      assertEquals(1, vd.virtualDevices.size());
      assertEquals("Kombisensor AP 254, Helligkeit/Temperatur/Schalten", vd.virtualDevices.get(0).getName());

      assertEquals(43, vd.maskEntries.size());
      assertEquals("AllocBuf", vd.maskEntries.get(0).getName());

      assertEquals(1, vd.deviceInfos.size());
      assertEquals("BCU 1", vd.deviceInfos.get(0).getName());

      assertEquals(13, vd.languages.size());
      assertEquals("Danish", vd.languages.get(0).getName());

      assertEquals(1, vd.deviceInfoValues.size());
      assertEquals(20783, vd.deviceInfoValues.get(0).getId());

      assertEquals(3, vd.parameterAtomicTypes.size());
      assertEquals("unsigned", vd.parameterAtomicTypes.get(0).getName());

      assertEquals(197, vd.parameterValues.size());
      assertEquals("8000 Lux", vd.parameterValues.get(0).getLabel());

      assertEquals(238, vd.parameters.size());
      assertEquals("Test", vd.parameters.get(0).getName());

      assertEquals(3, vd.objectTypes.size());
      assertEquals(7, vd.objectTypes.get(1).getId());
      assertEquals("1 Byte", vd.objectTypes.get(1).getName());

      assertEquals(1, vd.objectPriorities.size());
      assertEquals("Low", vd.objectPriorities.get(0).getName());

      assertEquals(25, vd.communicationObjects.size());
      assertEquals("Temperaturschwelle 1", vd.communicationObjects.get(0).getName());

      assertEquals(1046, vd.textAttributes.size());
      assertEquals("kleiner als Helligkeitsschwelle 1", vd.textAttributes.get(2).getText());

      assertEquals(4, vd.productDescriptions.size());
      assertEquals("info", vd.productDescriptions.get(1).getText());
      assertEquals(1036, vd.productDescriptions.get(1).getLanguageId());

      assertEquals(12, vd.helpFiles.size());
      assertEquals(1036, vd.productDescriptions.get(1).getLanguageId());

      assertEquals(2, vd.product2programs.size());
      assertEquals(260420, vd.product2programs.get(0).getId());
      assertEquals(23301, vd.product2programs.get(0).getProgramId());
      assertEquals("2002-05-29 14:27:28.600", vd.product2programs.get(0).getRegistrationTS());

      assertEquals(2, vd.prod2prog2mts.size());
      assertEquals(260932, vd.prod2prog2mts.get(1).getId());
      assertEquals(0, vd.prod2prog2mts.get(1).getMediumTypeId());

      assertEquals(4, vd.programDescriptions.size());
      assertEquals("Description  - see file:  221C01_apb_e.pdf", vd.programDescriptions.get(1).getText());

      assertEquals(25, vd.deviceObjects.size());
      assertEquals(8, vd.deviceObjects.get(1).getObjectTypeId());

      assertEquals(238, vd.deviceParameters.size());
      assertEquals(733310, vd.deviceParameters.get(0).getId());
   }
}
