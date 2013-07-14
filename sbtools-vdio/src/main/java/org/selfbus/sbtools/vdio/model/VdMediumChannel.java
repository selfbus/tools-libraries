package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A medium channel.
 */
@XmlType(name = "MediumChannel", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdMediumChannel
{
   @XmlAttribute(name = "medium_channel_id", required = true)
   private int id;

   @XmlAttribute(name = "medium_type_number", required = true)
   private int typeId;

   @XmlAttribute(name = "channel_number", required = true)
   private int channelId;

   @XmlAttribute(name = "max_device_duty_cycle")
   private Double maxDeviceDutyCycle;

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
      if (!(o instanceof VdMediumChannel))
         return false;
      final VdMediumChannel oo = (VdMediumChannel) o;
      return id == oo.id;
   }
}
