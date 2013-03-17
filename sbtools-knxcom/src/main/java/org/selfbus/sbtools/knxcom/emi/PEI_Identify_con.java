package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;

/**
 * Physical external interface (PEI) identify confirmation.
 */
public final class PEI_Identify_con extends AbstractEmiFrame
{
   protected PhysicalAddress addr = PhysicalAddress.NULL;
   protected long serial;

   /**
    * Create an empty object.
    */
   public PEI_Identify_con(EmiFrameType type)
   {
      super(type);
   }

   /**
    * Set the physical address.
    *
    * @param addr - the physical address to set
    */
   public void setAddr(PhysicalAddress addr)
   {
      this.addr = addr;
   }

   /**
    * @return the physical address
    */
   public PhysicalAddress getAddr()
   {
      return addr;
   }

   /**
    * Set the 6-byte serial number.
    *
    * @param serial - the serial number to set
    */
   public void setSerial(long serial)
   {
      this.serial = serial;
   }

   /**
    * @return the 6-byte serial number.
    */
   public long getSerial()
   {
      return serial;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in) throws IOException
   {
      addr = PhysicalAddress.valueOf(in.readUnsignedShort());

      serial = in.readUnsignedShort() << 32L;
      serial |= in.readUnsignedShort() << 16L;
      serial |= in.readUnsignedShort();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
      out.writeShort(addr == null ? 0 : addr.getAddr());
      out.writeShort((int) (serial >> 32));
      out.writeShort((int) (serial >> 16));
      out.writeShort((int) serial);
   }

   /**
    * @return the object in human readable form.
    */
   @Override
   public String toString()
   {
      final int serialHigh = (int) (serial >> 32) & 0xffff;
      final int serialMid = (int) (serial >> 16) & 0xffff;
      final int serialLow = (int) serial & 0xffff;

      return getTypeString() + ' ' + addr + String.format(" version %d.%d.%d", serialHigh, serialMid, serialLow);
   }
}