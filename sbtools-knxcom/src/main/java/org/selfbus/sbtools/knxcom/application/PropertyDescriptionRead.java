package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Read a property description.
 */
public class PropertyDescriptionRead extends AbstractPropertyApplication
{
   protected int objectId;
   protected int propertyId;
   protected int index;

   @Override
   public ApplicationType getType()
   {
      return ApplicationType.PropertyDescription_Read;
   }

   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      objectId = in.readUnsignedByte();
      propertyId = in.readUnsignedByte();
      index = in.readUnsignedByte();
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

      if (!(o instanceof PropertyDescriptionRead))
         return false;

      final PropertyDescriptionRead oo = (PropertyDescriptionRead) o;
      return oo.objectId == objectId && oo.propertyId == propertyId && oo.index == index;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();

      sb.append(super.toString());

      if (propertyId != 0)
         sb.append(getPropertyIdLabel());
      else sb.append(" index #").append(index);

      return sb.toString();
   }
}
