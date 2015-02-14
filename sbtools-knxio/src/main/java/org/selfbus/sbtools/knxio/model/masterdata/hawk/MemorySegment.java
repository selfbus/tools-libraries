package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * A memory segment.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class MemorySegment
{
   @XmlAttribute(name = "MemoryType")
   private MemoryType memoryType;

   @XmlAttribute(name = "Length")
   private int length;

   @XmlAttribute(name = "Optional")
   private boolean optional;

   @XmlElement(name = "Location", namespace = Namespaces.KNX)
   private MemorySegmentLocation location;

   @XmlElement(name = "AccessRights", namespace = Namespaces.KNX)
   private AccessRights accessRights;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
