package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * A parameter type.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterType extends Identified
{
   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "InternalDescription")
   private String internalDescription;

   @XmlElements(value = {
      @XmlElement(name = "TypeRestriction", namespace = Namespaces.KNX, type = TypeRestriction.class),
      @XmlElement(name = "TypeNumber", namespace = Namespaces.KNX, type = TypeNumber.class),
      @XmlElement(name = "TypeNone", namespace = Namespaces.KNX, type = TypeNone.class),
      @XmlElement(name = "TypeText", namespace = Namespaces.KNX, type = TypeText.class)
      })
   private AbstractType type;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
