package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A communication object of a program. Communication objects are the interface
 * to other devices on the bus. Group data telegrams send and receive the data
 * of the communication objects.
 */
@XmlType(name = "CommunicationObject", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdCommunicationObject
{
   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "object_name", required = true)
   private String name;

   @XmlAttribute(name = "object_function", required = true)
   private String function;

   @XmlAttribute(name = "object_readenabled", required = true)
   private int readEnabled;

   @XmlAttribute(name = "object_writeenabled", required = true)
   private int writeEnabled;

   @XmlAttribute(name = "object_commenabled", required = true)
   private int commEnabled;

   @XmlAttribute(name = "object_transenabled", required = true)
   private int transEnabled;

   @XmlAttribute(name = "object_display_order", required = true)
   private int order;

   @XmlAttribute(name = "parent_parameter_value")
   private Integer parentParameterValue;

   @XmlAttribute(name = "object_id", required = true)
   private int id;

   @XmlAttribute(name = "parameter_id")
   private Integer parentParameterId;

   @XmlAttribute(name = "object_number", required = true)
   private int number;

   @XmlAttribute(name = "object_description")
   private String description;

   @XmlAttribute(name = "object_type", required = true)
   private int typeId;

   @XmlAttribute(name = "object_priority", required = true)
   private int priorityId;

   @XmlAttribute(name = "object_updateenabled", required = true)
   private int updateEnabled;

   @XmlAttribute(name = "object_unique_number", required = true)
   private int uniqueNumber;

   /**
    * Create an empty communication object.
    */
   public VdCommunicationObject()
   {
   }

   /**
    * Create a communication object.
    * 
    * @param id - the id of the object
    */
   public VdCommunicationObject(int id)
   {
      this.id = id;
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
    * @return the readEnabled
    */
   public boolean isReadEnabled()
   {
      return readEnabled == 1;
   }

   /**
    * @param readEnabled the readEnabled to set
    */
   public void setReadEnabled(boolean readEnabled)
   {
      this.readEnabled = readEnabled ? 1 : 0;
   }

   /**
    * @return the writeEnabled
    */
   public boolean isWriteEnabled()
   {
      return writeEnabled == 1;
   }

   /**
    * @param writeEnabled the writeEnabled to set
    */
   public void setWriteEnabled(boolean writeEnabled)
   {
      this.writeEnabled = writeEnabled ? 1 : 0;
   }

   /**
    * @return the commEnabled
    */
   public boolean isCommEnabled()
   {
      return commEnabled == 1;
   }

   /**
    * @param commEnabled the commEnabled to set
    */
   public void setCommEnabled(boolean commEnabled)
   {
      this.commEnabled = commEnabled ? 1 : 0;
   }

   /**
    * @return the transEnabled
    */
   public boolean isTransEnabled()
   {
      return transEnabled == 1;
   }

   /**
    * @param transEnabled the transEnabled to set
    */
   public void setTransEnabled(boolean transEnabled)
   {
      this.transEnabled = transEnabled ? 1 : 0;
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
    * @return the parentParameterValue
    */
   public Integer getParentParameterValue()
   {
      return parentParameterValue;
   }

   /**
    * @param parentParameterValue the parentParameterValue to set
    */
   public void setParentParameterValue(Integer parentParameterValue)
   {
      this.parentParameterValue = parentParameterValue;
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
    * @return the parentParameterId
    */
   public Integer getParentParameterId()
   {
      return parentParameterId;
   }

   /**
    * @param parentParameterId the parentParameterId to set
    */
   public void setParentParameterId(Integer parentParameterId)
   {
      this.parentParameterId = parentParameterId;
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
      this.number = number == null ? 0 : number;
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
    * @return the objectTypeId
    */
   public int getTypeId()
   {
      return typeId;
   }

   /**
    * @param typeId the object type ID to set
    */
   public void setTypeId(int typeId)
   {
      this.typeId = typeId;
   }

   /**
    * @return the objectPriorityId
    */
   public int getPriorityId()
   {
      return priorityId;
   }

   /**
    * @param priorityId the objectPriorityId to set
    */
   public void setPriorityId(int priorityId)
   {
      this.priorityId = priorityId;
   }

   /**
    * @return the updateEnabled
    */
   public boolean isUpdateEnabled()
   {
      return updateEnabled == 1;
   }

   /**
    * @param updateEnabled the updateEnabled to set
    */
   public void setUpdateEnabled(boolean updateEnabled)
   {
      this.updateEnabled = updateEnabled ? 1 : 0;
   }

   /**
    * @return the uniqueNumber
    */
   public int getUniqueNumber()
   {
      return uniqueNumber;
   }

   /**
    * @param uniqueNumber the uniqueNumber to set
    */
   public void setUniqueNumber(int uniqueNumber)
   {
      this.uniqueNumber = uniqueNumber;
   }
}
