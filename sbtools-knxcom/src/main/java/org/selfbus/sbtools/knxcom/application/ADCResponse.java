package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Response to an A/D converter {@link ADCRead read request}.
 */
public class ADCResponse extends ADCRead
{
   private int value;

   /**
    * Create an A/D converter response object with channel 0, count 1 and value 0.
    */
   public ADCResponse()
   {
   }

   /**
    * Create an A/D converter response object.
    *
    * @param channel - A/D converter channel (0..63).
    * @param count - the number of read samples.
    * @param value - the sum of the count samples.
    *
    * @throws IllegalArgumentException if the channel is out of range.
    */
   public ADCResponse(int channel, int count, int value)
   {
      super(channel, count);
      this.value = value;
   }

   /**
    * @return the sum of the {@link #getCount count} samples.
    */
   public int getValue()
   {
      return value;
   }

   /**
    * Set the sum of the {@link #getCount count} samples.
    * 
    * @param value - the value to set.
    */
   public void setValue(int value)
   {
      this.value = value;
   }

   /**
    * @return The type of the application: {@link ApplicationType#ADC_Response}.
    */
   @Override
   public ApplicationType getType()
   {
      return ApplicationType.ADC_Response;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
      super.readData(in, length);
      value = in.readUnsignedShort();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      super.writeData(out);
      out.writeShort(getValue());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (super.hashCode() << 8) | value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof ADCResponse) || !super.equals(o))
         return false;

      final ADCResponse oo = (ADCResponse) o;
      return value == oo.value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return super.toString() + " value " + value;
   }
}
