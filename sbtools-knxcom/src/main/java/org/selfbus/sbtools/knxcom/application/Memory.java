package org.selfbus.sbtools.knxcom.application;

import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapper;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapping;
import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * Abstract base class for device memory access.
 */
public abstract class Memory extends AbstractApplication
{
   private MemoryAddressMapper mapper;
   private MemoryLocation location;
   private int address, offset;

   /**
    * Create a memory object.
    * 
    * @param address - the physical memory address.
    */
   protected Memory(int address)
   {
      this.address = address;
   }

   /**
    * Create a memory object.
    * 
    * @param location - the memory location.
    * @param offset - the offset to the starting address of the type.
    */
   protected Memory(MemoryLocation location, int offset)
   {
      this.location = location;
      this.offset = offset;
      address = -1;
   }

   /**
    * @return the physical memory address.
    */
   public int getAddress()
   {
      if (address < 0)
      {
         if (mapper == null)
            throw new RuntimeException("no memory address mapper is installed");

         update();

         if (address < 0)
            throw new RuntimeException("failed to map location to an address: " + location);
      }

      return address;
   }

   /**
    * Set the physical memory address.
    * 
    * @param address the address to set
    */
   public void setAddress(int address)
   {
      this.address = address;
      this.location = null;
      this.offset = 0;
   }

   /**
    * Set the memory address by location + offset.
    * 
    * @param location - the memory location.
    * @param offset - the offset to the starting address of the type.
    */
   public void setAddress(MemoryLocation location, int offset)
   {
      this.address = -1;
      this.location = location;
      this.offset = offset;
   }

   /**
    * @return The memory location, or null if no
    *         {@link #setAddressMapper(MemoryAddressMapper) address mapper} is
    *         installed.
    */
   public MemoryLocation getLocation()
   {
      if (location == null && mapper != null)
         update();

      return location;
   }

   /**
    * @return the offset to the memory location.
    */
   public int getOffset()
   {
      return offset;
   }

   /**
    * Set the memory address mapper.
    * 
    * @param mapper - the mapper to set.
    */
   public final void setAddressMapper(MemoryAddressMapper mapper)
   {
      this.mapper = mapper;
   }

   /**
    * @return The {@link #setAddressMapper(MemoryAddressMapper) installed}
    *         memory address mapper, or null if none is installed.
    */
   public final MemoryAddressMapper getAddressMapper()
   {
      return mapper;
   }

   /**
    * @return The address mapping for the {@link #getLocation() location}.
    */
   protected final MemoryAddressMapping getMapping()
   {
      return mapper.getMapping(location);
   }

   /**
    * Update mapping of address, type, and offset.
    */
   protected void update()
   {
      if (mapper == null)
         throw new RuntimeException("no memory address mapper installed");

      if (address < 0)
      {
         address = mapper.getAddress(location, offset);
      }
      else if (location == null)
      {
         final MemoryAddressMapping am = mapper.getMapping(address);
         if (am != null)
         {
            location = am.getLocation();
            offset = address - am.getAdress();
         }
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return address < 0 && location != null ? location.hashCode() : address;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      if (mapper != null && (location == null || address < 0))
         update();

      final StringBuffer sb = new StringBuffer();
      sb.append(getType().name()).append(" address");
      if (address >= 0)
         sb.append(" 0x").append(Integer.toHexString(address));
      if (location != null)
      {
         sb.append(" ").append(location.toString());
         if (offset != 0)
            sb.append('+').append(offset);
      }

      return sb.toString();
   }
}
