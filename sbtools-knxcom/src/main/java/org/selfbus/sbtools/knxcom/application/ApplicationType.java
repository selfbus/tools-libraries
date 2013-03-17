package org.selfbus.sbtools.knxcom.application;

import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Types of telegram applications. The types are stored in the APCI field of a
 * telegram.
 */
public enum ApplicationType
{
   /**
    * The response to the {@link #GroupValue_Read} request. The data depends on
    * the type of the communication object.
    * <p>
    * Multicast - To be sent to a group address.
    * <p>
    * 0-14 data bytes: the value of the communication object.
    */
   GroupValue_Response(0x040, 4, 0, 14, null, GroupValueResponse.class),

   /**
    * Read the value of the ASAP (communication object).
    * <p>
    * Multicast - To be sent to a group address.
    * <p>
    * Response: {@link #GroupValue_Response}.
    */
   GroupValue_Read(0x000, 10, 0, 0, GroupValue_Response, GroupValueRead.class),

   /**
    * Send an update of an ASAP (communication object) to all connected ASAPs.
    * The data depends on the type of the communication object.
    * <p>
    * 0-14 data bytes: data.
    */
   GroupValue_Write(0x080, 4, 0, 14, null, GroupValueWrite.class),

   /**
    * Set the physical address of all devices that are in programming mode.
    * Broadcast.
    * <p>
    * 2 data bytes: the new physical address (high/low byte).
    */
   IndividualAddress_Write(0x0c0, 10, 4, 4, null, IndividualAddressWrite.class),

   /**
    * The response to {@link #IndividualAddress_Read}.
    * <p>
    * Broadcast. No data bytes.
    */
   IndividualAddress_Response(0x140, 10, 0, 0, null, IndividualAddressResponse.class),

   /**
    * Read the physical address of all devices that are in programming mode.
    * <p>
    * Broadcast. No data bytes.
    * <p>
    * Response: {@link #IndividualAddress_Response}.
    */
   IndividualAddress_Read(0x100, 10, 0, 0, IndividualAddress_Response, IndividualAddressRead.class),

   /**
    * The response to the {@link #IndividualAddressSerialNumber_Read} request.
    * <p>
    * 10 data bytes: 6 bytes KNX serial number, 2 bytes address high/low, 2
    * bytes reserved.
    */
   IndividualAddressSerialNumber_Response(0x3dd, 10, 10, 10),

   /**
    * Read the individual address of a device which is identified by a given KNX
    * serial number.
    * <p>
    * Broadcast. 6 data bytes: KNX serial number.
    * <p>
    * Response: {@link #IndividualAddressSerialNumber_Response}.
    */
   IndividualAddressSerialNumber_Read(0x3dc, 10, 6, 6, IndividualAddressSerialNumber_Response),

   /**
    * Set the serial number of a device which is identified by a given KNX
    * serial number. Broadcast.
    * <p>
    * 12 data bytes: 6 bytes KNX serial number, 2 bytes new address high/low, 4
    * bytes reserved.
    */
   IndividualAddressSerialNumber_Write(0x3de, 10, 12, 12),

   /**
    * Response to the {@link #ADC_Read} request.
    * <p>
    * 4 data bytes: channel number, read count, sum of A/D converter values
    * high/low.
    */
   ADC_Response(0x1c0, 4, 4, 4, null, ADCResponse.class),

   /**
    * Read the value of an A/D converter.
    * <p>
    * 2 data bytes: channel number, read count.
    * <p>
    * Response: {@link #ADC_Response}.
    */
   ADC_Read(0x180, 4, 3, 3, ADC_Response, ADCRead.class),

   /**
    * Response to {@link #Memory_Read}.
    * <p>
    * 4+ data bytes: number of memory bytes (1..63), 2-byte address, memory
    * contents.
    */
   Memory_Response(0x240, 4, 3, 15, null, MemoryResponse.class),

   /**
    * Read application memory.
    * <p>
    * 3 data bytes: number of bytes to read (1..63), 2-byte address.
    * <p>
    * Response: {@link #Memory_Response}.
    */
   Memory_Read(0x200, 4, 3, 3, Memory_Response, MemoryRead.class),

   /**
    * Write application memory.
    * <p>
    * 3+ data bytes: number of bytes to read (1..63), 2-byte address, memory
    * contents.
    */
   Memory_Write(0x280, 4, 3, 15, null, MemoryWrite.class),

   /**
    * Response to {@link #UserMemory_Read}.
    * <p>
    * 3+ data bytes: 4-bit address extension + 4-bit number of bytes, 2-byte
    * address, memory contents.
    */
   UserMemory_Response(0x2c1, 10, 3, 15),

   /**
    * Read user-data memory.
    * <p>
    * 3 data bytes: 4-bit address extension + 4-bit number of bytes, 2-byte
    * address.
    * <p>
    * Response: {@link #UserMemory_Response}.
    */
   UserMemory_Read(0x2c0, 10, 3, 3, UserMemory_Response),

   /**
    * Write user-data memory.
    * <p>
    * 3+ data bytes: 4-bit address extension + 4-bit number of bytes, 2-byte
    * address, memory contents.
    */
   UserMemory_Write(0x2c2, 10, 3, 15),

   /**
    * Write a bit in the user-data memory.
    *
    * @deprecated according to the KNX standard.
    */
   @Deprecated
   UserMemoryBit_Write(0x2c4, 10, -1, -1),

   /**
    * Response to the {@link #UserManufacturerInfo_Read} request.
    * <p>
    * 3 data bytes: manufacturer id, 2 bytes manufacturer specific.
    */
   UserManufacturerInfo_Response(0x2c6, 10, 3, 3),

   /**
    * Read the manufacturer info.
    * <p>
    * Response: {@link #UserManufacturerInfo_Response}.
    */
   UserManufacturerInfo_Read(0x2c5, 10, 0, 0, UserManufacturerInfo_Response),

   /**
    * Call a function property command of an interface object.
    * <p>
    * 3+ data bytes: object index, property id, data.
    */
   FunctionPropertyCommand(0x2c7, 10, 3, 16),

   /**
    * Response to the {@link #FunctionPropertyState_Read} request.
    * <p>
    * 4+ data bytes: object index, property id, return code, data.
    */
   FunctionPropertyState_Response(0x2c9, 10, 4, 16),

   /**
    * Read the state of a function property of an interface object.
    * <p>
    * 3+ data bytes: object index, property id, data.
    * <p>
    * Response: {@link #FunctionPropertyState_Response}.
    */
   FunctionPropertyState_Read(0x2c8, 10, 3, 16, FunctionPropertyState_Response),

   /**
    * Response to {@link #DeviceDescriptor_Read}. Also called
    * DeviceDescriptor_InfoReport.
    */
   DeviceDescriptor_Response(0x340, 4, 1, 15, null, DeviceDescriptorResponse.class),

   /**
    * Read the device descriptor of a remote management server.
    * <p>
    * One data byte: the descriptor type to read.
    * <p>
    * Response: {@link #DeviceDescriptor_Response}.
    */
   DeviceDescriptor_Read(0x300, 4, 1, 1, DeviceDescriptor_Response, DeviceDescriptorRead.class),

   /**
    * Reset / restart the device.
    */
   Restart(0x380, 10, 0, 0, null, Restart.class),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Open_Routing_Table_Request(0x3c0, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Read_Routing_Table_Response(0x3c2, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Read_Routing_Table_Request(0x3c1, 10, -1, -1, Read_Routing_Table_Response),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Write_Routing_Table_Request(0x3c3, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Read_Router_Memory_Response(0x3c9, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Read_Router_Memory_Request(0x3c8, 10, -1, -1, Read_Router_Memory_Response),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Write_Router_Memory_Request(0x3ca, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Read_Router_Status_Response(0x3ce, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Read_Router_Status_Request(0x3cd, 10, -1, -1, Read_Router_Status_Response),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   Write_Router_Status_Request(0x3cf, 10, -1, -1),

   /** @deprecated according to the KNX standard. */
   @Deprecated
   MemoryBit_Write(0x3d0, 10, -1, -1),

   /**
    * Response to the {@link #Authorize_Request} request.#
    * <p>
    * 1 data byte: access level.
    */
   Authorize_Response(0x3d2, 10, 1, 1),

   /**
    * Send a key to the communication partner to gain access.
    * <p>
    * 5 data bytes: zero, 4-byte key.
    * <p>
    * Response: {@link #Authorize_Response}.
    */
   Authorize_Request(0x3d1, 10, 5, 5, Authorize_Response),

   /**
    * Response to {@link #Key_Write}.
    * <p>
    * 1 data byte: access level.
    */
   Key_Response(0x3d4, 10, -1, -1),

   /**
    * Modify or delete a key which is associated to a certain access level in
    * the communication partner.
    * <p>
    * 5 data bytes: access level, 4-byte key.
    */
   Key_Write(0x3d3, 10, -1, -1, Key_Response),

   /**
    * Response to the {@link #PropertyValue_Read} request.
    * <p>
    * 4+ data bytes: object index, property id, 4bit number of requested elems,
    * 12bit start index, the data.
    */
   PropertyValue_Response(0x3d6, 10, 4, 16),

   /**
    * Read the value of a property of an interface object.
    * <p>
    * 4 data bytes: object index, property id, 4bit number of requested elems,
    * 12bit start index
    */
   PropertyValue_Read(0x3d5, 10, 4, 4, PropertyValue_Response),

   /**
    * Set the value of a property of an interface object.
    * <p>
    * 4+ data bytes: object index, property id, 4bit number of requested elems,
    * 12bit start index, the data.
    */
   PropertyValue_Write(0x3d7, 10, 4, 16),

   /**
    * Response to the {@link #PropertyDescription_Read} request.
    * <p>
    * Data bytes: object index, property id, property index, property type,
    * 4bits reserved, 12 bits max number of elems, 4bits read-level access,
    * 4bits write-level access.
    */
   PropertyDescription_Response(0x3d9, 10, 7, 7),

   /**
    * Read the description of the property of an interface object.
    * <p>
    * 3 data bytes: object index, property id, property index.
    */
   PropertyDescription_Read(0x3d8, 10, 3, 3, PropertyDescription_Response),

   /**
    * The response to the {@link #NetworkParameter_Read} request.
    */
   NetworkParameter_Response(0x3da, 10, 4, 16),

   /**
    * Read the configuration of a network parameter.
    * <p>
    * Broadcast.
    */
   NetworkParameter_Read(0x3da, 10, 4, 16, NetworkParameter_Response),

   /**
    * Set a network configuration parameter on one or multiple management
    * servers. Either point-to-point connection-less or broadcast.
    */
   NetworkParameter_Write(0x3e4, 10, 4, 16),

   /**
    * Reserved for coupler-specific use.
    */
   Coupler_Specific_Reserved(0x3df, 10, -1, -1),

   /**
    * Set the domain address of a communication partner. 2+ data bytes: the
    * domain address (high to low byte).
    */
   DomainAddress_Write(0x3e0, 10, 2, 16),

   /**
    * Response to the {@link #DomainAddress_Read} request. 2+ data bytes: the
    * domain address (high to low byte).
    */
   DomainAddress_Response(0x3e2, 10, 2, 16),

   /**
    * Read the domain address of a communication partner.
    */
   DomainAddress_Read(0x3e1, 10, 0, 0, DomainAddress_Response),

   /**
    * A {@link #DomainAddress_Read} request, but more specific. Read the domain
    * address of a communication partner that is identified within a service.
    * <p>
    * Broadcast.
    */
   DomainAddressSelective_Read(0x3e3, 10, 2, 16, DomainAddress_Response),

   /**
    * Response to {@link #DomainAddressSerialNumber_Read}.
    * <p>
    * 8+ data bytes: 6 bytes KNX serial number, rest is domain address (high to
    * low byte).
    */
   DomainAddressSerialNumber_Response(0x3ed, 10, -1, -1),

   /**
    * Read the domain address of a communication partner. The communication
    * partner is identified by its KNX serial number.
    * <p>
    * 6 data bytes: the KNX serial number.
    */
   DomainAddressSerialNumber_Read(0x3ec, 10, 6, 6, DomainAddressSerialNumber_Response),

   /**
    * Set the domain address of a communication partner which is identified by
    * its KNX serial number.
    * <p>
    * 8+ data bytes: 6 bytes KNX serial number, rest is domain address (high to
    * low byte).
    */
   DomainAddressSerialNumber_Write(0x3ee, 10, -1, -1),

   /**
    * Response to {@link #Link_Read}.
    * <p>
    * Data bytes: group object number, 4bit sending address, 4bit start address,
    * 0..14 bytes group address list.
    */
   Link_Response(0x3e6, 10, 3, 14),

   /**
    * Read the links to a specific group object in a communication partner.
    * <p>
    * 2 data bytes: group object number, 4bits zero, 4bits start index.
    */
   Link_Read(0x3e5, 10, -1, -1, Link_Response),

   /**
    * Add a single group address to a group object or remove a group address
    * from a group object.
    * <p>
    * 4 data bytes: group object number, flags, group address high/low.
    */
   Link_Write(0x3e7, 10, 4, 4),

   /**
    * Response to {@link #GroupPropValue_Read}.
    */
   GroupPropValue_Response(0x3e9, 10, -1, -1),

   /**
    * Read a group properties value.
    */
   GroupPropValue_Read(0x3e8, 10, -1, -1, GroupPropValue_Response),

   /**
    * Write a group properties value.
    */
   GroupPropValue_Write(0x3ea, 10, -1, -1),

   /**
    * Group properties information report.
    */
   GroupPropValue_InfoReport(0x3eb, 10, -1, -1),

   /**
    * FTS internal value, to indicate an invalid or empty application type.
    */
   None(0xffff, 10, 0, 0);

   private final int apci;
   private final int minData;
   private final int maxData;
   private final int bits;
   private final ApplicationType responseType;
   private final Class<? extends Application> applicationClass;

   // The bit-masks for the values of the APCI-bits
   private static final int[] ACPI_MASK = new int[] { 0, 0x200, 0x300, 0x380, 0x3c0, 0x3e0, 0x3f0, 0x3f8, 0x3fc, 0x3fe, 0x3ff };

   /**
    * Get the contents of the APCI field. This is the type identifier of an
    * application.
    *
    * @return The contents of the APCI field.
    *
    * @see #getBits()
    * @see #getMask()
    */
   public int getApci()
   {
      return apci;
   }

   /**
    * @return The minimum length for application specific data in the telegram
    *         in bytes. -1 if unspecified.
    */
   public int getMinDataBytes()
   {
      return minData;
   }

   /**
    * @return The maximum length for application specific data in the telegram
    *         in bytes. -1 if unspecified.
    */
   public int getMaxDataBytes()
   {
      return maxData;
   }

   /**
    * @return The number of bits that the {@link #getApci() APCI type id}
    *         requires (1..10).
    */
   public int getBits()
   {
      return bits;
   }

   /**
    * @return The bit-mask for the APCI field.
    */
   public int getMask()
   {
      return ACPI_MASK[bits];
   }

   /**
    * @return The bit-mask for the data area in the APCI bytes. Zero indicates
    *         that no data can be stored in the APCI byte.
    */
   public int getDataMask()
   {
      return 0x3ff & ~ACPI_MASK[bits];
   }

   /**
    * @return the application type that is the response to this application
    *         type, or null if there is no response to be expected.
    */
   public ApplicationType getResponseType()
   {
      return responseType;
   }

   /**
    * Returns the class of the {@link Application application}. If no specific
    * class exists for an application type, null is returned.
    *
    * @return The class of the application.
    */
   public Class<? extends Application> getApplicationClass()
   {
      return applicationClass;
   }

   /**
    * @return the application type in human readable form.
    */
   @Override
   public String toString()
   {
      return name();
   }

   /**
    * Get the application type for the given APCI value.
    * 
    * @param apci - the ACPI value.
    * 
    * @return the application type for the given APCI field.
    * @throws InvalidDataException if the APCI value is unknown.
    */
   public static ApplicationType valueOf(int apci) throws InvalidDataException
   {
      for (ApplicationType a : values())
      {
         if ((apci & ACPI_MASK[a.bits]) == a.apci)
            return a;
      }

      throw new InvalidDataException("No telegram application type found that matches the APCI field contents", apci);
   }

   /*
    * Internal constructor.
    */
   private ApplicationType(int apci, int apciBits, int minData, int maxData)
   {
      this(apci, apciBits, minData, maxData, null, null);
   }

   /*
    * Internal constructor.
    */
   private ApplicationType(int apci, int apciBits, int minData, int maxData, ApplicationType responseType)
   {
      this(apci, apciBits, minData, maxData, responseType, null);
   }

   /*
    * Internal constructor.
    */
   private ApplicationType(int apci, int apciBits, int minData, int maxData, ApplicationType responseType,
         Class<? extends Application> clazz)
   {
      this.apci = apci;
      this.bits = apciBits;
      this.minData = minData;
      this.maxData = maxData;
      this.responseType = responseType;
      this.applicationClass = clazz;
   }
}
