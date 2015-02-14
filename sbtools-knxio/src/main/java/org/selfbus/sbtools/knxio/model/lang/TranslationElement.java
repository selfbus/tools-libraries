package org.selfbus.sbtools.knxio.model.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

@XmlAccessorType(XmlAccessType.NONE)
public class TranslationElement
{
   private Map<String, String> translations;

   @XmlIDREF
   @XmlAttribute(name = "RefId", required = true)
   private Identified refId;

   @XmlElement(name = "Translation", namespace = Namespaces.KNX)
   void setTranslations(List<Translation> lst)
   {
      translations = new HashMap<String, String>();
      for (Translation tr : lst)
      {
         translations.put(tr.key, tr.text);
      }
   }

   List<Translation> getTranslations()
   {
      if (translations == null)
         return null;

      List<Translation> result = new ArrayList<Translation>(translations.size());
      for (Entry<String, String> e : translations.entrySet())
         result.add(new Translation(e.getKey(), e.getValue()));
      return result;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
