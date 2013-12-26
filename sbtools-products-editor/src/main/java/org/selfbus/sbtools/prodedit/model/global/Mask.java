package org.selfbus.sbtools.prodedit.model.global;

import java.util.Properties;

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
public class Mask
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

   // @XmlAttribute(name = "mask_eeprom_data")
   private byte[] maskEepromData;

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

   @XmlAttribute(name = "medium_type_number")
   private Integer mediumTypeNumber;

   @XmlAttribute(name = "medium_type_number2")
   private Integer mediumTypeNumber2;

   @XmlAttribute(name = "bcu_type_number")
   private Integer bcuTypeId;

   /**
    * Create an empty mask object.
    */
   public Mask()
   {
   }
   
   /**
    * Create a mask object.
    *
    * @param version - the mask version.
    * @param props - the mask properties.
    */
   public Mask(int version, Properties props)
   {
      String val;

      this.id = 0; // TODO
      this.version = version;

      userRamStart = nullsafeToInteger(props.getProperty("user_ram_start"));
      userRamEnd = nullsafeToInteger(props.getProperty("user_ram_end"));
      userEepromStart = nullsafeToInteger(props.getProperty("user_eeprom_start"));
      userEepromEnd = nullsafeToInteger(props.getProperty("user_eeprom_end"));
      runErrorAddress = nullsafeToInteger(props.getProperty("run_error_address"));
      addressTabAddress = nullsafeToInteger(props.getProperty("address_tab_address"));
      assocTabPtrAddress = nullsafeToInteger(props.getProperty("assoctabptr_address"));
      commsTabPtrAddress = nullsafeToInteger(props.getProperty("commstabptr_address"));
      manufacturerDataAddress = nullsafeToInteger(props.getProperty("manufacturer_data_address"));
      manufacturerDataSize = nullsafeToInteger(props.getProperty("manufacturer_data_size"));
      manufacturerIdAddress = nullsafeToInteger(props.getProperty("manufacturer_id_address"));
      routeCountAddress = nullsafeToInteger(props.getProperty("routecnt_address"));
      addressTabLCS = nullsafeToInteger(props.getProperty("address_tab_lcs"));
      applicationProgramLCS = nullsafeToInteger(props.getProperty("application_program_lcs"));
      peiProgramLCS = nullsafeToInteger(props.getProperty("pei_program_lcs"));
      loadControlAddress = nullsafeToInteger(props.getProperty("load_control_address"));
      runControlAddress = nullsafeToInteger(props.getProperty("run_control_address"));
      externalMemoryStart = nullsafeToInteger(props.getProperty("external_memory_start"));
      externalMemoryEnd = nullsafeToInteger(props.getProperty("external_memory_end"));
      applicationProgramRCS = nullsafeToInteger(props.getProperty("application_program_rcs"));
      peiProgramRCS = nullsafeToInteger(props.getProperty("pei_program_rcs"));
      portADdr = nullsafeToInteger(props.getProperty("port_a_ddr"));
      mediumTypeNumber = nullsafeToInteger(props.getProperty("medium_type_number"));
      mediumTypeNumber2 = nullsafeToInteger(props.getProperty("medium_type_number2"));
      bcuTypeId = nullsafeToInteger(props.getProperty("bcu_type_number"));

      val = props.getProperty("mask_eeprom_data");
      maskEepromData = val == null || val.isEmpty() ? null : DatatypeConverter.parseHexBinary(val);
   }

   /**
    * Convert the string to an Integer, handle empty strings as null.
    *
    * @param str - the string to convert
    * @return The integer, or null if str is empty or null.
    */
   static private Integer nullsafeToInteger(String str)
   {
      if (str == null || str.isEmpty())
         return null;

      return Integer.parseInt(str);
   }
   
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
    * @return the maskEepromData
    */
   public byte[] getEepromData()
   {
      return maskEepromData;
   }

   /**
    * @param maskEepromData the maskEepromData to set
    */
   public void setEepromData(byte[] maskEepromData)
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
      if (!(o instanceof Mask))
         return false;
      final Mask oo = (Mask) o;
      return id == oo.id;
   }
}
