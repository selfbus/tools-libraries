package org.selfbus.sbtools.knxcom.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

import org.selfbus.sbtools.knxcom.DataConnection;
import org.selfbus.sbtools.knxcom.MemoryConnectionInterface;
import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * A simulated memory connection that works with internal byte buffers. It's
 * main use is probably for testing.
 */
public final class SimulatedMemoryConnection implements MemoryConnectionInterface
{
   private final byte[] mem;
   private final int startAddress;

   static public class MemoryBlock
   {
      public final int startAddress;
      public final byte[] data;

      MemoryBlock(int startAddress, byte[] data)
      {
         this.startAddress = startAddress;
         this.data = data;
      }
   }
   
   private final Vector<MemoryBlock> written = new Vector<MemoryBlock>();

   /**
    * Create a simulated memory connection.
    * 
    * @param startAddress - the starting address of the memory range
    * @param size - the size of the memory range
    */
   public SimulatedMemoryConnection(int startAddress, int size)
   {
      this.startAddress = startAddress;
      mem = new byte[size];
   }

   /**
    * @return The memory area, with the start address at index 0.
    */
   public byte[] getMem()
   {
      return mem;
   }

   /**
    * @return The list of written memory blocks.
    */
   public Vector<MemoryBlock> getWritten()
   {
      return written;
   }

   /**
    * Clear the memory area with zeroes and clear the list of written
    * memory blocks.
    */
   public void clear()
   {
      Arrays.fill(mem, (byte)0);
      written.clear();
   }

   /**
    * @return The start address of the memory area.
    */
   public int getStartAddress()
   {
      return startAddress;
   }

   @Override
   public DataConnection getConnection()
   {
      return null;
   }

   @Override
   public byte[] read(int address, int count) throws IOException, TimeoutException
   {
      if (address < startAddress || address + count >= startAddress + mem.length)
         throw new IllegalArgumentException("out of range");

      return Arrays.copyOfRange(mem, address - startAddress, count);
   }

   @Override
   public byte[] read(MemoryLocation location) throws IOException, TimeoutException
   {
      throw new IllegalAccessError("not implemented");
   }

   @Override
   public byte[] read(MemoryLocation location, int offset, int count) throws IOException, TimeoutException
   {
      throw new IllegalAccessError("not implemented");
   }

   @Override
   public void write(int address, byte[] data) throws IOException, TimeoutException
   {
      if (address < startAddress || address + data.length >= startAddress + mem.length)
      {
         throw new IllegalArgumentException("[" + address + ".." + (address + data.length)
               + "] is outside the memory range [" + startAddress + ".." + (startAddress + mem.length) + "]");
      }

      written.add(new MemoryBlock(address, data));
      
      final int offs = address - startAddress;
      for (int i = 0; i < data.length; ++i)
         mem[offs + i] = data[i];
   }

   @Override
   public void write(MemoryLocation location, byte[] data) throws IOException, TimeoutException
   {
      throw new IllegalAccessError("not implemented");
   }
}
