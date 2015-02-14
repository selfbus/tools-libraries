package org.selfbus.sbtools.knxio.model.manufacturer.program.memory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A location in a memory code segment.
 */
@XmlType
public class MemoryLocation
{
   @XmlIDREF
   @XmlAttribute(name = "CodeSegment")
   private AbstractSegment codeSegment;

   @XmlAttribute(name = "Offset")
   private Integer offset;

   @XmlAttribute(name = "BitOffset")
   private Integer bitOffset;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("codeSegment", codeSegment == null ? null : codeSegment.getId())
      .append("offset", offset)
      .append("bitOffset", bitOffset)
      .toString();
   }
}
