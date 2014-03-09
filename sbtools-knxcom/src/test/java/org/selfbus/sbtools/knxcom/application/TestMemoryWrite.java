package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.application.AbstractMemoryData;
import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.selfbus.sbtools.knxcom.application.MemoryWrite;

/**
 * Reduced tests, as testing of {@link AbstractMemoryData} is done in
 * {@link TestMemoryResponse}.
 */
public class TestMemoryWrite
{
   @Test
   public final void testMemoryWrite()
   {
      final MemoryWrite app = new MemoryWrite();

      assertEquals(ApplicationType.Memory_Write, app.getType());
      assertEquals(0, app.getAddress());
      assertEquals(0, app.getCount());
      assertNull(app.getData());
      assertNotNull(app.toString());
      assertNotNull(app.hashCode());
   }

   @Test
   public final void testMemoryWriteIntIntArray()
   {
      final byte data[] = new byte[] { 1, 2, 3 };
      final MemoryWrite app = new MemoryWrite(1230, data);

      assertNotNull(app.toString());

      assertEquals(1230, app.getAddress());
      assertEquals(3, app.getCount());
      assertArrayEquals(new byte[] { 1, 2, 3 }, app.getData());

      data[0] = 0;
      assertArrayEquals(new byte[] { 1, 2, 3 }, app.getData());

      new MemoryWrite(10900, null);
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testMemoryWriteIntIntArrayTooLarge()
   {
      new MemoryWrite(1230, new byte[64]);
   }
}
