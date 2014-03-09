package org.selfbus.sbtools.knxcom.link.netip;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.types.DescriptionInfoType;

public class TestDeviceDescriptionType
{
   @Test
   public final void testValueOf()
   {
      assertEquals(DescriptionInfoType.DEVICE_INFO, DescriptionInfoType.valueOf(0x1));
      assertEquals(DescriptionInfoType.RESERVED, DescriptionInfoType.valueOf(0xa1));
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testValueOfInvalid()
   {
      DescriptionInfoType.valueOf(-1);
   }
}
