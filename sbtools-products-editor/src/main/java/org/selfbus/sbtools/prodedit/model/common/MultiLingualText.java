package org.selfbus.sbtools.prodedit.model.prodgroup;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.selfbus.sbtools.prodedit.jaxb.JaxbMapAdapter;

/**
 * A text / string in multiple languages.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class MultiLingualText
{
   private Map<String, String> texts = new HashMap<String, String>();

   /**
    * Get the text for a specific language.
    * 
    * @param lang - the language to get the text for.
    * @return The text or null if not found
    */
   public String getText(String lang)
   {
      return texts.get(lang);
   }
   
   /**
    * @return the texts
    */
   @XmlElement(name = "text")
   @XmlJavaTypeAdapter(JaxbMapAdapter.class)
   public Map<String, String> getTexts()
   {
      return texts;
   }

   /**
    * @param texts the texts to set
    */
   public void setTexts(Map<String, String> texts)
   {
      this.texts = texts;
   }
}
