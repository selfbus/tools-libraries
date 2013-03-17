package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * Physical external interface (PEI) identify request.
 */
public final class PEI_Identify_req extends AbstractEmiFrame
{
   /**
    * Create a PEI-identify request.
    */
   public PEI_Identify_req(EmiFrameType type)
   {
      super(type);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
   }
}