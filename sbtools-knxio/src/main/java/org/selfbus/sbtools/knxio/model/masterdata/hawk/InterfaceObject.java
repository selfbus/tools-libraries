package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * An interface object.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class InterfaceObject
{
   @XmlAttribute(name = "ObjectType")
   private int type;

   @XmlElement(name = "Property", namespace = Namespaces.KNX)
   private List<Property> properties;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
