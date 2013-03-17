package org.selfbus.sbtools.knxcom.link.netip;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.types.ConnectionType;

public class TestConnectionType
{
   @Test
   public final void testValueOf()
   {
      assertEquals(ConnectionType.DEVICE_MGMT, ConnectionType.valueOf(3));
      assertEquals(ConnectionType.TUNNEL, ConnectionType.valueOf(4));
   }

   @Test(expected = IllegalArgumentException.class)
   public final void testValueOfInvalid()
   {
      ConnectionType.valueOf(-1);
   }
}
