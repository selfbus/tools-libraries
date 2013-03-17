package org.selfbus.sbtools.knxcom.link.serial;

/**
 * State of the FT1.2 communication.
 */
enum Ft12State
{
   /**
    * Idle, no error, ready to send.
    */
   OK,

   /**
    * Connection closed.
    */
   CLOSED,

   /**
    * Waiting for an acknowledgment after send.
    */
   ACK_PENDING;
}
