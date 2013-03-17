package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * T_Connect.con - transport layer connect confirmation
 */
public final class T_Connect_con extends AbstractEmiFrame
{
   protected int dest;

   /**
    * Create an empty object.
    */
   public T_Connect_con(EmiFrameType type)
   {
      super(type);
   }

   /**
    * Set the destination address
    *
    * @param dest - the address to set
    */
   public void setDest(int dest)
   {
      this.dest = dest;
   }

   /**
    * @return the destination address
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
      in.skipBytes(1); // control
      dest = in.readUnsignedShort();
      in.skipBytes(2);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.write(0);
      out.writeShort(dest);
      out.writeShort(0);
   }
}