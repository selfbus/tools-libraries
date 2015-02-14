package org.selfbus.sbtools.knxio.model.lang;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Translation
{
   @XmlAttribute(name = "AttributeName")
   public String key;

   @XmlAttribute(name = "Text")
   public String text;

   public Translation()
   {
   }

   public Translation(String key, String text)
   {
      this.key = key;
      this.text = text;
   }
}
