package org.selfbus.sbtools.knxio.model.masterdata;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.masterdata.hawk.HawkConfigurationData;

/**
 * A mask version.
 */
@XmlType
public class MaskVersion extends Identified
{
   @XmlAttribute(name = "MaskVersion")
   private int version;

   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "ManagementModel")
   private String managementModel;

   @XmlIDREF
   @XmlAttribute(name = "MediumTypeRefId")
   private MediumType mediumType;

   @XmlIDREF
   @XmlAttribute(name = "OtherMediumTypeRefId")
   private MediumType otherMediumType;

   @XmlAttribute(name = "MgmtDescriptor01")
   private byte[] mgmtDescriptor01;

   @XmlElementWrapper(name = "DownwardCompatibleMasks", namespace = Namespaces.KNX)
   @XmlElement(name = "DownwardCompatibleMask", namespace = Namespaces.KNX)
   private List<MaskVersionRef> downwardCompatibleMasks;

   @XmlElementWrapper(name = "MaskEntries", namespace = Namespaces.KNX)
   @XmlElement(name = "MaskEntry", namespace = Namespaces.KNX)
   private List<MaskEntry> maskEntries;

   @XmlElement(name = "HawkConfigurationData", namespace = Namespaces.KNX)
   private List<HawkConfigurationData> hawkConfigurationData;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
      .append("version", version)
      .append("name", name)
      .append("managementModel", managementModel)
      .append("mediumType", mediumType == null ? null : mediumType.getId())
      .append("otherMediumType", otherMediumType == null ? null : otherMediumType.getId())
      .append("mgmtDescriptor01", mgmtDescriptor01, false)
      .append("downwardCompatibleMasks", downwardCompatibleMasks, false)
      .append("maskEntries", maskEntries, false)
      .append("hawkConfigurationData", hawkConfigurationData, true)
      .toString();
   }
}
