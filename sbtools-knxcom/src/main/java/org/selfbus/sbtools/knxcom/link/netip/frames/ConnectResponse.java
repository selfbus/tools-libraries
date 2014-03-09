package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.blocks.EndPoint;
import org.selfbus.sbtools.knxcom.link.netip.types.ConnectionType;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Response to a {@link ConnectRequest connect request}.
 *
 * @see ConnectRequest
 */
public class ConnectResponse extends AbstractFrame
{
   private int channelId;
   private StatusCode status = StatusCode.OK;
   private final EndPoint dataEndPoint = new EndPoint();
   private ConnectionType type = ConnectionType.TUNNEL;
   private int[] data;

   /**
    * Create a connection response object.
    */
   public ConnectResponse()
   {
   }

   /**
    * @return {@link ServiceType#CONNECT_RESPONSE}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.CONNECT_RESPONSE;
   }

   /**
    * Returns the status of the connection request.
    *
    * Common status codes are: {@link StatusCode#OK},
    * {@link StatusCode#E_CONNECTION_TYPE},
    * {@link StatusCode#E_CONNECTION_OPTION},
    * {@link StatusCode#E_NO_MORE_CONNECTIONS}.
    *
    * @return The status code
    */
   public StatusCode getStatus()
   {
      return status;
   }

   /**
    * Set the status code.
    *
    * @param status - the status to set
    */
   public void setStatus(StatusCode status)
   {
      this.status = status;
   }

   /**
    * Returns the id of the communication channel.
    *
    * @return the channel id
    */
   public int getChannelId()
   {
      return channelId;
   }

   /**
    * Set the id of the communication channel.
    *
    * @param channelId - the channel id to set
    */
   public void setChannelId(int channelId)
   {
      this.channelId = channelId;
   }

   /**
    * Access the data endpoint. This describes the data endpoint of the
    * KNXnet/IP server for data transfers from us.
    *
    * @return the data endpoint
    */
   public EndPoint getDataEndPoint()
   {
      return dataEndPoint;
   }

   /**
    * @return the connection type.
    */
   public ConnectionType getType()
   {
      return type;
   }

   /**
    * Set the connection type.
    *
    * @param type - the type to set
    */
   public void setType(ConnectionType type)
   {
      this.type = type;
   }

   /**
    * Set the connection response data block (CRD).
    *
    * @see #getData()
    */
   public void setData(int[] data)
   {
      this.data = data;
   }

   /**
    * Returns the connection response data block (CRD). This is for example the
    * 2-byte bus address of the KNXnet/IP device for a
    * {@link ConnectionType#TUNNEL tunneling connection}.
    *
    * @return the response data. May be null.
    */
   public int[] getData()
   {
      return data;
   }

   /**
    * Initialize the object from the given {@link DataInput data input stream}.
    *
    * @param in - the input stream to read
    *
    * @throws InvalidDataException
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      channelId = in.readUnsignedByte();
      status = StatusCode.valueOf(in.readUnsignedByte());

      if (status != StatusCode.OK)
         return;

      dataEndPoint.readData(in);

      final int dataLen = in.readUnsignedByte() - 2;
      type = ConnectionType.valueOf(in.readUnsignedByte());

      if (dataLen > 0)
      {
         this.data = new int[dataLen];
         for (int i = 0; i < dataLen; ++i)
            this.data[i] = in.readUnsignedByte();
      }
      else data = null;
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
      dataEndPoint.writeData(out);

      final int dataLen = this.data == null ? 0 : this.data.length;

      out.write(dataLen + 2);
      out.write(type.code);

      for (int i = 0; i < dataLen; ++i)
         out.write(data[i]);
   }
}
