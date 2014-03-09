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
    * @return the number
    */
   public int getNumber()
   {
      return number;
   }

   /**
    * @param number the number to set
    */
   public void setNumber(int number)
   {
      this.number = number;
   }

   /**
    * @return the programId
    */
   public int getProgramId()
   {
      return programId;
   }

   /**
    * @param programId the programId to set
    */
   public void setProgramId(int programId)
   {
      this.programId = programId;
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
    * @return the type
    */
   public Integer getType()
   {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(Integer type)
   {
      this.type = type;
   }

   /**
    * @return the controlCode
    */
   public Integer getControlCode()
   {
      return controlCode;
   }

   /**
    * @param controlCode the controlCode to set
    */
   public void setControlCode(Integer controlCode)
   {
      this.controlCode = controlCode;
   }

   /**
    * @return the segmentType
    */
   public Integer getSegmentType()
   {
      return segmentType;
   }

   /**
    * @param segmentType the segmentType to set
    */
   public void setSegmentType(Integer segmentType)
   {
      this.segmentType = segmentType;
   }

   /**
    * @return the segmentId
    */
   public Integer getSegmentId()
   {
      return segmentId;
   }

   /**
    * @param segmentId the segmentId to set
    */
   public void setSegmentId(Integer segmentId)
   {
      this.segmentId = segmentId;
   }

   /**
    * @return the segmentAddress
    */
   public Integer getSegmentAddress()
   {
      return segmentAddress;
   }

   /**
    * @param segmentAddress the segmentAddress to set
    */
   public void setSegmentAddress(Integer segmentAddress)
   {
      this.segmentAddress = segmentAddress;
   }

   /**
    * @return the segmentLength
    */
   public Integer getSegmentLength()
   {
      return segmentLength;
   }

   /**
    * @param segmentLength the segmentLength to set
    */
   public void setSegmentLength(Integer segmentLength)
   {
      this.segmentLength = segmentLength;
   }

   /**
    * @return the accessAttributes
    */
   public Integer getAccessAttributes()
   {
      return accessAttributes;
   }

   /**
    * @param accessAttributes the accessAttributes to set
    */
   public void setAccessAttributes(Integer accessAttributes)
   {
      this.accessAttributes = accessAttributes;
   }

   /**
    * @return the memoryType
    */
   public Integer getMemoryType()
   {
      return memoryType;
   }

   /**
    * @param memoryType the memoryType to set
    */
   public void setMemoryType(Integer memoryType)
   {
      this.memoryType = memoryType;
   }

   /**
    * @return the memoryAttributes
    */
   public Integer getMemoryAttributes()
   {
      return memoryAttributes;
   }

   /**
    * @param memoryAttributes the memoryAttributes to set
    */
   public void setMemoryAttributes(Integer memoryAttributes)
   {
      this.memoryAttributes = memoryAttributes;
   }

   /**
    * @return the blockData
    */
   public byte[] getBlockData()
   {
      return blockData;
   }

   /**
    * @param blockData the blockData to set
    */
   public void setBlockData(byte[] blockData)
   {
      this.blockData = blockData;
   }

   /**
    * @return the blockMask
    */
   public byte[] getBlockMask()
   {
      return blockMask;
   }

   /**
    * @param blockMask the blockMask to set
    */
   public void setBlockMask(byte[] blockMask)
   {
      this.blockMask = blockMask;
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
