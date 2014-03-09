package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;

public class TestDeviceDescriptorRead
{
   @Test
   public final void testDeviceDescriptorRead()
   {
      final DeviceDescriptorRead app = new DeviceDescriptorRead();

      assertEquals(ApplicationType.DeviceDescriptor_Read, app.getType());
      assertEquals(0, app.getDescriptorType());
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
   }

   @Test
   public final void testDeviceDescriptorReadIntIntArray()
   {
      final DeviceDescriptorRead app = new DeviceDescriptorRead(3);

      assertEquals(ApplicationType.DeviceDescriptor_Read, app.getType());
      assertEquals(3, app.getDescriptorType());
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
   }

   @Test
   public final void testGetSetDescriptorType()
   {
      final DeviceDescriptorRead app = new DeviceDescriptorRead();

      app.setDescriptorType(63);
      assertEquals(63, app.getDescriptorType());

      app.setDescriptorType(0);
      assertEquals(0, app.getDescriptorType());
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      final byte[] data = HexString.valueOf("07 01 08 17");
      final Application gapp = ApplicationFactory.createApplication(3, data);

      assertEquals(ApplicationType.DeviceDescriptor_Read, gapp.getType());
      final DeviceDescriptorRead app = (DeviceDescriptorRead) gapp;

      assertEquals(ApplicationType.DeviceDescriptor_Read, app.getType());
      assertEquals(7, app.getDescriptorType());
   }

   @Test
   public final void testFromRawData2() throws IOException
   {
      final byte[] data = HexString.valueOf("3f");
      final Application gapp = ApplicationFactory.createApplication(3, data);

      assertEquals(ApplicationType.DeviceDescriptor_Read, gapp.getType());
      final DeviceDescriptorRead app = (DeviceDescriptorRead) gapp;

      assertEquals(ApplicationType.DeviceDescriptor_Read, app.getType());
      assertEquals(DeviceDescriptorRead.INVALID_DESCRIPTOR_TYPE, app.getDescriptorType());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new DeviceDescriptorRead(7);

      final byte[] expected = HexString.valueOf("03 07");
      final byte[] rawData = app.toByteArray();
      
      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testEqualsObject()
   {
      final DeviceDescriptorRead app1 = new DeviceDescriptorRead(8);
      final DeviceDescriptorRead app2 = new DeviceDescriptorRead(8);
      final DeviceDescriptorRead app3 = new DeviceDescriptorRead(5);

      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app1));
      assertTrue(app1.equals(app2));
      assertFalse(app1.equals(app3));

      app2.setDescriptorType(12);
      assertFalse(app1.equals(app2));
   }
}
