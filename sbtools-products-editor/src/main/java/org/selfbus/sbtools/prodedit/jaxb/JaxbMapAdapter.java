package org.selfbus.sbtools.prodedit.jaxb;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbMapAdapter<K, V> extends XmlAdapter<JaxbMapToList<K, V>, Map<K, V>>
{
   @Override
   public Map<K, V> unmarshal(JaxbMapToList<K, V> v) throws Exception
   {
      HashMap<K, V> result = new HashMap<K, V>();
      for (JaxbMapToListEntry<K, V> jme : v.getList())
      {
         result.put(jme.getKey(), jme.getValue());
      }
      return result;
   }

   @Override
   public JaxbMapToList<K, V> marshal(Map<K, V> v) throws Exception
   {
      JaxbMapToList<K, V> result = new JaxbMapToList<K, V>();
      for (Map.Entry<K, V> entry : v.entrySet())
      {
         JaxbMapToListEntry<K, V> jme = new JaxbMapToListEntry<K, V>();
         jme.setKey(entry.getKey());
         jme.setValue(entry.getValue());
         result.getList().add(jme);
      }
      return result;
   }
}
