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
   private Float busCurrent;

   @XmlAttribute(name = "HasIndividualAddress")
   private boolean hasIndividualAddress;

   @XmlAttribute(name = "HasApplicationProgram")
   private boolean hasApplicationProgram;

   @XmlAttribute(name = "OriginalManufacturer")
   private String originalManufacturer;

   @XmlElementWrapper(name = "Products", namespace = Namespaces.KNX)
   @XmlElement(name = "Product", namespace = Namespaces.KNX)
   private List<Product> products;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getSerialNumber()
   {
      return serialNumber;
   }

   public void setSerialNumber(String serialNumber)
   {
      this.serialNumber = serialNumber;
   }

   public String getVersionNumber()
   {
      return versionNumber;
   }

   public void setVersionNumber(String versionNumber)
   {
      this.versionNumber = versionNumber;
   }

   public Float getBusCurrent()
   {
      return busCurrent;
   }

   public void setBusCurrent(Float busCurrent)
   {
      this.busCurrent = busCurrent;
   }

   public boolean isHasIndividualAddress()
   {
      return hasIndividualAddress;
   }

   public void setHasIndividualAddress(boolean hasIndividualAddress)
   {
      this.hasIndividualAddress = hasIndividualAddress;
   }

   public boolean isHasApplicationProgram()
   {
      return hasApplicationProgram;
   }

   public void setHasApplicationProgram(boolean hasApplicationProgram)
   {
      this.hasApplicationProgram = hasApplicationProgram;
   }

   public String getOriginalManufacturer()
   {
      return originalManufacturer;
   }

   public void setOriginalManufacturer(String originalManufacturer)
   {
      this.originalManufacturer = originalManufacturer;
   }

   public List<Product> getProducts()
   {
      return products;
   }

   public void setProducts(List<Product> products)
   {
      this.products = products;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
