package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * Interface for "External Message Interface" (EMI) frames.
 */
public interface EmiFrame
{
   /**
    * @return the type of the message.
    */
   public EmiFrameType getType();

   /**
    * Set the frame format version. Default: {@link EmiVersion#EMI2}.
    *
    * @param version - the frame format version
    */
   public void setFrameVersion(EmiVersion version);

   /**
    * @return The frame format version
    */
   public EmiVersion getFrameVersion();

   /**
    * Initialize the object from a {@link DataInput data input stream}. The
    * first byte of the stream is expected to be the first byte of the frame
    * body, excluding the frame type.
    *
    * @param in - the input stream to read.
    *
    * @throws IOException in case of read problems.
    */
   public void readData(DataInput in) throws IOException;

   /**
    * Write the object to a {@link DataOutput data output stream}. The first
    * byte of the stream is expected to be the first byte of the frame body,
    * excluding the frame type.
    *
    * @param out - the output stream to write the object to.
    *
    * @throws IOException in case of write problems.
    */
   public void writeData(DataOutput out) throws IOException;

   /**
    * Write the frame into a byte array. The whole frame is written, including the
    * {@link EmiFrameType EMI frame type}.
    *
    * @return the frame serialized into a byte array
    */
   public byte[] toByteArray();
}
