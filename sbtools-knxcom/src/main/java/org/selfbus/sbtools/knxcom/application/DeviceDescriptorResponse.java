package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor0;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor2;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * A response to a {@link DeviceDescriptorRead device descriptor read} request.
 */
public class DeviceDescriptorResponse extends AbstractApplication
{
   private DeviceDescriptor descriptor;

   /**
    * Create an object with device descriptor type 0 and no device descriptor
    * data.
    */
   public DeviceDescriptorResponse()
   {
      this(DeviceDescriptor0.NULL);
   }

   /**
    * Create a response object with a device descriptor type. The device
    * descriptor is cloned.
    *
    * @param descriptor - the device descriptor.
    */
   public DeviceDescriptorResponse(DeviceDescriptor descriptor)
   {
      this.descriptor = descriptor;
   }

   /**
    * Set the device descriptor.
    *
    * @param descriptor - the device descriptor to set
    */
   public void setDescriptor(DeviceDescriptor descriptor)
   {
      this.descriptor = descriptor;
   }

   /**
    * @return the device descriptor.
    */
   public DeviceDescriptor getDescriptor()
   {
      return descriptor;
   }

   /**
    * @return the type of the device descriptor.
    */
   public int getDescriptorType()
   {
      if (descriptor == null)
         return DeviceDescriptorRead.INVALID_DESCRIPTOR_TYPE;
      return descriptor.getTypeCode();
   }

   /**
    * @return The type of the application:
    *         {@link ApplicationType#DeviceDescriptor_Read}.
    */
   @Override
   public ApplicationType getType()
   {
      return ApplicationType.DeviceDescriptor_Response;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final int getApciValue()
   {
      return getDescriptorType();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      final int type = super.getApciValue();

      if (type == 0)
         descriptor = new DeviceDescriptor0();
      else if (type == 2)
         descriptor = new DeviceDescriptor2();
      else if (type == DeviceDescriptorRead.INVALID_DESCRIPTOR_TYPE)
         descriptor = null;
      else throw new InvalidDataException("unknown device descriptor type", type);

      if (descriptor != null)
         descriptor.readData(in, length);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      descriptor.writeData(out);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (getType().getApci() << 8) | (descriptor == null ? 0 : descriptor.hashCode());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof DeviceDescriptorResponse))
         return false;

      final DeviceDescriptorResponse oo = (DeviceDescriptorResponse) o;

      return descriptor == null ? oo.descriptor == null : descriptor.equals(oo.descriptor);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return super.toString() + ' ' + descriptor;
   }
}
