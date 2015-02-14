package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A memory segment location.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class MemorySegmentLocation
{
   @XmlAttribute(name = "AddressSpace")
   private AddressSpace addressSpace;

   @XmlAttribute(name = "StartAddress")
   private int startAddress;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
