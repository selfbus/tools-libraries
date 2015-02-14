package org.selfbus.sbtools.knxio.model.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A generic reference
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class Reference
{
   @XmlAttribute(name = "RefId", required = true)
   protected String refId;

   public String getRefId()
   {
      return refId;
   }

   public void setRefId(String refId)
   {
      this.refId = refId;
   }

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("refId", refId)
         .toString();
   }
}
