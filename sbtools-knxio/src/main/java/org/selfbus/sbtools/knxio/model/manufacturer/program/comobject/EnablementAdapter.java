package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Translate between "Enabled" / "Disabled" and Boolean.
 */
public class EnablementAdapter extends XmlAdapter<String, Boolean>
{
   @Override
   public Boolean unmarshal(String v) throws Exception
   {
      return "Enabled".equals(v);
   }

   @Override
   public String marshal(Boolean v) throws Exception
   {
      if (v) return "Enabled";
      return "Disabled";
   }
}
