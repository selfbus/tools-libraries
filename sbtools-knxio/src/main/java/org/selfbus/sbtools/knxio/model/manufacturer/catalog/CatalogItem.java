package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

@XmlType(namespace = Namespaces.KNX, propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class CatalogItem extends Identified
{
   @XmlAttribute(name = "Name", required = true)
   private String name;

   @XmlAttribute(name = "Number")
   private String number;

   @XmlAttribute(name = "ProductRefId")
   private String productRefId;

   @XmlAttribute(name = "Hardware2ProgramRefId")
   private String hardware2ProgramRefId;

   @XmlAttribute(name = "DefaultLanguage")
   private String defaultLanguage;

   @XmlAttribute(name = "NonRegRelevantDataVersion")
   private String nonRegRelevantDataVersion;

   public CatalogItem()
   {
   }

   public CatalogItem(final CatalogItem copy)
   {
      this.name = copy.name;
      this.number = copy.number;
      this.productRefId = copy.productRefId;
      this.hardware2ProgramRefId = copy.hardware2ProgramRefId;
      this.defaultLanguage = copy.defaultLanguage;
      this.nonRegRelevantDataVersion = copy.nonRegRelevantDataVersion;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getNumber()
   {
      return number;
   }

   public void setNumber(String number)
   {
      this.number = number;
   }

   public String getProductRefId()
   {
      return productRefId;
   }

   public void setProductRefId(String productRefId)
   {
      this.productRefId = productRefId;
   }

   public String getHardware2ProgramRefId()
   {
      return hardware2ProgramRefId;
   }

   public void setHardware2ProgramRefId(String hardware2ProgramRefId)
   {
      this.hardware2ProgramRefId = hardware2ProgramRefId;
   }

   public String getDefaultLanguage()
   {
      return defaultLanguage;
   }

   public void setDefaultLanguage(String defaultLanguage)
   {
      this.defaultLanguage = defaultLanguage;
   }

   public String getNonRegRelevantDataVersion()
   {
      return nonRegRelevantDataVersion;
   }

   public void setNonRegRelevantDataVersion(String nonRegRelevantDataVersion)
   {
      this.nonRegRelevantDataVersion = nonRegRelevantDataVersion;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
