package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Interface for KNXnet/IP frame bodies.
 */
public interface Frame
{
   /**
    * The KNXnet/IP protocol version
    */
   public static final int PROTOCOL_VERSION = 0x10;

   /**
    * @return the frame service type.
    */
   public ServiceType getServiceType();

   /**
    * Write the frame into a byte array. The whole frame is written, including a
    * frame header.
    *
    * @return the frame serialized into a byte array
    */
   public byte[] toByteArray();

   /**
    * Initialize the object from a {@link DataInput data input stream}.
    *
    * @param in - the input stream to read.
    *
    * @throws InvalidDataException
    */
   public void readData(DataInput in) throws IOException;

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write the object to.
    *
    * @throws IOException
    */
   public void writeData(DataOutput out) throws IOException;
}
