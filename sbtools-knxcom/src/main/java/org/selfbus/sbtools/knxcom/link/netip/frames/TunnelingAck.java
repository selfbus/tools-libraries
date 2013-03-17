package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Acknowledge for a {@link TunnelingRequest tunneling request}.
 */
public class TunnelingAck extends AbstractConnectionFrame
{
   /**
    * Create a tunneling acknowledge object.
    *
    * @param channelId - the id of the communication channel.
    * @param sequence - the sequence counter.
    * @param status - the status code.
    */
   public TunnelingAck(int channelId, int sequence, StatusCode status)
   {
      super();
      setChannelId(channelId);
      setSequence(sequence);
      setStatus(status);
   }

   /**
    * Create an empty tunneling acknowledge object.
    */
   public TunnelingAck()
   {
      super();
      setStatus(StatusCode.OK);
   }

   /**
    * Returns the status of the connection request.
    *
    * Common status codes are: {@link StatusCode#OK}.
    * TODO what else can occur?
    *
    * @return The status code
    */
   public StatusCode getStatus()
   {
      return StatusCode.valueOf(connectionHeader.getServiceTypeSpecific());
   }

   /**
    * Set the status code.
    *
    * @param status - the status to set
    */
   public void setStatus(StatusCode status)
   {
      connectionHeader.setServiceTypeSpecific(status.code);
   }

   /**
    * @return {@link ServiceType#TUNNELING_ACK}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.TUNNELING_ACK;
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
   }
}
