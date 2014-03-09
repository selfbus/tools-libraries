package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.application.interfaceobject.PropertyDataType;

/**
 * Response to a {@link PropertyDescriptionRead} request.
 */
public class PropertyDescriptionResponse extends AbstractPropertyApplication
{
   protected int index, maxCount, access;
   protected PropertyDataType type;
   boolean checksum;

   @Override
   public ApplicationType getType()
   {
      return ApplicationType.PropertyDescription_Response;
   }

   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      objectId = in.readUnsignedByte();
      propertyId = in.readUnsignedByte();
      index = in.readUnsignedByte();
      int typeChecksum = in.readUnsignedByte();
      maxCount = in.readUnsignedShort();
      access = in.readUnsignedByte();

      type = PropertyDataType.valueOf(typeChecksum & 0x7f);
      checksum = (typeChecksum & 0x80) != 0;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeByte(objectId);
      out.writeByte(propertyId);
      out.writeByte(index);
      out.writeByte((checksum ? 0x80 : 0) | type.ordinal());
      out.writeShort(maxCount);
      out.writeByte(access);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (objectId << 16) | (propertyId << 8) | index;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof PropertyDescriptionResponse))
         return false;

      final PropertyDescriptionResponse oo = (PropertyDescriptionResponse) o;
      return oo.objectId == objectId && oo.propertyId == propertyId && oo.index == index;
   }

   @Override
   protected String getPropertyTypeLabel()
   {
      if (propertyId == 0)
         return null;

      return "index #" + index + ": " + type.toString().replaceFirst("PDT_", "").toLowerCase();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();

      sb.append(super.toString());

      if (propertyId == 0)
      {
         sb.append(" index #")
           .append(index)
           .append(" not found");
      }
      else
      {
         sb.append(getPropertyIdLabel())
           .append(" maxCount ").append(maxCount)
           .append(" access $").append(Integer.toHexString(access));
   
         if (checksum)
            sb.append(" with checksum");
      }

      return sb.toString();
   }
}
