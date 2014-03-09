package org.selfbus.sbtools.knxcom.application;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * An abstract base class for {@link Application application},
 * with default implementations for common methods.
 */
public abstract class AbstractApplication implements Application
{
   private int apciValue;

   /**
    * {@inheritDoc}
    */
   @Override
   public int getApciValue()
   {
      return apciValue;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setApciValue(int value)
   {
      this.apciValue = value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final byte[] toByteArray()
   {
      try
      {
         final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream(256);
         final DataOutputStream out = new DataOutputStream(outByteStream);

         final ApplicationType type = getType();
         final int dataMask = type.getDataMask();
         int apciValue = getApciValue();
         int apci = type.getApci();
         out.writeShort(apci | (apciValue & dataMask));

         writeData(out);

         return outByteStream.toByteArray();
      }
      catch (IOException e)
      {
         throw new RuntimeException();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return getType().toString();
   }
}
