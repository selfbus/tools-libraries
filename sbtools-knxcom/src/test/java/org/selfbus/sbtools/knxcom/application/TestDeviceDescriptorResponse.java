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
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor0;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor2;

public class TestDeviceDescriptorResponse
{
   @Test
   public final void testDeviceDescriptorResponse()
   {
      final DeviceDescriptorResponse app = new DeviceDescriptorResponse();

      assertEquals(ApplicationType.DeviceDescriptor_Response, app.getType());
      assertEquals(DeviceDescriptor0.NULL, app.getDescriptor());
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
   }

   @Test
   public final void testDeviceDescriptorResponseIntIntArray()
   {
      final DeviceDescriptor desc = new DeviceDescriptor2();
      final DeviceDescriptorResponse app = new DeviceDescriptorResponse(desc);

      assertEquals(ApplicationType.DeviceDescriptor_Response, app.getType());
      assertEquals(desc, app.getDescriptor());
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
   }

   @Test
   public final void testGetSetDescriptor()
   {
      final DeviceDescriptorResponse app = new DeviceDescriptorResponse();
      final DeviceDescriptor desc = new DeviceDescriptor2();

      app.setDescriptor(desc);
      assertEquals(desc, app.getDescriptor());

      app.setDescriptor(null);
      assertNull(app.getDescriptor());
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      final byte[] data = HexString.valueOf("40 01 08");
      final Application gapp = ApplicationFactory.createApplication(3, data);

      assertEquals(ApplicationType.DeviceDescriptor_Response, gapp.getType());
      final DeviceDescriptorResponse app = (DeviceDescriptorResponse) gapp;

      assertEquals(0, app.getDescriptorType());
      assertEquals(DeviceDescriptor0.class, app.getDescriptor().getClass());
   }

   @Test
   public final void testFromRawData2() throws IOException
   {
      final byte[] data = HexString.valueOf("7f");
      final Application gapp = ApplicationFactory.createApplication(3, data);

      assertEquals(ApplicationType.DeviceDescriptor_Response, gapp.getType());
      final DeviceDescriptorResponse app = (DeviceDescriptorResponse) gapp;

      assertEquals(DeviceDescriptorRead.INVALID_DESCRIPTOR_TYPE, app.getDescriptorType());
      assertNull(app.getDescriptor());
   }

   @Test
   public final void testFromRawData3() throws IOException
   {
      final byte[] data = HexString.valueOf("40 00 12");
      final Application gapp = ApplicationFactory.createApplication(3, data);

      assertEquals(ApplicationType.DeviceDescriptor_Response, gapp.getType());
      final DeviceDescriptorResponse app = (DeviceDescriptorResponse) gapp;

      assertEquals(0, app.getDescriptorType());
      DeviceDescriptor0 desc = (DeviceDescriptor0) app.getDescriptor();
      assertEquals(0x12, desc.getMaskVersion());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new DeviceDescriptorResponse(new DeviceDescriptor0(0x1234));

      final byte[] expected = HexString.valueOf("03 40 12 34");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testEqualsObject()
   {
      final DeviceDescriptorResponse app1 = new DeviceDescriptorResponse(new DeviceDescriptor0(123));
      final DeviceDescriptorResponse app2 = new DeviceDescriptorResponse(new DeviceDescriptor0(123));
      final DeviceDescriptorResponse app3 = new DeviceDescriptorResponse(new DeviceDescriptor0(456));

      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app1));
      assertTrue(app1.equals(app2));
      assertFalse(app1.equals(app3));

      app2.setDescriptor(null);
      assertFalse(app1.equals(app2));
      assertFalse(app2.equals(app1));

      app1.setDescriptor(null);
      assertTrue(app1.equals(app2));

      app1.setDescriptor(new DeviceDescriptor0(123));
      assertFalse(app1.equals(app2));

      app2.setDescriptor(new DeviceDescriptor0(456));
      assertFalse(app1.equals(app2));
   }
}
