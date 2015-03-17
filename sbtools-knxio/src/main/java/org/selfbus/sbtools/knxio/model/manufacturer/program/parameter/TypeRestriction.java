package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.BitSized;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * Parameter data type: enumeration
 */
@XmlAccessorType(XmlAccessType.NONE)
public class TypeRestriction extends AbstractType implements BitSized
{
   private static final long serialVersionUID = -3191597851952931537L;

   // Observed contents: Value
   @XmlAttribute(name = "Base")
   private String base;

   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @XmlElement(name = "Enumeration", namespace = Namespaces.KNX)
   private List<Enumeration> enumerations;

   public String getBase()
   {
      return base;
   }

   public void setBase(String base)
   {
      this.base = base;
   }

   @Override
   public Integer getSizeInBit()
   {
      return sizeInBit;
   }

   @Override
   public void setSizeInBit(Integer sizeInBit)
   {
      this.sizeInBit = sizeInBit;
   }

   public List<Enumeration> getEnumerations()
   {
      return enumerations;
   }

   public void setEnumerations(List<Enumeration> enumerations)
   {
      this.enumerations = enumerations;
   }

   @Override
   public ParameterAtomicType getAtomicType()
   {
      return ParameterAtomicType.RESTRICTION;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

   /**
    * An enumeration value of a {@link TypeRestriction restriction} parameter data type.
    */
   @XmlAccessorType(XmlAccessType.NONE)
   public static class Enumeration extends Identified
   {
      private static final long serialVersionUID = -2887866782935426376L;

      @XmlAttribute(name = "Text")
      private String text;

      @XmlAttribute(name = "DisplayOrder")
      private int displayOrder;

      @XmlAttribute(name = "Value")
      private Integer value;

      public String getText()
      {
         return text;
      }

      public void setText(String text)
      {
         this.text = text;
      }

      public int getDisplayOrder()
      {
         return displayOrder;
      }

      public void setDisplayOrder(int displayOrder)
      {
         this.displayOrder = displayOrder;
      }

      public Integer getValue()
      {
         return value;
      }

      public void setValue(Integer value)
      {
         this.value = value;
      }

      public static long getSerialversionuid()
      {
         return serialVersionUID;
      }

      @Override
      public String toString()
      {
         return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
      }
   }
}
