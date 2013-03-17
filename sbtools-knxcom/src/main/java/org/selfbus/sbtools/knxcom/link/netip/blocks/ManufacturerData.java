package org.selfbus.sbtools.knxcom.link.netip.blocks;

import java.util.Arrays;

import org.selfbus.sbtools.knxcom.link.netip.types.DescriptionInfoType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Manufacturer data device-information block.
 */
public final class ManufacturerData implements Block
{
   private int id;
   private int[] data;

   /**
    * @return the 2-byte KNX manufacturer id.
    */
   public int getManufacturerId()
   {
      return id;
   }

   /**
    * Set the 2-byte KNX manufacturer id.
    * 
    * @param id - the manufacturer id to set.
    */
   public void setManufacturerId(int id)
   {
      this.id = id;
   }

   /**
    * @return manufacturer specific data.
    */
   public int[] getData()
   {
      return data;
   }

   /**
    * Set manufacturer specific data.
    * 
    * @param data - the data to set.
    */
   public void setData(int[] data)
   {
      this.data = data;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int fromData(int[] data, int start) throws InvalidDataException
   {
      int pos = start;
      final int blockLength = data[pos++];

      final int typeCode = data[pos++];
      final DescriptionInfoType type = DescriptionInfoType.valueOf(typeCode);
      if (type != DescriptionInfoType.MANUFACTURER_DATA)
         throw new InvalidDataException("Invalid type-code", typeCode);

      id = (data[pos++] << 8) | data[pos++];

      if (blockLength > 4)
      {
         final int len = blockLength - 4;
         this.data = Arrays.copyOfRange(data, pos, pos + len);
         pos += len;
      }
      else this.data = null;

      if (pos - start != blockLength)
         throw new InvalidDataException("Invalid block length", blockLength);

      return pos - start;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int toData(int[] data, int start)
   {
      int pos = start;
      final int dataLen = (this.data == null ? 0 : this.data.length);

      data[pos++] = dataLen + 4;
      data[pos++] = DescriptionInfoType.MANUFACTURER_DATA.code;
      data[pos++] = (id >> 8) & 0xff;
      data[pos++] = id & 0xff;

      for (int i = 0; i < dataLen; ++i)
         data[pos++] = this.data[i];

      return pos - start;
   }

}
