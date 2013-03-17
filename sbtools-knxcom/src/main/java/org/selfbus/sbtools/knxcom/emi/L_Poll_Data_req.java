package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * L_Poll_Data.req - data polling request
 */
public final class L_Poll_Data_req extends AbstractEmiFrame
{
   protected int dest = 0;
   protected int numSlots = 15;

   /**
    * Create an empty poll-data-request object.
    */
   public L_Poll_Data_req(EmiFrameType type)
   {
      super(type);
   }

   /**
    * Create a poll-data-request object.
    *
    * @param dest the destination group address.
    * @param numSlots the number of polling slots (1..15)
    */
   public L_Poll_Data_req(EmiFrameType type, int dest, int numSlots)
   {
      super(type);
      this.dest = dest;
   }

   /**
    * Set the destination group address.
    */
   public void setDest(int dest)
   {
      this.dest = dest;
   }

   /**
    * @return the destination group address.
    */
   public int getDest()
   {
      return dest;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      in.skipBytes(3);
      dest = in.readUnsignedShort();
      numSlots = in.readUnsignedByte() & 0x0f;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.write(0xf0); // control field
      out.write(0);
      out.write(0);
      out.writeShort(dest);
      out.write(numSlots & 0x0f);
   }
}