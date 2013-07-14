package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * An address fixup.
 */
@XmlType(name = "S19AddressFixup", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdAddressFixup
{
   @XmlAttribute(name = "fixup_id", required = true)
   private int id;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "fixup_type")
   private Integer type;

   @XmlAttribute(name = "fixup_name")
   private String name;

   @XmlAttribute(name = "fixup_address")
   private Integer address;

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
      if (!(o instanceof VdAddressFixup))
         return false;
      final VdAddressFixup oo = (VdAddressFixup) o;
      return id == oo.id;
   }
}
