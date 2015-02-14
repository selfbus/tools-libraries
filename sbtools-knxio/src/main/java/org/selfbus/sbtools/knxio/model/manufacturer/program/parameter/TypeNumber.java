package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Parameter data type: number
 */
public class TypeNumber extends AbstractType
{
   @XmlAttribute(name = "minInclusive")
   private Integer minInclusive;

   @XmlAttribute(name = "maxInclusive")
   private Integer maxInclusive;

   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @XmlAttribute(name = "Type")
   private String type;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
