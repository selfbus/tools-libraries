package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class AbstractConnectionFrame extends AbstractFrame
{

   protected CommonConnectionHeader connectionHeader;

   public AbstractConnectionFrame()
   {
      super();
      connectionHeader = new CommonConnectionHeader();
   }

   /**
    * @return the communication channel id
    */
   public int getChannelId()
   {
      return connectionHeader.getChannelId();
   }

   /**
    * Set the communication channel id.
    *
    * @param channelId - the channel id to set
    */
   public void setChannelId(int channelId)
   {
      connectionHeader.setChannelId(channelId);
   }

   /**
    * @return the sequence number
    */
   public int getSequence()
   {
      return connectionHeader.getSequence();
   }

   /**
    * Set the sequence number.
    *
    * @param sequence - the sequence number to set
    */
   public void setSequence(int sequence)
   {
      connectionHeader.setSequence(sequence);
   }
   
   public void readHeader(DataInput in) throws IOException
   {
      connectionHeader.readFrom(in);
   }
   
   public void writeHeader(DataOutput out) throws IOException
   {
      connectionHeader.writeTo(out);
   }
}
