package org.selfbus.sbtools.knxcom.application.devicedescriptor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Device descriptor type 0. This device descriptor holds a single 16 bit value
 * that contains medium type, firmware type, firmware version, and firmware sub
 * version. Each 4 bits.
 */
public class DeviceDescriptor0 implements DeviceDescriptor
{
   /**
    * A device descriptor type 0 with zero values.
    */
   public static final DeviceDescriptor0 NULL = new DeviceDescriptor0(0);

   private int maskVersion;

   /**
    * Create a device descriptor type 0 object.
    */
   public DeviceDescriptor0()
   {
   }

   /**
    * Create a device descriptor type 0 object.
    *
    * @param maskVersion - the mask version to set.
    */
   public DeviceDescriptor0(int maskVersion)
   {
      this.maskVersion = maskVersion;
   }

   /**
    * @return the numerical descriptor type: 0.
    */
   public int getTypeCode()
   {
      return 0;
   }

   /**
    * Set the 16 bit mask version.
    *
    * @param maskVersion - the mask version to set.
    */
   public void setMaskVersion(int maskVersion)
   {
      this.maskVersion = maskVersion;
   }

   /**
    * @return the 16 bit mask version.
    */
   public int getMaskVersion()
   {
      return maskVersion;
   }

   /**
    * Set the medium type.
    *
    * @param type - the medium type to set
    */
   public void setMediumType(DeviceDescriptorMedium type)
   {
      maskVersion = (maskVersion & 0x0fff) | ((type.code & 15) << 12);
   }

   /**
    * @return the medium type.
    */
   public DeviceDescriptorMedium getMediumType()
   {
      return DeviceDescriptorMedium.valueOf((maskVersion >> 12) & 15);
   }

   /**
    * Set the mask type.
    *
    * @param type - the mask type to set, in the range 0..15
    */
   public void setMaskType(int type)
   {
      maskVersion = (maskVersion & 0xf0ff) | ((type & 15) << 8);
   }

   /**
    * @return the mask type.
    */
   public int getMaskType()
   {
      return (maskVersion >> 8) & 15;
   }

   /**
    * Set the firmware version and sub version.
    *
    * @param version - the firmware version, in the range 0..15
    * @param subVersion - the sub version, in the range 0..15
    */
   public void setVersion(int version, int subVersion)
   {
      maskVersion = (maskVersion & 0xff00) | ((version & 15) << 4) | (subVersion & 15);
   }

   /**
    * @return the firmware version.
    */
   public int getVersion()
   {
      return (maskVersion >> 4) & 15;
   }

   /**
    * @return the firmware sub version.
    */
   public int getSubVersion()
   {
      return maskVersion & 15;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      maskVersion = in.readUnsignedShort();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeShort(maskVersion);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return maskVersion;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;

      if (!(o instanceof DeviceDescriptor0))
         return false;

      DeviceDescriptor0 oo = (DeviceDescriptor0) o;
      return maskVersion == oo.maskVersion;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return "medium " + getMediumType() + " type " + getMaskType() + " version " + getVersion() + "."
            + getSubVersion();
   }
}
