package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.PhysicalAddress;

public class TestIndividualAddressWrite
{
   @Test
   public final void testIndividualAddressWrite()
   {
      final IndividualAddressWrite app = new IndividualAddressWrite();
      assertNull(app.getAddress());
      assertEquals(ApplicationType.IndividualAddress_Write, app.getType());
   }

   @Test
   public final void testIndividualAddressWritePhysicalAddress()
   {
      final IndividualAddressWrite app = new IndividualAddressWrite(new PhysicalAddress(3, 45, 67));
      assertEquals(new PhysicalAddress(3, 45, 67), app.getAddress());
   }

   @Test
   public final void testGetSetAddress()
   {
      final IndividualAddressWrite app = new IndividualAddressWrite();

      app.setAddress(new PhysicalAddress(1, 17, 4));
      assertEquals(new PhysicalAddress(1, 17, 4), app.getAddress());

      app.setAddress(null);
      assertEquals(null, app.getAddress());
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      final byte[] data = HexString.valueOf("c0 12 34");
      final Application gapp = ApplicationFactory.createApplication(0, data);

      assertEquals(ApplicationType.IndividualAddress_Write, gapp.getType());
      final IndividualAddressWrite app = (IndividualAddressWrite) gapp;

      assertEquals(new PhysicalAddress(1, 2, 0x34), app.getAddress());
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new IndividualAddressWrite(new PhysicalAddress(1, 2));

      final byte[] expected = HexString.valueOf("00 c0 01 02");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawDataNullAddr()
   {
      final IndividualAddressWrite app = new IndividualAddressWrite();

      final byte[] expected = HexString.valueOf("00 c0 00 00");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }
}
