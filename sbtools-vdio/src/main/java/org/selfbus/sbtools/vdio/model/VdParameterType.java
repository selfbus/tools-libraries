package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The type of a program's parameter. The parameter type is used to group
 * parameters of the same type that can have the same range of values.
 *
 * The parameter type holds e.g. the possible values for a parameter and
 * minimum/maximum values for numbers.
 *
 * What one would expect here, which type a parameter is, contains the class
 * {@link VdParameterAtomicType}, which can be access with
 * {@link #getAtomicType()}.
 */
@XmlType(name = "ParameterType", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdParameterType
{
   @XmlAttribute(name = "parameter_type_id", required = true)
   private int id;

   @XmlAttribute(name = "atomic_type_number", required = true)
   private int atomicTypeId;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "parameter_type_name", required = true)
   private String name;

   @XmlAttribute(name = "parameter_minimum_value")
   private Integer minValue;

   @XmlAttribute(name = "parameter_maximum_value")
   private Integer maxValue;

   @XmlAttribute(name = "parameter_type_description")
   private String description;

   @XmlAttribute(name = "parameter_type_low_access")
   private Integer lowAccess;

   @XmlAttribute(name = "parameter_type_high_access")
   private Integer highAccess;

   @XmlAttribute(name = "parameter_type_size")
   private Integer size;

   @XmlAttribute(name = "parameter_minimum_double_value")
   private Double minDoubleValue;

   @XmlAttribute(name = "parameter_maximum_double_value")
   private Double maxDoubleValue;

   /**
    * Create an empty parameter type.
    */
   public VdParameterType()
   {
   }

   /**
    * Create a parameter type.
    *
    * @param atomicTypeId - the parameter's atomic type.
    */
   public VdParameterType(int atomicTypeId)
   {
      this.atomicTypeId = atomicTypeId;
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
    * @return the atomicTypeId
    */
   public int getAtomicTypeId()
   {
      return atomicTypeId;
   }

   /**
    * @param atomicTypeId the atomicTypeId to set
    */
   public void setAtomicTypeId(int atomicTypeId)
   {
      this.atomicTypeId = atomicTypeId;
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
    * @return the minValue
    */
   public Integer getMinValue()
   {
      return minValue;
   }

   /**
    * @param minValue the minValue to set
    */
   public void setMinValue(Integer minValue)
   {
      this.minValue = minValue;
   }

   /**
    * @return the maxValue
    */
   public Integer getMaxValue()
   {
      return maxValue;
   }

   /**
    * @param maxValue the maxValue to set
    */
   public void setMaxValue(Integer maxValue)
   {
      this.maxValue = maxValue;
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
    * @return the lowAccess
    */
   public Integer getLowAccess()
   {
      return lowAccess;
   }

   /**
    * @param lowAccess the lowAccess to set
    */
   public void setLowAccess(Integer lowAccess)
   {
      this.lowAccess = lowAccess;
   }

   /**
    * @return the highAccess
    */
   public Integer getHighAccess()
   {
      return highAccess;
   }

   /**
    * @param highAccess the highAccess to set
    */
   public void setHighAccess(Integer highAccess)
   {
      this.highAccess = highAccess;
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
    * @return the minDoubleValue
    */
   public Double getMinDoubleValue()
   {
      return minDoubleValue;
   }

   /**
    * @param minDoubleValue the minDoubleValue to set
    */
   public void setMinDoubleValue(Double minDoubleValue)
   {
      this.minDoubleValue = minDoubleValue;
   }

   /**
    * @return the maxDoubleValue
    */
   public Double getMaxDoubleValue()
   {
      return maxDoubleValue;
   }

   /**
    * @param maxDoubleValue the maxDoubleValue to set
    */
   public void setMaxDoubleValue(Double maxDoubleValue)
   {
      this.maxDoubleValue = maxDoubleValue;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id;
   }
}
