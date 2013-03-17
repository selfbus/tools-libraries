package org.selfbus.sbtools.knxcom.application.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.exception.FtsRuntimeException;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapper;
import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

public class TestMemoryAddressMapper
{
   @Test
   public void testMemoryAddressMapper()
   {
      final MemoryAddressMapper mapper = new MemoryAddressMapper(0x0010);
      assertNotNull(mapper);

      assertEquals(0x4e, mapper.getAddress(MemoryLocation.MaskType, 0));
      assertEquals(0x5100, mapper.getAddress(MemoryLocation.SystemROM, 0x100));
      assertEquals(MemoryLocation.MaskType, mapper.getMapping(MemoryLocation.MaskType).getLocation());
   }

   @Test(expected = FtsRuntimeException.class)
   public void testMemoryAddressMapperNotFound() throws IOException
   {
      MemoryAddressMapper.getProperties(0xffff);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testMemoryAddressMapperNoMapping() throws IOException
   {
      final MemoryAddressMapper mapper = new MemoryAddressMapper(0x0010);
      assertNotNull(mapper);
      mapper.getAddress(null, 1);
   }

   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void testMemoryAddressMapperOutOfRange() throws IOException
   {
      final MemoryAddressMapper mapper = new MemoryAddressMapper(0x0010);
      assertNotNull(mapper);
      mapper.getAddress(MemoryLocation.MaskType, 2);
   }
}
