package org.selfbus.sbtools.sniffer.serial;

import gnu.io.SerialPort;

import org.selfbus.sbtools.sniffer.internal.I18n;

/**
 * Serial transmission parity.
 */
public enum Parity
{
   /**
    * No parity.
    */
   NONE(SerialPort.PARITY_NONE, I18n.getMessage("Parity.none")),

   /**
    * Odd parity.
    */
   ODD(SerialPort.PARITY_ODD, I18n.getMessage("Parity.odd")),
   
   /**
    * Even parity.
    */
   EVEN(SerialPort.PARITY_EVEN, I18n.getMessage("Parity.even")),

   /**
    * Mark parity.
    */
   MARK(SerialPort.PARITY_MARK, I18n.getMessage("Parity.mark")),

   /**
    * Space parity.
    */
   SPACE(SerialPort.PARITY_SPACE, I18n.getMessage("Parity.space"));

   /**
    * The ID of the parity, as used in the {@link SerialPort} class.
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
   private Parity(int id, String label)
   {
      this.id = id;
      this.label = label;
   }
}
