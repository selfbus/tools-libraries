package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.CEmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;

public class DeviceConfigurationRequest extends AbstractConnectionFrame
{
   private final CEmiFrame frame = new CEmiFrame();

   /**
    * Create a device configuration request object.
    *
    * @param channelId - the id of the communication channel.
    * @param sequence - the sequence counter.
    */
   public DeviceConfigurationRequest(int channelId, int sequence)
   {
      super();
      setChannelId(channelId);
      setSequence(sequence);
   }

   /**
    * Create a device configuration request object.
    */
   public DeviceConfigurationRequest()
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

   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.DEVICE_CONFIGURATION_REQUEST;
   }

   @Override
   public void readData(DataInput in) throws IOException
   {
      readHeader(in);
      frame.readData(in);
   }

   @Override
   public void writeData(DataOutput out) throws IOException
   {
      writeHeader(out);
      frame.writeData(out);
   }

}
