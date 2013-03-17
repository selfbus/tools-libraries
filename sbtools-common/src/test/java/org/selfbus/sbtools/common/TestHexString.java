package org.selfbus.sbtools.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;

public class TestHexString
{
   @Test
   public final void testValueOf()
   {
      assertArrayEquals(new byte[0], HexString.valueOf(""));
      assertArrayEquals(new byte[0], HexString.valueOf("  "));
      assertArrayEquals(new byte[] { 0, 1, (byte) 0xff, (byte) 0x80 }, HexString.valueOf("00 1  ff    80"));
   }

   @Test
   public final void testToStringByteArray()
   {
      assertEquals("", HexString.toString(new byte[0]));
      assertEquals("7f", HexString.toString(new byte[] { 127 }));
      assertEquals("00 01 ff 80", HexString.toString(new byte[] { 0, 1, (byte) 0xff, (byte) 0x80 }));
   }

}
