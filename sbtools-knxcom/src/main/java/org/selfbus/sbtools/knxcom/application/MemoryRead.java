package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * Read a block of bytes from the memory of a remote device. Up to 63 bytes can
 * be read. Depending on the type of the (your) bus connection, only 12 bytes
 * can be read.
 */
public class MemoryRead extends AbstractMemory
{
   private int count;

   /**
    * Create a memory read object with address and count 0.
    */
   public MemoryRead()
   {
      super(0);
   }

   /**
    * Create a memory read object.
    * 
    * @param address - the memory address.
    * @param count - the number of bytes to read/write, in the range 0..63
    * 
    * @throws IllegalArgumentException if count is not in the range 0..63
    */
   public MemoryRead(int address, int count)
   {
      super(address);
      setCount(count);
   }

   /**
    * Create a memory read object that reads the entire memory range of the
    * location.
    * 
    * @param location - the memory location.
    */
   public MemoryRead(MemoryLocation location)
   {
      super(location, 0);
      count = -1;
   }

   /**
    * Create a memory read object that reads count bytes from a memory address
    * which is specified by a location and an offset.
    * 
    * @param location - the memory location.
    * @param offset - the offset relative to the location.
    * @param count - the number of bytes to read/write, in the range 1..63
    * 
    * @throws IllegalArgumentException if count is not in the range 1..63
    */
   public MemoryRead(MemoryLocation location, int offset, int count)
   {
      super(location, offset);
      setCount(count);
   }

   /**
    * Set the number of bytes to read from the memory.
    * 
    * @param count - the number of bytes to read. Range 1..63.
    * 
    * @throws IllegalArgumentException if count is not in the range 1..63.
    */
   public void setCount(int count)
   {
      if (count < 1 || count > 63)
         throw new IllegalArgumentException("count must be in the range 1..63");

      this.count = count;
      super.setApciValue(count);
   }

   /**
    * @return The number of bytes to read from the memory.
    */
   public int getCount()
   {
      if (count < 0)
         update();

      return count;
   }

   /**
    * @return The type of the application: {@link ApplicationType#Memory_Read}.
    */
   @Override
   public ApplicationType getType()
   {
      return ApplicationType.Memory_Read;
   }

   /**
    * @return The {@link #getCount() count}.
    */
   @Override
   public int getApciValue()
   {
      return getCount();
   }

   /**
    * Update mapping of address, type, and offset.
    */
   @Override
   protected void update()
   {
      super.update();

      if (count < 0)
      {
         count = getMapping().getSize();
         super.setApciValue(count);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      count = super.getApciValue();
      setAddress(in.readUnsignedShort());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeShort(getAddress());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof MemoryRead))
         return false;

      final MemoryRead oo = (MemoryRead) o;
      return getAddress() == oo.getAddress() && count == oo.count;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return super.toString() + ", " + getCount() + " bytes";
   }
}
