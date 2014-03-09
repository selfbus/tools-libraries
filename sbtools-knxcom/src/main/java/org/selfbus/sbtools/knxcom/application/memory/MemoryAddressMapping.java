package org.selfbus.sbtools.knxcom.application.memory;

/**
 * A memory address mapping between an {@link MemoryLocation memory address
 * location} and a physical memory address.
 */
public final class MemoryAddressMapping implements Comparable<MemoryAddressMapping>
{
   private final MemoryLocation location;
   private final int address;
   private final int size;

   /**
    * Create a new memory address object.
    * 
    * @param location - The address location.
    * @param adress - The physical memory address.
    * @param size - The size of the memory block in bytes.
    */
   protected MemoryAddressMapping(MemoryLocation location, int adress, int size)
   {
      this.location = location;
      this.address = adress;
      this.size = size;
   }

   /**
    * @return The address location.
    */
   public MemoryLocation getLocation()
   {
      return location;
   }

   /**
    * 
    * @return The physical memory address.
    */
   public int getAdress()
   {
      return address;
   }

   /**
    * @return The size of the memory block in bytes.
    */
   public int getSize()
   {
      return size;
   }

   /**
    * Test if a specific address is inside the range that the mapping specifies.
    * 
    * @param address - the requested address.
    * 
    * @return True if the mapping contains the address.
    */
   public boolean containsAddress(int address)
   {
      return this.address <= address && this.address + size > address;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return address;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof MemoryAddressMapping))
         return false;
      final MemoryAddressMapping oo = (MemoryAddressMapping) o;
      return location == oo.location && address == oo.address && size == oo.size;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(MemoryAddressMapping o)
   {
      final int diff = address - o.address;
      if (diff != 0)
         return diff;

      return size - o.size;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return location + " 0x" + Integer.toHexString(address) + " (" + size + " bytes)";
   }
}
