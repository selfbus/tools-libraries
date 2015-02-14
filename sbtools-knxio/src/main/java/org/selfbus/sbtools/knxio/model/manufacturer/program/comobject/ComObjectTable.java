package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * A table of communication objects.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ComObjectTable
{
   @XmlAttribute(name = "CodeSegment")
   private String codeSegment;

   @XmlAttribute(name = "Offset")
   private Integer offset;

   @XmlElement(name = "ComObject", namespace = Namespaces.KNX)
   private Set<ComObject> comObjects;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("codeSegment", codeSegment)
         .append("offset", offset)
         .append("comObjects", comObjects, false)
         .toString();
   }
}
