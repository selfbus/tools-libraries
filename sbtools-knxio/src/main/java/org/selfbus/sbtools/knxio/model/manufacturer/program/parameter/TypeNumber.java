package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.common.BitSized;

/**
 * Parameter data type: number
 */
@XmlAccessorType(XmlAccessType.NONE)
public class TypeNumber extends AbstractType implements BitSized
{
   private static final long serialVersionUID = 2244175660827614890L;

   @XmlAttribute(name = "minInclusive")
   private Integer minimum;

   @XmlAttribute(name = "maxInclusive")
   private Integer maximum;

   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @XmlAttribute(name = "Type")
   private String type;

   public Integer getMinimum()
   {
      return minimum;
   }

   public void setMinimum(Integer minimum)
   {
      this.minimum = minimum;
   }

   public Integer getMaximum()
   {
      return maximum;
   }

   public void setMaximum(Integer maximum)
   {
      this.maximum = maximum;
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

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }

   @Override
   public ParameterAtomicType getAtomicType()
   {
      return ParameterAtomicType.NUMBER;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
