package org.selfbus.sbtools.sniffer.serial;

import gnu.io.SerialPort;

import org.selfbus.sbtools.sniffer.misc.I18n;

/**
 * Serial transmission stop bits.
 */
public enum Stop
{
   /**
    * 1 stop bit.
    */
   BITS_1(SerialPort.STOPBITS_1, I18n.getMessage("Stop.1")),

   /**
    * 1.5 stop bits.
    */
   BITS_1_5(SerialPort.STOPBITS_1_5, I18n.getMessage("Stop.1_5")),

   /**
    * 2 stop bits.
    */
   BITS_2(SerialPort.STOPBITS_2, I18n.getMessage("Stop.2"));

   /**
    * The ID of the stop bit, as used in the {@link SerialPort} class.
    */
   public final int id;

   /**
    * The human readable name.
    */
   public final String label;

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return label;
   }

   /*
    * Internal constructor.
    */
   private Stop(int id, String label)
   {
      this.id = id;
      this.label = label;
   }
}
