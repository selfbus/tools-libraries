package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.model.Namespaces;

@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class Catalog
{
   @XmlElement(name = "CatalogSection", namespace = Namespaces.KNX)
   List<CatalogSection> sections;

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

//   @Override
//   public String toString()
//   {
//      return ToStringBuilder.reflectionToString(this);
//   }
}
