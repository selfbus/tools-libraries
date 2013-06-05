package org.selfbus.sbtools.sniffer.serial;

import org.selfbus.sbtools.sniffer.model.Direction;

/**
 * A byte was received.
 */
public class ReceivedByte
{
   /**
    * The direction.
    */
   public final Direction direction;

   /**
    * The data byte.
    */
   public final int data;

   /**
    * The system time when the byte was received.
    */
   public final long time;

   /**
    * Create a received-byte object.
    *
    * @param direction - the direction
    * @param data - the received data byte
    */
   public ReceivedByte(Direction direction, int data)
   {
      this.direction = direction;
      this.data = data;
      this.time = System.currentTimeMillis();
   }
}
