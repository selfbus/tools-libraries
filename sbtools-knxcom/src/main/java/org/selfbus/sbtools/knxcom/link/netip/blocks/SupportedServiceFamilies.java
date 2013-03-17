package org.selfbus.sbtools.knxcom.link.netip.blocks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.selfbus.sbtools.knxcom.link.netip.types.DescriptionInfoType;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceFamily;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Supported service families device information block.
 */
public class SupportedServiceFamilies implements DescriptionInfoBlock
{
   private final Map<ServiceFamily, Integer> families = new HashMap<ServiceFamily, Integer>();

   /**
    * Add a service family. If the same family was added before then it is
    * overwritten by this call.
    */
   public void addServiceFamily(ServiceFamily family, int version)
   {
      families.put(family, version);
   }

   /**
    * @return the version of a service family, or zero if not found.
    */
   public int getServiceFamily(ServiceFamily family)
   {
      final Integer v = families.get(family);
      if (v == null) return 0;
      return v;
   }

   /**
    * @return true if the object contains the given service family.
    */
   public boolean hasServiceFamily(ServiceFamily family)
   {
      return families.containsKey(family);
   }

   /**
    * Initialize the object from the given {@link DataInput data input stream}.
    *
    * @param in - the input stream to read
    *
    * @throws InvalidDataException
    */
   public void readData(DataInput in) throws IOException
   {
      final int numServices = (in.readUnsignedByte() - 2) >> 1;

      final int typeCode = in.readUnsignedByte();
      final DescriptionInfoType type = DescriptionInfoType.valueOf(typeCode);
      if (type != DescriptionInfoType.SERVICE_FAMILIES)
         throw new InvalidDataException("Invalid type " + type + ", expected " + DescriptionInfoType.SERVICE_FAMILIES, typeCode);

      families.clear();
      for (int i = 0; i < numServices; ++i)
         families.put(ServiceFamily.valueOf(in.readUnsignedByte()), in.readUnsignedByte());
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write the object to
    *
    * @throws IOException
    */
   public void writeData(DataOutput out) throws IOException
   {
      out.write((families.size() << 1) + 2);
      out.write(DescriptionInfoType.SERVICE_FAMILIES.code);

      for (ServiceFamily sf: families.keySet())
      {
         out.write(sf.code);
         out.write(families.get(sf));
      }
   }
}
