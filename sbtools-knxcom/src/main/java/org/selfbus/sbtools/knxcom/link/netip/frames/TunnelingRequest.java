package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.CEmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * A request that tunnels a KNX frame, either from the KNXnet/IP client to the
 * server, or from the server to the client.
 *
 * The request shall be answered with a {@link TunnelingAck tunneling
 * acknowledge}.
 */
public class TunnelingRequest extends AbstractConnectionFrame
{
   private final CEmiFrame frame = new CEmiFrame();

   /**
    * Create a tunneling request object.
    *
    * @param channelId - the id of the communication channel.
    * @param sequence - the sequence counter.
    */
   public TunnelingRequest(int channelId, int sequence)
   {
      super();
      setChannelId(channelId);
      setSequence(sequence);
   }

   /**
    * Create a tunneling request object.
    */
   public TunnelingRequest()
   {
      super();
   }


   /**
    * @return the EMI frame
    */
   public EmiFrame getFrame()
   {
      return frame.getFrame();
   }

   /**
    * Set the EMI frame.
    *
    * @param frame - the frame to set
    */
   public void setFrame(EmiFrame frame)
   {
      this.frame.setFrame(frame);
   }

   /**
    * @return {@link ServiceType#TUNNELING_REQUEST}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.TUNNELING_REQUEST;
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
      readHeader(in);
      frame.readData(in);
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
      writeHeader(out);
      frame.writeData(out);
   }
}
