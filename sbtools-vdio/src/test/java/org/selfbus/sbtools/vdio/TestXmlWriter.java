package org.selfbus.sbtools.vdio;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.xml.sax.SAXException;

import test.model.Manufacturer;
import test.model.ObjectFactory;
import test.model.VD;

public class TestXmlWriter
{
   protected VD createTestVD()
   {
      VD vd = new VD();
      vd.created = "2013-05-14 20:15:10";
      vd.name = "test-vd";
      vd.version = "1.01";

      vd.manufacturers = new LinkedList<Manufacturer>();

      Manufacturer m = new Manufacturer();
      m.id = 10;
      m.name = "Manufacturer #10";
      vd.manufacturers.add(m);

      m = new Manufacturer();
      m.id = 22;
      m.name = "Manufacturer #22";
      vd.manufacturers.add(m);

      return vd;
   }

   @Test
   public void test() throws JAXBException, FileNotFoundException, SAXException
   {
      final JAXBContext context = JAXBContext.newInstance("test.model");

      final Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

      final ObjectFactory objectFactory = new ObjectFactory();
      JAXBElement<VD> jaxbVD = objectFactory.createVD(createTestVD());

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      marshaller.marshal(jaxbVD, out);

      assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
         + "<virtual_device name=\"test-vd\" created=\"2013-05-14 20:15:10\" version=\"1.01\">\n"
         + "    <manufacturer>\n"
         + "        <manufacturer manufacturer_name=\"Manufacturer #10\" manufacturer_id=\"10\"/>\n"
         + "        <manufacturer manufacturer_name=\"Manufacturer #22\" manufacturer_id=\"22\"/>\n"
         + "    </manufacturer>\n"
         + "</virtual_device>\n", out.toString());
   }
}
