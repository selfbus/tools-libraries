package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Access;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * A parameter that has no memory location.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class AnonymousParameter extends Identified implements ParameterElement
{
   @XmlAttribute(name = "Name", required = true)
   private String name;

   @XmlAttribute(name = "Text")
   private String text;

   @XmlIDREF
   @XmlAttribute(name = "ParameterType")
   private ParameterType parameterType;

   @XmlAttribute(name = "Access")
   private Access access = Access.READ_WRITE;

   @XmlAttribute(name = "Value")
   private String value = "";

   @XmlAttribute(name = "LegacyPatchAlways")
   private boolean legacyPatchAlways = false;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .appendSuper(super.toString())
         .append("name", name)
         .append("text", text)
         .append("parameterType", parameterType == null ? null : parameterType.getId())
         .append("access", access)
         .append("value", value)
         .append("legacyPatchAlways", legacyPatchAlways)
         .toString();
   }
}
