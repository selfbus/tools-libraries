package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;

import org.selfbus.sbtools.knxcom.link.netip.blocks.EndPoint;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

/**
 * A abstract request that consists only of one {@link EndPoint end point}
 * and can be used as a base class for most request types.
 */
public abstract class AbstractEndPointFrame extends AbstractFrame
{
   private final EndPoint endPoint;

   /**
    * Create a request object.
    *
    * @param type - the transport type.
    * @param addr - the address of the sender.
    * @param port - the port of the sender.
    */
   public AbstractEndPointFrame(TransportType type, InetAddress addr, int port)
   {
      endPoint = new EndPoint(type, addr, port);
   }

   /**
    * Create an empty request object.
    */
   public AbstractEndPointFrame()
   {
      endPoint = new EndPoint();
   }

   /**
    * @return the host protocol address info object.
    */
   public EndPoint getEndPoint()
   {
      return endPoint;
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}.
    *
    * @param in - the input stream to read
    *
    * @throws IOException
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      endPoint.readData(in);
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
      endPoint.writeData(out);
   }
}
