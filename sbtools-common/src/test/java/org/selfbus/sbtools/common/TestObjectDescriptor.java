package org.selfbus.sbtools.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.selfbus.sbtools.common.types.ObjectPriority;
import org.selfbus.sbtools.common.types.ObjectType;

public class TestObjectDescriptor
{
   @Test
   public void testObjectDescriptor()
   {
      final ObjectDescriptor od = new ObjectDescriptor();
      assertNotNull(od.getPriority());
      assertNotNull(od.getType());
      assertNotNull(od.hashCode());
      assertNotNull(od.toString());
   }
   
   @Test
   public void testEquals()
   {
      final ObjectDescriptor od = new ObjectDescriptor();
      assertTrue(od.equals(od));
      assertFalse(od.equals(null));
      assertTrue(od.equals(new ObjectDescriptor()));
      
      od.setDataPointer(12, true);
      assertFalse(od.equals(new ObjectDescriptor()));
   }

   @Test
   public void testSetters()
   {
      final ObjectDescriptor od = new ObjectDescriptor();

      od.setTransEnabled(true);
      assertTrue(od.isTransEnabled());

      od.setWriteEnabled(true);
      assertTrue(od.isWriteEnabled());
      
      od.setPriority(ObjectPriority.ALARM);
      assertEquals(ObjectPriority.ALARM, od.getPriority());
      
      od.setType(ObjectType.BITS_6);
      assertEquals(ObjectType.BITS_6, od.getType());

      od.setCommEnabled(true);
      assertTrue(od.isCommEnabled());

      od.setReadEnabled(true);
      assertTrue(od.isReadEnabled());

      od.setDataPointer(13, true);
      assertEquals(13, od.getDataPointer());
      assertTrue(od.isEepromDataPointer());
      
      od.setDataPointer(255, false);
      assertEquals(255, od.getDataPointer());
      assertFalse(od.isEepromDataPointer());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testSetDataPointerMinBounds()
   {
      new ObjectDescriptor().setDataPointer(-129, true);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testSetDataPointerMaxBounds()
   {
      new ObjectDescriptor().setDataPointer(256, true);
   }   

   @Test
   public void testFromToByteArray()
   {
      final byte[] data = new byte[] { 107, (byte)254, 10 };
      final ObjectDescriptor od = new ObjectDescriptor();
      od.fromByteArray(data, 0);

      assertEquals(107, od.getDataPointer());
      assertTrue(od.isEepromDataPointer());
      assertTrue(od.isReadEnabled());
      assertTrue(od.isTransEnabled());
      assertTrue(od.isWriteEnabled());
      assertEquals(ObjectPriority.ALARM, od.getPriority());
      assertEquals(ObjectType.BYTES_4, od.getType());

      final byte[] dataOut = od.toByteArray();
      assertArrayEquals(data, dataOut);
   }

   @Test
   public void testFromByteArray()
   {
      final byte[] data = new byte[] { 12, (byte)0, 10 };
      final ObjectDescriptor od = new ObjectDescriptor();
      od.fromByteArray(data, 0);

      assertEquals(12, od.getDataPointer());
      assertFalse(od.isEepromDataPointer());
      assertFalse(od.isReadEnabled());
      assertFalse(od.isTransEnabled());
      assertFalse(od.isWriteEnabled());
   }
}
