package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A parameter of a program.
 */
@XmlType(name = "Parameter", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdParameter
{
   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "parameter_type_id", required = true)
   private int paramTypeId;

   // The parameter number is unique for the program and starts with 1
   @XmlAttribute(name = "parameter_number")
   private Integer number;

   @XmlAttribute(name = "parameter_name", required = true)
   private String name;

   @XmlAttribute(name = "parameter_low_access", required = true)
   private int lowAccess;

   @XmlAttribute(name = "parameter_high_access", required = true)
   private int highAccess;

   @XmlAttribute(name = "parent_parm_value")
   private Integer parentValue;

   @XmlAttribute(name = "parameter_size")
   private Integer size;

   @XmlAttribute(name = "parameter_function")
   private String function;

   @XmlAttribute(name = "parameter_display_order")
   private int order;

   @XmlAttribute(name = "parameter_address")
   private Integer address;

   @XmlAttribute(name = "parameter_bitoffset")
   private Integer bitOffset;

   @XmlAttribute(name = "parameter_description")
   private String description;

   @XmlAttribute(name = "parameter_id", required = true)
   private int id;

   @XmlAttribute(name = "par_parameter_id")
   private Integer parentId;

   @XmlAttribute(name = "parameter_label")
   private String label;

   @XmlAttribute(name = "parameter_default_long")
   private Integer defaultInt;

   @XmlAttribute(name = "parameter_default_string")
   private String defaultString;

   @XmlAttribute(name = "context_id")
   private Integer contextId;

   @XmlAttribute(name = "parameter_default_double")
   private Double defaultDouble;

   @XmlAttribute(name = "patch_always")
   private int patchAlways;

   @XmlAttribute(name = "address_space")
   private Integer addressSpace;

   @XmlAttribute(name = "eib_object_ref")
   private Integer eibObjectRef;

   @XmlAttribute(name = "eib_property_id")
   private Integer eibPropertyId;

   @XmlAttribute(name = "calculationid")
   private Integer calculationId;

   @XmlAttribute(name = "calculationset")
   private String calculationSet;

   @XmlAttribute(name = "aliasname")
   private String aliasName;

   /**
    * Create an empty parameter object.
    */
   public VdParameter()
   {
   }

   /**
    * @return the programId
    */
   public int getProgramId()
   {
      return programId;
   }

   /**
    * @param programId the programId to set
    */
   public void setProgramId(int programId)
   {
      this.programId = programId;
   }

   /**
    * @return the paramType ID
    */
   public int getParamTypeId()
   {
      return paramTypeId;
   }

   /**
    * @param id - the paramType ID to set
    */
   public void setParamTypeId(int id)
   {
      this.paramTypeId = id;
   }

   /**
    * @return the number
    */
   public Integer getNumber()
   {
      return number;
   }

   /**
    * @param number the number to set
    */
   public void setNumber(Integer number)
   {
      this.number = number;
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the lowAccess
    */
   public int getLowAccess()
   {
      return lowAccess;
   }

   /**
    * @param lowAccess the lowAccess to set
    */
   public void setLowAccess(int lowAccess)
   {
      this.lowAccess = lowAccess;
   }

   /**
    * @return the highAccess
    */
   public int getHighAccess()
   {
      return highAccess;
   }

   /**
    * @param highAccess the highAccess to set
    */
   public void setHighAccess(int highAccess)
   {
      this.highAccess = highAccess;
   }

   /**
    * @return the parentValue
    */
   public Integer getParentValue()
   {
      return parentValue;
   }

   /**
    * @param parentValue the parentValue to set
    */
   public void setParentValue(Integer parentValue)
   {
      this.parentValue = parentValue;
   }

   /**
    * @return the size
    */
   public Integer getSize()
   {
      return size;
   }

   /**
    * @param size the size to set
    */
   public void setSize(Integer size)
   {
      this.size = size;
   }

   /**
    * @return the function
    */
   public String getFunction()
   {
      return function;
   }

   /**
    * @param function the function to set
    */
   public void setFunction(String function)
   {
      this.function = function;
   }

   /**
    * @return the order
    */
   public int getOrder()
   {
      return order;
   }

   /**
    * @param order the order to set
    */
   public void setOrder(int order)
   {
      this.order = order;
   }

   /**
    * @return the address
    */
   public Integer getAddress()
   {
      return address;
   }

   /**
    * @param address the address to set
    */
   public void setAddress(Integer address)
   {
      this.address = address;
   }

   /**
    * @return the bitOffset
    */
   public Integer getBitOffset()
   {
      return bitOffset;
   }

   /**
    * @param bitOffset the bitOffset to set
    */
   public void setBitOffset(Integer bitOffset)
   {
      this.bitOffset = bitOffset;
   }

   /**
    * @return the description
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description)
   {
      this.description = description;
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
    * @return the parentId
    */
   public Integer getParentId()
   {
      return parentId;
   }

   /**
    * @param parentId the parentId to set
    */
   public void setParentId(Integer parentId)
   {
      this.parentId = parentId;
   }

   /**
    * @return the label
    */
   public String getLabel()
   {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label)
   {
      this.label = label;
   }

   /**
    * @return the defaultInt
    */
   public Integer getDefaultInt()
   {
      return defaultInt;
   }

   /**
    * @param defaultInt the defaultInt to set
    */
   public void setDefaultInt(Integer defaultInt)
   {
      this.defaultInt = defaultInt;
   }

   /**
    * @return the defaultString
    */
   public String getDefaultString()
   {
      return defaultString;
   }

   /**
    * @param defaultString the defaultString to set
    */
   public void setDefaultString(String defaultString)
   {
      this.defaultString = defaultString;
   }

   /**
    * @return the contextId
    */
   public Integer getContextId()
   {
      return contextId;
   }

   /**
    * @param contextId the contextId to set
    */
   public void setContextId(Integer contextId)
   {
      this.contextId = contextId;
   }

   /**
    * @return the defaultDouble
    */
   public Double getDefaultDouble()
   {
      return defaultDouble;
   }

   /**
    * @param defaultDouble the defaultDouble to set
    */
   public void setDefaultDouble(Double defaultDouble)
   {
      this.defaultDouble = defaultDouble;
   }

   /**
    * @return the patchAlways
    */
   public boolean isPatchAlways()
   {
      return patchAlways == 1;
   }

   /**
    * @param patchAlways the patchAlways to set
    */
   public void setPatchAlways(boolean patchAlways)
   {
      this.patchAlways = patchAlways ? 1 : 0;
   }

   /**
    * @return the addressSpace
    */
   public Integer getAddressSpace()
   {
      return addressSpace;
   }

   /**
    * @param addressSpace the addressSpace to set
    */
   public void setAddressSpace(Integer addressSpace)
   {
      this.addressSpace = addressSpace;
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

      if (!(o instanceof VdParameter))
         return false;

      final VdParameter oo = (VdParameter) o;
      return id == oo.id;
   }
}
