package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The channel list.
 */
@XmlType(name = "ChannelList", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdChannelList
{
   @XmlAttribute(name = "channel_list_id", required = true)
   private int id;

   @XmlAttribute(name = "medium_type_number", required = true)
   private int mediumTypeId;

   @XmlAttribute(name = "channel_list_name")
   private String name;

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof VdChannelList))
         return false;
      final VdChannelList oo = (VdChannelList) o;
      return id == oo.id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return name;
   }
}
