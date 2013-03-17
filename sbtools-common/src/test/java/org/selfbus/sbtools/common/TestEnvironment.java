package org.selfbus.sbtools.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.selfbus.sbtools.common.Environment;

public class TestEnvironment
{
   private String appDirName;

   @Before
   public final void setUp()
   {
      Environment.disposeInstance();
      appDirName = null;
   }

   @After
   public final void tearDown()
   {
      Environment.disposeInstance();

      if (appDirName != null && !appDirName.isEmpty())
      {
         final File appDirFile = new File(appDirName);
         if (appDirFile.exists())
            appDirFile.delete();
      }
   }

   @Test
   public final void testEnvironment()
   {
      Environment.init();
      assertNotNull(Environment.getInstance());
      assertEquals("fts", Environment.getAppName());
   }

   @Test
   public final void testLinux()
   {
      Environment.disposeInstance();
      System.setProperty("os.name", "Linux x86_64");

      assertEquals("linux", Environment.getOS());
      assertEquals("/tmp", Environment.getTempDir());
      assertNotNull(Environment.getHomeDir());
      assertNotNull(Environment.getAppDir());
      assertEquals("home/.appName", Environment.getAppDir("home", "appName"));
   }

   @Test
   public final void testWindows()
   {
      Environment.disposeInstance();
      System.setProperty("os.name", "Windows-123");

      assertEquals("windows", Environment.getOS());
      assertEquals("c:/windows/temp", Environment.getTempDir());
      assertNotNull(Environment.getHomeDir());
      assertNotNull(Environment.getAppDir());
      assertEquals("home/appName", Environment.getAppDir("home", "appName"));
   }

   @Test
   public final void testOther()
   {
      Environment.disposeInstance();
      System.setProperty("os.name", "Unknown-System-1");

      assertEquals("other", Environment.getOS());
      assertEquals("/tmp", Environment.getTempDir());
      assertNotNull(Environment.getHomeDir());
      assertNotNull(Environment.getAppDir());
      assertEquals("home/appName", Environment.getAppDir("home", "appName"));
   }

   @Test
   public final void testGetSetAppName()
   {
      Environment.disposeInstance();

      appDirName = Environment.getAppDir(Environment.getHomeDir(), "fts-test");
      Environment.setAppName("fts-test");
      assertEquals("fts-test", Environment.getAppName());
      assertTrue((new File(Environment.getAppDir())).isDirectory());
   }
}
