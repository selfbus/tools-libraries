package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Interface for application layer services.
 *
 * @see AbstractApplication
 * @see Telegram
 */
public interface Application
{
   /**
    * @return The type of the application.
    */
   public ApplicationType getType();

   /**
    * The APCI field of a {@link Telegram telegram} has up to 6 bits extra space
    * for a value. Whether or not this 6 bit are used depends on the
    * {@link Application application}.
    * <p>
    * The caller might not call {@link #writeData(DataOutput)}, so the
    * implementation of this method shall not depend on
    * {@link #writeData(DataOutput)} being called.
    *
    * @return the APCI data value
    */
   public int getApciValue();

   /**
    * Set the APCI data value.
    * <p>
    * The caller might not call {@link #readData(DataInput, int)}, so the
    * implementation of this method shall not depend on
    * {@link #readData(DataInput, int)} being called.
    *
    * @param value - the data value
    *
    * @see #getApciValue()
    */
   public void setApciValue(int value);

   /**
    * Write the application into a byte array. The first two bytes of the
    * returned array contain the {@link ApplicationType ACPI application type}
    * and (optionally) the application type data value.
    *
    * @return the application serialized into a byte array
    */
   public byte[] toByteArray();

   /**
    * Initialize the object from a {@link DataInput data input stream}. The
    * first byte of the stream is expected to be the first regular byte of the
    * application data. The extra (up to 6) bits from the telegram's APCI field
    * are passed with {@link #setApciValue(int)}.
    *
    * @param in - the input stream to read.
    * @param length - the number of bytes that the application shall read
    *
    * @throws IOException if the stream could not be read.
    */
   public void readData(DataInput in, int length) throws IOException;

   /**
    * Write the object to a {@link DataOutput data output stream}. The first
    * byte of the stream is expected to be the first regular byte of the
    * application data. The extra (up to 6) bits from the telegram's APCI field
    * are fetched by the caller with {@link #getApciValue()}.
    *
    * @param out - the output stream to write the object to.
    *
    * @throws IOException if the stream could not be written.
    */
   public void writeData(DataOutput out) throws IOException;
}
