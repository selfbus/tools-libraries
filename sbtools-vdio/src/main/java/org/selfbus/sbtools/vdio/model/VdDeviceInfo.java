package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A device information.
 */
@XmlType(name = "DeviceInfo", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdDeviceInfo
{
   @XmlAttribute(name = "device_info_id", required = true)
   private int id;

   @XmlAttribute(name = "mask_id", required = true)
   private int maskId;

   @XmlAttribute(name = "dev_device_info_id")
   private Integer parentId;

   @XmlAttribute(name = "device_info_name", required = true)
   private String name;

   @XmlAttribute(name = "function_number")
   private Integer functionNumber;

   @XmlAttribute(name = "device_info_type")
   private Integer type;

   @XmlAttribute(name = "bit_position")
   private Integer bitPosition;

   @XmlAttribute(name = "device_info_visible")
   private Integer visible;

   /**
    * Create a mask entry object.
    */
   public VdDeviceInfo()
   {
   }

   /**
    * Create a mask entry object.
    *
    * @param id - the id.
    * @param name - the name.
    */
   public VdDeviceInfo(int id, String name)
   {
      this.id = id;
      this.name = name;
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
    * @return the maskId
    */
   public int getMaskId()
   {
      return maskId;
   }

   /**
    * @param maskId the maskId to set
    */
   public void setMaskId(int maskId)
   {
      this.maskId = maskId;
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
    * @return the functionNumber
    */
   public Integer getFunctionNumber()
   {
      return functionNumber;
   }

   /**
    * @param functionNumber the functionNumber to set
    */
   public void setFunctionNumber(Integer functionNumber)
   {
      this.functionNumber = functionNumber;
   }

   /**
    * @return the type
    */
   public Integer getType()
   {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(Integer type)
   {
      this.type = type;
   }

   /**
    * @return the bitPosition
    */
   public Integer getBitPosition()
   {
      return bitPosition;
   }

   /**
    * @param bitPosition the bitPosition to set
    */
   public void setBitPosition(Integer bitPosition)
   {
      this.bitPosition = bitPosition;
   }

   /**
    * @return the visible
    */
   public Integer getVisible()
   {
      return visible;
   }

   /**
    * @param visible the visible to set
    */
   public void setVisible(Integer visible)
   {
      this.visible = visible;
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
      if (!(o instanceof VdDeviceInfo))
         return false;
      final VdDeviceInfo oo = (VdDeviceInfo) o;
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
