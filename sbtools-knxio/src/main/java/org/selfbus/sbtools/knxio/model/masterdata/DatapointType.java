package org.selfbus.sbtools.knxio.model.masterdata;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * A datapoint type.
 */
@XmlType
public class DatapointType extends Identified
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

   @XmlElementWrapper(name = "DatapointSubtypes", namespace = Namespaces.KNX)
   @XmlElement(name = "DatapointSubtype", namespace = Namespaces.KNX)
   private List<DatapointSubtype> subTypes;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
