package org.selfbus.sbtools.knxcom;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.application.GroupValueResponse;

public class TestGroupValueResponse
{
   @Test
   public final void testToByteArray1()
   {
      final Application app = new GroupValueResponse(12);

      final byte[] expected = HexString.valueOf("00 4c");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToByteArray2()
   {
      final Application app = new GroupValueResponse();
      app.setApciValue(12);

      final byte[] expected = HexString.valueOf("00 4c");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }

   @Test
   public final void testToByteArray3()
   {
      final Application app = new GroupValueResponse(new byte[] { 12 });

      final byte[] expected = HexString.valueOf("00 40 0c");
      final byte[] rawData = app.toByteArray();

      assertArrayEquals(expected, rawData);
   }
}
