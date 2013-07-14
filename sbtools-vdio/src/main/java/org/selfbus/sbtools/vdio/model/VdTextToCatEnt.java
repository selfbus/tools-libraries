package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Text to catalog entry mapping.
 */
@XmlType(name = "TextToCatEnt", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdTextToCatEnt
{
   @XmlAttribute(name = "text_to_catent_id", required = true)
   private int id;

   @XmlAttribute(name = "catalog_entry_id")
   private int catalogEntryId;

   @XmlAttribute(name = "spec_text_id")
   private int specTextId;

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
      if (!(o instanceof VdTextToCatEnt))
         return false;
      final VdTextToCatEnt oo = (VdTextToCatEnt) o;
      return id == oo.id;
   }
}
