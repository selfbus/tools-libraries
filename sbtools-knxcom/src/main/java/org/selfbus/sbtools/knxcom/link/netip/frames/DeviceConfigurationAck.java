package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;

public class DeviceConfigurationAck extends AbstractConnectionFrame
{

   /**
    * Create a device configuration acknowledge object.
    *
    * @param channelId - the id of the communication channel.
    * @param sequence - the sequence counter.
    * @param status - the status code.
    */
   public DeviceConfigurationAck(int channelId, int sequence, StatusCode status)
   {
      super();
      setChannelId(channelId);
      setSequence(sequence);
      setStatus(status);
   }

   /**
    * Create an empty device configuration acknowledge object.
    */
   public DeviceConfigurationAck()
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

   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.DEVICE_CONFIGURATION_ACK;
   }

   @Override
   public void readData(DataInput in) throws IOException
   {
      readHeader(in);
   }

   @Override
   public void writeData(DataOutput out) throws IOException
   {
      writeHeader(out);
   }

}
