package org.selfbus.sbtools.knxio.model.manufacturer.program;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * An application program.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ApplicationProgram extends Identified
{
   @XmlAttribute(name = "Name", required = true)
   private String name;

   @XmlAttribute(name = "ApplicationNumber")
   private Integer number;

   @XmlAttribute(name = "ApplicationVersion")
   private Integer version;

   @XmlAttribute(name = "ProgramType")
   private String programType;

   @XmlAttribute(name = "MaskVersion")
   private String maskVersion;

   @XmlAttribute(name = "LoadProcedureStyle")
   private String loadProcedureStyle;

   @XmlAttribute(name = "PeiType")
   private String peiType;

   @XmlAttribute(name = "HelpFile")
   private String helpFile;

   @XmlAttribute(name = "DefaultLanguage")
   private String defaultLanguage;

   @XmlAttribute(name = "DynamicTableManagement")
   private boolean dynamicTableManagement;

   @XmlAttribute(name = "Linkable")
   private boolean linkable;

   @XmlAttribute(name = "MinEtsVersion")
   private String minEtsVersion;

   @XmlAttribute(name = "OriginalManufacturer")
   private String originalManufacturer;

   @XmlAttribute(name = "PreEts4Style")
   private boolean preEts4Style;

   @XmlAttribute(name = "NonRegRelevantDataVersion")
   private String nonRegRelevantDataVersion;

   @XmlAttribute(name = "Hash")
   private String hash;

   @XmlAttribute(name = "ConvertedFromPreEts4Data")
   private boolean convertedFromPreEts4Data;

   @XmlAttribute(name = "Broken")
   private boolean broken;

   @XmlAttribute(name = "IPConfig")
   private String ipConfig;

   @XmlAttribute(name = "AdditionalAddressesCount")
   private Integer additionalAddressesCount;

   @XmlAttribute(name = "DownloadInfoIncomplete")
   private boolean downloadInfoIncomplete;

   @XmlAttribute(name = "CreatedFromLegacySchemaVersion")
   private Integer createdFromLegacySchemaVersion;

   @XmlElement(name = "Static", namespace = Namespaces.KNX)
   private StaticPart staticPart;

   @XmlElement(name = "Dynamic", namespace = Namespaces.KNX)
   private DynamicPart dynamicPart;

//   public void afterUnmarshal(Unmarshaller unmarshaller, Object parent)
//   {
//      System.err.println("staticPart: " + staticPart);
//      System.err.println("dynamicPart: " + dynamicPart);
//   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
