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
    * @return the id
    */
   public int getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the blockId
    */
   public int getBlockId()
   {
      return blockId;
   }

   /**
    * @param blockId the blockId to set
    */
   public void setBlockId(int blockId)
   {
      this.blockId = blockId;
   }

   /**
    * @return the ptColumnId
    */
   public Integer getPtColumnId()
   {
      return ptColumnId;
   }

   /**
    * @param ptColumnId the ptColumnId to set
    */
   public void setPtColumnId(Integer ptColumnId)
   {
      this.ptColumnId = ptColumnId;
   }

   /**
    * @return the dataLong
    */
   public Integer getDataLong()
   {
      return dataLong;
   }

   /**
    * @param dataLong the dataLong to set
    */
   public void setDataLong(Integer dataLong)
   {
      this.dataLong = dataLong;
   }

   /**
    * @return the dataBinary
    */
   public byte[] getDataBinary()
   {
      return dataBinary;
   }

   /**
    * @param dataBinary the dataBinary to set
    */
   public void setDataBinary(byte[] dataBinary)
   {
      this.dataBinary = dataBinary;
   }

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
