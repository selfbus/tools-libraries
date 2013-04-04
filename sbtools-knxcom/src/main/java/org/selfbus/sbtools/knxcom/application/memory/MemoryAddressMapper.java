package org.selfbus.sbtools.knxcom.application.memory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.selfbus.sbtools.common.exception.SbToolsRuntimeException;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor0;

/**
 * A mapper to map to / from {@link MemoryLocation symbolic memory addresses}
 * and physical memory addresses.
 * 
 * @see MemoryAddressMapperFactory#getMemoryAddressMapper(int)
 */
public final class MemoryAddressMapper
{
   private final Map<MemoryLocation, MemoryAddressMapping> map = new HashMap<MemoryLocation, MemoryAddressMapping>();
   private final MemoryAddressMapping[] sorted;

   /**
    * Create a memory address mapper for a specific mask version.
    * 
    * @param maskVersion - the mask version as got from
    *           {@link DeviceDescriptor0#getMaskVersion()}.
    * 
    * @see MemoryAddressMapperFactory#getMemoryAddressMapper(int)
    */
   MemoryAddressMapper(int maskVersion)
   {
      final Properties props = getProperties(maskVersion);

      for (MemoryLocation type : MemoryLocation.values())
      {
         final String key = type.name();

         final String addrStr = props.getProperty(key + ".addr");
         if (addrStr == null || addrStr.isEmpty())
            continue;
         final int addr = Integer.valueOf(addrStr, 16);

         final int size = Integer.valueOf(props.getProperty(key + ".size", "1"));
         // final boolean writable = Boolean.parseBoolean(props.getProperty(key
         // + ".writable", "false"));

         map.put(type, new MemoryAddressMapping(type, addr, size));
      }

      sorted = new MemoryAddressMapping[map.size()];
      map.values().toArray(sorted);

      Arrays.sort(sorted);
   }

   /**
    * Map from an address location + offset to a physical address.
    * 
    * @param location - the address location.
    * @param offset - the offset to the location.
    * 
    * @return The physical address.
    * 
    * @throws IllegalArgumentException if no mapping is available for the given
    *            location.
    * @throws ArrayIndexOutOfBoundsException if the offset is outside the memory
    *            range of the location.
    */
   public int getAddress(MemoryLocation location, int offset)
   {
      MemoryAddressMapping am = map.get(location);
      if (am == null)
         throw new IllegalArgumentException("No mapping for address type available: " + location);

      if (offset < 0 || offset > am.getSize())
         throw new ArrayIndexOutOfBoundsException("The offset must be in the range 0.." + am.getSize());

      return am.getAdress() + offset;
   }

   /**
    * Test if there exists a mapping for the given address type.
    * 
    * @param type - the address type.
    * @return true if a mapping is available.
    */
   public boolean hasMapping(MemoryLocation type)
   {
      return map.containsKey(type);
   }

   /**
    * Get the mapping for a specific address.
    * 
    * @param address - the searched address.
    * @return The mapping for the address, or null if no mapping was found.
    */
   public MemoryAddressMapping getMapping(int address)
   {
      for (int i = sorted.length - 1; i >= 0; --i)
      {
         if (sorted[i].containsAddress(address))
            return sorted[i];
      }

      return null;
   }

   /**
    * Get the mapping for a specific location.
    * 
    * @param location - the searched location.
    * @return The mapping for the location, or null if no mapping was found.
    */
   public MemoryAddressMapping getMapping(MemoryLocation location)
   {
      return map.get(location);
   }

   /**
    * Get the properties for a specific mask version.
    * 
    * @param maskVersion - the mask version.
    * @return The resource bundle, or null if the bundle could not be loaded.
    *
    * @throws SbToolsRuntimeException if the memory address mapping file could not be loaded.
    */
   public static Properties getProperties(int maskVersion)
   {
      final int type = (maskVersion >> 8) & 255;
      final int version = (maskVersion >> 4) & 15;
      final int subVersion = maskVersion & 15;

      final String name = String.format("memory-addresses-%1$d-%2$d.%3$d.properties", new Object[] { type, version, subVersion });
      final ClassLoader loader = MemoryAddressMapper.class.getClassLoader();

      InputStream in = loader.getResourceAsStream(name);
      if (in == null)
      {
         in = loader.getResourceAsStream("src/main/resources/" + name);

         if (in == null)
            throw new SbToolsRuntimeException("No memory address mapping file \"" + name + "\" found");
      }

      final Properties props = new Properties();
      try
      {
         props.load(in);
      }
      catch (IOException e)
      {
         throw new SbToolsRuntimeException("Failed to load address mapping file \"" + name + "\"", e);
      }

      return props;
   }
}
