package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CommonConnectionHeader
{
   private int length = 4;
   private int channelId = 1;
   private int sequence = 1;
   private int serviceTypeSpecific = 0;
   private byte[] connectionTypeSpecific;

   public CommonConnectionHeader()
   {
      super();
   }

   public void readFrom(DataInput in) throws IOException
   {
      length = in.readUnsignedByte();
      if (length < 4)
         throw new IOException("CommonConnectionHeader is less than 4 bytes");

      channelId = in.readUnsignedByte();
      sequence = in.readUnsignedByte();
      serviceTypeSpecific = in.readUnsignedByte();

      if (length > 4)
      {
         connectionTypeSpecific = new byte[length - 4];
         in.readFully(connectionTypeSpecific);
      }
   }

   public void writeTo(DataOutput out) throws IOException
   {
      if (connectionTypeSpecific != null)
         length = connectionTypeSpecific.length + 4;
      else length = 4;

      out.write(length);
      out.write(channelId);
      out.write(sequence);
      out.write(serviceTypeSpecific);

      if (length > 4)
      {
         out.write(connectionTypeSpecific);
      }
   }

   public int getLength()
   {
      return length;
   }

   public void setLength(int length)
   {
      this.length = length;
   }

   public int getChannelId()
   {
      return channelId;
   }

   public void setChannelId(int channelId)
   {
      this.channelId = channelId;
   }

   public int getSequence()
   {
      return sequence;
   }

   public void setSequence(int sequence)
   {
      this.sequence = sequence;
   }

   public int getServiceTypeSpecific()
   {
      return serviceTypeSpecific;
   }

   public void setServiceTypeSpecific(int serviceTypeSpecific)
   {
      this.serviceTypeSpecific = serviceTypeSpecific;
   }

   public byte[] getConnectionTypeSpecific()
   {
      return connectionTypeSpecific;
   }

   public void setConnectionTypeSpecific(byte[] connectionTypeSpecific)
   {
      this.connectionTypeSpecific = connectionTypeSpecific;
   }

}
