package org.selfbus.sbtools.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.selfbus.sbtools.common.ByteUtils;


public class TestByteUtils
{
   @Test
   public void toInteger()
   {
      assertEquals(0, ByteUtils.toInteger(new byte[] { 1, 2, 3 }, 0, 0));
      assertEquals(1, ByteUtils.toInteger(new byte[] { 1, 2, 3 }, 0, 1));
      assertEquals(2, ByteUtils.toInteger(new byte[] { 1, 2, 3 }, 1, 1));
      assertEquals(3, ByteUtils.toInteger(new byte[] { 1, 2, 3 }, 2, 1));
      assertEquals(255, ByteUtils.toInteger(new byte[] { (byte) 255 }, 0, 1));

      assertEquals(0x1122, ByteUtils.toInteger(new byte[] { 0x11, 0x22, 0x33 }, 0, 2));
      assertEquals(0x2233, ByteUtils.toInteger(new byte[] { 0x11, 0x22, 0x33 }, 1, 2));
      assertEquals(0x112233, ByteUtils.toInteger(new byte[] { 0x11, 0x22, 0x33 }, 0, 3));
   }
}
