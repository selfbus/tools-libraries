package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * Abstract class for memory response or write, including memory data.
 */
public abstract class MemoryData extends Memory
{
   private byte[] data;

   /**
    * Create a memory data object.
    * 
    * @param address - the memory address.
    * @param data - the data. Up to 63 bytes.
    * 
    * @throws IllegalArgumentException if the supplied memory data has more than
    *            63 bytes.
    */
   protected MemoryData(int address, byte[] data)
   {
      super(address);
      setData(data);
   }

   /**
    * Create a memory data object.
    * 
    * @param location - the memory location.
    * @param data - the data. Up to 63 bytes.
    * 
    * @throws IllegalArgumentException if the supplied memory data has more than
    *            63 bytes.
    */
   protected MemoryData(MemoryLocation location, byte[] data)
   {
      super(location, 0);
      setData(data);
   }

   /**
    * @return The memory data
    */
   public final byte[] getData()
   {
      return data;
   }

   /**
    * @return The number of bytes that {@link #getData() the data} contains.
    */
   public final int getCount()
   {
      return data == null ? 0 : data.length;
   }

   /**
    * Set the data. Up to 63 bytes are allowed. The supplied data is copied.
    * 
    * @param data the data to set.
    * 
    * @throws IllegalArgumentException if the supplied memory data has more than
    *            63 bytes.
    */
   public final void setData(byte[] data)
   {
      if (data == null)
      {
         this.data = null;
      }
      else
      {
         if (data.length > 63)
            throw new IllegalArgumentException("memory data is too long");

         this.data = data.clone();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final int getApciValue()
   {
      return data == null ? 0 : data.length;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      int count = super.getApciValue();
      setAddress(in.readUnsignedShort());

      if (count > 0)
      {
         data = new byte[count];
         in.readFully(data);
      }
      else data = null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeShort(getAddress());

      if (data != null)
         out.write(data);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (getAddress() << 8) | (data == null ? 0 : data.length);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof MemoryData))
         return false;

      final MemoryData oo = (MemoryData) o;
      return getAddress() == oo.getAddress() && (data == null ? oo.data == null : Arrays.equals(data, oo.data));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();
      sb.append(super.toString());

      if (data != null)
      {
         sb.append(':');
         for (int i = 0; i < data.length; ++i)
            sb.append(String.format(" %02X", data[i] & 255));
      }

      return sb.toString();
   }
}
