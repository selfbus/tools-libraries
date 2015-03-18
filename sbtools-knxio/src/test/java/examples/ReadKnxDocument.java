package examples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.selfbus.sbtools.knxio.model.KnxDocument;
import org.selfbus.sbtools.knxio.reader.KnxprodReader;

/**
 * This example reads a XML file of a knxprod file
 */
public class ReadKnxDocument
{
   // The file to load
   public static final String FILE_NAME = "/tmp/knx_master.xml";

   public static void main(String[] args) throws FileNotFoundException
   {
      InputStream in = new FileInputStream(FILE_NAME);

      KnxprodReader reader = new KnxprodReader();
      KnxDocument doc = reader.readXml(in);

      System.out.println(doc);
   }
}
