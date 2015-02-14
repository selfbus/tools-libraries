package org.selfbus.sbtools.knxio.model.common;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Registration information.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class RegistrationInfo
{
   @XmlAttribute(name = "RegistrationStatus")
   private String registrationStatus;

   @XmlAttribute(name = "RegistrationNumber")
   private String registrationNumber;

   @XmlAttribute(name = "OriginalRegistrationNumber")
   private String originalRegistrationNumber;

   @XmlAttribute(name = "RegistrationDate")
   private Date registrationDate;

   @XmlAttribute(name = "RegistrationSignature")
   private String registrationSignature;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
