package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

/**
 * An abstract request class for requests concerning a communication channel.
 */
public abstract class AbstractConnectionRequest extends AbstractEndPointFrame
{
   private int channelId;

   /**
    * Create a connection state request object.
    *
    * @param type - the transport type of the client's endpoint.
    * @param addr - the address of the client's endpoint.
    * @param port - the port of the client's endpoint.
    * @param channelId - the ID if the communication channel.
    */
   public AbstractConnectionRequest(TransportType type, InetAddress addr, int port, int channelId)
   {
      super(type, addr, port);

      this.channelId = channelId;
   }

   /**
    * Create a connect request object.
    */
   public AbstractConnectionRequest()
   {
      super();
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
    * {@inheritDoc}
    */
   @Override
   public void readData(final DataInput in) throws IOException
   {
      channelId = in.readUnsignedByte();
      in.skipBytes(1);

      super.readData(in);
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
      out.write(0);

      super.writeData(out);
   }
}
