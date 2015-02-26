package org.selfbus.sbtools.knxio.model.manufacturer;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.lang.Language;
import org.selfbus.sbtools.knxio.model.manufacturer.catalog.Catalog;
import org.selfbus.sbtools.knxio.model.manufacturer.hardware.Hardware;
import org.selfbus.sbtools.knxio.model.manufacturer.program.ApplicationProgram;

@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class ManufacturerData
{
   @XmlAttribute(name = "RefId")
   private String refId;

   @XmlElement(name = "Catalog", namespace = Namespaces.KNX)
   private Catalog catalog;

   @XmlElementWrapper(name = "Hardware", namespace = Namespaces.KNX)
   @XmlElement(name = "Hardware", namespace = Namespaces.KNX)
   private List<Hardware> hardware;

   @XmlElementWrapper(name = "ApplicationPrograms", namespace = Namespaces.KNX)
   @XmlElement(name = "ApplicationProgram", namespace = Namespaces.KNX)
   private List<ApplicationProgram> applicationPrograms;

   @XmlElementWrapper(name = "Languages", namespace = Namespaces.KNX)
   @XmlElement(name = "Language", namespace = Namespaces.KNX)
   private List<Language> languages;

   /**
    * @return The reference Id of the manufacturer, e.g. "M-007b".
    */
   public String getRefId()
   {
      return refId;
   }

   /**
    * Set the reference Id of the manufacturer.
    *
    * @param refId The reference Id, e.g. "M-007b"
    */
   public void setRefId(String refId)
   {
      this.refId = refId;
   }

   public Catalog getCatalog()
   {
      return catalog;
   }

   public void setCatalog(Catalog catalog)
   {
      this.catalog = catalog;
   }

   public List<Hardware> getHardware()
   {
      return hardware;
   }

   public void setHardware(List<Hardware> hardware)
   {
      this.hardware = hardware;
   }

   public List<ApplicationProgram> getApplicationPrograms()
   {
      return applicationPrograms;
   }

   public void setApplicationPrograms(List<ApplicationProgram> applicationPrograms)
   {
      this.applicationPrograms = applicationPrograms;
   }

   public List<Language> getLanguages()
   {
      return languages;
   }

   public void setLanguages(List<Language> languages)
   {
      this.languages = languages;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
