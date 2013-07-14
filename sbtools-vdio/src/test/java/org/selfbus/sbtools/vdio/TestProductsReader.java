package org.selfbus.sbtools.vdio;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.junit.BeforeClass;
import org.junit.Test;
import org.selfbus.sbtools.vdio.model.VD;

public class TestProductsReader
{
   static private JFrame frame = new JFrame();

   @BeforeClass
   static public void beforeClass()
   {
      frame.setSize(50, 30);
//    frame.setVisible(true);
   }

   @Test
   public void testReadEmptyTable() throws FileNotFoundException, VdioException
   {
      ProductsReader target = new ProductsReader(frame);
      target.setInteractive(false);

      VD vd = target.read(new File("src/test/resources/test-device-short-2.vd_"));
      assertNotNull(vd);
   }

   @Test
   public void testReadZipPasswd() throws FileNotFoundException, VdioException
   {
      ProductsReader target = new ProductsReader(frame);
      target.setInteractive(false);
      target.setZipPassword("test");

      VD vd = target.read(new File("src/test/resources/test-device-short-passwd.vd1"));
      assertNotNull(vd);
   }

   @Test(expected = VdioException.class)
   public void testReadZipWrongPasswd() throws FileNotFoundException, VdioException
   {
      ProductsReader target = new ProductsReader(frame);
      target.setInteractive(false);
      target.setZipPassword("wrong-password");

      target.read(new File("src/test/resources/test-device-short-passwd.vd1"));
   }

   @Test
   public void testReadZip() throws FileNotFoundException, VdioException
   {
      ProductsReader target = new ProductsReader(frame);
      target.setInteractive(false);

      VD vd = target.read(new File("src/test/resources/test-device-short.vd1"));
      assertNotNull(vd);
   }
}
