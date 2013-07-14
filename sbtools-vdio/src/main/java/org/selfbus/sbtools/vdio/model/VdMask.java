package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The mask describes the interface of a device, where the specific addresses
 * are located (like RAM, EEPROM, address table), and such.
 */
@XmlType(name = "Mask", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdMask
{
   @XmlAttribute(name = "mask_id", required = true)
   private int id;

   @XmlAttribute(name = "mask_version", required = true)
   private int version;

   @XmlAttribute(name = "user_ram_start", required = true)
   private int userRamStart;

   @XmlAttribute(name = "user_ram_end", required = true)
   private int userRamEnd;

   @XmlAttribute(name = "user_eeprom_start", required = true)
   private int userEepromStart;

   @XmlAttribute(name = "user_eeprom_end", required = true)
   private int userEepromEnd;

   @XmlAttribute(name = "run_error_address", required = true)
   private int runErrorAddress;

   @XmlAttribute(name = "address_tab_address", required = true)
   private int addressTabAddress;

   @XmlAttribute(name = "assoctabptr_address", required = true)
   private int assocTabPtrAddress;

   @XmlAttribute(name = "commstabptr_address", required = true)
   private int commsTabPtrAddress;

   @XmlAttribute(name = "manufacturer_data_address", required = true)
   private int manufacturerDataAddress;

   @XmlAttribute(name = "manufacturer_data_size", required = true)
   private int manufacturerDataSize;

   @XmlAttribute(name = "manufacturer_id_address", required = true)
   private int manufacturerIdAddress;

   @XmlAttribute(name = "routecnt_address", required = true)
   private int routeCountAddress;

   @XmlAttribute(name = "manufacturer_id_protected", required = true)
   private int manufacturerIdProtected;

   private byte[] maskEepromData;

   @XmlAttribute(name = "mask_version_name")
   private String maskVersionName;

   @XmlAttribute(name = "address_tab_lcs")
   private Integer addressTabLCS;

   @XmlAttribute(name = "assoc_tab_lcs")
   private Integer assocTabLCS;

   @XmlAttribute(name = "application_program_lcs")
   private Integer applicationProgramLCS;

   @XmlAttribute(name = "pei_program_lcs")
   private Integer peiProgramLCS;

   @XmlAttribute(name = "load_control_address")
   private Integer loadControlAddress;

   @XmlAttribute(name = "run_control_address")
   private Integer runControlAddress;

   @XmlAttribute(name = "external_memory_start")
   private Integer externalMemoryStart;

   @XmlAttribute(name = "external_memory_end")
   private Integer externalMemoryEnd;

   @XmlAttribute(name = "application_program_rcs")
   private Integer applicationProgramRCS;

   @XmlAttribute(name = "pei_program_rcs")
   private Integer peiProgramRCS;

   @XmlAttribute(name = "port_a_ddr")
   private Integer portADdr;

   @XmlAttribute(name = "port_address_protected", required = true)
   private int portAddressProtected;

   @XmlAttribute(name = "medium_type_number")
   private Integer mediumTypeNumber;

   @XmlAttribute(name = "medium_type_number2")
   private Integer mediumTypeNumber2;

   @XmlAttribute(name = "bcu_type_number")
   private Integer bcuTypeId;

   /**
    * @return the id
    */
   public int getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the version
    */
   public int getVersion()
   {
      return version;
   }

   /**
    * @param version the version to set
    */
   public void setVersion(int version)
   {
      this.version = version;
   }

   /**
    * @return the maskVersionName
    */
   public String getMaskVersionName()
   {
      return maskVersionName;
   }

   /**
    * @param maskVersionName the maskVersionName to set
    */
   public void setMaskVersionName(String maskVersionName)
   {
      this.maskVersionName = maskVersionName;
   }

   /**
    * @return the userRamStart
    */
   public int getUserRamStart()
   {
      return userRamStart;
   }

   /**
    * @param userRamStart the userRamStart to set
    */
   public void setUserRamStart(int userRamStart)
   {
      this.userRamStart = userRamStart;
   }

   /**
    * @return the userRamEnd
    */
   public int getUserRamEnd()
   {
      return userRamEnd;
   }

   /**
    * @param userRamEnd the userRamEnd to set
    */
   public void setUserRamEnd(int userRamEnd)
   {
      this.userRamEnd = userRamEnd;
   }

   /**
    * @return the userEepromStart
    */
   public int getUserEepromStart()
   {
      return userEepromStart;
   }

   /**
    * @param userEepromStart the userEepromStart to set
    */
   public void setUserEepromStart(int userEepromStart)
   {
      this.userEepromStart = userEepromStart;
   }

   /**
    * @return the userEepromEnd
    */
   public int getUserEepromEnd()
   {
      return userEepromEnd;
   }

   /**
    * @param userEepromEnd the userEepromEnd to set
    */
   public void setUserEepromEnd(int userEepromEnd)
   {
      this.userEepromEnd = userEepromEnd;
   }

   /**
    * @return the runErrorAddress
    */
   public int getRunErrorAddress()
   {
      return runErrorAddress;
   }

   /**
    * @param runErrorAddress the runErrorAddress to set
    */
   public void setRunErrorAddress(int runErrorAddress)
   {
      this.runErrorAddress = runErrorAddress;
   }

   /**
    * @return the addressTabAddress
    */
   public int getAddressTabAddress()
   {
      return addressTabAddress;
   }

   /**
    * @param addressTabAddress the addressTabAddress to set
    */
   public void setAddressTabAddress(int addressTabAddress)
   {
      this.addressTabAddress = addressTabAddress;
   }

   /**
    * @return the assocTabPtrAddress
    */
   public int getAssocTabPtrAddress()
   {
      return assocTabPtrAddress;
   }

   /**
    * @param assocTabPtrAddress the assocTabPtrAddress to set
    */
   public void setAssocTabPtrAddress(int assocTabPtrAddress)
   {
      this.assocTabPtrAddress = assocTabPtrAddress;
   }

   /**
    * @return the commsTabPtrAddress
    */
   public int getCommsTabPtrAddress()
   {
      return commsTabPtrAddress;
   }

   /**
    * @param commsTabPtrAddress the commsTabPtrAddress to set
    */
   public void setCommsTabPtrAddress(int commsTabPtrAddress)
   {
      this.commsTabPtrAddress = commsTabPtrAddress;
   }

   /**
    * @return the manufacturerDataAddress
    */
   public int getManufacturerDataAddress()
   {
      return manufacturerDataAddress;
   }

   /**
    * @param manufacturerDataAddress the manufacturerDataAddress to set
    */
   public void setManufacturerDataAddress(int manufacturerDataAddress)
   {
      this.manufacturerDataAddress = manufacturerDataAddress;
   }

   /**
    * @return the manufacturerDataSize
    */
   public int getManufacturerDataSize()
   {
      return manufacturerDataSize;
   }

   /**
    * @param manufacturerDataSize the manufacturerDataSize to set
    */
   public void setManufacturerDataSize(int manufacturerDataSize)
   {
      this.manufacturerDataSize = manufacturerDataSize;
   }

   /**
    * @return the manufacturerIdAddress
    */
   public int getManufacturerIdAddress()
   {
      return manufacturerIdAddress;
   }

   /**
    * @param manufacturerIdAddress the manufacturerIdAddress to set
    */
   public void setManufacturerIdAddress(int manufacturerIdAddress)
   {
      this.manufacturerIdAddress = manufacturerIdAddress;
   }

   /**
    * @return the routeCountAddress
    */
   public int getRouteCountAddress()
   {
      return routeCountAddress;
   }

   /**
    * @param routeCountAddress the routeCountAddress to set
    */
   public void setRouteCountAddress(int routeCountAddress)
   {
      this.routeCountAddress = routeCountAddress;
   }

   /**
    * @return the manufacturerIdProtected
    */
   public boolean isManufacturerIdProtected()
   {
      return manufacturerIdProtected == 1;
   }

   /**
    * @param manufacturerIdProtected the manufacturerIdProtected to set
    */
   public void setManufacturerIdProtected(boolean manufacturerIdProtected)
   {
      this.manufacturerIdProtected = manufacturerIdProtected ? 1 : 0;
   }

   /**
    * @return the maskEepromData
    */
   public byte[] getMaskEepromData()
   {
      return maskEepromData;
   }

   /**
    * @param maskEepromData the maskEepromData to set
    */
   public void setMaskEepromData(byte[] maskEepromData)
   {
      this.maskEepromData = maskEepromData;
   }

   @XmlAttribute(name = "mask_eeprom_data")
   String getMaskEepromDataStr()
   {
      return DatatypeConverter.printHexBinary(maskEepromData).toLowerCase();
   }

   void setMaskEepromDataStr(String str)
   {
      maskEepromData = DatatypeConverter.parseHexBinary(str);
   }

   /**
    * @return the addressTabLCS
    */
   public Integer getAddressTabLCS()
   {
      return addressTabLCS;
   }

   /**
    * @param addressTabLCS the addressTabLCS to set
    */
   public void setAddressTabLCS(Integer addressTabLCS)
   {
      this.addressTabLCS = addressTabLCS;
   }

   /**
    * @return the assocTabLCS
    */
   public Integer getAssocTabLCS()
   {
      return assocTabLCS;
   }

   /**
    * @param assocTabLCS the assocTabLCS to set
    */
   public void setAssocTabLCS(Integer assocTabLCS)
   {
      this.assocTabLCS = assocTabLCS;
   }

   /**
    * @return the applicationProgramLCS
    */
   public Integer getApplicationProgramLCS()
   {
      return applicationProgramLCS;
   }

   /**
    * @param applicationProgramLCS the applicationProgramLCS to set
    */
   public void setApplicationProgramLCS(Integer applicationProgramLCS)
   {
      this.applicationProgramLCS = applicationProgramLCS;
   }

   /**
    * @return the peiProgramLCS
    */
   public Integer getPeiProgramLCS()
   {
      return peiProgramLCS;
   }

   /**
    * @param peiProgramLCS the peiProgramLCS to set
    */
   public void setPeiProgramLCS(Integer peiProgramLCS)
   {
      this.peiProgramLCS = peiProgramLCS;
   }

   /**
    * @return the loadControlAddress
    */
   public Integer getLoadControlAddress()
   {
      return loadControlAddress;
   }

   /**
    * @param loadControlAddress the loadControlAddress to set
    */
   public void setLoadControlAddress(Integer loadControlAddress)
   {
      this.loadControlAddress = loadControlAddress;
   }

   /**
    * @return the runControlAddress
    */
   public Integer getRunControlAddress()
   {
      return runControlAddress;
   }

   /**
    * @param runControlAddress the runControlAddress to set
    */
   public void setRunControlAddress(Integer runControlAddress)
   {
      this.runControlAddress = runControlAddress;
   }

   /**
    * @return the externalMemoryStart
    */
   public Integer getExternalMemoryStart()
   {
      return externalMemoryStart;
   }

   /**
    * @param externalMemoryStart the externalMemoryStart to set
    */
   public void setExternalMemoryStart(Integer externalMemoryStart)
   {
      this.externalMemoryStart = externalMemoryStart;
   }

   /**
    * @return the externalMemoryEnd
    */
   public Integer getExternalMemoryEnd()
   {
      return externalMemoryEnd;
   }

   /**
    * @param externalMemoryEnd the externalMemoryEnd to set
    */
   public void setExternalMemoryEnd(Integer externalMemoryEnd)
   {
      this.externalMemoryEnd = externalMemoryEnd;
   }

   /**
    * @return the applicationProgramRCS
    */
   public Integer getApplicationProgramRCS()
   {
      return applicationProgramRCS;
   }

   /**
    * @param applicationProgramRCS the applicationProgramRCS to set
    */
   public void setApplicationProgramRCS(Integer applicationProgramRCS)
   {
      this.applicationProgramRCS = applicationProgramRCS;
   }

   /**
    * @return the peiProgramRCS
    */
   public Integer getPeiProgramRCS()
   {
      return peiProgramRCS;
   }

   /**
    * @param peiProgramRCS the peiProgramRCS to set
    */
   public void setPeiProgramRCS(Integer peiProgramRCS)
   {
      this.peiProgramRCS = peiProgramRCS;
   }

   /**
    * @return the port A DDR.
    */
   public Integer getPortADdr()
   {
      return portADdr;
   }

   /**
    * Set the port A DDR.
    *
    * @param portADdr the port A DDR to set
    */
   public void setPortADdr(Integer portADdr)
   {
      this.portADdr = portADdr;
   }

   /**
    * @return the portAddressProtected
    */
   public boolean isPortAddressProtected()
   {
      return portAddressProtected == 1;
   }

   /**
    * @param portAddressProtected the portAddressProtected to set
    */
   public void setPortAddressProtected(boolean portAddressProtected)
   {
      this.portAddressProtected = portAddressProtected ? 1 : 0;
   }

   /**
    * @return the mediumTypeNumber
    */
   public Integer getMediumTypeNumber()
   {
      return mediumTypeNumber;
   }

   /**
    * @param mediumTypeNumber the mediumTypeNumber to set
    */
   public void setMediumTypeNumber(Integer mediumTypeNumber)
   {
      this.mediumTypeNumber = mediumTypeNumber;
   }

   /**
    * @return the mediumTypeNumber2
    */
   public Integer getMediumTypeNumber2()
   {
      return mediumTypeNumber2;
   }

   /**
    * @param mediumTypeNumber2 the mediumTypeNumber2 to set
    */
   public void setMediumTypeNumber2(Integer mediumTypeNumber2)
   {
      this.mediumTypeNumber2 = mediumTypeNumber2;
   }

   /**
    * @return The BCU type ID
    */
   public int getBcuTypeId()
   {
      return bcuTypeId;
   }

   /**
    * Set the BCU type ID.
    *
    * @param bcuTypeId - the BCU type ID to set
    */
   public void setBcuType(int bcuTypeId)
   {
      this.bcuTypeId = bcuTypeId;
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
      if (!(o instanceof VdMask))
         return false;
      final VdMask oo = (VdMask) o;
      return id == oo.id;
   }
}
