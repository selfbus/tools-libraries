package org.selfbus.sbtools.knxcom.emi;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;


/**
 * Base class for "External Message Interface" (EMI) frames.
 */
public abstract class AbstractEmiFrame implements EmiFrame
{
   private final EmiFrameType type;

   /**
    * Internal constructor that sets the message type to {@link EmiFrameType#UNKNOWN}.
    */
   protected AbstractEmiFrame()
   {
      this.type = EmiFrameType.UNKNOWN;
   }

   /**
    * Create a new message and set the message type.
    * Internal constructor for subclasses.
    * 
    * @param type - the frame type.
    */
   protected AbstractEmiFrame(EmiFrameType type)
   {
      this.type = type;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final EmiFrameType getType()
   {
      return type;
   }

   /**
    * @return the message type as a string.
    */
   public final String getTypeString()
   {
      final Class<?> clazz = getClass();
      final Class<?> enclosingClazz = clazz.getEnclosingClass();

      if (enclosingClazz == null) return clazz.getSimpleName();
      return enclosingClazz.getSimpleName() + '.' + clazz.getSimpleName();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public abstract void readData(DataInput in) throws IOException;

   /**
    * {@inheritDoc}
    */
   @Override
   public abstract void writeData(DataOutput out) throws IOException;

   /**
    * {@inheritDoc}
    */
   @Override
   final public byte[] toByteArray()
   {
      final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream(1024);
      final DataOutputStream out = new DataOutputStream(outByteStream);

      try
      {
         out.write(getType().code);
         writeData(out);

         return outByteStream.toByteArray();
      }
      catch (IOException e)
      {
         throw new RuntimeException();
      }
   }

   /**
    * @return the message in a human readable form.
    */
   @Override
   public String toString()
   {
      return getTypeString();
   }
}
