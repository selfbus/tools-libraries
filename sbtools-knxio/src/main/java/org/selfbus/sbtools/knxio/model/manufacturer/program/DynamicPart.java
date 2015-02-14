package org.selfbus.sbtools.knxio.model.manufacturer.program;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.manufacturer.program.channel.Channel;

/**
 * Dynamic part of an application program.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class DynamicPart
{
   @XmlElement(name = "Channel", namespace = Namespaces.KNX)
   private Channel channel;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
