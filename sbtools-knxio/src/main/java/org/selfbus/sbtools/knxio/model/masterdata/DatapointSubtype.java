package org.selfbus.sbtools.knxio.model.masterdata;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.common.Named;

/**
 * A datapoint sub type.
 */
@XmlType
public class DatapointSubtype extends Identified implements Named
{
   private static final long serialVersionUID = 5243089376915461297L;

   @XmlAttribute(name = "Number")
   private int number;

   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "Text")
   private String text;

   @XmlAttribute(name = "SizeInBit")
   private int sizeInBit;

   @XmlAttribute(name = "Default")
   private boolean isDefault;

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

   public int getSizeInBit()
   {
      return sizeInBit;
   }

   public void setSizeInBit(int sizeInBit)
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

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
