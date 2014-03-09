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

public class TestGenericDataApplication
{
   @Test
   public final void testGenericDataApplicationApplicationType()
   {
      final GenericDataApplication app = new GenericDataApplication(ApplicationType.IndividualAddress_Read);

      assertNotNull(app);
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
      assertNull(app.getData());
      assertEquals(ApplicationType.IndividualAddress_Read, app.getType());
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testGenericDataApplicationApplicationTypeNull()
   {
      new GenericDataApplication(null);
   }

   @Test
   public final void testGenericDataApplicationApplicationTypeIntArray()
   {
      final byte[] data = new byte[] { 1, 2, 3, 4 };
      final GenericDataApplication app = new GenericDataApplication(ApplicationType.IndividualAddress_Read, data);

      assertNotNull(app);
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
      assertArrayEquals(data, app.getData());
      assertFalse(data == app.getData());
      assertEquals(ApplicationType.IndividualAddress_Read, app.getType());
   }

   @Test
   public final void testGetSetData()
   {
      final GenericDataApplication app = new GenericDataApplication(ApplicationType.IndividualAddress_Read);

      app.setData(new byte[] { 4, 8, 12 });
      assertArrayEquals(new byte[] { 4, 8, 12 }, app.getData());

      app.setData(null);
      assertNull(app.getData());
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      final byte[] data = HexString.valueOf("80 01 04 09");
      final Application app = ApplicationFactory.createApplication(data);

      assertEquals(ApplicationType.GroupValue_Write, app.getType());
      assertArrayEquals(new byte[] { 1, 4, 9 }, ((GroupValueWrite) app).getData());
   }

   @Test
   public final void testFromRawData2() throws IOException
   {
      final byte[] data = HexString.valueOf("87");
      final Application app = ApplicationFactory.createApplication(data);

      assertEquals(ApplicationType.GroupValue_Write, app.getType());
      final GroupValueWrite iapp = (GroupValueWrite) app;

      assertNull(iapp.getData());
      assertEquals(7, iapp.getApciValue());
   }

   @Test
   public final void testFromRawData3() throws IOException
   {
      final byte[] data = HexString.valueOf("41 05 01 04 09");
      final Application app = ApplicationFactory.createApplication(data);

      assertEquals(ApplicationType.GroupValue_Response, app.getType());
      final GroupValueResponse iapp = (GroupValueResponse) app;

      assertArrayEquals(new byte[] { 5, 1, 4, 9 }, iapp.getData());
   }

   @Test
   public final void testFromRawData4() throws IOException
   {
      final byte[] data = HexString.valueOf("45");
      final Application app = ApplicationFactory.createApplication(data);

      assertEquals(ApplicationType.GroupValue_Response, app.getType());
      final GroupValueResponse iapp = (GroupValueResponse) app;

      assertNull(iapp.getData());
      assertEquals(5, iapp.getApciValue());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new GenericDataApplication(ApplicationType.IndividualAddress_Read, new byte[] { 2, 4, 9 });

      final byte[] expected = HexString.valueOf("01 00 02 04 09");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawData2()
   {
      final Application app = new GenericDataApplication(ApplicationType.IndividualAddress_Read);

      final byte[] expected = HexString.valueOf("01 00");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawData3()
   {
      final Application app = new GenericDataApplication(ApplicationType.GroupValue_Response, new byte[] { 5, 1, 4, 9 });

      final byte[] expected = HexString.valueOf("00 40 05 01 04 09");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawData4()
   {
      final Application app = new GroupValueResponse(new byte[] { 5, 1, 4, 9 });

      final byte[] expected = HexString.valueOf("00 40 05 01 04 09");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawData5()
   {
      final Application app = new GroupValueWrite(new byte[] { 1 });

      final byte[] expected = HexString.valueOf("00 80 01");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawData6()
   {
      final Application app = new GroupValueWrite();
      app.setApciValue(1);

      final byte[] expected = HexString.valueOf("00 81");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testEqualsObject()
   {
      final GenericDataApplication app1 = new GenericDataApplication(ApplicationType.IndividualAddress_Read, new byte[] { 1, 2 });
      final GenericDataApplication app2 = new GenericDataApplication(ApplicationType.IndividualAddress_Read, new byte[] { 1, 2 });
      final GenericDataApplication app3 = new GenericDataApplication(ApplicationType.GroupValue_Response, new byte[] { 1, 2 });

      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app1));
      assertTrue(app1.equals(app2));
      assertFalse(app1.equals(app3));

      app2.setData(null);
      assertFalse(app1.equals(app2));

      app1.setData(new byte[] { 1, 3, 7 });
      assertFalse(app1.equals(app2));

      app1.setData(null);
      assertTrue(app1.equals(app2));
   }

}
