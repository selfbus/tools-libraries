package org.selfbus.sbtools.common;

import org.selfbus.sbtools.common.types.ObjectPriority;
import org.selfbus.sbtools.common.types.ObjectType;

/**
 * A descriptor that describes a communication object of a device. These
 * descriptors are stored in the communication objects table of a device.
 */
public class ObjectDescriptor
{
   private final static int TRANSMIT_ENABLED = 0x40;
   private final static int EEPROM_DATAPOINTER = 0x20;
   private final static int WRITE_ENABLED = 0x10;
   private final static int READ_ENABLED = 0x08;
   private final static int COMM_ENABLED = 0x04;

   private boolean transmitEnabled, writeEnabled, readEnabled, commEnabled;
   private boolean eepromDataPointer;

   private int dataPointer;
   private ObjectType type = ObjectType.BITS_1;
   private ObjectPriority priority = ObjectPriority.LOW;

   /**
    * Create an empty object descriptor.
    */
   public ObjectDescriptor()
   {
   }

   /**
    * @return true if transmit is enabled.
    */
   public boolean isTransEnabled()
   {
      return transmitEnabled;
   }

   /**
    * Set transmit enabled.
    * 
    * @param transmitEnabled - the transmit enabled flag to set.
    */
   public void setTransEnabled(boolean transmitEnabled)
   {
      this.transmitEnabled = transmitEnabled;
   }

   /**
    * @return true if write is enabled.
    */
   public boolean isWriteEnabled()
   {
      return writeEnabled;
   }

   /**
    * Set write enabled.
    * 
    * @param writeEnabled - the write enabled flag to set.
    */
   public void setWriteEnabled(boolean writeEnabled)
   {
      this.writeEnabled = writeEnabled;
   }

   /**
    * @return true if read is enabled.
    */
   public boolean isReadEnabled()
   {
      return readEnabled;
   }

   /**
    * Set read enabled.
    * 
    * @param readEnabled - the read enabled flag to set.
    */
   public void setReadEnabled(boolean readEnabled)
   {
      this.readEnabled = readEnabled;
   }

   /**
    * @return true if communication is enabled.
    */
   public boolean isCommEnabled()
   {
      return commEnabled;
   }

   /**
    * Set communication enabled.
    * 
    * @param commEnabled - the communication enabled flag to set.
    */
   public void setCommEnabled(boolean commEnabled)
   {
      this.commEnabled = commEnabled;
   }

   /**
    * @return The data pointer.
    */
   public int getDataPointer()
   {
      return dataPointer;
   }

   /**
    * @return true if the data pointer points to EEPROM, false if it points to
    *         RAM.
    */
   public boolean isEepromDataPointer()
   {
      return eepromDataPointer;
   }

   /**
    * Set the data pointer.
    *
    * @param dataPointer - the data pointer to set (0..255)
    * @param isEepromDataPointer - true if the data pointer points into EEPROM,
    *           false if the data pointer points into RAM.
    */
   public void setDataPointer(int dataPointer, boolean isEepromDataPointer)
   {
      if (dataPointer < -128 || dataPointer > 255)
         throw new IllegalArgumentException("data pointer must be a single byte value");

      this.dataPointer = dataPointer & 255;
      this.eepromDataPointer = isEepromDataPointer;
   }

   /**
    * @return The object type.
    */
   public ObjectType getType()
   {
      return type;
   }

   /**
    * Set the object type.
    * 
    * @param type - the object type to set.
    */
   public void setType(ObjectType type)
   {
      this.type = type;
   }

   /**
    * @return The object priority.
    */
   public ObjectPriority getPriority()
   {
      return priority;
   }

   /**
    * Set the object priority.
    * 
    * @param priority - the object priority to set.
    */
   public void setPriority(ObjectPriority priority)
   {
      this.priority = priority;
   }

   /**
    * @return The control byte.
    */
   public byte getControlByte()
   {
      int control = (1 << 7) | priority.ordinal();
      if (transmitEnabled)
         control |= TRANSMIT_ENABLED;
      if (eepromDataPointer)
         control |= EEPROM_DATAPOINTER;
      if (writeEnabled)
         control |= WRITE_ENABLED;
      if (readEnabled)
         control |= READ_ENABLED;
      if (commEnabled)
         control |= COMM_ENABLED;

      return (byte) control;
   }
   
   /**
    * Initialize the object descriptor from a 3 byte array starting at a
    * specific offset.
    * 
    * @param data - the raw data to use.
    * @param offset - the offset within data.
    */
   public void fromByteArray(byte[] data, int offset)
   {
      dataPointer = data[offset] & 255;

      final int control = data[offset + 1];
      priority = ObjectPriority.valueOf(control & 3);
      transmitEnabled = (control & TRANSMIT_ENABLED) == TRANSMIT_ENABLED;
      eepromDataPointer = (control & EEPROM_DATAPOINTER) == EEPROM_DATAPOINTER;
      writeEnabled = (control & WRITE_ENABLED) == WRITE_ENABLED;
      readEnabled = (control & READ_ENABLED) == READ_ENABLED;
      commEnabled = (control & COMM_ENABLED) == COMM_ENABLED;

      type = ObjectType.valueOf(data[offset + 2] & 63);
   }

   /**
    * @return The object descriptor as 3 byte array.
    */
   public byte[] toByteArray()
   {
      final byte[] data = new byte[3];

      data[0] = (byte) dataPointer;
      data[1] = (byte) getControlByte();
      data[2] = (byte) type.ordinal();

      return data;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return (dataPointer << 6) | (type == null ? 0 : type.ordinal());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof ObjectDescriptor))
         return false;
      final ObjectDescriptor oo = (ObjectDescriptor) o;

      return dataPointer == oo.dataPointer && type == oo.type && priority == oo.priority
            && transmitEnabled == oo.transmitEnabled && writeEnabled == oo.writeEnabled
            && readEnabled == oo.readEnabled && commEnabled == oo.commEnabled
            && eepromDataPointer == oo.eepromDataPointer;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return "object descriptor to " + (eepromDataPointer ? "EEPROM" : "RAM") + " @" + dataPointer;
   }
}
