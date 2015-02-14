package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Parameter data type: text
 */
public class TypeText extends AbstractType
{
   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
