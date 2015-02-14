package org.selfbus.sbtools.knxio.model.manufacturer.program.memory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An absolute memory/code segment.
 */
@XmlType
public class AbsoluteSegment extends AbstractSegment
{
   @XmlAttribute(name = "Address")
   private Integer address;

   @XmlAttribute(name = "Size")
   private Integer size;

   @XmlAttribute(name = "UserMemory")
   private Integer userMemory;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append(address)
         .append(size)
         .append(userMemory)
         .append("data", getData() == null ? null : "<bytes>")
         .append("mask", getMask() == null ? null : "<bytes>")
         .toString();
   }
}
