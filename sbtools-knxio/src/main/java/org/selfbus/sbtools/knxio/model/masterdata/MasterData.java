package org.selfbus.sbtools.knxio.model.masterdata;

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
 * Master data
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class MasterData
{
   @XmlAttribute(name = "Version")
   private int version;

   @XmlAttribute(name = "Signature")
   private byte[] signature;

   @XmlElementWrapper(name = "DatapointTypes", namespace = Namespaces.KNX)
   @XmlElement(name = "DatapointType", namespace = Namespaces.KNX)
   private List<DatapointType> datapointTypes;

   @XmlElementWrapper(name = "MediumTypes", namespace = Namespaces.KNX)
   @XmlElement(name = "MediumType", namespace = Namespaces.KNX)
   private List<MediumType> mediumTypes;

   @XmlElementWrapper(name = "MaskVersions", namespace = Namespaces.KNX)
   @XmlElement(name = "MaskVersion", namespace = Namespaces.KNX)
   private List<MaskVersion> maskVersions;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
         .append("version", version)
         .append("signature", signature, false)
         .append("datapointTypes", datapointTypes, false)
         .append("mediumTypes", mediumTypes, false)
         .append("maskVersions", maskVersions, false)
         .toString();
   }
}
