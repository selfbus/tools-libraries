package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

/**
 * Request information about the state of a connection to the KNXnet/IP server.
 *
 * @see ConnectionStateResponse
 */
public class ConnectionStateRequest extends AbstractConnectionRequest
{
   /**
    * Create a connection state request object.
    *
    * @param type - the transport type of the client's endpoint.
    * @param addr - the address of the client's endpoint.
    * @param port - the port of the client's endpoint.
    * @param channelId - the ID if the communication channel that is queried.
    */
   public ConnectionStateRequest(TransportType type, InetAddress addr, int port, int channelId)
   {
      super(type, addr, port, channelId);
   }

   /**
    * Create a connect request object.
    */
   public ConnectionStateRequest()
   {
      super();
   }

   /**
    * @return {@link ServiceType#CONNECTIONSTATE_REQUEST}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.CONNECTIONSTATE_REQUEST;
   }
}
