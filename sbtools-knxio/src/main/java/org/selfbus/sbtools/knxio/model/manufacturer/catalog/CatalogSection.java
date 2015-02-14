package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

@XmlAccessorType(XmlAccessType.NONE)
public class CatalogSection extends Identified
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
   public synchronized List<CatalogSection> getSections()
   {
      if (sections == null)
      {
         sections = new ArrayList<CatalogSection>();
      }
      return sections;
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

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
