package org.selfbus.sbtools.knxcom;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.types.MediumType;

public class TestMediumType
{
   @Test
   public final void testValueOf()
   {
      assertEquals(MediumType.TWISTED_PAIR, MediumType.valueOf(2));
      assertEquals(MediumType.POWER_LINE, MediumType.valueOf(4));

      assertEquals("TP", MediumType.TWISTED_PAIR.shortName);
      assertEquals(2, MediumType.valueOf(2).code);
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testValueOfInvalid()
   {
      MediumType.valueOf(-1);
   }
}
