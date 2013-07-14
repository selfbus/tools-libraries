package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A device parameter.
 */
@XmlType(name = "DeviceParameter", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdDeviceParameter
{
   @XmlAttribute(name = "device_id", required = true)
   private int deviceId;

   @XmlAttribute(name = "device_parameter_id", required = true)
   private int id;

   @XmlAttribute(name = "parameter_id", required = true)
   private int parameterId;

   @XmlAttribute(name = "device_parameter_number")
   private Integer number;

   @XmlAttribute(name = "device_parameter_visible")
   private int visible;

   @XmlAttribute(name = "parameter_value_long")
   private Integer intValue;

   @XmlAttribute(name = "parameter_value_string")
   private String strValue;

   @XmlAttribute(name = "program_type", required = true)
   private int programType;

   @XmlAttribute(name = "parameter_value_double")
   private Double doubleValue;

   @XmlAttribute(name = "valueisvalid")
   private int valueIsValid = 1;

   /**
    * Create a product description.
    */
   public VdDeviceParameter()
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
    * @return the parameterId
    */
   public int getParameterId()
   {
      return parameterId;
   }

   /**
    * @param parameterId the parameterId to set
    */
   public void setParameterId(int parameterId)
   {
      this.parameterId = parameterId;
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
    * @return the intValue
    */
   public Integer getIntValue()
   {
      return intValue;
   }

   /**
    * @param intValue the intValue to set
    */
   public void setIntValue(Integer intValue)
   {
      this.intValue = intValue;
   }

   /**
    * @return the strValue
    */
   public String getStrValue()
   {
      return strValue;
   }

   /**
    * @param strValue the strValue to set
    */
   public void setStrValue(String strValue)
   {
      this.strValue = strValue;
   }

   /**
    * @return the programType
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
    * @return the doubleValue
    */
   public Double getDoubleValue()
   {
      return doubleValue;
   }

   /**
    * @param doubleValue the doubleValue to set
    */
   public void setDoubleValue(Double doubleValue)
   {
      this.doubleValue = doubleValue;
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
      if (!(o instanceof VdDeviceParameter))
         return false;
      final VdDeviceParameter oo = (VdDeviceParameter) o;
      return id == oo.id;
   }
}
