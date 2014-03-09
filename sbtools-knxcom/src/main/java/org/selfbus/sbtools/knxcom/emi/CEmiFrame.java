/**
 *
 */
package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameFormat;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * A cEMI frame.
 */
public class CEmiFrame
{
   private EmiFrame frame;
   private AdditionalInfos additionalInfo;

   /**
    * Create an empty cEMI frame object.
    */
   public CEmiFrame()
   {
      super();
      additionalInfo = new AdditionalInfos();
   }

   /**
    * @return the EMI frame
    */
   public EmiFrame getFrame()
   {
      return frame;
   }

   /**
    * Set the {@link EmiFrame EMI frame}.
    *
    * @param frame - the frame to set
    */
   public void setFrame(EmiFrame frame)
   {
      this.frame = frame;
   }

   /**
    * @return the additional information
    */
   public AdditionalInfos getAdditionalInfo()
   {
      return additionalInfo;
   }

   /**
    * Set additional information.
    *
    * @param info - the additional information to set
    */
   public void setAdditionalInfo(AdditionalInfos additionalInfo)
   {
      this.additionalInfo = additionalInfo;
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}.
    *
    * @param in - the input stream to read.
    *
    * @throws IOException in case of read problems.
    */
   public void readData(DataInput in) throws IOException
   {
      final EmiFrameType type = EmiFrameType.valueOf(in.readUnsignedByte(), EmiVersion.cEMI);

      additionalInfo = new AdditionalInfos();
      additionalInfo.readFrom(in);

      frame = EmiFrameFactory.createFrame(type);

      if (frame.getType().frameFormat == EmiFrameFormat.cEMI)
         ((EmiTelegramFrame) frame).setForceExtTelegram(true);

      frame.readData(in);
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write the object to.
    *
    * @throws IOException in case of write problems.
    */
   public void writeData(DataOutput out) throws IOException
   {
      if (frame instanceof EmiTelegramFrame)
         ((EmiTelegramFrame) frame).setForceExtTelegram(true);

      out.write(frame.getType().code);

      additionalInfo.writeTo(out);
      frame.writeData(out);
   }
}
