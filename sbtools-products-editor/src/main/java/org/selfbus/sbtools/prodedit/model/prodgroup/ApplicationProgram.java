package org.selfbus.sbtools.prodedit.model.prodgroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.interfaces.Identifiable;
import org.selfbus.sbtools.prodedit.utils.IdentifiableUtils;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.collect.ArrayListModel;

/**
 * An application program.
 * 
 * Device type and program version identify the application program.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class ApplicationProgram extends Model implements Identifiable
{
   private static final long serialVersionUID = 2365607976221764138L;

   @XmlAttribute(name = "program_id", required = true)
   private int id;

   @XmlAttribute(name = "symbol_id")
   private int symbolId;

   @XmlAttribute(name = "mask_id", required = true)
   private int maskId;

   @XmlAttribute(name = "program_name", required = true)
   private String name = "";

   @XmlAttribute(name = "program_version")
   private String version;

   @XmlAttribute(name = "program_version_number")
   private Integer versionNumber;

   @XmlAttribute(name = "linkable")
   private boolean linkable;

   @XmlAttribute(name = "device_type")
   private int deviceType;

   @XmlAttribute(name = "pei_type")
   private int peiType;

   @XmlAttribute(name = "address_tab_size")
   private int addrTabSize;

   @XmlAttribute(name = "assoctab_address")
   private int assocTabAddr;

   @XmlAttribute(name = "assoctab_size")
   private int assocTabSize;

   @XmlAttribute(name = "commstab_address")
   private int commsTabAddr;

   @XmlAttribute(name = "commstab_size")
   private int commsTabSize;

   @XmlAttribute(name = "program_serial_number")
   private String serial;

   @XmlAttribute(name = "manufacturer_id", required = true)
   private int manufacturerId;

   @XmlAttribute(name = "eeprom_data")
   private byte[] eepromData;

   @XmlAttribute(name = "data_length")
   private int eepromDataLength;

   @XmlAttribute(name = "s91_file")
   private int s19FileName;

   @XmlAttribute(name = "map_file")
   private int mapFileName;

   @XmlAttribute(name = "assembler_id")
   private int assemblerId;

   @XmlAttribute(name = "help_file_name")
   private int helpFileName;

   @XmlAttribute(name = "contextId")
   private int contextId;

   @XmlAttribute(name = "dynamic_management")
   private boolean dynamicManagement;

   @XmlAttribute(name = "program_type")
   private int programType;

   @XmlAttribute(name = "ram_size")
   private int ramSize;

   @XmlAttribute(name = "original_manufacturer_id")
   private int originalManufacturerId;

   @XmlAttribute(name = "api_version")
   private int apiVersion;

   @XmlAttribute(name = "program_style")
   private int programStyle;

   @XmlAttribute(name = "is_polling_master")
   private boolean pollingMaster;

   @XmlAttribute(name = "number_of_polling_groups")
   private int numPollingGroups;

   @XmlElementWrapper(name = "parameters")
   @XmlElement(name = "parameter")
   private ArrayListModel<Parameter> parameters = new ArrayListModel<Parameter>();

   @XmlElementWrapper(name = "parameter_types")
   @XmlElement(name = "parameter_type")
   private ArrayListModel<ParameterType> parameterTypes = new ArrayListModel<ParameterType>();

   @XmlElementWrapper(name = "communication_objects")
   @XmlElement(name = "communication_object")
   private ArrayListModel<CommunicationObject> communicationObjects = new ArrayListModel<CommunicationObject>();

   @XmlElementWrapper(name = "s19_blocks")
   @XmlElement(name = "s19_block")
   private final ArrayListModel<S19Block> s19Blocks = new ArrayListModel<S19Block>();

   @XmlElement(name = "description")
   private MultiLingualText description;

   /**
    * Create an empty program object.
    */
   public ApplicationProgram()
   {
   }

   /**
    * Create a program object.
    * 
    * @param id - the database ID of the object.
    * @param name - the name of the object.
    * @param manufacturerId - the manufacturer.
    * @param maskId - the mask.
    */
   public ApplicationProgram(int id, String name, int manufacturerId, int maskId)
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
    * @return the program id as string
    */
   public String getIdStr()
   {
      return Integer.toString(id);
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
    * @return the mask.
    */
   public int getMaskId()
   {
      return maskId;
   }

   /**
    * Set the mask.
    * 
    * @param mask - the mask to set.
    */
   public void setMaskId(int maskId)
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
      firePropertyChange("name", this.name, name);
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
    * The device type. Is written to the KNX device also.
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
    * @return the manufacturer.
    */
   public int getManufacturerId()
   {
      return manufacturerId;
   }

   /**
    * Set the manufacturer.
    * 
    * @param manufacturerId - the manufacturer to set.
    */
   public void setManufacturerId(int manufacturerId)
   {
      this.manufacturerId = manufacturerId;
   }

   /**
    * Get a parameter by parameter-id.
    * 
    * @param id - the parameter id
    * 
    * @return the parameter, or null if not found.
    */
   public Parameter getParameter(int id)
   {
      if (parameters == null)
         return null;

      for (final Parameter param : parameters)
      {
         if (param.getId() == id)
            return param;
      }

      return null;
   }

   /**
    * Add a parameter to the program.
    * 
    * @param param - the parameter to add.
    */
   public void addParameter(Parameter param)
   {
      if (parameters == null)
         parameters = new ArrayListModel<Parameter>();

      parameters.add(param);
//      param.setProgram(this);
   }

   /**
    * Remove a parameter from the program.
    * 
    * @param param - the parameter to remove.
    */
   public void removeParameter(Parameter param)
   {
      if (parameters != null)
         parameters.remove(param);

//      param.setProgram(null);
   }

   /**
    * Remove all parameter from the program.
    */
   public void removeAllParameters()
   {
      if (parameters != null)
      {
//         for (final Parameter param : parameters)
//            param.setProgram(null);

         parameters.clear();
      }
   }

   /**
    * Set the parameters of the program.
    * 
    * @param parameters - the parameters to set.
    */
   public void setParameters(ArrayListModel<Parameter> parameters)
   {
      this.parameters = parameters;
   }

   /**
    * @return the parameters of the program.
    */
   public ArrayListModel<Parameter> getParameters()
   {
      return parameters;
   }

   /**
    * Set the communication objects of the program.
    * 
    * @param communicationObjects the set of communication objects.
    */
   public void setCommunicationObjects(ArrayListModel<CommunicationObject> communicationObjects)
   {
      this.communicationObjects = communicationObjects;
   }

   /**
    * @return the communication objects of the program.
    */
   public ArrayListModel<CommunicationObject> getCommunicationObjects()
   {
      return communicationObjects;
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
   public int getNumPollingGroups()
   {
      return numPollingGroups;
   }

   /**
    * @param numPollingGroups the numPollingGroups to set
    */
   public void setNumPollingGroups(int numPollingGroups)
   {
      this.numPollingGroups = numPollingGroups;
   }

   /**
    * @param parameterTypes the parameterTypes to set
    */
   public void setParameterTypes(ArrayListModel<ParameterType> parameterTypes)
   {
      this.parameterTypes = parameterTypes;
   }

   /**
    * @return the parameterTypes
    */
   public ArrayListModel<ParameterType> getParameterTypes()
   {
      return parameterTypes;
   }

   /**
    * Create a new parameter type.
    * 
    * @return The created parameter type.
    */
   public ParameterType createParameterType()
   {
      ParameterType paramType = new ParameterType(ParameterAtomicType.UNSIGNED);
      paramType.setSize(1);
      paramType.setMinValue(0);
      paramType.setMaxValue(1);
      paramType.setId(IdentifiableUtils.createUniqueId(parameterTypes));
      paramType.setName(I18n.formatMessage("ParameterType.newName", Integer.toString(paramType.getId())));

      parameterTypes.add(paramType);

      return paramType;
   }

   /**
    * Set the program description.
    * 
    * @param description - the program description to set.
    */
   public void setDescription(MultiLingualText description)
   {
      this.description = description;
   }

   /**
    * @return The program description.
    */
   public MultiLingualText getDescription()
   {
      return description;
   }

   /**
    * @return the S19 Blocks
    */
   public ArrayListModel<S19Block> getS19Blocks()
   {
      return s19Blocks;
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

      if (!(o instanceof ApplicationProgram))
         return false;

      final ApplicationProgram oo = (ApplicationProgram) o;

      return id == oo.id && peiType == oo.peiType && deviceType == oo.deviceType
            && (version == null ? oo.version == null : version.equals(oo.version))
            && maskId == oo.maskId;
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
