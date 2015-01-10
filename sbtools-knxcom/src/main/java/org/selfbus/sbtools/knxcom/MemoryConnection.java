package org.selfbus.sbtools.knxcom;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.selfbus.sbtools.knxcom.application.MemoryRead;
import org.selfbus.sbtools.knxcom.application.MemoryResponse;
import org.selfbus.sbtools.knxcom.application.MemoryWrite;
import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * An adapter for a {@link DataConnection} that handles reading and writing of
 * memory of a device (BCU) on the KNX bus.
 */
public class MemoryConnection implements MemoryConnectionInterface
{
   private final DataConnection connection;

   /**
    * Create a memory connection.
    *
    * @param connection - the data connection to use.
    * @throws NullPointerException if the connection is null.
    */
   public MemoryConnection(DataConnection connection)
   {
      if (connection == null)
         throw new NullPointerException("connection is null");

      this.connection = connection;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public DataConnection getConnection()
   {
      return connection;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public byte[] read(int address, int count) throws IOException, TimeoutException
   {
      final MemoryRead memoryRead = new MemoryRead();
      MemoryResponse memoryResponse;

      final int endAddress = address + count;
      final int maxBlockSize = 12;
      final byte[] data = new byte[count];
      int rlen;

      for (int idx = -1; address < endAddress; address += rlen, count -= rlen)
      {
         rlen = count > maxBlockSize ? maxBlockSize : count;

         memoryRead.setAddress(address);
         memoryRead.setCount(rlen);
         memoryResponse = (MemoryResponse) connection.query(memoryRead);

         final byte[] dataBlock = memoryResponse.getData();
         for (int i = 0; i < rlen; ++i)
            data[++idx] = dataBlock[i];
      }

      return data;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public byte[] read(MemoryLocation location) throws IOException, TimeoutException
   {
      final MemoryRead memRead = new MemoryRead(location);
      return read(memRead.getAddress(), memRead.getCount());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public byte[] read(MemoryLocation location, int offset, int count) throws IOException, TimeoutException
   {
      final MemoryRead memRead = new MemoryRead(location, offset, count);
      return read(memRead.getAddress(), memRead.getCount());
   }

   /**
    * Read bytes, using the supplied memory-read application object.
    *
    * @param memoryRead - the read application to send.
    * @return The read bytes.
    *
    * @throws TimeoutException if there is no reply from the remote device.
    * @throws IOException if there is a communication error.
    */
   byte[] read(final MemoryRead memoryRead) throws IOException, TimeoutException
   {
      final MemoryResponse memoryResponse = (MemoryResponse) connection.query(memoryRead);
      return memoryResponse.getData();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void write(int address, byte[] data) throws IOException, TimeoutException
   {
      final int endAddress = address + data.length;
      final int maxBlockSize = 12;

      for (int addr = address; addr < endAddress; addr += maxBlockSize)
      {
         int blockSize = Math.min(endAddress - addr, maxBlockSize);
         int offs = addr - address;

         byte[] dataBlock = Arrays.copyOfRange(data, offs, offs + blockSize);
         byte[] currentBlock = read(addr, blockSize);
         if (Arrays.equals(dataBlock, currentBlock))
            continue;

         connection.send(new MemoryWrite(addr, dataBlock));

         currentBlock = read(addr, blockSize);
         if (!Arrays.equals(dataBlock, currentBlock))
            throw new IOException("memory write: verify failed");
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void write(MemoryLocation location, byte[] data) throws IOException, TimeoutException
   {
      write(connection.getMemoryAddressMapper().getMapping(location).getAdress(), data);
   }
}
