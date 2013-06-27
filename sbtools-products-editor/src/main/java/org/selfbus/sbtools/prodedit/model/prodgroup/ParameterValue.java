package org.selfbus.sbtools.prodedit.model.prodgroup;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAttribute;

import org.selfbus.sbtools.prodedit.model.interfaces.Identifiable;

import com.jgoodies.binding.beans.Model;

/**
 * Values of an enum parameter type.
 */
public class ParameterValue extends Model implements Identifiable
{
   private static final long serialVersionUID = -6983752023306872120L;

   @XmlAttribute(name = "parameter_value_id", required = true)
   private int id;

   @XmlAttribute(name = "displayed_value")
   private String displayedValue;

   @XmlAttribute(name = "display_order")
   private int displayOrder;

   @XmlAttribute(name = "real_value")
   private int intValue;

   @XmlAttribute(name = "binary_value")
   private byte[] binaryValue;

   @XmlAttribute(name = "double_value")
   private double doubleValue;

   /**
    * Create an empty parameter value.
    */
   public ParameterValue()
   {
   }

   /**
    * Create a parameter value.
    *
    * @param id - the ID
    * @param displayedValue - the displayed value string
    * @param value - the integer value
    */
   public ParameterValue(int id, String displayedValue, int value)
   {
      this.id = id;
      this.displayedValue = displayedValue;
      this.intValue = value;
   }

   /**
    * Create a parameter value.
    *
    * @param id - the ID
    * @param displayedValue - the displayed value string
    * @param value - the binary value
    */
   public ParameterValue(int id, String displayedValue, byte[] value)
   {
      this.id = id;
      this.displayedValue = displayedValue;
      this.binaryValue = Arrays.copyOf(value, value.length);
   }

   /**
    * Create a parameter value.
    *
    * @param id - the ID
    * @param displayedValue - the displayed value string
    * @param value - the double value
    */
   public ParameterValue(int id, String displayedValue, double value)
   {
      this.id = id;
      this.displayedValue = displayedValue;
      this.doubleValue = value;
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
    * @return the displayOrder
    */
   public int getDisplayOrder()
   {
      return displayOrder;
   }

   /**
    * @param displayOrder the displayOrder to set
    */
   public void setDisplayOrder(int displayOrder)
   {
      this.displayOrder = displayOrder;
   }

   /**
    * @return The integer value that will be loaded into the BCU's memory.
    */
   public int getIntValue()
   {
      return intValue;
   }

   /**
    * Set the integer value that will be loaded into the BCU's memory.
    *
    * @param intValue the intValue to set
    */
   public void setIntValue(int intValue)
   {
      this.intValue = intValue;
   }

   /**
    * @return The binary value that will be loaded into the BCU's memory. May be
    *         null.
    */
   public byte[] getBinaryValue()
   {
      return binaryValue;
   }

   /**
    * Set the binary value that will be loaded into the BCU's memory. May be
    * null.
    *
    * @param binaryValue - the binary value to set
    */
   public void setBinaryValue(byte[] binaryValue)
   {
      this.binaryValue = binaryValue;
   }

   /**
    * @return The double value that will be loaded into the BCU's memory.
    */
   public double getDoubleValue()
   {
      return doubleValue;
   }

   /**
    * Set the double value that will be loaded into the BCU's memory.
    *
    * @param doubleValue - the double value to set.
    */
   public void setDoubleValue(double doubleValue)
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
}
