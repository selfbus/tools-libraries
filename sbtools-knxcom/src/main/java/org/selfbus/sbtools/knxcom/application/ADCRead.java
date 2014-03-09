package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Read values from an A/D converter channel.
 */
public class ADCRead extends AbstractApplication
{
   private int channel = 0;
   private int count = 1;

   /**
    * Create an A/D converter read object with channel 0 and count 1.
    */
   public ADCRead()
   {
   }

   /**
    * Create an A/D converter read object.
    *
    * @param channel - A/D converter channel (0..63).
    * @param count - the number of samples to read.
    *
    * @throws IllegalArgumentException if the channel is out of range.
    */
   public ADCRead(int channel, int count)
   {
      setChannel(channel);
      setCount(count);
   }

   /**
    * @return the A/D converter channel (0..63).
    */
   public int getChannel()
   {
      return channel;
   }

   /**
    * Set the A/D converter channel (0..63).
    *
    * @param channel the channel to set
    *
    * @throws IllegalArgumentException if the channel is out of range.
    */
   public void setChannel(int channel)
   {
      if (channel < 0 || channel > 63)
         throw new IllegalArgumentException("invalid ADC channel");

      this.channel = channel;
   }

   /**
    * @return the number of samples to read.
    */
   public int getCount()
   {
      return count;
   }

   /**
    * Set the number of samples to read.
    *
    * @param count - the number of samples.
    */
   public void setCount(int count)
   {
      this.count = count;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getApciValue()
   {
      return channel;
   }

   /**
    * @return The type of the application: {@link ApplicationType#ADC_Read}.
    */
   @Override
   public ApplicationType getType()
   {
      return ApplicationType.ADC_Read;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      channel = super.getApciValue();
      count = in.readUnsignedByte();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.write(getCount());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (getType().getApci() << 6) | channel;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof ADCRead))
         return false;

      final ADCRead oo = (ADCRead) o;
      return channel == oo.channel && count == oo.count;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return getType().name() + String.format(" channel %d, %d samples", channel, count);
   }
}
