package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalInfo
{
   private final static Logger LOGGER = LoggerFactory.getLogger(AdditionalInfo.class);

   private AdditionalInfoType type;
   private int length;
   private byte[] value;
   
   public AdditionalInfo()
   {
      super();
   }

   public void readFrom(DataInput in, int maxLength) throws IOException
   {
      if (maxLength < 3)
      {
         in.skipBytes(maxLength);
         throw new IllegalArgumentException("Wrong length of additional data");
      }
      type = AdditionalInfoType.valueOf(in.readUnsignedByte());
      LOGGER.debug("Found additional info: " + Integer.toHexString(type.code));
      length = in.readUnsignedByte();
      if ((maxLength - 2) < length)
         length = (maxLength - 2);
      if (type.length != length)
         LOGGER.warn("Wrong length of additional info " + type.code + ": " + length);
      value = new byte[length];
      in.readFully(value);
   }
   
   public void writeTo(DataOutput out) throws IOException
   {
      out.write(type.code);
      out.write(length);
      out.write(value, 0, length);
   }

   public AdditionalInfoType getType()
   {
      return type;
   }

   public void setType(AdditionalInfoType type)
   {
      this.type = type;
   }

   public int getLength()
   {
      return length;
   }

   public void setLength(int length)
   {
      this.length = length;
   }

   public byte[] getValue()
   {
      return value;
   }

   public void setValue(byte[] value)
   {
      this.value = value;
   }
}
