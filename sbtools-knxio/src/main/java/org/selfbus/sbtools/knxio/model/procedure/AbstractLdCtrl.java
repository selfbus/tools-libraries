package org.selfbus.sbtools.knxio.model.procedure;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract base class for load control commands.
 */
@XmlTransient
public abstract class AbstractLdCtrl
{
   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
