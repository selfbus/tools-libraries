package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapper;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapperFactory;
import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

public class TestMemoryRead
{
   @Test
   public final void testMemoryRead()
   {
      final MemoryRead app = new MemoryRead();

      assertEquals(ApplicationType.Memory_Read, app.getType());
      assertEquals(0, app.getAddress());
      assertEquals(0, app.getCount());
      assertNotNull(app.toString());
      assertNotNull(app.hashCode());
   }

   @Test
   public final void testMemoryReadIntInt()
   {
      final MemoryRead app = new MemoryRead(12345, 17);
      assertEquals(12345, app.getAddress());
      assertEquals(17, app.getCount());
   }

   @Test
   public final void testMemoryReadLocation()
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.ApplicationID);
      assertEquals(MemoryLocation.ApplicationID, app.getLocation());

      app.setAddressMapper(MemoryAddressMapperFactory.getMemoryAddressMapper(0x0010));
      assertEquals(5, app.getCount());
      assertEquals(259, app.getAddress());
   }

   @Test(expected = RuntimeException.class)
   public final void testMemoryReadLocationNoMapper()
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.ApplicationID);
      assertEquals(MemoryLocation.ApplicationID, app.getLocation());
      app.getCount();
   }

   @Test(expected = RuntimeException.class)
   public final void testMemoryReadLocationNoMapper2()
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.ApplicationID);
      assertEquals(MemoryLocation.ApplicationID, app.getLocation());
      app.getAddress();
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testMemoryReadIntIntTooSmall()
   {
      new MemoryRead(12345, -1);
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testMemoryReadIntIntTooLarge()
   {
      new MemoryRead(12345, 64);
   }

   @Test
   public final void testEqualsObject()
   {
      final MemoryRead app1 = new MemoryRead(12345, 5);
      final MemoryRead app2 = new MemoryRead(12345, 5);

      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app1));
      assertTrue(app1.equals(app2));

      app2.setCount(7);
      assertFalse(app1.equals(app2));

      app2.setAddress(0);
      assertFalse(app1.equals(app2));
   }

   @Test
   public final void testGetSetCount()
   {
      final MemoryRead app = new MemoryRead();

      app.setCount(41);
      assertEquals(41, app.getCount());

      app.setCount(1);
      assertEquals(1, app.getCount());

      app.setCount(63);
      assertEquals(63, app.getCount());
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testGetSetCountTooSmall()
   {
      final MemoryRead app = new MemoryRead();
      app.setCount(0);
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testGetSetCountTooLarge()
   {
      final MemoryRead app = new MemoryRead();
      app.setCount(64);
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      // app.fromRawData(new int[] { 0x03, 0x10, 0x20 }, 0, 3);
      final byte[] data = HexString.valueOf("03 10 20");
      final Application gapp = ApplicationFactory.createApplication(2, data);

      assertEquals(ApplicationType.Memory_Read, gapp.getType());
      final MemoryRead app = (MemoryRead) gapp;

      assertEquals(3, app.getCount());
      assertEquals(0x1020, app.getAddress());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new MemoryRead(0x1020, 3);

      final byte[] expected = HexString.valueOf("02 03 10 20");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testGetSetAddress()
   {
      final MemoryRead app = new MemoryRead();

      app.setAddress(1704);
      assertEquals(1704, app.getAddress());

      app.setAddress(0);
      assertEquals(0, app.getAddress());
   }

   @Test
   public final void testLocation() throws Exception
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.SystemState, 0, 1);

      assertEquals(MemoryLocation.SystemState, app.getLocation());
      assertEquals(0, app.getOffset());
      assertEquals(1, app.getCount());

      final MemoryAddressMapper mapper = MemoryAddressMapperFactory.getMemoryAddressMapper(0x12);
      assertNotNull(mapper);

      app.setAddressMapper(mapper);
      assertEquals(mapper, app.getAddressMapper());

      assertEquals(96, app.getAddress());
      assertEquals(1, app.getCount());
   }

   @Test
   public final void testLocationToString() throws Exception
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.ApplicationID, 0, 1);

      assertEquals(MemoryLocation.ApplicationID, app.getLocation());
      assertEquals(0, app.getOffset());
      assertEquals(1, app.getCount());

      final MemoryAddressMapper mapper = MemoryAddressMapperFactory.getMemoryAddressMapper(0x12);
      assertNotNull(mapper);

      assertNotNull(app.toString());
   }

   @Test(expected = RuntimeException.class)
   public final void testLocationNoMapper() throws Exception
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.SystemState, 0, 1);
      app.getAddress();
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testLocationInvalidCount() throws Exception
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.SystemState, 0, -1);
      app.getAddress();
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testLocationInvalidCount2() throws Exception
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.SystemState, 0, 64);
      app.getAddress();
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testLocationInvalidLocation() throws Exception
   {
      final MemoryRead app = new MemoryRead(null, 0, 1);
      app.setAddressMapper(MemoryAddressMapperFactory.getMemoryAddressMapper(0x12));
      app.getAddress();
   }

   @Test
   public final void testToData() throws Exception
   {
      final MemoryRead app = new MemoryRead(MemoryLocation.SystemState, 0, 1);
      final MemoryAddressMapper mapper = MemoryAddressMapperFactory.getMemoryAddressMapper(0x12);
      app.setAddressMapper(mapper);

      final byte[] expected = HexString.valueOf("02 01 00 60");
      final byte[] data = app.toByteArray();
      assertArrayEquals(expected, data);
   }
}
