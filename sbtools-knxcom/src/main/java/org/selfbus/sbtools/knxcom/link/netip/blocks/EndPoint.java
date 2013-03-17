package org.selfbus.sbtools.knxcom.link.netip.blocks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.types.AddressFamily;
import org.selfbus.sbtools.knxcom.link.netip.types.ProtocolType;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

/**
 * Endpoint information. Also called host protocol address information (HPAI).
 * This is a part of a KNXnet/IP frame body.
 */
public final class EndPoint
{
   private TransportType type;
   private InetAddress addr;
   private int port;

   /**
    * Create an empty endpoint information object.
    */
   public EndPoint()
   {
   }

   /**
    * Create a host protocol address information object with the IP address and
    * port from <code>addr</code> as data.
    *
    * @param type - the transport type: {@link TransportType#UDP UDP} or
    *           {@link TransportType#TCP TCP}.
    * @param addr - the address of the endpoint.
    * @param port - the port of the endpoint.
    */
   public EndPoint(TransportType type, InetAddress addr, int port)
   {
      this.type = type;
      this.addr = addr;
      this.port = port;
   }

   /**
    * Set the type of the data transport on the IP network:
    * {@link TransportType#UDP UDP} or {@link TransportType#TCP TCP}.
    *
    * @param type - the transport type
    */
   public void setTransportType(TransportType type)
   {
      this.type = type;
   }

   /**
    * @return the transport type
    */
   public TransportType getTransportType()
   {
      return type;
   }

   /**
    * @return the address of the endpoint.
    */
   public InetAddress getAddress()
   {
      return addr;
   }

   /**
    * Set the address of the endpoint.
    *
    * @param addr - the address to set
    */
   public void setAddress(InetAddress addr)
   {
      this.addr = addr;
   }

   /**
    * @return the port of the endpoint.
    */
   public int getPort()
   {
      return port;
   }

   /**
    * Set the port of the endpoint.
    *
    * @param port - the port to set, in the range 0..65535
    *
    * @throws IllegalArgumentException if the port number is out of range
    */
   public void setPort(int port)
   {
      if (port < 0 || port > 65535)
         throw new IllegalArgumentException("invalid port number");

      this.port = port;
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}.
    *
    * @param in - the input stream to read
    *
    * @throws IOException
    */
   public void readData(DataInput in) throws IOException
   {
      final int len = in.readUnsignedByte();
      final ProtocolType protocolType = ProtocolType.valueOf(in.readUnsignedByte());

      type = protocolType.transportType;

      final byte[] addrData = new byte[len - 4];
      in.readFully(addrData);
      addr = InetAddress.getByAddress(addrData);

      port = in.readUnsignedShort();
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write to
    *
    * @throws IOException
    */
   public void writeData(DataOutput out) throws IOException
   {
      AddressFamily family = null;
      if (addr instanceof Inet4Address)
         family = AddressFamily.IPv4;
      else if (addr instanceof Inet6Address)
         family = AddressFamily.IPv6;

      final ProtocolType protocolType = ProtocolType.valueOf(type, family);
      final byte[] addrData = addr.getAddress();

      out.writeByte(addrData.length + 4);
      out.writeByte(protocolType.code);
      out.write(addrData);
      out.writeShort(port);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return addr == null ? 0 : addr.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object o)
   {
      if (this == o)
         return true;

      if (!(o instanceof EndPoint))
         return false;

      final EndPoint oo = (EndPoint) o;

      if (port != oo.port || type != oo.type)
         return false;

      return addr == null ? oo.addr == null : addr.equals(oo.addr);
   }
}
