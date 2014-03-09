package org.selfbus.sbtools.knxcom.application;

import org.selfbus.sbtools.knxcom.application.memory.MemoryLocation;

/**
 * Response to a {@link MemoryRead memory read} request.
 */
public class MemoryResponse extends AbstractMemoryData
{
   /**
    * Create a memory response object with address 0 and no data.
    */
   public MemoryResponse()
   {
      this(0, null);
   }

   /**
    * Create a memory response object.
    * 
    * @param address - the memory address.
    * @param data - the data. Up to 63 bytes.
    * 
    * @throws IllegalArgumentException if the supplied memory data has more than
    *            63 bytes.
    */
   protected MemoryResponse(int address, byte[] data)
   {
      super(address, data);
   }

   /**
    * Create a memory response object.
    * 
    * @param location - the memory location.
    * @param data - the data. Up to 63 bytes.
    * 
    * @throws IllegalArgumentException if the supplied memory data has more than
    *            63 bytes.
    */
   protected MemoryResponse(MemoryLocation location, byte[] data)
   {
      super(location, data);
   }

   /**
    * @return The type of the application:
    *         {@link ApplicationType#Memory_Response}.
    */
   @Override
   public ApplicationType getType()
   {
      return ApplicationType.Memory_Response;
   }
}
