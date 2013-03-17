package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;

/**
 * Base class for KNXnet/IP frame bodies.
 */
public abstract class AbstractFrame implements Frame
{
   /**
    * {@inheritDoc}
    */
   public abstract ServiceType getServiceType();

   /**
    * {@inheritDoc}
    */
   public abstract void readData(DataInput in) throws IOException;

   /**
    * {@inheritDoc}
    */
   public abstract void writeData(DataOutput out) throws IOException;

   /**
    * Write the frame into a byte array. The whole frame is written, including a
    * frame header.
    *
    * @return the created byte array
    */
   public final byte[] toByteArray()
   {
      final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream(1024);
      final DataOutputStream out = new DataOutputStream(outByteStream);
      byte[] data = null;

      try
      {
         out.write(6); // header size
         out.write(Frame.PROTOCOL_VERSION);
         out.writeShort(getServiceType().code);
         out.writeShort(0); // frame size is written later

         writeData(out);
         out.close();

         data = outByteStream.toByteArray();

         // write frame size
         data[4] = (byte) (data.length >> 8);
         data[5] = (byte) data.length;
      }
      catch (IOException e)
      {
         // should never happen anyways
         throw new RuntimeException();
      }
      return data;
   }
}
