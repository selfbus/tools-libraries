package org.selfbus.sbtools.knxcom.link.netip.frames;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Factory class that creates {@link AbstractFrame frame} objects from raw data.
 */
public final class FrameFactory
{
   /**
    * Create a frame object from the input stream. It is expected that the data input
    * stream contains a full frame, including the frame header.
    *
    * @param in - the frame is read from this stream.
    *
    * @return the created frame (body), or null if the frame type is valid, but
    *         there exists no frame class for the frame type.
    *
    * @throws IOException
    * @throws InvalidDataException
    */
   static public Frame createFrame(final DataInput in) throws IOException
   {
      in.skipBytes(1); // header size
      final int version = in.readUnsignedByte();

      if (version != Frame.PROTOCOL_VERSION)
         throw new InvalidDataException("unsupported protocol version", version);

      final int serviceTypeCode = in.readShort();
      final ServiceType serviceType = ServiceType.valueOf(serviceTypeCode);

      @SuppressWarnings("unused")
      final int frameSize = in.readUnsignedShort();

      final Frame frame = serviceType.newFrameInstance();
      if (frame == null)
         return null; // frame body type is not implemented, just return

      frame.readData(in);

      return frame;
   }

   /**
    * Create a frame object from the data. It is expected that the data input
    * stream contains a full frame, including the frame header.
    *
    * @param data - the raw data of the frame.
    *
    * @return the created frame (body), or null if the frame type is valid, but
    *         there exists no frame class for the frame type.
    *
    * @throws IOException
    * @throws InvalidDataException
    */
   static public Frame createFrame(final byte[] data) throws IOException
   {
      return createFrame(new DataInputStream(new ByteArrayInputStream(data)));
   }

   /*
    * Disabled
    */
   private FrameFactory()
   {
   }
}
