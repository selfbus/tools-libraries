package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Values of an enum parameter type.
 */
@XmlType(name = "ParameterValue", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdParameterValue
{
   @XmlAttribute(name = "parameter_type_id", required = true)
   private int parameterTypeId;

   @XmlAttribute(name = "real_value")
   private Integer intValue;

   @XmlAttribute(name = "displayed_value")
   private String label;

   @XmlAttribute(name = "display_order", required = true)
   private int order;

   @XmlAttribute(name = "parameter_value_id", required = true)
   private int id;

   @XmlAttribute(name = "binary_value")
   private byte[] binaryValue;

   @XmlAttribute(name = "binary_value_length", required = true)
   private int binaryValueLength;

   @XmlAttribute(name = "double_value")
   private Double doubleValue;

   /**
    * Create an empty parameter value.
    */
   public VdParameterValue()
   {
   }

   /**
    * @return the parameterTypeId
    */
   public int getParameterTypeId()
   {
      return parameterTypeId;
   }

   /**
    * @param parameterTypeId the parameterTypeId to set
    */
   public void setParameterTypeId(int parameterTypeId)
   {
      this.parameterTypeId = parameterTypeId;
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
    * @return the displayed label
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
    * @return the binaryValue
    */
   public byte[] getBinaryValue()
   {
      return binaryValue;
   }

   /**
    * @param binaryValue the binaryValue to set
    */
   public void setBinaryValue(byte[] binaryValue)
   {
      this.binaryValue = binaryValue;
      this.binaryValueLength = binaryValue == null ? 0 : binaryValue.length;
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
}
