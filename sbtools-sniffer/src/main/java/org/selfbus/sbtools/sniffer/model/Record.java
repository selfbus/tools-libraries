package org.selfbus.sbtools.sniffer.model;

import java.util.Date;

/**
 * A record is a block of bytes.
 */
public class Record
{
   /**
    * The timestamp when the first data byte was received.
    */
   public final Date date;

   /**
    * The direction of the record.
    */
   public final Direction direction;

   /**
    * The data bytes.
    */
   public byte[] data;

   /**
    * Create a record with the date set to now.
    *
    * @param direction - the direction
    * @param data - the data bytes
    */
   public Record(Direction direction, byte[] data)
   {
      this(direction, data, new Date());
   }

   /**
    * Create a record.
    *
    * @param direction - the direction
    * @param data - the data bytes
    * @param date - the timestamp when the first data byte was received.
    */
   public Record(Direction direction, byte[] data, Date date)
   {
      this.direction = direction;
      this.data = data;
      this.date = date;
   }
}
