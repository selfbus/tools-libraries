package org.selfbus.sbtools.knxio.model.lang;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;

@XmlAccessorType(XmlAccessType.NONE)
public class Language
{
   @XmlID
   @XmlAttribute(name = "Identifier")
   private String id;

   @XmlElement(name = "TranslationUnit", namespace = Namespaces.KNX)
   private List<TranslationUnit> translationUnits;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
