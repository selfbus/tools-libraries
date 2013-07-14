package org.selfbus.sbtools.prodedit.vdio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.selfbus.sbtools.prodedit.model.ProjectService;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.vdio.VdioException;

public class TestProductsImporter
{
   @Test
   public void read() throws FileNotFoundException, VdioException
   {
      ProductsImporter importer = new ProductsImporter(new ProjectService());
      Project project = importer.read(new File("src/test/resources/test-device.vd_"));
//      Project project = importer.read(new File("src/test/resources/Bosch-Freebus12.vd_"));
      assertNotNull(project);

      assertEquals(4, project.getLanguages().size());
      assertEquals(2, project.getManufacturers().size());
      assertNotNull(project.getManufacturer(72));
   }
}
