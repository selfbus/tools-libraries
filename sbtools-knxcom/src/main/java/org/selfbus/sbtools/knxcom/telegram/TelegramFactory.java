package org.selfbus.sbtools.knxcom.telegram;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.selfbus.sbtools.common.address.GroupAddress;
import org.selfbus.sbtools.knxcom.application.GroupValueWrite;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;

/**
 * Factory class that creates {@link EmiFrame EMI frame} objects from raw data
 * and {@link DataInput data input} streams.
 */
public final class TelegramFactory
{
   /**
    * Create a group-value-write telegram.
    * 
    * @param dest - the destination address
    * @param data - the payload
    */
   static public Telegram createTelegram(GroupAddress dest, byte[] data)
   {
      final Telegram telegram = new Telegram(new GroupValueWrite(data));
      telegram.setDest(dest);

      return telegram;
   }

   /**
    * Create a telegram from a raw data buffer.
    *
    * @param data - the data buffer to process.
    *
    * @return the created telegram.
    *
    * @throws IOException
    */
   static public Telegram createTelegram(final byte[] data) throws IOException
   {
      return createTelegram(new DataInputStream(new ByteArrayInputStream(data)));
   }

   /**
    * Create a telegram from a data input stream.
    *
    * @param in - the telegram is read from this stream.
    *
    * @return the created telegram.
    *
    * @throws IOException
    */
   static public Telegram createTelegram(DataInput in) throws IOException
   {
      final Telegram telegram = new Telegram();
      telegram.readData(in);
      return telegram;
   }

   /*
    * Disabled
    */
   private TelegramFactory()
   {
   }
}
