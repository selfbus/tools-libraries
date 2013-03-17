package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * T_Connect.req - transport layer connect request
 */
public final class T_Connect_req extends AbstractEmiFrame
{
   protected int dest = 0;

   /**
    * Create an empty connect-request object.
    */
   public T_Connect_req(EmiFrameType type)
   {
      super(type);
   }

   /**
    * Create a connect-request object.
    */
   public T_Connect_req(EmiFrameType type, int dest)
   {
      super(type);
      this.dest = dest;
   }

   /**
    * Set the destination address.
    */
   public void setDest(int dest)
   {
      this.dest = dest;
   }

   /**
    * @return he destination address.
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
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.write(0);
      out.writeShort(0);
      out.writeShort(dest);
   }
}