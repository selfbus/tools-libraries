package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * The reference to a parameter.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterRef extends Identified
{
   @XmlIDREF
   @XmlAttribute(name = "RefId")
   private AnonymousParameter parameter;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
