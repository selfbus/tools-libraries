package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.common.BitSized;

/**
 * Parameter data type: text
 */
@XmlAccessorType(XmlAccessType.NONE)
public class TypeText extends AbstractType implements BitSized
{
   private static final long serialVersionUID = -3584928921597448643L;

   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

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

   @Override
   public ParameterAtomicType getAtomicType()
   {
      return ParameterAtomicType.TEXT;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
