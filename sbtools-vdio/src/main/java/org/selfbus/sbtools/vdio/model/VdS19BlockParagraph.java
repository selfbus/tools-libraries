package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * An S19 block paragraph.
 */
@XmlType(name = "S19BlockParagraph", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdS19BlockParagraph
{
   @XmlAttribute(name = "s19_block_paragraph_id", required = true)
   private int id;

   @XmlAttribute(name = "block_id", required = true)
   private int blockId;

   @XmlAttribute(name = "pt_column_id")
   private Integer ptColumnId;

   @XmlAttribute(name = "data_long")
   private Integer dataLong;

   @XmlAttribute(name = "data_binary")
   private byte[] dataBinary;

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
      if (!(o instanceof VdS19BlockParagraph))
         return false;
      final VdS19BlockParagraph oo = (VdS19BlockParagraph) o;
      return id == oo.id;
   }
}
