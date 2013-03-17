package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Base class for EMI frames that contain a {@link Telegram} telegram. Used for
 * e.g. {@link L_Data_ind}, {@link L_Busmon_ind}.
 */
public abstract class EmiTelegramFrame extends AbstractEmiFrame
{
   protected Telegram telegram;
   protected boolean forceExtTelegram;

   /**
    * Create a frame object with the given telegram.
    * 
    * @param type - the frame type.
    * @param telegram - the telegram.
    */
   protected EmiTelegramFrame(EmiFrameType type, Telegram telegram)
   {
      super(type);
      this.telegram = telegram;
   }

   /**
    * Create an empty frame object.
    * 
    * @param type - the frame type.
    */
   protected EmiTelegramFrame(EmiFrameType type)
   {
      this(type, new Telegram());
   }

   /**
    * @return the contained telegram, or null if the frame is a confirmation
    *         frame.
    */
   public Telegram getTelegram()
   {
      return telegram;
   }
   
   /**
    * @return true if enforcing long telegrams is enabled.
    */
   public boolean isForceExtTelegram()
   {
      return forceExtTelegram;
   }

   /**
    * Set if extended telegrams shall be enforced. If enabled, telegrams read
    * are always expected to be extended frame format, and telegrams written
    * will always be in extended frame format. Default is to depend on the
    * telegram's frame format bit.
    * 
    * @param enable - enforce long telegram format
    */
   public void setForceExtTelegram(boolean enable)
   {
      this.forceExtTelegram = enable;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      telegram.readData(in, forceExtTelegram);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      telegram.setExtFormat(forceExtTelegram);
      telegram.writeData(out);
   }

   /**
    * @return the message in a human readable form.
    */
   @Override
   public String toString()
   {
      return getTypeString() + " " + telegram;
   }
}
