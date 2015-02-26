package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

@XmlAccessorType(XmlAccessType.NONE)
public class CatalogSection extends Identified implements CatalogNode
{
   @XmlAttribute(name = "Name", required = true)
   private String name;

   @XmlAttribute(name = "Number")
   private String number;

   @XmlAttribute(name = "VisibleDescription")
   private String visibleDescription;

   @XmlAttribute(name = "DefaultLanguage")
   private String defaultLanguage;

   @XmlAttribute(name = "NonRegRelevantDataVersion")
   private String nonRegRelevantDataVersion;

   @XmlElement(name = "CatalogSection", namespace = Namespaces.KNX)
   List<CatalogSection> sections;

   @XmlElement(name = "CatalogItem", namespace = Namespaces.KNX)
   List<CatalogItem> items;

   /**
    * @return The list of catalog sections
    */
   @Override
   public synchronized List<CatalogSection> getSections()
   {
      if (sections == null)
      {
         sections = new ArrayList<CatalogSection>();
      }
      return sections;
   }

   /**
    * @return True if the section has child sections.
    */
   public boolean hasSections()
   {
      return sections != null && !sections.isEmpty();
   }

   /**
    * @return The list of catalog items
    */
   public synchronized List<CatalogItem> getItems()
   {
      if (items == null)
      {
         items = new ArrayList<CatalogItem>();
      }
      return items;
   }

   /**
    * @return True if the section has items.
    */
   public boolean hasItems()
   {
      return items != null && !items.isEmpty();
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

   public String getVisibleDescription()
   {
      return visibleDescription;
   }

   public void setVisibleDescription(String visibleDescription)
   {
      this.visibleDescription = visibleDescription;
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
