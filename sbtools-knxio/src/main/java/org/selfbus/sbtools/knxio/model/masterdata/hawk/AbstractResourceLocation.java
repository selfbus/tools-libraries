package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A hawk configuration resource.
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractResourceLocation
{
   @XmlAttribute(name = "AddressSpace")
   private AddressSpace addressSpace;

   @XmlAttribute(name = "PtrResource")
   private String ptrResource;

   @XmlAttribute(name = "InterfaceObjectRef")
   private Integer interfaceObjectRef;

   @XmlAttribute(name = "PropertyID")
   private Integer propertyID;

   @XmlAttribute(name = "StartAddress")
   private Integer startAddress;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
