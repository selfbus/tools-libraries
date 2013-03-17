package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * L_Poll_Data.L_Poll_Data_con - data polling confirmation
 *
 * TODO not implemented
 */
public final class L_Poll_Data_con extends AbstractEmiFrame
{
   protected int dest = 0;
   protected int status = 0;

   /**
    * Create an empty object.
    */
   public L_Poll_Data_con(EmiFrameType type)
   {
      super(type);
   }

   /**
    * Set the confirmation status: 0=ok
    */
   public void setStatus(int status)
   {
      this.status = status;
   }

   /**
    * @return the confirmation status: 0=ok
    */
   public int getStatus()
   {
      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      in.skipBytes(4);
      status = in.readUnsignedByte();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.write(0); // control field
      out.write(0);
      out.write(0);
      out.write(0);
      out.write(status);
   }
}