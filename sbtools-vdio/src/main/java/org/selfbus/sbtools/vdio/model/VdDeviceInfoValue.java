package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A device information value.
 */
@XmlType(name = "DeviceInfoValue", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdDeviceInfoValue
{
   @XmlAttribute(name = "device_info_value_id", required = true)
   private int id;

   @XmlAttribute(name = "device_info_id", required = true)
   private int deviceInfoId;

   @XmlAttribute(name = "bitmap_id")
   private Integer bitmapId;

   @XmlAttribute(name = "minimum_value")
   private Integer minValue;

   @XmlAttribute(name = "maximum_value")
   private Integer maxValue;

   @XmlAttribute(name = "displayed_value")
   private String displayedValue;

   /**
    * Create a device info object.
    */
   public VdDeviceInfoValue()
   {
   }

   /**
    * Create a device info object.
    *
    * @param id - the value ID.
    * @param deviceInfoId - the device info ID.
    */
   public VdDeviceInfoValue(int id, int deviceInfoId)
   {
      this.id = id;
      this.deviceInfoId = deviceInfoId;
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
    * @return the deviceInfoId
    */
   public int getDeviceInfoId()
   {
      return deviceInfoId;
   }

   /**
    * @param deviceInfoId the deviceInfoId to set
    */
   public void setDeviceInfoId(int deviceInfoId)
   {
      this.deviceInfoId = deviceInfoId;
   }

   /**
    * @return the bitmapId
    */
   public Integer getBitmapId()
   {
      return bitmapId;
   }

   /**
    * @param bitmapId the bitmapId to set
    */
   public void setBitmapId(Integer bitmapId)
   {
      this.bitmapId = bitmapId;
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
    * @return the displayedValue
    */
   public String getDisplayedValue()
   {
      return displayedValue;
   }

   /**
    * @param displayedValue the displayedValue to set
    */
   public void setDisplayedValue(String displayedValue)
   {
      this.displayedValue = displayedValue;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof VdDeviceInfoValue))
         return false;
      final VdDeviceInfoValue oo = (VdDeviceInfoValue) o;
      return id == oo.id;
   }
}
