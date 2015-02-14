package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Resource access rights for read and write access.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class AccessRights
{
   @XmlAttribute(name = "Read")
   private AccessRight read;

   @XmlAttribute(name = "Write")
   private AccessRight write;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
