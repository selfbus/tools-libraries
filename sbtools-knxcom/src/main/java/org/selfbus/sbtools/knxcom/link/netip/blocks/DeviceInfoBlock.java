package org.selfbus.sbtools.knxcom.link.netip.blocks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.link.netip.types.DescriptionInfoType;
import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;
import org.selfbus.sbtools.knxcom.types.MediumType;

/**
 * Device information block. Contains description about the device.
 */
public final class DeviceInfoBlock implements DescriptionInfoBlock
{
   private MediumType medium = MediumType.TWISTED_PAIR;
   private int status;
   private PhysicalAddress busAddress = PhysicalAddress.NULL;
   private int projectId;
   private final byte[] serial = new byte[6];
   private InetAddress routingAddr;
   private final byte[] macAddr = new byte[6];
   private String name;

   /**
    * Create an empty device-info block.
    */
   public DeviceInfoBlock()
   {
      this(MediumType.TWISTED_PAIR);
   }

   /**
    * Create a device-info block.
    */
   public DeviceInfoBlock(MediumType medium)
   {
      this.medium = medium;
   }

   /**
    * @return the medium type.
    */
   public MediumType getMedium()
   {
      return medium;
   }

   /**
    * Set the medium type.
    */
   public void setMedium(MediumType medium)
   {
      this.medium = medium;
   }

   /**
    * Returns the device status. bits 7..1 are reserved, bit 0: programming
    * mode.
    *
    * @return the device status.
    */
   public int getStatus()
   {
      return status;
   }

   /**
    * Set the device status.
    *
    * @param status - the status to set.
    *
    * @see #getStatus()
    */
   public void setStatus(int status)
   {
      this.status = status;
   }

   /**
    * @return the KNX/EIB physical address.
    */
   public PhysicalAddress getBusAddress()
   {
      return busAddress;
   }

   /**
    * Set the KNX/EIB physical address.
    *
    * @param busAddress - the bus address
    */
   public void setBusAddress(PhysicalAddress busAddress)
   {
      this.busAddress = busAddress;
   }

   /**
    * @return the 2-byte project installation identifier. Bit 0..3: installation
    *         number, bit 4..15: project number.
    */
   public int getProjectId()
   {
      return projectId;
   }

   /**
    * Set the 2-byte project installation identifier. Bit 0..3: installation
    * number, bit 4..15: project number.
    */
   public void setProjectId(int projectId)
   {
      this.projectId = projectId;
   }

   /**
    * @return the name of the device.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name of the device. Encoding of the string is ISO 8859-1, maximum
    * length is 30 characters.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the 6-byte KNX serial number of the device.
    */
   public byte[] getSerial()
   {
      return serial;
   }

   /**
    * @return the KNXnet/IP routing multicast {@link Inet4Address IPv4 address}.
    *         Devices that do not implement KNXnet/IP routing shall set this
    *         value to null.
    */
   public InetAddress getRoutingAddr()
   {
      return routingAddr;
   }

   /**
    * Set the KNXnet/IP routing multicast {@link Inet4Address IPv4 address}.
    * Devices that do not implement KNXnet/IP routing shall set this value to
    * null
    */
   public void setRoutingAddr(InetAddress addr)
   {
      this.routingAddr = addr;
   }

   /**
    * Return the 6-byte MAC address of the device.
    */
   public byte[] getMacAddr()
   {
      return macAddr;
   }

   /**
    * Initialize the object from the given {@link DataInput data input stream}.
    *
    * @param in - the input stream to read
    *
    * @throws InvalidDataException
    */
   public void readData(DataInput in) throws IOException
   {
      in.skipBytes(1); // structure length

      final int typeCode = in.readUnsignedByte();
      final DescriptionInfoType type = DescriptionInfoType.valueOf(typeCode);
      if (type != DescriptionInfoType.DEVICE_INFO)
         throw new InvalidDataException("Invalid type " + type + ", expected " + DescriptionInfoType.DEVICE_INFO,
               typeCode);

      medium = MediumType.valueOf(in.readUnsignedByte());
      status = in.readUnsignedByte();
      busAddress = new PhysicalAddress(in.readUnsignedShort());
      projectId = in.readUnsignedShort();
      in.readFully(serial);

      final byte[] addrData = new byte[4];
      in.readFully(addrData);
      if (Arrays.equals(new byte[] { 0, 0, 0, 0 }, addrData))
         routingAddr = null;
      else routingAddr = InetAddress.getByAddress(addrData);

      in.readFully(macAddr);

      final byte[] nameBytes = new byte[30];
      final char[] nameChars = new char[30];
      in.readFully(nameBytes);
      int i = 0;
      for (; i < 30 && nameBytes[i] != 0; ++i)
         nameChars[i] = (char) nameBytes[i];

      name = String.valueOf(nameChars, 0, i);
   }

   /**
    * Write the object to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write the object to
    *
    * @throws IOException
    */
   public void writeData(DataOutput out) throws IOException
   {
      out.writeByte(54); // length of this block
      out.writeByte(DescriptionInfoType.DEVICE_INFO.code);
      out.writeByte(medium.code);
      out.writeByte(status);
      out.writeShort(busAddress.getAddr());
      out.writeShort(projectId);
      out.write(serial);

      if (routingAddr == null)
         out.write(new byte[] { 0, 0, 0, 0 });
      else out.write(routingAddr.getAddress());

      out.write(macAddr);

      int nameLen = name == null ? 0 : name.length();
      if (nameLen > 30)
         nameLen = 30;

      for (int i = 0; i < nameLen; ++i)
         out.write((byte) name.charAt(i));

      for (; nameLen < 30; ++nameLen)
         out.write(0);
   }
}
