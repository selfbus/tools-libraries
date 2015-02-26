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
   private static final long serialVersionUID = -6965945748733663635L;

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

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Integer getNumber()
   {
      return number;
   }

   public void setNumber(Integer number)
   {
      this.number = number;
   }

   public Integer getVersion()
   {
      return version;
   }

   public void setVersion(Integer version)
   {
      this.version = version;
   }

   public String getProgramType()
   {
      return programType;
   }

   public void setProgramType(String programType)
   {
      this.programType = programType;
   }

   public String getMaskVersion()
   {
      return maskVersion;
   }

   public void setMaskVersion(String maskVersion)
   {
      this.maskVersion = maskVersion;
   }

   public String getLoadProcedureStyle()
   {
      return loadProcedureStyle;
   }

   public void setLoadProcedureStyle(String loadProcedureStyle)
   {
      this.loadProcedureStyle = loadProcedureStyle;
   }

   public String getPeiType()
   {
      return peiType;
   }

   public void setPeiType(String peiType)
   {
      this.peiType = peiType;
   }

   public String getHelpFile()
   {
      return helpFile;
   }

   public void setHelpFile(String helpFile)
   {
      this.helpFile = helpFile;
   }

   public String getDefaultLanguage()
   {
      return defaultLanguage;
   }

   public void setDefaultLanguage(String defaultLanguage)
   {
      this.defaultLanguage = defaultLanguage;
   }

   public boolean isDynamicTableManagement()
   {
      return dynamicTableManagement;
   }

   public void setDynamicTableManagement(boolean dynamicTableManagement)
   {
      this.dynamicTableManagement = dynamicTableManagement;
   }

   public boolean isLinkable()
   {
      return linkable;
   }

   public void setLinkable(boolean linkable)
   {
      this.linkable = linkable;
   }

   public String getMinEtsVersion()
   {
      return minEtsVersion;
   }

   public void setMinEtsVersion(String minEtsVersion)
   {
      this.minEtsVersion = minEtsVersion;
   }

   public String getOriginalManufacturer()
   {
      return originalManufacturer;
   }

   public void setOriginalManufacturer(String originalManufacturer)
   {
      this.originalManufacturer = originalManufacturer;
   }

   public boolean isPreEts4Style()
   {
      return preEts4Style;
   }

   public void setPreEts4Style(boolean preEts4Style)
   {
      this.preEts4Style = preEts4Style;
   }

   public String getNonRegRelevantDataVersion()
   {
      return nonRegRelevantDataVersion;
   }

   public void setNonRegRelevantDataVersion(String nonRegRelevantDataVersion)
   {
      this.nonRegRelevantDataVersion = nonRegRelevantDataVersion;
   }

   public String getHash()
   {
      return hash;
   }

   public void setHash(String hash)
   {
      this.hash = hash;
   }

   public boolean isConvertedFromPreEts4Data()
   {
      return convertedFromPreEts4Data;
   }

   public void setConvertedFromPreEts4Data(boolean convertedFromPreEts4Data)
   {
      this.convertedFromPreEts4Data = convertedFromPreEts4Data;
   }

   public boolean isBroken()
   {
      return broken;
   }

   public void setBroken(boolean broken)
   {
      this.broken = broken;
   }

   public String getIpConfig()
   {
      return ipConfig;
   }

   public void setIpConfig(String ipConfig)
   {
      this.ipConfig = ipConfig;
   }

   public Integer getAdditionalAddressesCount()
   {
      return additionalAddressesCount;
   }

   public void setAdditionalAddressesCount(Integer additionalAddressesCount)
   {
      this.additionalAddressesCount = additionalAddressesCount;
   }

   public boolean isDownloadInfoIncomplete()
   {
      return downloadInfoIncomplete;
   }

   public void setDownloadInfoIncomplete(boolean downloadInfoIncomplete)
   {
      this.downloadInfoIncomplete = downloadInfoIncomplete;
   }

   public Integer getCreatedFromLegacySchemaVersion()
   {
      return createdFromLegacySchemaVersion;
   }

   public void setCreatedFromLegacySchemaVersion(Integer createdFromLegacySchemaVersion)
   {
      this.createdFromLegacySchemaVersion = createdFromLegacySchemaVersion;
   }

   public StaticPart getStaticPart()
   {
      return staticPart;
   }

   public void setStaticPart(StaticPart staticPart)
   {
      this.staticPart = staticPart;
   }

   public DynamicPart getDynamicPart()
   {
      return dynamicPart;
   }

   public void setDynamicPart(DynamicPart dynamicPart)
   {
      this.dynamicPart = dynamicPart;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
