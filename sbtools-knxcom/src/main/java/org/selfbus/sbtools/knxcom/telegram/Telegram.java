package org.selfbus.sbtools.knxcom.telegram;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.selfbus.sbtools.common.address.Address;
import org.selfbus.sbtools.common.address.GroupAddress;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.application.ApplicationFactory;
import org.selfbus.sbtools.knxcom.application.ApplicationType;

/**
 * A communication data packet as it is sent on the KNX/EIB bus.
 *
 * It is mandatory for subclasses to override {@link #clone()} to avoid
 * problems.
 */
public class Telegram implements Cloneable
{
   private PhysicalAddress from = PhysicalAddress.NULL;
   private Address dest = GroupAddress.BROADCAST;
   private int routingCounter = 6;
   private Priority priority = Priority.LOW;
   private boolean repeated = true;  // not sure if this is wise, but ETS does it so
   private Transport transport = Transport.Individual;
   private boolean extFormat;
   private int sequence;
   private Application application;
   private Object userData;

   /**
    * Create an empty telegram object.
    */
   public Telegram()
   {
   }

   /**
    * Create a telegram object with an application.
    *
    * @param application - the application to set
    */
   public Telegram(Application application)
   {
      this.application = application;
   }

   /**
    * Clone the telegram.
    */
   @Override
   public Telegram clone()
   {
      try
      {
         return (Telegram) super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         throw new RuntimeException(e);
      }
   }

   /**
    * @return the application.
    */
   public Application getApplication()
   {
      return application;
   }

   /**
    * @return the application type.
    */
   public ApplicationType getApplicationType()
   {
      return application == null ? ApplicationType.None : application.getType();
   }

   /**
    * Returns the destination address. This can either be a
    * {@link PhysicalAddress} physical address, or a {@link GroupAddress} group
    * address.
    *
    * @return the destination address.
    */
   public Address getDest()
   {
      return dest;
   }

   /**
    * @return the sender address.
    */
   public PhysicalAddress getFrom()
   {
      return from;
   }

   /**
    * @return the priority.
    */
   public Priority getPriority()
   {
      return priority;
   }

   /**
    * Returns the routing counter. 0 means never route, 1..6 is the number of
    * routing hops that would occur, 7 means route always.
    *
    * @return the routing counter.
    */
   public int getRoutingCounter()
   {
      return routingCounter;
   }

   /**
    * @return the sequence number. Only used for connected-data mode transport
    *         types.
    */
   public int getSequence()
   {
      return sequence;
   }

   /**
    * @return the transport type.
    */
   public Transport getTransport()
   {
      return transport;
   }

   /**
    * @return the repeated flag.
    */
   public boolean isRepeated()
   {
      return repeated;
   }

   /**
    * Set the application.
    *
    * @param application - the application to set.
    */
   public void setApplication(Application application)
   {
      this.application = application;
   }

   /**
    * Set the application by specifying the {@link ApplicationType application
    * type}.
    *
    * @param type - the application type.
    */
   public void setApplicationType(ApplicationType type)
   {
      application = type == null ? null : ApplicationFactory.createApplication(type);
   }

   /**
    * Set the destination address. This can either be a {@link PhysicalAddress}
    * physical address, or a {@link GroupAddress} group address.
    *
    * Also sets the transport type, if it is yet unset, to
    * {@link Transport#Individual}.
    */
   public void setDest(Address dest)
   {
      this.dest = dest;

      if (transport == null)
      {
         transport = Transport.Individual;
      }
   }

   /**
    * Set the sender address.
    */
   public void setFrom(PhysicalAddress from)
   {
      this.from = from;
   }

   /**
    * Set the priority. The default priority is {@link Priority#LOW}.
    */
   public void setPriority(Priority priority)
   {
      this.priority = priority;
   }

   /**
    * Set the repeated flag.
    */
   public void setRepeated(boolean repeated)
   {
      this.repeated = repeated;
   }

   /**
    * Set the routing counter. 0 means never route, 1..6 is the number of
    * routing hops that would occur, 7 means route always. Be careful with using
    * 7, it may result in telegrams that run on the bus infinitely.
    */
   public void setRoutingCounter(int routingCounter)
   {
      if (routingCounter < 0)
         routingCounter = 0;
      else if (routingCounter > 7)
         routingCounter = 7;

      this.routingCounter = routingCounter;
   }

   /**
    * Set the sequence number. Only used for connected-data mode transport
    * types.
    */
   public void setSequence(int sequence)
   {
      this.sequence = sequence;
   }

   /**
    * Set the transport type.
    */
   public void setTransport(Transport transport)
   {
      this.transport = transport;
   }

   /**
    * @return true if the telegram is written in extended frame format.
    */
   public boolean isExtFormat()
   {
      return extFormat;
   }

   /**
    * Switch the telegram to extended frame format for
    * {@link #writeData(DataOutput)}.
    *
    * @param extFormat - enable extended frame format
    */
   public void setExtFormat(boolean extFormat)
   {
      this.extFormat = extFormat;
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}. The
    * first byte of the stream is expected to be the first byte of the frame
    * body, excluding the frame type.
    *
    * @param in - the input stream to read.
    *
    * @throws InvalidDataException
    */
   public void readData(DataInput in) throws IOException
   {
      readData(in, false);
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}. The
    * first byte of the stream is expected to be the first byte of the frame
    * body, excluding the frame type.
    *
    * @param in - the input stream to read.
    * @param forceExtFormat - enforce extended frame format.
    *
    * @throws InvalidDataException
    */
   public void readData(DataInput in, boolean forceExtFormat) throws IOException
   {
      final int ctrl = in.readUnsignedByte();
      int dataLen;

      extFormat = forceExtFormat || (ctrl & 0x80) == 0;
      if (extFormat)
         dataLen = readDataExtendedHeader(in, ctrl);
      else dataLen = readDataShortHeader(in, ctrl);

      // TPCI - transport control field
      int tpci = in.readUnsignedByte();
      transport = Transport.valueOf(dest instanceof GroupAddress, tpci);
      if (transport == null)
         throw new InvalidDataException("TPCI contains no valid transport type", tpci);

      if (transport.hasSequence)
         sequence = (tpci >> 2) & 15;
      else sequence = 0;

      if ((transport.mask & 3) == 0)
      {
         // APCI - application type & data bits
         final int apciByte = in.readUnsignedByte();
         final int apci = ((tpci & 3) << 8) | apciByte;
         final ApplicationType type = ApplicationType.valueOf(apci);
         application = ApplicationFactory.createApplication(type);

         final int dataMask = type.getDataMask();
         application.setApciValue(apciByte & dataMask);

         if (dataLen > 1)
            application.readData(in, dataLen - 1);
      }
      else
      {
         application = null;
      }
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}, short
    * telegram frame format. This method is usually called by
    * {@link #readData(DataInput, boolean)}.
    *
    * @param in - the input stream to read.
    * @param ctrl - the control byte.
    *
    * @return the number of APCI data bytes
    *
    * @throws IOException
    */
   int readDataShortHeader(DataInput in, int ctrl) throws IOException
   {
      // Control byte:
      //
      // bit 7: frame length-type: 0=extended frame, 1=standard frame;
      // bit 6: frame type 1: 0=data telegram, 1=poll-data telegram;
      // bit 5: repeated flag: 0=repeated, 1=not repeated;
      // bit 4: frame type 2: 0=acknowledge frame, 1=normal frame;
      // bit 3+2: Priority: 0=system, 1=urgent, 2=normal, 3=low priority;
      // bit 1: 0 bit
      // bit 0: confirmation: 0=ok, 1=error.
      //
      priority = Priority.valueOf((ctrl >> 2) & 3);
      repeated = (ctrl & 0x20) == 0;

      // 16-bit sender address
      from = new PhysicalAddress(in.readUnsignedShort());

      // 16-bit destination address
      final int destAddr = in.readUnsignedShort();

      /*
       * DRL byte (DRL means: destination address, routing, length).
       * bit 7: destination is 0=physical address, 1=group address bit
       * 6..4: routing hop count: 0=never route, 1..6=number of routings, 7=always route bit
       * 3..0: data length minus 2: 0 means 2 bytes, 15 means 17 bytes
       */
      final int drl = in.readUnsignedByte();

      routingCounter = (drl >> 4) & 7;

      final boolean isGroup = (drl & 0x80) != 0;
      if (isGroup)
         dest = new GroupAddress(destAddr);
      else dest = new PhysicalAddress(destAddr);

      // data length
      return drl & 15;
   }

   /**
    * Initialize the object from a {@link DataInput data input stream}, extended
    * telegram frame format. This method is usually called by
    * {@link #readData(DataInput, boolean)}.
    *
    * @param in - the input stream to read.
    * @param ctrl - the control byte.
    *
    * @return the number of APCI data bytes
    *
    * @throws IOException
    */
   int readDataExtendedHeader(DataInput in, int ctrl) throws IOException
   {
      // Control byte #1:
      //
      // bit 7: frame length-type: 0=extended frame, 1=standard frame;
      // bit 6: 0;
      // bit 5: repeated flag: 0=repeated, 1=not repeated;
      // bit 4: system broadcast flag: 0=system broadcast, 1=broadcast;
      // bit 3+2: Priority: 0=system, 1=urgent, 2=normal, 3=low priority;
      // bit 1: acknowledge request flag: 0=no ack, 1=ack requested;
      // bit 0: confirmation: 0=ok, 1=error.
      //
      priority = Priority.valueOf((ctrl >> 2) & 3);
      repeated = (ctrl & 0x20) == 0;

      // Control byte #2:
      //
      // bit 7: destination address type: 0=individual, 1=group;
      // bit 6..4: routing counter;
      // bit 3..0: encoding: 0=standard long frame, 01xxb=LTE frame.
      //
      final int ctrl2 = in.readUnsignedByte();
      final boolean isGroup = (ctrl2 & 0x80) != 0;
      routingCounter = (ctrl2 >> 4) & 7;

      from = new PhysicalAddress(in.readUnsignedShort());
      if (isGroup)
         dest = new GroupAddress(in.readUnsignedShort());
      else dest = new PhysicalAddress(in.readUnsignedShort());

      // 8-bit data length
      return in.readUnsignedByte();
   }

   /**
    * Write the telegram to a {@link DataOutput data output stream}.
    *
    * @param out - the output stream to write the telegram to.
    *
    * @throws IOException
    */
   public void writeData(DataOutput out) throws IOException
   {
      byte[] appData = null;
      int appDataLen = 0;
      int apciHigh = 0;

      if (transport.mask != 255 && application != null)
      {
         appData = application.toByteArray();
         appDataLen = appData.length - 1;

         if (appDataLen > 15)
            extFormat = true;

         apciHigh = appData[0];
      }

      if (extFormat)
         writeDataExtendedHeader(out, appDataLen);
      else writeDataShortHeader(out, appDataLen);

      int tpci = transport.value;
      tpci |= apciHigh & ~transport.mask;
      if (transport.hasSequence)
         tpci |= (sequence & 15) << 2;
      out.write(tpci);

      if (appData != null)
         out.write(appData, 1, appData.length - 1);
   }

   /**
    * Write the telegram header to a {@link DataOutput data output stream} with
    * short telegram frame format.
    *
    * @param out - the output stream to write the telegram to.
    * @param appDataLen - the length of the application data.
    *
    * @throws IOException
    */
   void writeDataShortHeader(DataOutput out, int appDataLen) throws IOException
   {
      // Control byte:
      //
      // bit 7: frame length-type: 0=extended frame, 1=standard frame;
      // bit 6: frame type 1: 0=data telegram, 1=poll-data telegram;
      // bit 5: repeated flag: 0=repeated, 1=not repeated;
      // bit 4: frame type 2: 0=acknowledge frame, 1=normal frame;
      // bit 3+2: Priority: 0=system, 1=urgent, 2=normal, 3=low priority;
      // bit 1: 0 bit 0: confirmation: 0=ok, 1=error.
      //
      int ctrl = (1 << 7) | (1 << 4);
      if (!repeated)
         ctrl |= 1 << 5;
      ctrl |= priority.id << 2;
      out.write(ctrl);

      out.writeShort(from.getAddr());
      out.writeShort(dest.getAddr());

      int drl = ((routingCounter & 7) << 4) | (appDataLen & 15);
      if (dest instanceof GroupAddress)
         drl |= 0x80;

      out.write(drl);
   }

   /**
    * Write the telegram header to a {@link DataOutput data output stream} with
    * extended telegram frame format.
    *
    * @param out - the output stream to write the telegram to.
    * @param appDataLen - the length of the application data.
    *
    * @throws IOException
    */
   void writeDataExtendedHeader(DataOutput out, int appDataLen) throws IOException
   {
      // Control byte #1:
      //
      // bit 7: frame length-type: 0=extended frame, 1=standard frame;
      // bit 6: 0;
      // bit 5: repeated flag: 0=repeated, 1=not repeated;
      // bit 4: system broadcast flag: 0=system broadcast, 1=broadcast;
      // bit 3+2: Priority: 0=system, 1=urgent, 2=normal, 3=low priority;
      // bit 1: acknowledge request flag: 0=no ack, 1=ack requested;
      // bit 0: confirmation: 0=ok, 1=error.
      //
      int ctrl = (1 << 4);
      if (!repeated)
         ctrl |= 1 << 5;
      ctrl |= priority.id << 2;
      out.write(ctrl);

      // Control byte #2:
      //
      // bit 7: destination address type: 0=individual, 1=group;
      // bit 6..4: routing counter;
      // bit 3..0: encoding: 0=standard long frame, 01xxb=LTE frame.
      //
      int ctrl2 = routingCounter << 4;
      if (dest instanceof GroupAddress)
         ctrl2 |= 0x80;
      out.write(ctrl2);

      out.writeShort(from.getAddr());
      out.writeShort(dest.getAddr());

      out.write(appDataLen);
   }

   /**
    * @return The custom user data object.
    */
   public Object getUserData()
   {
      return userData;
   }

   /**
    * Set the custom user data object. The object is not used by the library and can be
    * accessed with {@link #getUserData()}.
    *
    * @param userData - the user data to set
    */
   public void setUserData(Object userData)
   {
      this.userData = userData;
   }

   /**
    * Write the telegram into a byte array.
    *
    * @return the telegram serialized into a byte array
    */
   public byte[] toByteArray()
   {
      try
      {
         final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream(1024);
         final DataOutputStream out = new DataOutputStream(outByteStream);

         writeData(out);

         return outByteStream.toByteArray();
      }
      catch (IOException e)
      {
         throw new RuntimeException();
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return from.getAddr() * 13 + dest.getAddr();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object o)
   {
      if (this == o)
         return true;

      if (!(o instanceof Telegram))
         return false;

      final Telegram oo = (Telegram) o;

      return from.equals(oo.from) && dest.equals(oo.dest) && transport == oo.transport && sequence == oo.sequence
      && (application == null ? oo.application == null : application.equals(oo.application));
   }

   /**
    * Test if two telegrams are mostly equal. This is the same as {@link #equals(Object)}, except
    * that the {@link #getFrom() from address} is not compared.
    *
    * @param o - the telegram to compare with.
    * @return true if o is similar to this telegram.
    */
   public boolean isSimilar(Telegram o)
   {
      return (dest == null ? o.dest == null : dest.equals(o.dest))
      && transport.mask == o.transport.mask && transport.value == o.transport.value
      && (transport != Transport.Connected || sequence == o.sequence)
      && (!transport.hasApplication || application.getType() == o.application.getType());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();

      sb.append(getTransport()).append(' ');
      sb.append("from ").append(getFrom()).append(" to ").append(getDest()).append(", ");

      if (getTransport().hasSequence)
         sb.append("sequence ").append(getSequence()).append(", ");

      sb.append(application == null ? "no application" : application.toString());

      return sb.toString();
   }
}
