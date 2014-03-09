package org.selfbus.sbtools.knxcom.link.netip.frames;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;

/**
 * Response to a {@link DisconnectRequest disconnect request}.
 *
 * @see DisconnectRequest
 */
public class DisconnectResponse extends AbstractConnectionResponse
{
   /**
    * Create a disconnect response object.
    *
    * @param channelId - the ID if the communication channel that is queried.
    * @param status - the status code.
    */
   public DisconnectResponse(int channelId, StatusCode status)
   {
      super(channelId, status);
   }

   /**
    * Create a disconnect response object.
    */
   public DisconnectResponse()
   {
      super();
   }

   /**
    * @return {@link ServiceType#DISCONNECT_RESPONSE}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.DISCONNECT_RESPONSE;
   }
}
