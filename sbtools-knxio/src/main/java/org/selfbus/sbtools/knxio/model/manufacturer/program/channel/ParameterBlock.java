package org.selfbus.sbtools.knxio.model.manufacturer.program.channel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A block of parameters.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterBlock extends AbstractParameterBlock
{

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
