package org.selfbus.sbtools.knxio.model.masterdata;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * A datapoint sub type.
 */
@XmlType
public class DatapointSubtype extends Identified
{
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

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
