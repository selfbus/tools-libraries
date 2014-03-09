package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A device object.
 */
@XmlType(name = "DeviceObject", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdDeviceObject
{
   @XmlAttribute(name = "device_id", required = true)
   private int deviceId;

   @XmlAttribute(name = "object_prio", required = true)
   private int objectPriority;

   @XmlAttribute(name = "object_read", required = true)
   private int objectRead;

   @XmlAttribute(name = "object_write", required = true)
   private int objectWrite;

   @XmlAttribute(name = "object_comm", required = true)
   private int objectComm;

   @XmlAttribute(name = "object_trans", required = true)
   private int objectTrans;

   @XmlAttribute(name = "object_subgroups_text")
   private String subgroupsText;

   @XmlAttribute(name = "device_object_id", required = true)
   private int id;

   @XmlAttribute(name = "object_id", required = true)
   private int objectId;

   @XmlAttribute(name = "device_object_number")
   private Integer number;

   @XmlAttribute(name = "object_subgroups_text2")
   private String subgroupsText2;

   @XmlAttribute(name = "device_object_visible", required = true)
   private int visible;

   @XmlAttribute(name = "device_object_unique_name")
   private String name;

   @XmlAttribute(name = "object_update", required = true)
   private int update;

   @XmlAttribute(name = "device_object_unique_number")
   private Integer uniqueNumber;

   @XmlAttribute(name = "device_object_type")
   private Integer objectTypeId;

   @XmlAttribute(name = "eib_data_type_code")
   private Integer eibDataTypeCode;

   @XmlAttribute(name = "eib_data_subtype_code")
   private Integer eibDataSubTypeCode;

   @XmlAttribute(name = "name")
   private String extraName;

   @XmlAttribute(name = "description")
   private String description;

   /**
    * Create a product description.
    */
   public VdDeviceObject()
   {
   }

   /**
    * @return the deviceId
    */
   public int getDeviceId()
   {
      return deviceId;
   }

   /**
    * @param deviceId the deviceId to set
    */
   public void setDeviceId(int deviceId)
   {
      this.deviceId = deviceId;
   }

   /**
    * @return the objectPriority
    */
   public int getObjectPriority()
   {
      return objectPriority;
   }

   /**
    * @param objectPriority the objectPriority to set
    */
   public void setObjectPriority(int objectPriority)
   {
      this.objectPriority = objectPriority;
   }

   /**
    * @return the objectRead
    */
   public boolean isReadEnabled()
   {
      return objectRead == 1;
   }

   /**
    * @param objectRead the objectRead to set
    */
   public void setReadEnabled(boolean objectRead)
   {
      this.objectRead = objectRead ? 1 : 0;
   }

   /**
    * @return the objectWrite
    */
   public boolean isWriteEnabled()
   {
      return objectWrite == 1;
   }

   /**
    * @param objectWrite the objectWrite to set
    */
   public void setWriteEnabled(boolean objectWrite)
   {
      this.objectWrite = objectWrite ? 1 : 0;
   }

   /**
    * @return the objectComm
    */
   public boolean isCommEnabled()
   {
      return objectComm == 1;
   }

   /**
    * @param objectComm the objectComm to set
    */
   public void setCommEnabled(boolean objectComm)
   {
      this.objectComm = objectComm ? 1 : 0;
   }

   /**
    * @return the objectTrans
    */
   public boolean isTransEnabled()
   {
      return objectTrans == 1;
   }

   /**
    * @param objectTrans the objectTrans to set
    */
   public void setTransEnabled(boolean objectTrans)
   {
      this.objectTrans = objectTrans ? 1 : 0;
   }

   /**
    * @return the update
    */
   public boolean isUpdateEnabled()
   {
      return update == 1;
   }

   /**
    * @param update the update to set
    */
   public void setUpdateEnabled(boolean update)
   {
      this.update = update ? 1 : 0;
   }

   /**
    * @return the subgroupsText
    */
   public String getSubgroupsText()
   {
      return subgroupsText;
   }

   /**
    * @param subgroupsText the subgroupsText to set
    */
   public void setSubgroupsText(String subgroupsText)
   {
      this.subgroupsText = subgroupsText;
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
    * @return the objectId
    */
   public int getObjectId()
   {
      return objectId;
   }

   /**
    * @param objectId the objectId to set
    */
   public void setObjectId(int objectId)
   {
      this.objectId = objectId;
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
    * @return the subgroupsText2
    */
   public String getSubgroupsText2()
   {
      return subgroupsText2;
   }

   /**
    * @param subgroupsText2 the subgroupsText2 to set
    */
   public void setSubgroupsText2(String subgroupsText2)
   {
      this.subgroupsText2 = subgroupsText2;
   }

   /**
    * @return the visible
    */
   public boolean isVisible()
   {
      return visible == 1;
   }

   /**
    * @param visible the visible to set
    */
   public void setVisible(boolean visible)
   {
      this.visible = visible ? 1 : 0;
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
    * @return the uniqueNumber
    */
   public Integer getUniqueNumber()
   {
      return uniqueNumber;
   }

   /**
    * @param uniqueNumber the uniqueNumber to set
    */
   public void setUniqueNumber(Integer uniqueNumber)
   {
      this.uniqueNumber = uniqueNumber;
   }

   /**
    * @return the objectTypeId
    */
   public int getObjectTypeId()
   {
      return objectTypeId;
   }

   /**
    * @param objectTypeId the objectTypeId to set
    */
   public void setObjectTypeId(int objectTypeId)
   {
      this.objectTypeId = objectTypeId;
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
      if (!(o instanceof VdDeviceObject))
         return false;
      final VdDeviceObject oo = (VdDeviceObject) o;
      return id == oo.id;
   }
}
