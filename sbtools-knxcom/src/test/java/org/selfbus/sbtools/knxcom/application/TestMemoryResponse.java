package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;

public class TestMemoryResponse
{
   @Test
   public final void testMemoryResponse()
   {
      final MemoryResponse app = new MemoryResponse();

      assertEquals(ApplicationType.Memory_Response, app.getType());
      assertEquals(0, app.getAddress());
      assertEquals(0, app.getCount());
      assertNull(app.getData());
      assertNotNull(app.toString());
      assertNotNull(app.hashCode());
   }

   @Test
   public final void testMemoryResponseIntIntArray()
   {
      final byte[] data = HexString.valueOf("01 02 03");
      final MemoryResponse app = new MemoryResponse(1230, data);

      assertNotNull(app.toString());
      assertNotNull(app.hashCode());

      assertEquals(1230, app.getAddress());
      assertEquals(3, app.getCount());
      assertArrayEquals(new byte[] { 1, 2, 3 }, app.getData());

      data[0] = 0;
      assertArrayEquals(new byte[] { 1, 2, 3 }, app.getData());

      new MemoryResponse(10900, null);
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testMemoryResponseIntIntArrayTooLarge()
   {
      new MemoryResponse(4000, new byte[64]);
   }

   @Test
   public final void testEqualsObject()
   {
      final MemoryResponse app1 = new MemoryResponse(1230, new byte[] { 1, 2, 3 });
      final MemoryResponse app2 = new MemoryResponse(1230, new byte[] { 1, 2, 3 });

      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app1));
      assertTrue(app1.equals(app2));

      app2.setData(new byte[] { 1, 2, 3, 4 });
      assertFalse(app1.equals(app2));

      app1.setData(null);
      assertFalse(app1.equals(app2));

      app1.setData(new byte[] { 1, 2, 3, 4 });
      app2.setData(null);
      assertFalse(app1.equals(app2));

      app1.setData(null);
      assertTrue(app1.equals(app2));

      app2.setAddress(0);
      assertFalse(app1.equals(app2));
   }

   @Test
   public final void testGetSetData()
   {
      final byte data[] = new byte[] { 1, 2, 3 };
      final MemoryResponse app = new MemoryResponse();

      app.setData(data);
      assertEquals(3, app.getCount());
      assertArrayEquals(new byte[] { 1, 2, 3 }, app.getData());

      data[0] = 0;
      assertArrayEquals(new byte[] { 1, 2, 3 }, app.getData());
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testSetDataTooLarge()
   {
      final MemoryResponse app = new MemoryResponse();
      app.setData(new byte[64]);
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      //app.fromRawData(new int[] { 0x43, 0x10, 0x20, 0x11, 0x12, 0x13, 0x14 }, 0, 7);
      final byte[] data = HexString.valueOf("43 10 20 11 12 13 14");
      final Application gapp = ApplicationFactory.createApplication(2, data);

      assertEquals(ApplicationType.Memory_Response, gapp.getType());
      final MemoryResponse app = (MemoryResponse) gapp;

      assertEquals(3, app.getCount());
      assertEquals(0x1020, app.getAddress());
      assertArrayEquals(new byte[] { 0x11, 0x12, 0x13 }, app.getData());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new MemoryResponse(0x1020, new byte[] { 0x11, 0x12, 0x13 });

      final byte[] expected = HexString.valueOf("02 43 10 20 11 12 13");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }
}
