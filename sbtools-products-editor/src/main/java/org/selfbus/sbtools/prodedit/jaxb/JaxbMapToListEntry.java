package org.selfbus.sbtools.prodedit.jaxb;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;

public class JaxbMapToListEntry<K, V>
{
   private K key;
   private V value;

   public JaxbMapToListEntry()
   {
   }

   public JaxbMapToListEntry(Map.Entry<K, V> e)
   {
      key = e.getKey();
      value = e.getValue();
   }

   @XmlAttribute
   public K getKey()
   {
      return key;
   }

   public void setKey(K key)
   {
      this.key = key;
   }

   @XmlAttribute
   public V getValue()
   {
      return value;
   }

   public void setValue(V value)
   {
      this.value = value;
   }

}