package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;

public class TestGenericApplication
{
   @Test
   public final void testGenericApplicationApplicationType()
   {
      final GenericApplication app = new GenericApplication(ApplicationType.IndividualAddress_Read);

      assertNotNull(app);
      assertNotNull(app.hashCode());
      assertNotNull(app.toString());
      assertEquals(ApplicationType.IndividualAddress_Read, app.getType());
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testGenericApplicationApplicationTypeNull()
   {
      new GenericApplication(null);
   }

   @Test
   public final void testToRawData()
   {
      final Application app = new GenericApplication(ApplicationType.IndividualAddress_Read);

      final byte[] expected = HexString.valueOf("01 00");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToRawData2()
   {
      final Application app = new GenericApplication(ApplicationType.GroupValue_Response);
      app.setApciValue(7);

      final byte[] expected = HexString.valueOf("00 47");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testEqualsObject()
   {
      final GenericApplication app1 = new GenericApplication(ApplicationType.IndividualAddress_Read);
      final GenericApplication app2 = new GenericApplication(ApplicationType.IndividualAddress_Read);
      final GenericApplication app3 = new GenericApplication(ApplicationType.GroupValue_Response);

      assertFalse(app1.equals(null));
      assertFalse(app1.equals(new Object()));
      assertTrue(app1.equals(app1));
      assertTrue(app1.equals(app2));
      assertFalse(app1.equals(app3));
   }

}
