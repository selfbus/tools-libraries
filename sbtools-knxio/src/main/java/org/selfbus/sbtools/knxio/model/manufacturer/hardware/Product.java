package org.selfbus.sbtools.knxio.model.manufacturer.hardware;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.common.RegistrationInfo;

@XmlAccessorType(XmlAccessType.NONE)
public class Product extends Identified
{
   @XmlAttribute(name = "Text")
   private String name;

   @XmlAttribute(name = "WidthInMillimeter")
   private Float widthInMillimeter;

   @XmlElement(name = "RegistrationInfo", namespace = Namespaces.KNX)
   private RegistrationInfo registrationInfo;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
