package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

/**
 * A request for searching KNXnet/IP servers.
 */
public class SearchRequest extends AbstractEndPointFrame
{
   /**
    * Create a search request object.
    *
    * @param type - the transport type of the sender.
    * @param addr - the address of the sender.
    * @param port - the port of the sender.
    */
   public SearchRequest(TransportType type, InetAddress addr, int port)
   {
      super(type, addr, port);
   }

   /**
    * Create an empty search request object.
    */
   public SearchRequest()
   {
      super();
   }

   /**
    * @return {@link ServiceType#SEARCH_REQUEST}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.SEARCH_REQUEST;
   }
}
