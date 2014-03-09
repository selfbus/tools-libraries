package org.selfbus.sbtools.knxcom.application;

import org.selfbus.sbtools.common.address.PhysicalAddress;

/**
 * Query the physical address of all devices that are in programming mode.
 * To be sent as a broadcast to {@link PhysicalAddress#NULL 0.0.0}
 */
public class IndividualAddressRead extends GenericApplication
{
   /**
    * Create an object with default values.
    */
   public IndividualAddressRead()
   {
      super(ApplicationType.IndividualAddress_Read);
   }
}
