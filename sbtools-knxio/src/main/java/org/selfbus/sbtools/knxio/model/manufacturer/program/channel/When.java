package org.selfbus.sbtools.knxio.model.manufacturer.program.channel;

import java.awt.image.renderable.ParameterBlock;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * A branch of a {@link Choose}.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class When extends AbstractParameterBlock implements ChannelElement
{
   @XmlElements(value = {
      @XmlElement(name = "ComObjectRefRef", namespace = Namespaces.KNX, type = ComObjectRefRef.class),
      @XmlElement(name = "ParameterRefRef", namespace = Namespaces.KNX, type = ParameterRefRef.class),
      @XmlElement(name = "ParameterBlock", namespace = Namespaces.KNX, type = ParameterBlock.class),
      @XmlElement(name = "Choose", namespace = Namespaces.KNX, type = Choose.class)
      })
   private List<ChannelElement> childs;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
