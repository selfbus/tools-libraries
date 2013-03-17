package org.selfbus.sbtools.knxcom.emi;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * Factory class that creates {@link EmiFrame EMI frame} objects from raw data
 * and {@link DataInput data input} streams.
 */
public final class EmiFrameFactory
{
   /**
    * Create an EMI frame object.
    * 
    * @param type - the type of the EMI frame to create.
    * 
    * @return the created frame, or null if the frame type is valid, but there
    *         exists no frame class for the frame type.
    */
   static public EmiFrame createFrame(EmiFrameType type)
   {
      if (type.clazz == null)
         return null;

      try
      {
         return type.clazz.getDeclaredConstructor(EmiFrameType.class).newInstance(type);
      }
      catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e)
      {
         throw new RuntimeException(e);
      }
   }

   /**
    * Create an EMI frame object from the input stream. It is expected that the
    * first byte of the data input stream contains the {@link EmiFrameType EMI
    * frame type}.
    * 
    * @param in - the EMI frame is read from this stream.
    * 
    * @return the created frame, or null if the frame type is valid, but there
    *         exists no frame class for the frame type.
    * 
    * @throws IOException if the frame could not be read from in.
    */
   static public EmiFrame createFrame(final DataInput in, final EmiVersion version) throws IOException
   {
      final EmiFrameType type = EmiFrameType.valueOf(in.readUnsignedByte(), version);
      final EmiFrame frame = createFrame(type);

      if (frame != null)
      {
         frame.readData(in);
      }
      else
      {

      }

      return frame;
   }

   /**
    * Create an EMI frame object from the data. It is expected that the data
    * contains a full frame, including the frame header.
    * 
    * @param data - the raw data of the EMI frame.
    * 
    * @return the created frame (body), or null if the frame type is valid, but
    *         there exists no frame class for the frame type.
    * 
    * @throws IOException if the frame could not be read from data.
    */
   static public EmiFrame createFrame(final byte[] data, final EmiVersion version) throws IOException
   {
      return createFrame(new DataInputStream(new ByteArrayInputStream(data)), version);
   }

   /*
    * Disabled
    */
   private EmiFrameFactory()
   {
   }
}
