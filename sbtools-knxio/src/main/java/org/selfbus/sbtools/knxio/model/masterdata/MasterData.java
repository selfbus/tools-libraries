package org.selfbus.sbtools.knxio.model.masterdata;

import java.util.Comparator;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.lang.Language;

import com.jgoodies.common.collect.ArrayListModel;

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

   @XmlElementWrapper(name = "Languages", namespace = Namespaces.KNX)
   @XmlElement(name = "Language", namespace = Namespaces.KNX)
   private List<Language> languages;

   @XmlElementWrapper(name = "MaskVersions", namespace = Namespaces.KNX)
   @XmlElement(name = "MaskVersion", namespace = Namespaces.KNX)
   private ArrayListModel<MaskVersion> maskVersions;

   public int getVersion()
   {
      return version;
   }

   public void setVersion(int version)
   {
      this.version = version;
   }

   public byte[] getSignature()
   {
      return signature;
   }

   public void setSignature(byte[] signature)
   {
      this.signature = signature;
   }

   public List<DatapointType> getDatapointTypes()
   {
      return datapointTypes;
   }

   public void setDatapointTypes(List<DatapointType> datapointTypes)
   {
      this.datapointTypes = datapointTypes;
   }

   public List<MediumType> getMediumTypes()
   {
      return mediumTypes;
   }

   public void setMediumTypes(List<MediumType> mediumTypes)
   {
      this.mediumTypes = mediumTypes;
   }

   /**
    * Get a {@link MaskVersion} by Id.
    *
    * @param id The mask version Id
    * @return The mask version, null if not found
    */
   public MaskVersion getMaskVersion(String id)
   {
      if (maskVersions == null)
         return null;

      for (MaskVersion maskVersion : maskVersions)
      {
         if (id.equals(maskVersion.getId()))
            return maskVersion;
      }
      return null;
   }

   public ArrayListModel<MaskVersion> getMaskVersions()
   {
      return maskVersions;
   }

   void setMaskVersions(ArrayListModel<MaskVersion> maskVersions)
   {
      this.maskVersions = maskVersions;
   }

   public List<Language> getLanguages()
   {
      return languages;
   }

   public void setLanguages(List<Language> languages)
   {
      this.languages = languages;
   }

   void afterUnmarshal(Unmarshaller u, Object parent)
   {
      if (maskVersions != null)
      {
         maskVersions.sort(new Comparator<MaskVersion>()
         {
            @Override
            public int compare(MaskVersion a, MaskVersion b)
            {
               int cmp = a.getManagementModel().compareTo(b.getManagementModel());
               if (cmp == 0) cmp = a.getName().compareTo(b.getName());
               return cmp;
            }
         });
      }
   }

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
