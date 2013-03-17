package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.common.address.PhysicalAddress;

/**
 * Set the physical address of all devices that are in programming mode. To be
 * sent as a broadcast to {@link PhysicalAddress#NULL 0.0.0}
 */
public class IndividualAddressWrite extends AbstractApplication
{
   private PhysicalAddress address = null;

   /**
    * Create an object with an undefined address.
    */
   public IndividualAddressWrite()
   {
   }

   /**
    * Create an empty instance, with the given address.
    * 
    * @param address - the physical address.
    */
   public IndividualAddressWrite(PhysicalAddress address)
   {
      this.address = address;
   }

   /**
    * @return The type of the application:
    *         {@link ApplicationType#IndividualAddress_Write}.
    */
   @Override
   public ApplicationType getType()
   {
      return ApplicationType.IndividualAddress_Write;
   }

   /**
    * Set the physical address.
    * 
    * @param address - the physical address.
    */
   public void setAddress(PhysicalAddress address)
   {
      this.address = address;
   }

   /**
    * @return the physical address
    */
   public PhysicalAddress getAddress()
   {
      return address;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      address = new PhysicalAddress(in.readUnsignedShort());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeShort(address == null ? 0 : address.getAddr());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return getType().toString() + ": " + address;
   }
}
