package org.selfbus.sbtools.common.address;

import junit.framework.TestCase;

import org.selfbus.sbtools.common.address.GroupAddress;

public class TestGroupAddress extends TestCase
{
   public void testHashCode()
   {
      assertEquals(0x0000, new GroupAddress().hashCode());
      assertEquals(0x7fff, new GroupAddress(0x7fff).hashCode());
      assertEquals(0x1203, new GroupAddress(0x2, 0x2, 0x3).hashCode());
      assertEquals(0x0fff, new GroupAddress(0x1, 0x7, 0xff).hashCode());
      assertEquals(0x0000, new GroupAddress(0, 0, 0).hashCode());
   }

   public void testGroupAddress()
   {
      assertEquals(0, new GroupAddress().getAddr());
   }

   public void testGroupAddressInt()
   {
      assertEquals(0x0000, new GroupAddress(0x0000).getAddr());
      assertEquals(0x1234, new GroupAddress(0x1234).getAddr());
      assertEquals(0x7fff, new GroupAddress(0x7fff).getAddr());
      assertEquals(0x7fff, new GroupAddress(0xffff).getAddr());
   }

   public void testGroupAddressIntInt()
   {
      assertEquals(0x0000, new GroupAddress(0x00, 0x00).getAddr());
      assertEquals(0x1234, new GroupAddress(0x12, 0x34).getAddr());
      assertEquals(0x7fff, new GroupAddress(0x7f, 0xff).getAddr());
      assertEquals(0x7fff, new GroupAddress(0xff, 0xff).getAddr());
      assertEquals(0x3bcd, new GroupAddress(0x123b, 0x34cd).getAddr());
   }

   public void testGroupAddressIntIntInt()
   {
      assertEquals(0x0000, new GroupAddress(0x0, 0x0, 0x00).getAddr());
      assertEquals(0x0a34, new GroupAddress(0x1, 0x2, 0x34).getAddr());
      assertEquals(0x1234, new GroupAddress(0x2, 0x2, 0x34).getAddr());
      assertEquals(0x7fff, new GroupAddress(0xf, 0x7, 0xff).getAddr());
      assertEquals(0x53cd, new GroupAddress(0x1a, 0x2b, 0x3cd).getAddr());
   }

   public void testGetMain()
   {
      assertEquals(0, new GroupAddress(0, 0, 0).getMain());
      assertEquals(0, new GroupAddress(0, 1, 2).getMain());
      assertEquals(0, new GroupAddress(0, 7, 255).getMain());
      assertEquals(1, new GroupAddress(1, 0, 0).getMain());
      assertEquals(15, new GroupAddress(15, 6, 138).getMain());
   }

   public void testGetMiddle()
   {
      assertEquals(0, new GroupAddress(0, 0, 0).getMiddle());
      assertEquals(0, new GroupAddress(1, 0, 3).getMiddle());
      assertEquals(0, new GroupAddress(15, 0, 255).getMiddle());
      assertEquals(1, new GroupAddress(0, 1, 2).getMiddle());
      assertEquals(7, new GroupAddress(14, 7, 138).getMiddle());
   }

   public void testGetSub()
   {
      assertEquals(0, new GroupAddress(0, 0, 0).getSub());
      assertEquals(0, new GroupAddress(15, 7, 0).getSub());
      assertEquals(2, new GroupAddress(0, 1, 2).getSub());
      assertEquals(1, new GroupAddress(0, 0, 1).getSub());
      assertEquals(255, new GroupAddress(5, 3, 255).getSub());
   }

   public void testCreateAddr()
   {
      assertEquals(0x0000, GroupAddress.createAddr(0x0, 0x0, 0x00));
      assertEquals(0x0a34, GroupAddress.createAddr(0x1, 0x2, 0x34));
      assertEquals(0x7fff, GroupAddress.createAddr(0xf, 0x7, 0xff));
   }

   public void testIsValid()
   {
      assertTrue(GroupAddress.isValid(0, 0, 0));
      assertTrue(GroupAddress.isValid(1, 2, 3));
      assertTrue(GroupAddress.isValid(15, 7, 255));
      assertFalse(GroupAddress.isValid(-1, 0, 0));
      assertFalse(GroupAddress.isValid(0, -1, 0));
      assertFalse(GroupAddress.isValid(0, 0, -1));
      assertFalse(GroupAddress.isValid(16, 0, 0));
      assertFalse(GroupAddress.isValid(0, 8, 0));
      assertFalse(GroupAddress.isValid(0, 0, 256));
   }

   public void testGetBytes()
   {
      final GroupAddress addr = new GroupAddress(0x12, 0x34);
      assertEquals(0x12, addr.getBytes()[0]);
      assertEquals(0x34, addr.getBytes()[1]);
   }

   public void testValueOf()
   {
      assertEquals(new GroupAddress(0, 0, 0), GroupAddress.valueOf("0/0/0"));
      assertEquals(new GroupAddress(1, 2, 34), GroupAddress.valueOf("1/2/34"));
      assertEquals(null, GroupAddress.valueOf("1/2-34"));
      assertEquals(null, GroupAddress.valueOf("1-2-34"));
      assertEquals(null, GroupAddress.valueOf(""));
      assertEquals(null, GroupAddress.valueOf("//"));
      assertEquals(null, GroupAddress.valueOf("a/b/c"));
   }

   public void testEquals()
   {
      final GroupAddress addr = new GroupAddress(1, 2, 3);
      final GroupAddress addr2 = new GroupAddress(1, 2, 3);
      final GroupAddress addr3 = new GroupAddress(1, 2, 4);

      assertFalse(addr.equals(null));
      assertFalse(addr.equals(new Object()));
      assertTrue(addr.equals(addr));
      assertTrue(addr.equals(addr2));
      assertFalse(addr.equals(addr3));
   }

   public void testToString()
   {
      assertEquals("0/0/0", new GroupAddress(0, 0, 0).toString());
      assertEquals("1/4/217", new GroupAddress(1, 4, 217).toString());
      assertEquals("4/0/12", new GroupAddress(4, 0, 12).toString());
      assertEquals("15/7/255", new GroupAddress(15, 7, 255).toString());
   }

}
