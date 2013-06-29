package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * An application program.
 * 
 * Device type and program version identify the application program.
 */
@XmlType(name = "ApplicationProgram", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdApplicationProgram
{
   @XmlAttribute(name = "program_id", required = true)
   private int id;

   @XmlAttribute(name = "symbol_id")
   private Integer symbolId;

   @XmlAttribute(name = "mask_id", required = true)
   private int maskId;

   @XmlAttribute(name = "program_name", required = true)
   private String name = "";

   @XmlAttribute(name = "program_version")
   private String version;

   @XmlAttribute(name = "program_version_number")
   private Integer versionNumber;

   @XmlAttribute(name = "linkable", required = true)
   private boolean linkable;

   @XmlAttribute(name = "device_type", required = true)
   private int deviceType;

   @XmlAttribute(name = "pei_type", required = true)
   private int peiType;

   @XmlAttribute(name = "address_tab_size", required = true)
   private int addrTabSize;

   @XmlAttribute(name = "assoctab_address", required = true)
   private int assocTabAddr;

   @XmlAttribute(name = "assoctab_size", required = true)
   private int assocTabSize;

   @XmlAttribute(name = "commstab_address", required = true)
   private int commsTabAddr;

   @XmlAttribute(name = "commstab_size", required = true)
   private int commsTabSize;

   @XmlAttribute(name = "program_serial_number")
   private String serial;

   @XmlAttribute(name = "manufacturer_id", required = true)
   private int manufacturerId;

   @XmlAttribute(name = "eeprom_data")
   private byte[] eepromData;

   @XmlAttribute(name = "dynamic_management", required = true)
   private boolean dynamicManagement;

   @XmlAttribute(name = "program_type", required = true)
   private int programType;

   @XmlAttribute(name = "ram_size")
   private Integer ramSize;

   @XmlAttribute(name = "program_style", required = true)
   private int programStyle;

   @XmlAttribute(name = "is_polling_master", required = true)
   private boolean pollingMaster;

   @XmlAttribute(name = "number_of_polling_groups")
   private Integer numPollingGroups;

   /**
    * Create an empty program object.
    */
   public VdApplicationProgram()
   {
   }

   /**
    * Create a program object.
    * 
    * @param id - the database ID of the object.
    * @param name - the name of the object.
    * @param manufacturerId - the manufacturer ID.
    * @param maskId - the mask ID.
    */
   public VdApplicationProgram(int id, String name, int manufacturerId, int maskId)
   {
      this.id = id;
      this.name = name;
      this.manufacturerId = manufacturerId;
      this.maskId = maskId;
   }

   /**
    * @return the program id
    */
   public int getId()
   {
      return id;
   }

   /**
    * Set the program id.
    * 
    * @param id - the id to set.
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the mask ID.
    */
   public int getMaskId()
   {
      return maskId;
   }

   /**
    * Set the mask ID.
    * 
    * @param maskId - the mask ID to set.
    */
   public void setMask(int maskId)
   {
      this.maskId = maskId;
   }

   /**
    * @return the name of the program.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name of the program.
    * 
    * @param name - the name to set.
    */
   public void setName(String name)
   {
      this.name = name == null ? "" : name;
   }

   /**
    * Get the version of the program. The name is a bit misleading,
    * as higher version numbers do not mean newer revisions of the
    * program. The version is used to distinguish different application
    * program types for the same physical device.
    * 
    * @return The version
    */
   public String getVersion()
   {
      return version;
   }

   /**
    * Set the version. See {@link #getVersion()} for details about
    * a version.
    * 
    * @param version - the version to set.
    */
   public void setVersion(String version)
   {
      this.version = version;
   }

   /**
    * @return the linkable flag.
    */
   public boolean isLinkable()
   {
      return linkable;
   }

   /**
    * @param linkable the linkable to set
    */
   public void setLinkable(boolean linkable)
   {
      this.linkable = linkable;
   }

   /**
    * The device type. This is written to the EIB device also.
    * 
    * @return the device type.
    */
   public int getDeviceType()
   {
      return deviceType;
   }

   /**
    * Set the device type.
    * 
    * @param deviceType - the device type to set.
    */
   public void setDeviceType(int deviceType)
   {
      this.deviceType = deviceType;
   }

   /**
    * @return the type of the physical external interface (PEI).
    */
   public int getPeiType()
   {
      return peiType;
   }

   /**
    * Set the type of the physical external interface (PEI).
    * 
    * @param peiType - the PEI type to set.
    */
   public void setPeiType(int peiType)
   {
      this.peiType = peiType;
   }

   /**
    * @return the address-tab size.
    */
   public int getAddrTabSize()
   {
      return addrTabSize;
   }

   /**
    * Set the address-tab size.
    * 
    * @param addrTabSize - the address-tab size to set.
    */
   public void setAddrTabSize(int addrTabSize)
   {
      this.addrTabSize = addrTabSize;
   }

   /**
    * @return the association-tab address.
    */
   public int getAssocTabAddr()
   {
      return assocTabAddr;
   }

   /**
    * Set the association-tab address.
    * 
    * @param assocTabAddr - the association-tab address to set.
    */
   public void setAssocTabAddr(int assocTabAddr)
   {
      this.assocTabAddr = assocTabAddr;
   }

   /**
    * @return the association-tab size.
    */
   public int getAssocTabSize()
   {
      return assocTabSize;
   }

   /**
    * Set the association-tab size.
    * 
    * @param assocTabSize - the association-tab size.
    */
   public void setAssocTabSize(int assocTabSize)
   {
      this.assocTabSize = assocTabSize;
   }

   /**
    * @return The address of the communications table.
    */
   public int getCommsTabAddr()
   {
      return commsTabAddr;
   }

   /**
    * Set the address of the communications table.
    * 
    * @param commsTabAddr - the address to set.
    */
   public void setCommsTabAddr(int commsTabAddr)
   {
      this.commsTabAddr = commsTabAddr;
   }

   /**
    * @return The size of the communications table.
    */
   public int getCommsTabSize()
   {
      return commsTabSize;
   }

   /**
    * Set the size of the communications table.
    * 
    * @param commsTabSize - the size to set
    */
   public void setCommsTabSize(int commsTabSize)
   {
      this.commsTabSize = commsTabSize;
   }

   /**
    * @return the serial
    */
   public String getSerial()
   {
      return serial;
   }

   /**
    * @param serial the serial to set
    */
   public void setSerial(String serial)
   {
      this.serial = serial;
   }

   /**
    * @return the manufacturer ID.
    */
   public int getManufacturerId()
   {
      return manufacturerId;
   }

   /**
    * Set the manufacturer ID.
    * 
    * @param manufacturerId - the manufacturer ID to set.
    */
   public void setManufacturer(int manufacturerId)
   {
      this.manufacturerId = manufacturerId;
   }

   /**
    * @return the eepromData
    */
   public byte[] getEepromData()
   {
      return eepromData;
   }

   /**
    * @param eepromData the eepromData to set
    */
   public void setEepromData(byte[] eepromData)
   {
      this.eepromData = eepromData;
   }

   /**
    * @return the dynamicManagement
    */
   public boolean isDynamicManagement()
   {
      return dynamicManagement;
   }

   /**
    * @param dynamicManagement the dynamicManagement to set
    */
   public void setDynamicManagement(boolean dynamicManagement)
   {
      this.dynamicManagement = dynamicManagement;
   }

   /**
    * Get the program type. The program type is a 16 bit number that is unique
    * for a specific manufacturer. It can be used to identify the program that
    * is running in a BCU.
    * 
    * @return The program type
    */
   public int getProgramType()
   {
      return programType;
   }

   /**
    * @param programType the programType to set
    */
   public void setProgramType(int programType)
   {
      this.programType = programType;
   }

   /**
    * @return the ramSize
    */
   public int getRamSize()
   {
      return ramSize;
   }

   /**
    * @param ramSize the ramSize to set
    */
   public void setRamSize(int ramSize)
   {
      this.ramSize = ramSize;
   }

   /**
    * @return the programStyle
    */
   public int getProgramStyle()
   {
      return programStyle;
   }

   /**
    * @param programStyle the programStyle to set
    */
   public void setProgramStyle(int programStyle)
   {
      this.programStyle = programStyle;
   }

   /**
    * @return the pollingMaster
    */
   public boolean isPollingMaster()
   {
      return pollingMaster;
   }

   /**
    * @param pollingMaster the pollingMaster to set
    */
   public void setPollingMaster(boolean pollingMaster)
   {
      this.pollingMaster = pollingMaster;
   }

   /**
    * @return the numPollingGroups
    */
   public Integer getNumPollingGroups()
   {
      return numPollingGroups;
   }

   /**
    * @param numPollingGroups the numPollingGroups to set
    */
   public void setNumPollingGroups(Integer numPollingGroups)
   {
      this.numPollingGroups = numPollingGroups;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof VdApplicationProgram))
         return false;

      final VdApplicationProgram oo = (VdApplicationProgram) o;
      return id == oo.id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return name;
   }
}
