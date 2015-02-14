package org.selfbus.sbtools.knxio.model.masterdata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * The reference to a {@link MaskVersion}.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class MaskVersionRef extends Identified
{
   @XmlIDREF
   @XmlAttribute(name = "RefId")
   private MaskVersion maskVersion;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
