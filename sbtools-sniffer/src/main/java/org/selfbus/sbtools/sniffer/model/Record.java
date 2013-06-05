package org.selfbus.sbtools.sniffer.model;


/**
 * A record is a block of bytes.
 */
public class Record
{
   /**
    * The time in milliseconds when the first data byte was received.
    */
   public final long time;

   /**
    * The direction of the record.
    */
   public final Direction direction;

   /**
    * The data bytes.
    */
   public byte[] data;

   /**
    * The number of valid bytes in {@link #data}.
    */
   public int length;
   
   /**
    * Create a record with the date set to now.
    *
    * @param direction - the direction
    * @param data - the data bytes
    * @param length - the number of bytes in data
    */
   public Record(Direction direction, byte[] data, int length)
   {
      this(System.currentTimeMillis(), direction, data, length);
   }

   /**
    * Create a record.
    *
    * @param time - the time in milliseconds when the first data byte was received.
    * @param direction - the direction
    * @param data - the data bytes
    * @param length - the number of bytes in data
    */
   public Record(long time, Direction direction, byte[] data, int length)
   {
      this.time = time;
      this.direction = direction;
      this.data = data;
      this.length = length;
   }
}
