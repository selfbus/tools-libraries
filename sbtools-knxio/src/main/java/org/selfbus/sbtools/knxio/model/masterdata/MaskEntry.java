package org.selfbus.sbtools.knxio.model.masterdata;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * A mask entry.
 */
@XmlType
public class MaskEntry extends Identified
{
   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "Address")
   private int address;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
