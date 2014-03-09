package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A mask entry.
 */
@XmlType(name = "MaskEntry", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdMaskEntry
{
   @XmlAttribute(name = "mask_entry_id", required = true)
   private int id;

   @XmlAttribute(name = "mask_id", required = true)
   private int maskId;

   @XmlAttribute(name = "mask_entry_name", required = true)
   private String name;

   @XmlAttribute(name = "mask_entry_address", required = true)
   private int address;

   /**
    * Create a mask entry object.
    */
   public VdMaskEntry()
   {
   }

   /**
    * Create a mask entry object.
    *
    * @param id - the id.
    * @param name - the name.
    */
   public VdMaskEntry(int id, String name)
   {
      this.id = id;
      this.name = name;
   }

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
    * @return the maskId
    */
   public int getMaskId()
   {
      return maskId;
   }

   /**
    * @param maskId the maskId to set
    */
   public void setMaskId(int maskId)
   {
      this.maskId = maskId;
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the address
    */
   public int getAddress()
   {
      return address;
   }

   /**
    * @param address the address to set
    */
   public void setAddress(int address)
   {
      this.address = address;
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
      if (!(o instanceof VdMaskEntry))
         return false;
      final VdMaskEntry oo = (VdMaskEntry) o;
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
