package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.blocks.DeviceInfoBlock;
import org.selfbus.sbtools.knxcom.link.netip.blocks.SupportedServiceFamilies;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * The response to a description request.
 *
 * @see DescriptionRequest
 */
public class DescriptionResponse extends AbstractFrame
{
   private final DeviceInfoBlock hardwareInfo = new DeviceInfoBlock();
   private final SupportedServiceFamilies servicesInfo = new SupportedServiceFamilies();

   /**
    * @return the service type: {@link ServiceType#DESCRIPTION_RESPONSE}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.DESCRIPTION_RESPONSE;
   }

   /**
    * @return the information block describing the KNXnet/IP server hardware.
    */
   public DeviceInfoBlock getHardwareInfo()
   {
      return hardwareInfo;
   }

   /**
    * @return the block describing the supported services.
    */
   public SupportedServiceFamilies getServicesInfo()
   {
      return servicesInfo;
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
      hardwareInfo.readData(in);
      servicesInfo.readData(in);
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
      hardwareInfo.writeData(out);
      servicesInfo.writeData(out);
   }
}
