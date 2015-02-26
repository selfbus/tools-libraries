package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The reference to a communication object.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ComObjectRef extends AbstractComObject
{
   @XmlIDREF
   @XmlAttribute(name = "RefId")
   private ComObject comObject;

   @XmlAttribute(name = "Tag")
   private String tag;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
