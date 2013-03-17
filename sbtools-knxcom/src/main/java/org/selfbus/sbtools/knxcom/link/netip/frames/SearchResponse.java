package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.blocks.DeviceInfoBlock;
import org.selfbus.sbtools.knxcom.link.netip.blocks.SupportedServiceFamilies;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * The response to a search request.
 *
 * @see SearchRequest
 */
public class SearchResponse extends AbstractEndPointFrame
{
   private final DeviceInfoBlock hardwareInfo = new DeviceInfoBlock();
   private final SupportedServiceFamilies servicesInfo = new SupportedServiceFamilies();

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
    * {@inheritDoc}
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.SEARCH_RESPONSE;
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
      super.readData(in);
      hardwareInfo.readData(in);
      servicesInfo.readData(in);
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write the object to
    *
    * @throws IOException
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      super.writeData(out);
      hardwareInfo.writeData(out);
      servicesInfo.writeData(out);
   }
}
