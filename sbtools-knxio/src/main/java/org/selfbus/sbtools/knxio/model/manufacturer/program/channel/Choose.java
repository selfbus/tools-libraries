package org.selfbus.sbtools.knxio.model.manufacturer.program.channel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * A selection block.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Choose
{
   @XmlAttribute(name = "ParamRefId")
   private String paramRefId;

   @XmlElement(name = "when", namespace = Namespaces.KNX)
   private List<When> branches;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
