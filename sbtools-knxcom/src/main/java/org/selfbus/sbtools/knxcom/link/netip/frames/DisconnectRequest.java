package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

/**
 * A request for disconnecting from the KNXnet/IP server.
 */
public class DisconnectRequest extends AbstractConnectionRequest
{
   /**
    * Create a disconnect request object.
    *
    * @param type - the transport type of the client's endpoint.
    * @param addr - the address of the client's endpoint.
    * @param port - the port of the client's endpoint.
    * @param channelId - the ID if the communication channel that is queried.
    */
   public DisconnectRequest(TransportType type, InetAddress addr, int port, int channelId)
   {
      super(type, addr, port, channelId);
   }

   /**
    * Create a disconnect request object.
    */
   public DisconnectRequest()
   {
      super();
   }

   /**
    * @return {@link ServiceType#DISCONNECT_REQUEST}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.DISCONNECT_REQUEST;
   }
}
