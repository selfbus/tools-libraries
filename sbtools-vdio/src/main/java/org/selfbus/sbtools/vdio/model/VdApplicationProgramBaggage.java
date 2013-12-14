package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * An application program baggage.
 */
@XmlType(name = "ApplicationProgramBaggage", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdApplicationProgramBaggage
{
   @XmlAttribute(name = "id", required = true)
   private int id;

   @XmlAttribute(name = "ApplicationProgramID", required = true)
   private int programId;

   @XmlAttribute(name = "BaggageID", required = true)
   private Integer baggageId;

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
      if (!(o instanceof VdApplicationProgramBaggage))
         return false;
      final VdApplicationProgramBaggage oo = (VdApplicationProgramBaggage) o;
      return id == oo.id;
   }
}
