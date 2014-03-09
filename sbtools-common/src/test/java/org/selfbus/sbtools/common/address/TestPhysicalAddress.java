package org.selfbus.sbtools.common.address;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfbus.sbtools.common.address.PhysicalAddress;

public class TestPhysicalAddress
{
   @Test
   public void testHashCode()
   {
      assertEquals(0x0000, new PhysicalAddress().hashCode());
      assertEquals(0xffff, new PhysicalAddress(0xffff).hashCode());
      assertEquals(0x1203, new PhysicalAddress(1, 2, 3).hashCode());
      assertEquals(0x1fff, new PhysicalAddress(1, 15, 255).hashCode());
      assertEquals(0x0000, new PhysicalAddress(0, 0, 0).hashCode());
   }

   @Test
   public void testPhysicalAddress()
   {
      assertEquals(0, new PhysicalAddress().getAddr());
   }

   @Test
   public void testPhysicalAddressInt()
   {
      assertEquals(0x0000, new PhysicalAddress(0x0000).getAddr());
      assertEquals(0x1234, new PhysicalAddress(0x1234).getAddr());
      assertEquals(0xffff, new PhysicalAddress(0xffff).getAddr());
   }

   @Test
   public void testPhysicalAddressIntInt()
   {
      assertEquals(0x0000, new PhysicalAddress(0x00, 0x00).getAddr());
      assertEquals(0x1234, new PhysicalAddress(0x12, 0x34).getAddr());
      assertEquals(0xffff, new PhysicalAddress(0xff, 0xff).getAddr());
      assertEquals(0xabcd, new PhysicalAddress(0x12ab, 0x34cd).getAddr());
   }

   @Test
   public void testPhysicalAddressIntIntInt()
   {
      assertEquals(0x0000, new PhysicalAddress(0x0, 0x0, 0x00).getAddr());
      assertEquals(0x1234, new PhysicalAddress(0x1, 0x2, 0x34).getAddr());
      assertEquals(0xffff, new PhysicalAddress(0xf, 0xf, 0xff).getAddr());
      assertEquals(0xabcd, new PhysicalAddress(0x1a, 0x2b, 0x3cd).getAddr());
   }

   @Test
   public void testGetZone()
   {
      assertEquals(0, new PhysicalAddress(0, 0, 0).getZone());
      assertEquals(0, new PhysicalAddress(0, 1, 2).getZone());
      assertEquals(0, new PhysicalAddress(0, 15, 255).getZone());
      assertEquals(15, new PhysicalAddress(15, 0, 0).getZone());
      assertEquals(7, new PhysicalAddress(7, 14, 138).getZone());
   }

   @Test
   public void testGetLine()
   {
      assertEquals(1, new PhysicalAddress(0, 1, 2).getLine());
      assertEquals(0, new PhysicalAddress(1, 0, 3).getLine());
      assertEquals(15, new PhysicalAddress(14, 15, 138).getLine());
   }

   @Test
   public void testGetNode()
   {
      assertEquals(2, new PhysicalAddress(0, 1, 2).getNode());
      assertEquals(1, new PhysicalAddress(0, 0, 1).getNode());
      assertEquals(255, new PhysicalAddress(5, 3, 255).getNode());
   }

   @Test
   public void testCreateAddr()
   {
      assertEquals(0x0000, PhysicalAddress.createAddr(0x0, 0x0, 0x00));
      assertEquals(0x1234, PhysicalAddress.createAddr(0x1, 0x2, 0x34));
      assertEquals(0xffff, PhysicalAddress.createAddr(0xf, 0xf, 0xff));
   }

   @Test
   public void testIsValid()
   {
      assertTrue(PhysicalAddress.isValid(0, 0, 0));
      assertTrue(PhysicalAddress.isValid(1, 2, 3));
      assertTrue(PhysicalAddress.isValid(15, 15, 255));
      assertFalse(PhysicalAddress.isValid(-1, 0, 0));
      assertFalse(PhysicalAddress.isValid(0, -1, 0));
      assertFalse(PhysicalAddress.isValid(0, 0, -1));
      assertFalse(PhysicalAddress.isValid(16, 0, 0));
      assertFalse(PhysicalAddress.isValid(0, 16, 0));
      assertFalse(PhysicalAddress.isValid(0, 0, 256));
   }

   @Test
   public void testGetBytes()
   {
      final PhysicalAddress addr = new PhysicalAddress(0x12, 0x34);
      assertEquals(0x12, addr.getBytes()[0]);
      assertEquals(0x34, addr.getBytes()[1]);
   }

   @Test
   public void testValueOf()
   {
      assertEquals(new PhysicalAddress(0, 0, 0), PhysicalAddress.valueOf("0.0.0"));
      assertEquals(new PhysicalAddress(1, 2, 34), PhysicalAddress.valueOf("1.2.34"));
      assertEquals(null, PhysicalAddress.valueOf("1.2-34"));
      assertEquals(null, PhysicalAddress.valueOf("1-2-34"));
      assertEquals(null, PhysicalAddress.valueOf("a.b.c"));
      assertEquals(null, PhysicalAddress.valueOf(".."));
   }

   @Test
   public void testEquals()
   {
      final PhysicalAddress addr = new PhysicalAddress(1, 2, 3);
      final PhysicalAddress addr2 = new PhysicalAddress(1, 2, 3);
      final PhysicalAddress addr3 = new PhysicalAddress(1, 2, 4);

      assertFalse(addr.equals(null));
      assertFalse(addr.equals(new Object()));
      assertTrue(addr.equals(addr));
      assertTrue(addr.equals(addr2));
      assertFalse(addr.equals(addr3));
   }

   @Test
   public void testToString()
   {
      assertEquals("0.0.0", new PhysicalAddress(0, 0, 0).toString());
      assertEquals("1.12.217", new PhysicalAddress(1, 12, 217).toString());
      assertEquals("15.15.255", new PhysicalAddress(15, 15, 255).toString());
   }
}
