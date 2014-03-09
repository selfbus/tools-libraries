package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;

public class TestADCRead
{
   @Test
   public final void testADCRead()
   {
      final ADCRead app = new ADCRead();

      assertEquals(ApplicationType.ADC_Read, app.getType());
      assertEquals(0, app.getChannel());
      assertEquals(1, app.getCount());
      assertNotNull(app.toString());
      assertNotNull(app.hashCode());
   }

   @Test
   public final void testADCReadIntInt()
   {
      final ADCRead app = new ADCRead(12, 7);

      assertEquals(ApplicationType.ADC_Read, app.getType());
      assertEquals(12, app.getChannel());
      assertEquals(7, app.getCount());
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testADCReadInvalid()
   {
      new ADCRead(64, 7);
   }

   @Test
   public final void testGetSetChannel()
   {
      final ADCRead app = new ADCRead();

      app.setChannel(1);
      assertEquals(1, app.getChannel());

      app.setChannel(63);
      assertEquals(63, app.getChannel());

      app.setChannel(0);
      assertEquals(0, app.getChannel());
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testGetSetChannelTooHigh()
   {
      final ADCRead app = new ADCRead();
      app.setChannel(64);
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testGetSetChannelTooLow()
   {
      final ADCRead app = new ADCRead();
      app.setChannel(-1);
   }

   @Test
   public final void testGetSetCount()
   {
      final ADCRead app = new ADCRead();

      app.setCount(1);
      assertEquals(1, app.getCount());

      app.setCount(88);
      assertEquals(88, app.getCount());
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      final byte[] data = HexString.valueOf("86 10");
      final Application gapp = ApplicationFactory.createApplication(1, data);

      assertEquals(ApplicationType.ADC_Read, gapp.getType());
      final ADCRead app = (ADCRead) gapp;

      assertEquals(6, app.getChannel());
      assertEquals(0x10, app.getCount());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new ADCRead(6, 16);

      final byte[] expected = HexString.valueOf("01 86 10");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testEqualsObject()
   {
      final ADCRead app1 = new ADCRead(6, 16);
      final ADCRead app2 = new ADCRead(6, 16);

      assertTrue(app1.equals(app1));
      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app2));

      app1.setCount(17);
      assertFalse(app1.equals(app2));

      app1.setCount(16);
      app1.setChannel(4);
      assertFalse(app1.equals(app2));
   }
}
