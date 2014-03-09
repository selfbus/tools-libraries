package org.selfbus.sbtools.knxcom.application;

/**
 * Response to a {@link IndividualAddressRead} telegram. Sent as a reply to an
 * {@link IndividualAddressRead} telegram by devices that are in programming
 * mode.
 */
public class IndividualAddressResponse extends GenericApplication
{
   /**
    * Create an object with default values.
    */
   public IndividualAddressResponse()
   {
      super(ApplicationType.IndividualAddress_Response);
   }
}
