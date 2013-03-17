package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.blocks.EndPoint;
import org.selfbus.sbtools.knxcom.link.netip.types.ConnectionType;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;
import org.selfbus.sbtools.knxcom.types.LinkMode;

/**
 * Connect request to the KNXnet/IP server.
 *
 * @see #getEndPoint to access the control endpoint,
 * @see #getDataEndPoint to access the data endpoint.
 */
public class ConnectRequest extends AbstractEndPointFrame
{
   /**
    * Establish a bus monitor tunnel. Valid for {@link ConnectionType#TUNNEL
    * tunneling} connections. Server to client transfer only.
    */
   public static int BUSMON_TUNNEL = 0x80;

   /**
    * Establish a data link layer tunnel. Valid for
    * {@link ConnectionType#TUNNEL tunneling} connections.
    */
   public static int LINK_TUNNEL = 2;

   /**
    * Establish a raw data tunnel. Valid for {@link ConnectionType#TUNNEL
    * tunneling} connections.
    */
   public static int RAW_TUNNEL = 4;

   private final EndPoint dataEndPoint;
   private ConnectionType type = ConnectionType.TUNNEL;
   private int layer = LINK_TUNNEL;

   /**
    * Create a connect request object.
    *
    * @param ctrlType - the transport type of the client's control endpoint.
    * @param ctrlAddr - the address of the client's control endpoint.
    * @param ctrlPort - the port of the client's control endpoint.
    *
    * @param dataType - the transport type of the client's data endpoint.
    * @param dataAddr - the address of the client's data endpoint.
    * @param dataPort - the port of the client's data endpoint.
    */
   public ConnectRequest(TransportType ctrlType, InetAddress ctrlAddr, int ctrlPort, TransportType dataType,
         InetAddress dataAddr, int dataPort)
   {
      super(ctrlType, ctrlAddr, ctrlPort);
      dataEndPoint = new EndPoint(dataType, dataAddr, dataPort);
   }

   /**
    * Create a connect request object.
    */
   public ConnectRequest()
   {
      super();
      dataEndPoint = new EndPoint();
   }

   /**
    * @return {@link ServiceType#CONNECT_REQUEST}.
    */
   @Override
   public ServiceType getServiceType()
   {
      return ServiceType.CONNECT_REQUEST;
   }

   /**
    * @return the connection type.
    */
   public ConnectionType getType()
   {
      return type;
   }

   /**
    * Set the connection type.
    *
    * @param type - the type to set
    */
   public void setType(ConnectionType type)
   {
      this.type = type;
   }

   /**
    * Returns the data endpoint information.
    *
    * @return the data endpoint
    */
   public EndPoint getDataEndPoint()
   {
      return dataEndPoint;
   }

   /**
    * @return the KNX layer
    */
   public int getLayer()
   {
      return layer;
   }

   /**
    * Set the KNX layer. Valid values: {@link #BUSMON_TUNNEL},
    * {@link #LINK_TUNNEL}, {@link #RAW_TUNNEL}.
    *
    * @param layer - the layer to set
    */
   public void setLayer(int layer)
   {
      this.layer = layer;
   }

   /**
    * Set the KNX layer. E.g. 2 is the link layer.
    *
    * @param mode - the link mode to set
    */
   public void setLayer(LinkMode mode)
   {
      if (mode == LinkMode.BusMonitor)
         setLayer(BUSMON_TUNNEL);
      else if (mode == LinkMode.LinkLayer)
         setLayer(LINK_TUNNEL);
      else throw new IllegalArgumentException("invalid link mode " + mode);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(final DataInput in) throws IOException
   {
      super.readData(in);
      dataEndPoint.readData(in);

      in.skipBytes(1); // data length
      type = ConnectionType.valueOf(in.readUnsignedByte());
      layer = in.readUnsignedByte();
      in.skipBytes(1); // reserved
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write to
    *
    * @throws IOException
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      super.writeData(out);
      dataEndPoint.writeData(out);

      out.write(4); // data length
      out.write(type.code);
      out.write(layer);
      out.write(0); // reserved
   }
}
