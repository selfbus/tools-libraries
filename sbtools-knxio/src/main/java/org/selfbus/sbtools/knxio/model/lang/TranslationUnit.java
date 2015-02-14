package org.selfbus.sbtools.knxio.model.lang;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

@XmlAccessorType(XmlAccessType.NONE)
public class TranslationUnit
{
   @XmlElement(name = "TranslationElement", namespace = Namespaces.KNX)
   private List<TranslationElement> translationElements;

   @XmlIDREF
   @XmlAttribute(name = "RefId", required = true)
   private Identified refId;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
