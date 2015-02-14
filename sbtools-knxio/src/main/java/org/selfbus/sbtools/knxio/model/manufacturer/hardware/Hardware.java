package org.selfbus.sbtools.knxio.model.manufacturer.hardware;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * A hardware description.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Hardware extends Identified
{
   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "SerialNumber")
   private String serialNumber;

   @XmlAttribute(name = "VersionNumber")
   private String versionNumber;

   @XmlAttribute(name = "BusCurrent")
   private Integer busCurrent;

   @XmlAttribute(name = "HasIndividualAddress")
   private boolean hasIndividualAddress;

   @XmlAttribute(name = "HasApplicationProgram")
   private boolean hasApplicationProgram;

   @XmlAttribute(name = "OriginalManufacturer")
   private String originalManufacturer;

   @XmlElementWrapper(name = "Products", namespace = Namespaces.KNX)
   @XmlElement(name = "Product", namespace = Namespaces.KNX)
   private List<Product> products;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
