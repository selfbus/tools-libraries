package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * A generic application with data bytes. For application types where no application class exists.
 */
public class GenericDataApplication extends AbstractApplication
{
   private final ApplicationType type;
   private byte[] data;

   /**
    * Create an instance for a specific application type.
    * 
    * @param type - the application type.
    * 
    * @throws IllegalArgumentException if the type is null
    */
   public GenericDataApplication(ApplicationType type)
   {
      this(type, null);
   }

   /**
    * Create an instance for a specific application type. The data is cloned.
    * 
    * @param type - the application type.
    * @param data - the application's data.
    * 
    * @throws IllegalArgumentException if the type is null
    */
   public GenericDataApplication(ApplicationType type, byte[] data)
   {
      if (type == null)
         throw new IllegalArgumentException("type is null");

      this.type = type;
      setData(data);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ApplicationType getType()
   {
      return type;
   }

   /**
    * Set the application data. The data is cloned.
    * 
    * @param data - the data to set
    */
   public void setData(byte[] data)
   {
      this.data = (data == null ? null : data.clone());
   }

   /**
    * @return the application data.
    */
   public byte[] getData()
   {
      return data;
   }

   /**
    * Set the application data. The first data byte is the APCI byte.
    * The data is cloned.
    * 
    * @param data - the data to set
    */
   public void setApciData(byte[] data)
   {
      if (data == null)
      {
         this.data = null;
         setApciValue(0);
      }
      else if (data.length == 1)
      {
         setApciValue(data[0] & 255);
         this.data = null;
      }
      else
      {
         setApciValue(data[0] & 255);

         this.data = new byte[data.length - 1];
         for (int i = 1; i < data.length; ++i)
            this.data[i - 1] = data[i];
      }
   }

   /**
    * @return The application data, with the first data byte containing the APCI byte.
    */
   public byte[] getApciData()
   {
      if (data == null || data.length == 0)
         return new byte[] { (byte) getApciValue() };

      byte[] result = new byte[data.length + 1];
      result[0] = (byte) getApciValue();

      for (int i = 0; i < data.length; ++i)
         result[i + 1] = data[i];

      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      if (length > 0)
      {
         data = new byte[length];
         for (int i = 0; i < length; ++i)
            data[i] = (byte) in.readUnsignedByte();
      }
      else data = null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      if (data != null)
      {
         for (int i = 0; i < data.length; ++i)
            out.write(data[i]);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return type.getApci();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof GenericDataApplication))
         return false;

      final GenericDataApplication oo = (GenericDataApplication) o;
      return type == oo.type && (data == null ? oo.data == null : Arrays.equals(data, oo.data));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();

      sb.append(type.name());

      if (data != null && data.length > 0)
      {
         sb.append(" data");
         for (int i = 0; i < data.length; ++i)
            sb.append(String.format(" %02x", data[i]));
      }
      else if (getType().getDataMask() > 0)
      {
         sb.append(" data").append(String.format(" %02x", getApciValue()));
      }
      else
      {
         sb.append(" no data");
      }

      return sb.toString();
   }
}
