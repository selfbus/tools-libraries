package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.FilterInputStream;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * A Bus-monitor message. This is the type of message that encapsulates
 * telegrams from the KNX/EIB bus in bus-monitor mode.
 */
public class L_Busmon_ind extends EmiTelegramFrame
{
   // Status
   // 7: frame error (in the frame bits)
   // 6: bit error (invalid bit in the frame characters)
   // 5: parity error (in the frame bits)
   // 4: overflow
   // 3: lost flag (at least one frame or frame piece is lost by the bus
   // monitor)
   // 2..0: sequence number
   private int status;

   // Time stamp in BAU timer units
   private int timestamp;

   // The control byte if it is a bus confirmation, -1 if it is a regular telegram
   private int control = -1;
   
   /**
    * Create a L_Busmon.ind message with the given encapsulated telegram.
    * 
    * @param telegram - the contained telegram.
    */
   protected L_Busmon_ind(EmiFrameType type, Telegram telegram)
   {
      super(type, telegram);
   }

   /**
    * Create an empty L_Busmon.ind message.
    */
   protected L_Busmon_ind(EmiFrameType type)
   {
      this(type, new Telegram());
   }

   /**
    * Set the message status byte.
    * 
    * @param status - the status byte.
    * 
    * @see #getStatus()
    */
   public void setStatus(int status)
   {
      this.status = status;
   }

   /**
    * Returns the status. The status bit indicate various error conditions:
    * 
    * <ul>
    * <li>bit 7: frame error (in the frame bits)
    * <li>bit 6: bit error (invalid bit in the frame characters)
    * <li>bit 5: parity error (in the frame bits)
    * <li>bit 4: overflow
    * <li>bit 3: lost flag (at least one frame or frame piece is lost by the bus
    * monitor)
    * <li>bits 2..0: sequence number
    * </ul>
    * 
    * @return the message status byte.
    */
   public int getStatus()
   {
      return status;
   }

   /**
    * Set the time stamp, in seconds since 1970-01-01 00:00:00.
    * 
    * @param timestamp - the time stamp to set
    * 
    * @see #getTimestamp()
    */
   public void setTimestamp(int timestamp)
   {
      this.timestamp = timestamp;
   }

   /**
    * Returns the time stamp. This is a 16-bit value that contains the time
    * taken when the frame's control field is completely received. The timer is
    * an internal counter of the BAU - the time unit depends on the clock rate
    * of the BAU micro controller.
    * 
    * @return the time-stamp.
    */
   public int getTimestamp()
   {
      return timestamp;
   }

   /**
    * @return True if the frame is a confirmation indication. A confirmation
    *         indication contains no telegram and is sent by the device that
    *         received a previous telegram.
    */
   public boolean isConfirmation()
   {
      return control != -1;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      if (getType() == EmiFrameType.EMI1_L_BUSMON_IND
            || getType() == EmiFrameType.EMI2_L_BUSMON_IND)
      {
         setStatus(in.readUnsignedByte());
         setTimestamp(in.readUnsignedShort());
   
         if (in instanceof FilterInputStream)
         {
            int avail = ((FilterInputStream) in).available();
            if (avail == 1)
            {
               // A confirmation byte
               control = in.readUnsignedByte();
               return;
            }
         }
      }

      super.readData(in);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      if (getType() == EmiFrameType.EMI1_L_BUSMON_IND
            || getType() == EmiFrameType.EMI2_L_BUSMON_IND)
      {
         out.write(getStatus());
         out.writeShort(getTimestamp());
      }
      super.writeData(out);
   }

   /**
    * @return the message in a human readable form.
    */
   @Override
   public String toString()
   {
      return getTypeString() + telegram.toString();
   }
}
