package org.selfbus.sbtools.prodedit.model.prodgroup;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.common.MultiLingualText;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.interfaces.Identifiable;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterNode;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.Parameter;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterRootNode;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterTreeModel;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterType;
import org.selfbus.sbtools.prodedit.utils.IdentifiableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
   private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProgram.class);
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

   @XmlAttribute(name = "device_type")
   private int deviceType;

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

   // @XmlAttribute(name = "eeprom_data")
   private byte[] eepromData;

   @XmlAttribute(name = "data_length")
   private int eepromDataLength;

   @XmlAttribute(name = "contextId")
   private int contextId;

   @XmlAttribute(name = "program_type")
   private int typeId;

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

   @XmlElement(name = "parameters")
   private ParameterTreeModel parameterTree;

   @XmlElementWrapper(name = "parameter_types")
   @XmlElement(name = "parameter_type")
   private ArrayListModel<ParameterType> parameterTypes = new ArrayListModel<ParameterType>();

   @XmlElementWrapper(name = "s19_blocks")
   @XmlElement(name = "s19_block")
   private final ArrayListModel<S19Block> s19Blocks = new ArrayListModel<S19Block>();

   @XmlElement(name = "description")
   private MultiLingualText description = new MultiLingualText();

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
    * @param maskId - the mask.
    */
   public ApplicationProgram(int id, String name, int maskId)
   {
      this.id = id;
      this.name = name;
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
    * @return The parameter tree model.
    */
   public ParameterTreeModel getParameterTreeModel()
   {
      return parameterTree;
   }

   /**
    * @return The root of the parameter tree model.
    */
   public ParameterRootNode getParameterRoot()
   {
      if (parameterTree == null)
         parameterTree = new ParameterTreeModel();

      return parameterTree.getRoot();
   }

   /**
    * Get a parameter by parameter-id.
    * 
    * @param id - the parameter id
    * 
    * @return the parameter, or null if not found.
    */
   public AbstractParameterNode getParameter(int id)
   {
      if (parameterTree == null)
         return null;

      return parameterTree.findById(id);
   }

   /**
    * Add a top-level parameter to the program.
    * 
    * @param param - the parameter to add.
    */
   public void addParameter(AbstractParameterNode param)
   {
      if (parameterTree == null)
         parameterTree = new ParameterTreeModel();

      parameterTree.getRoot().addChild(param);
   }

   /**
    * Add a parameter type to the program.
    * 
    * @param paramType - the parameter type to add.
    */
   public void addParameterType(ParameterType paramType)
   {
      if (parameterTypes == null)
         parameterTypes = new ArrayListModel<ParameterType>();

      parameterTypes.add(paramType);
   }

   /**
    * Remove a top-level parameter from the program.
    * 
    * @param param - the parameter to remove.
    */
   public void removeParameter(Parameter param)
   {
      if (parameterTree != null)
         parameterTree.getRoot().removeChild(param);
   }

   /**
    * Remove all parameter from the program.
    */
   public void removeAllParameters()
   {
      if (parameterTree != null)
         parameterTree.getRoot().removeChilds();
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

   @XmlAttribute(name = "eeprom_data")
   String getEepromDataStr()
   {
      return DatatypeConverter.printHexBinary(eepromData).toLowerCase();
   }

   void setEepromDataStr(String str)
   {
      eepromData = DatatypeConverter.parseHexBinary(str);
   }

   /**
    * Get the program type ID. The program type ID is a 16 bit number that is unique
    * for a specific manufacturer. It can be used to identify the program that
    * is running in a BCU.
    * 
    * @return The program type ID
    */
   public int getTypeId()
   {
      return typeId;
   }

   /**
    * Set the program type ID. The program type ID is a 16 bit number that is unique
    * for a specific manufacturer. It can be used to identify the program that
    * is running in a BCU.
    *
    * @param id - the program type ID to set
    */
   public void setTypeId(int id)
   {
      this.typeId = id;
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
      if (parameterTypes == null)
         parameterTypes = new ArrayListModel<ParameterType>();

      return parameterTypes;
   }

   /**
    * Get a parameter type.
    *
    * @param id - the ID of the parameter type
    * @return The parameter type, or null if not found.
    */
   public ParameterType getParameterType(int id)
   {
      if (parameterTypes == null)
         return null;

      for (ParameterType paramType : parameterTypes)
      {
         if (paramType.getId() == id)
            return paramType;
      }

      LOGGER.info("Parameter type #{} not found", id);
      return null;
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
