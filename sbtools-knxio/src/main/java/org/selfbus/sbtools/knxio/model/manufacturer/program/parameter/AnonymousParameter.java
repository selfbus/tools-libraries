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
   private static final long serialVersionUID = -939516211002012463L;

   @XmlAttribute(name = "Name", required = true)
   private String name;

   @XmlAttribute(name = "Text")
   private String text;

   @XmlAttribute(name = "SuffixText")
   private String suffixText;

   @XmlIDREF
   @XmlAttribute(name = "ParameterType")
   private ParameterType type;

   @XmlAttribute(name = "Access")
   private Access access = Access.READ_WRITE;

   @XmlAttribute(name = "Value")
   private String value = "";

   @XmlAttribute(name = "LegacyPatchAlways")
   private boolean legacyPatchAlways = false;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getText()
   {
      return text;
   }

   public void setText(String text)
   {
      this.text = text;
   }

   public String getSuffixText()
   {
      return suffixText;
   }

   public void setSuffixText(String suffixText)
   {
      this.suffixText = suffixText;
   }

   public ParameterType getType()
   {
      return type;
   }

   public void setType(ParameterType type)
   {
      this.type = type;
   }

   public Access getAccess()
   {
      return access;
   }

   public void setAccess(Access access)
   {
      this.access = access;
   }

   public String getValue()
   {
      return value;
   }

   public void setValue(String value)
   {
      this.value = value;
   }

   public boolean isLegacyPatchAlways()
   {
      return legacyPatchAlways;
   }

   public void setLegacyPatchAlways(boolean legacyPatchAlways)
   {
      this.legacyPatchAlways = legacyPatchAlways;
   }

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .appendSuper(super.toString())
         .append("name", name)
         .append("text", text)
         .append("parameterType", type == null ? null : type.getId())
         .append("access", access)
         .append("value", value)
         .append("legacyPatchAlways", legacyPatchAlways)
         .toString();
   }
}
