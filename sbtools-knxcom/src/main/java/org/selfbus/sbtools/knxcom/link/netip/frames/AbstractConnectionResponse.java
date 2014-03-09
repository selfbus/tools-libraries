package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;

/**
 * An abstract class for responses concerning a communication channel.
 */
public abstract class AbstractConnectionResponse extends AbstractFrame
{
   private int channelId;
   private StatusCode status = StatusCode.OK;

   /**
    * Create a connection response object.
    *
    * @param channelId - the ID if the communication channel.
    * @param status - the status.
    */
   public AbstractConnectionResponse(int channelId, StatusCode status)
   {
      this.channelId = channelId;
      this.status = status;
   }

   /**
    * Create a connection response object.
    */
   public AbstractConnectionResponse()
   {
   }

   /**
    * @return the ID of the communication channel.
    */
   public int getChannelId()
   {
      return channelId;
   }

   /**
    * Set the ID of the communication channel.
    *
    * @param channelId - the ID to set.
    */
   public void setChannelId(int channelId)
   {
      this.channelId = channelId;
   }

   /**
    * @return the status
    */
   public StatusCode getStatus()
   {
      return status;
   }

   /**
    * Set the status.
    *
    * @param status - the status to set.
    */
   public void setStatus(StatusCode status)
   {
      this.status = status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(final DataInput in) throws IOException
   {
      channelId = in.readUnsignedByte();
      status = StatusCode.valueOf(in.readUnsignedByte());
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write to
    *
    * @throws IOException
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.write(channelId);
      out.write(status.code);
   }
}
