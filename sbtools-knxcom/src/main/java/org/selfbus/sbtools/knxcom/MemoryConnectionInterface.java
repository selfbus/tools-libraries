package org.selfbus.sbtools.knxcom;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * Interface for memory connections.
 */
public interface MemoryConnectionInterface
{
   /**
    * @return The data connection.
    */
   public abstract DataConnection getConnection();

   /**
    * Read bytes from an absolute memory address.
    * 
    * @param address - the memory address.
    * @param count - the number of bytes to read/write, in the range 0..63
    * 
    * @return The read bytes.
    * 
    * @throws TimeoutException if there is no reply from the remote device.
    * @throws IOException if there is a communication error.
    * @throws IllegalArgumentException if count is not in the range 0..63
    */
   public abstract byte[] read(int address, int count) throws IOException, TimeoutException;

   /**
    * Read bytes from the entire memory range of the location.
    * 
    * @param location - the memory location.
    * 
    * @return The read bytes.
    * 
    * @throws TimeoutException if there is no reply from the remote device.
    * @throws IOException if there is a communication error.
    */
   public abstract byte[] read(MemoryLocation location) throws IOException, TimeoutException;

   /**
    * Read count bytes from a memory address which is specified by a location
    * and an offset.
    * 
    * @param location - the memory location.
    * @param offset - the offset relative to the location.
    * @param count - the number of bytes to read/write, in the range 1..63
    * 
    * @return The read bytes.
    * 
    * @throws IllegalArgumentException if count is not in the range 1..63
    * @throws TimeoutException if there is no reply from the remote device.
    * @throws IOException if there is a communication error.
    */
   public abstract byte[] read(MemoryLocation location, int offset, int count) throws IOException, TimeoutException;

   /**
    * Write bytes to an absolute memory address. The written bytes are verified.
    * 
    * @param address - the memory address.
    * @param data - the bytes to write.
    * 
    * @throws TimeoutException if there is no reply from the remote device.
    * @throws IOException if there is a communication error.
    */
   public abstract void write(int address, byte[] data) throws IOException, TimeoutException;

   /**
    * Write bytes to a location. The written bytes are verified.
    * 
    * @param location - the memory location.
    * @param data - the bytes to write.
    * 
    * @throws TimeoutException if there is no reply from the remote device.
    * @throws IOException if there is a communication error.
    */
   public abstract void write(MemoryLocation location, byte[] data) throws IOException, TimeoutException;

}
