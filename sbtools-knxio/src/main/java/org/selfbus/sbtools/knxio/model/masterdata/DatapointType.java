package org.selfbus.sbtools.knxio.model.masterdata;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.BitSized;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.common.Named;

/**
 * A datapoint type.
 */
@XmlType
public class DatapointType extends Identified implements Named, BitSized
{
   private static final long serialVersionUID = 6625819426380681728L;

   @XmlAttribute(name = "Number")
   private int number;

   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "Text")
   private String text;

   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @XmlAttribute(name = "Default")
   private boolean isDefault;

   @XmlElementWrapper(name = "DatapointSubtypes", namespace = Namespaces.KNX)
   @XmlElement(name = "DatapointSubtype", namespace = Namespaces.KNX)
   private List<DatapointSubtype> subtypes;

   /**
    * Get the datapoint type Id for a datapoint subtype Id.
    * E.g. "DPST-13-11" becomes "DPT-13"
    *
    * @param subtypeId The Id of the datapoint subtype
    * @return The datapoint type Id
    */
   static String subtypeToType(String subtypeId)
   {
      return subtypeId.replaceFirst("^DPST-(\\d+)-.*$", "DPT-$1");
   }

   public int getNumber()
   {
      return number;
   }

   public void setNumber(int number)
   {
      this.number = number;
   }

   @Override
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getText()
   {
      return text;
   }

   public void setText(String text)
   {
      this.text = text;
   }

   @Override
   public Integer getSizeInBit()
   {
      return sizeInBit;
   }

   @Override
   public void setSizeInBit(Integer sizeInBit)
   {
      this.sizeInBit = sizeInBit;
   }

   public boolean isDefault()
   {
      return isDefault;
   }

   public void setDefault(boolean isDefault)
   {
      this.isDefault = isDefault;
   }

   /**
    * Get a datapoint subtype by subtype Id.
    *
    * @param id The Id of the subtype
    * @return The datapoint subtype, null if not found
    */
   public DatapointSubtype getSubtype(String id)
   {
      if (subtypes == null || id == null)
         return null;

      for (DatapointSubtype subtype : subtypes)
      {
         if (id.equals(subtype.getId()))
            return subtype;
      }

      return null;
   }

   public List<DatapointSubtype> getSubtypes()
   {
      return subtypes;
   }

   public void setSubtypes(List<DatapointSubtype> subtypes)
   {
      this.subtypes = subtypes;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
