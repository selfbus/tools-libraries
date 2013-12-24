package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Abstract class for property-value read/write/response.
 */
public abstract class AbstractPropertyValue extends AbstractApplication
{
   protected int objectId;
   protected int propertyId;
   protected int startIndex;
   protected int count;

   /**
    * Create a property-value object.
    */
   protected AbstractPropertyValue()
   {
   }
   
   /**
    * Create a property-value object.
    * 
    * @param objectId - the ID of the property object.
    * @param propertyId - the ID of the property in the property object.
    * @param startIndex - the first element to access.
    * @param count - the number of elements to access.
    */
   protected AbstractPropertyValue(int objectId, int propertyId, int startIndex, int count)
   {
      this.objectId = objectId;
      this.propertyId = propertyId;
      this.startIndex = startIndex;
      this.count = count;
   }

   /**
    * @return the object ID
    */
   public int getObjectId()
   {
      return objectId;
   }

   /**
    * @param objectId the objectId to set
    */
   public void setObjectId(int objectId)
   {
      this.objectId = objectId;
   }

   /**
    * @return the propertyId
    */
   public int getPropertyId()
   {
      return propertyId;
   }

   /**
    * @param propertyId the propertyId to set
    */
   public void setPropertyId(int propertyId)
   {
      this.propertyId = propertyId;
   }

   /**
    * @return the startIndex
    */
   public int getStartIndex()
   {
      return startIndex;
   }

   /**
    * @param startIndex the startIndex to set
    */
   public void setStartIndex(int startIndex)
   {
      this.startIndex = startIndex;
   }

   /**
    * @return the count
    */
   public int getCount()
   {
      return count;
   }

   /**
    * @param count the count to set
    */
   public void setCount(int count)
   {
      this.count = count;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      objectId = in.readUnsignedByte();
      propertyId = in.readUnsignedByte();

      int countStart = in.readUnsignedShort();
      count = countStart >> 12;
      startIndex = countStart & 0xfff;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeByte(objectId);
      out.writeByte(propertyId);
      out.writeByte((count << 4) | (startIndex >> 8));
      out.writeByte(startIndex & 0xff);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (objectId << 24) | (propertyId << 16) | (startIndex << 8) | count;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof AbstractPropertyValue))
         return false;

      final AbstractPropertyValue oo = (AbstractPropertyValue) o;
      return oo.objectId == objectId && oo.propertyId == propertyId && oo.startIndex == startIndex && oo.count == count;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();

      sb.append(super.toString())
        .append(": obj ").append(objectId)
        .append(" pid ").append(propertyId)
        .append(" start ").append(startIndex)
        .append(" count ").append(count);

      return sb.toString();
   }
}
