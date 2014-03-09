package org.selfbus.sbtools.knxcom.application.memory;

/**
 * Factory class for {@link MemoryAddressMapper memory address mappers}.
 */
public final class MemoryAddressMapperFactory
{
   /**
    * Get a memory address mapper for a specific mask version.
    * 
    * @param maskVersion - the mask version
    * @return The memory address mapper.
    */
   public static MemoryAddressMapper getMemoryAddressMapper(int maskVersion)
   {
      return new MemoryAddressMapper(maskVersion);
   }

   /*
    * Disabled
    */
   private MemoryAddressMapperFactory()
   {
   }
}
