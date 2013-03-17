package org.selfbus.sbtools.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.selfbus.sbtools.common.SimpleConfig;

public class TestSimpleConfig
{
   private File tmpFile;

   // Helper method for getting a temporary file name. The temporary
   // file is deleted in tearDown() if it then exists.
   private String getTempFileName() throws IOException
   {
      tmpFile = File.createTempFile("TestSimpleConfig", ".tmp");
      return tmpFile.getAbsolutePath();
   }

   @Before
   public final void setUp()
   {
      SimpleConfig.disposeInstance();
      tmpFile = null;
   }

   @After
   public final void tearDown()
   {
      SimpleConfig.disposeInstance();

      if (tmpFile != null && tmpFile.exists())
         tmpFile.delete();
   }

   @Test
   public final void testGetInstance()
   {
      final SimpleConfig cfg = SimpleConfig.getInstance();

      assertNotNull(SimpleConfig.getInstance());
      assertEquals(cfg, SimpleConfig.getInstance());
   }

   @Test
   public final void testGetSetStringValue()
   {
      final SimpleConfig cfg = new SimpleConfig();

      assertFalse(cfg.containsKey(""));
      assertEquals(null, cfg.get("key-1"));
      assertEquals("", cfg.getStringValue("key-1"));
      assertFalse(cfg.containsKey("key-1"));
      assertFalse(cfg.containsKey("key-2"));

      cfg.put("key-1", "val-1");
      assertTrue(cfg.containsKey("key-1"));
      assertEquals("val-1", cfg.getStringValue("key-1"));
      assertEquals("val-1", cfg.getStringValue("key-1", "default"));
      assertEquals("default", cfg.getStringValue("key-999", "default"));
      assertFalse(cfg.containsKey("key-2"));

      cfg.put("key-2", "val-2");
      assertTrue(cfg.containsKey("key-1"));
      assertTrue(cfg.containsKey("key-2"));
   }

   @Test
   public final void testGetSetIntValue()
   {
      final SimpleConfig cfg = SimpleConfig.getInstance();

      assertFalse(cfg.containsKey(""));
      assertEquals(null, cfg.get("key-1"));
      assertEquals(0, cfg.getIntValue("key-1"));
      assertFalse(cfg.containsKey("key-1"));
      assertFalse(cfg.containsKey("key-2"));

      cfg.put("key-1", 1);
      assertTrue(cfg.containsKey("key-1"));
      assertEquals(1, cfg.getIntValue("key-1"));
      assertEquals(1, cfg.getIntValue("key-1", 123));
      assertEquals(123, cfg.getIntValue("key-999", 123));
      assertFalse(cfg.containsKey("key-2"));

      cfg.put("key-2", 2);
      assertTrue(cfg.containsKey("key-1"));
      assertTrue(cfg.containsKey("key-2"));
      assertEquals(1, cfg.getIntValue("key-1"));
      assertEquals(2, cfg.getIntValue("key-2"));
   }

   @Test
   public final void testClear()
   {
      final SimpleConfig cfg = SimpleConfig.getInstance();

      cfg.clear();
      assertFalse(cfg.containsKey("key-1"));

      cfg.put("key-1", "val-1");
      cfg.put("key-2", "val-2");
      assertTrue(cfg.containsKey("key-1"));

      cfg.clear();
      assertFalse(cfg.containsKey("key-1"));
   }

   @Test
   public final void testInit()
   {
      final SimpleConfig cfg = SimpleConfig.getInstance();
      cfg.init();
   }

   @Test
   public final void testLoadSave() throws IOException
   {
      final SimpleConfig cfg = SimpleConfig.getInstance();
      final String fname = getTempFileName();

      cfg.clear();
      cfg.put("key-1", "val-1");
      cfg.put("key-2", "val-2");
      cfg.save(fname);

      cfg.clear();
      cfg.put("key-1", "invalid-1");

      cfg.load(fname);
      assertEquals("val-1", cfg.getStringValue("key-1"));
      assertEquals("val-2", cfg.getStringValue("key-2"));
   }
}
