package org.selfbus.sbtools.vdio;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXResult;

import org.junit.Test;
import org.xml.sax.SAXException;

import test.model.Manufacturer;
import test.model.ObjectFactory;
import test.model.VD;

public class TestVdWriter
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
      m.name = "Manufacturer\n #10";
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

      SAXResult result = new SAXResult(new VDWritingContentHandler(out));
      marshaller.marshal(jaxbVD, result);

      assertEquals("EX-IM\n"
                  + "N test-vd\n"
                  + "K null\n"
                  + "D 2013-05-14 20:15:10\n"
                  + "V 1.01\n"
                  +"H virtual_device\n"
                  +"-------------------------------------\n"
                  +"T 3 manufacturer\n"
                  +"C1 T3 1 4 N manufacturer_id\n"
                  +"C2 T3 3 50 Y manufacturer_name\n"
                  +"C3 T3 1 4 Y address_id\n"
                  +"R 1 T 3 manufacturer\n"
                  +"10\n"
                  +"Manufacturer\n"
                  +"\\\\ #10\n"
                  +"\n"
                  +"R 2 T 3 manufacturer\n"
                  +"22\n"
                  +"Manufacturer #22\n"
                  +"\n"
                  +"XXX\n"
                  , out.toString());
   }
}
