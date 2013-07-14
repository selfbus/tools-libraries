package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * An S19 block.
 */
@XmlType(name = "S19Block", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdS19Block
{
   @XmlAttribute(name = "block_id", required = true)
   private int id;

   @XmlAttribute(name = "block_number", required = true)
   private int number;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "block_name", required = true)
   private String name;

   @XmlAttribute(name = "block_type")
   private Integer type;

   @XmlAttribute(name = "control_code")
   private Integer controlCode;

   @XmlAttribute(name = "segment_type")
   private Integer segmentType;

   @XmlAttribute(name = "segment_id")
   private Integer segmentId;

   @XmlAttribute(name = "segment_address")
   private Integer segmentAddress;

   @XmlAttribute(name = "segment_length")
   private Integer segmentLength;

   @XmlAttribute(name = "access_attributes")
   private Integer accessAttributes;

   @XmlAttribute(name = "memory_type")
   private Integer memoryType;

   @XmlAttribute(name = "memory_attributes")
   private Integer memoryAttributes;

   @XmlAttribute(name = "block_data")
   private byte[] blockData;

   @XmlAttribute(name = "block_mask")
   private byte[] blockMask;

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
      if (!(o instanceof VdS19Block))
         return false;
      final VdS19Block oo = (VdS19Block) o;
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
