package org.selfbus.sbtools.knxio.model.manufacturer.program.channel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.manufacturer.program.comobject.ComObjectRef;

/**
 * Reference to a com object reference.
 */
public class ComObjectRefRef implements ChannelElement
{
   @XmlIDREF
   @XmlAttribute(name = "RefId")
   ComObjectRef ref;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
