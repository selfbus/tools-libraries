package org.selfbus.sbtools.knxio.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.manufacturer.ManufacturerData;
import org.selfbus.sbtools.knxio.model.masterdata.MasterData;

import com.jgoodies.binding.beans.Model;

/**
 * A KNX-XML document.
 */
@XmlRootElement(namespace = Namespaces.KNX)
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class KnxDocument extends Model
{
   private static final long serialVersionUID = 704994336917173614L;

   @XmlAttribute(name = "CreatedBy")
   private String createdBy;

   @XmlAttribute(name = "ToolVersion")
   private String toolVersion;

   @XmlElement(name = "MasterData", namespace = Namespaces.KNX)
   private MasterData masterData;

   @XmlElementWrapper(name = "ManufacturerData", namespace = Namespaces.KNX)
   @XmlElement(name = "Manufacturer", namespace = Namespaces.KNX)
   private List<ManufacturerData> manufacturerDatas;

   /**
    * Create a KNX-XML document.
    */
   public KnxDocument()
   {
   }

   public String getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(String createdBy)
   {
      this.createdBy = createdBy;
   }

   public String getToolVersion()
   {
      return toolVersion;
   }

   public void setToolVersion(String toolVersion)
   {
      this.toolVersion = toolVersion;
   }

   public MasterData getMasterData()
   {
      return masterData;
   }

   public void setMasterData(MasterData masterData)
   {
      this.masterData = masterData;
   }

   public List<ManufacturerData> getManufacturerDatas()
   {
      return manufacturerDatas;
   }

   public void setManufacturerDatas(List<ManufacturerData> manufacturerDatas)
   {
      this.manufacturerDatas = manufacturerDatas;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
   }
}
