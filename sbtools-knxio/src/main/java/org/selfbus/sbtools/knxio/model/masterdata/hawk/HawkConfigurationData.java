package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * Hawk configuration data.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class HawkConfigurationData
{
   @XmlAttribute(name = "LegacyVersion")
   private Integer legacyVersion;

   @XmlAttribute(name = "Ets3SystemPlugin")
   private String ets3SystemPlugin;

   @XmlElementWrapper(name = "Features", namespace = Namespaces.KNX)
   @XmlElement(name = "Feature", namespace = Namespaces.KNX)
   private List<Feature> features;

   @XmlElementWrapper(name = "Resources", namespace = Namespaces.KNX)
   @XmlElement(name = "Resource", namespace = Namespaces.KNX)
   private List<Resource> resources;

   @XmlElementWrapper(name = "Procedures", namespace = Namespaces.KNX)
   @XmlElement(name = "Procedure", namespace = Namespaces.KNX)
   private List<Procedure> procedures;

   @XmlElementWrapper(name = "MemorySegments", namespace = Namespaces.KNX)
   @XmlElement(name = "MemorySegment", namespace = Namespaces.KNX)
   private List<MemorySegment> memorySegments;

   @XmlElementWrapper(name = "InterfaceObjects", namespace = Namespaces.KNX)
   @XmlElement(name = "InterfaceObject", namespace = Namespaces.KNX)
   private List<InterfaceObject> interfaceObjects;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
      .append("legacyVersion", legacyVersion)
      .append("ets3SystemPlugin", ets3SystemPlugin)
      .append("features", features, false)
      .append("resources", resources, false)
      .append("procedures", procedures, false)
      .append("memorySegments", memorySegments, false)
      .append("interfaceObjects", interfaceObjects, false)
      .toString();
   }
}
