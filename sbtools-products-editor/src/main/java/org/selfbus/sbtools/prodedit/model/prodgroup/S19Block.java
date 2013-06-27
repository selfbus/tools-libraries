package org.selfbus.sbtools.prodedit.model.prodgroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * A block of memory, used for non-BCU1 devices.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class S19Block
{
//   @Id
//   @TableGenerator(name = "S19Block", initialValue = 1, allocationSize = 10)
//   @GeneratedValue(strategy = GenerationType.TABLE, generator = "S19Block")
//   @Column(name = "block_id", nullable = false)
//   private int id;
//
//   @Column(name = "block_number", nullable = false)
//   private int number;
//
//   @ManyToOne(optional = false, fetch = FetchType.LAZY)
//   @JoinColumn(name = "program_id", nullable = false)
//   private ApplicationProgram program;
//
//   @Column(name = "block_name")
//   private String name;
//
//   @Column(name = "block_type")
//   private int blockType;
//
//   @Column(name = "control_code")
//   private int controlCode;
//
//   @Column(name = "segment_type")
//   private int segmentType;
//
//   @Column(name = "segment_id")
//   private int segmentId;
//
//   @Column(name = "segment_address")
//   private int segmentAddress;
//
//   @Column(name = "segment_length")
//   private int segmentLength;
//
//   @Column(name = "access_attributes")
//   private int accessAttributes;
//
//   @Column(name = "memory_type")
//   private int memoryType;
//
//   @Column(name = "memory_attributes")
//   private int memoryAttributes;
//
//   @Lob
//   @Column(name = "block_data")
//   private byte[] blockData;
//
//   @Lob
//   @Column(name = "block_mask")
//   private byte[] blockMask;
//
//   @Lob
//   @Column(name = "record")
//   private byte[] record;
//
//   @Column(name = "merge_id")
//   private int mergeId;
//
//   @Column(name = "proc_mask")
//   private int procMask;
//
//   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//   @JoinColumn(name = "block_id")
//   private Set<S19BlockParagraph> paragraphs;
//
//   /**
//    * Create a S19 block object.
//    */
//   public S19Block()
//   {
//   }
//
//   /**
//    * @return the id
//    */
//   public int getId()
//   {
//      return id;
//   }
//
//   /**
//    * @param id the id to set
//    */
//   public void setId(int id)
//   {
//      this.id = id;
//   }
//
//   /**
//    * @return the number
//    */
//   public int getNumber()
//   {
//      return number;
//   }
//
//   /**
//    * @param number the number to set
//    */
//   public void setNumber(int number)
//   {
//      this.number = number;
//   }
//
//   /**
//    * @return the program
//    */
//   public ApplicationProgram getProgram()
//   {
//      return program;
//   }
//
//   /**
//    * @param program the program to set
//    */
//   public void setProgram(ApplicationProgram program)
//   {
//      this.program = program;
//   }
//
//   /**
//    * @return the name
//    */
//   @Override
//   public String getName()
//   {
//      return name;
//   }
//
//   /**
//    * @param name the name to set
//    */
//   public void setName(String name)
//   {
//      this.name = name;
//   }
//
//   /**
//    * @return the blockType
//    */
//   public int getBlockType()
//   {
//      return blockType;
//   }
//
//   /**
//    * @param blockType the blockType to set
//    */
//   public void setBlockType(int blockType)
//   {
//      this.blockType = blockType;
//   }
//
//   /**
//    * @return the controlCode
//    */
//   public int getControlCode()
//   {
//      return controlCode;
//   }
//
//   /**
//    * @param controlCode the controlCode to set
//    */
//   public void setControlCode(int controlCode)
//   {
//      this.controlCode = controlCode;
//   }
//
//   /**
//    * @return the segmentType
//    */
//   public int getSegmentType()
//   {
//      return segmentType;
//   }
//
//   /**
//    * @param segmentType the segmentType to set
//    */
//   public void setSegmentType(int segmentType)
//   {
//      this.segmentType = segmentType;
//   }
//
//   /**
//    * @return the segmentId
//    */
//   public int getSegmentId()
//   {
//      return segmentId;
//   }
//
//   /**
//    * @param segmentId the segmentId to set
//    */
//   public void setSegmentId(int segmentId)
//   {
//      this.segmentId = segmentId;
//   }
//
//   /**
//    * @return the segmentAddress
//    */
//   public int getSegmentAddress()
//   {
//      return segmentAddress;
//   }
//
//   /**
//    * @param segmentAddress the segmentAddress to set
//    */
//   public void setSegmentAddress(int segmentAddress)
//   {
//      this.segmentAddress = segmentAddress;
//   }
//
//   /**
//    * @return the segmentLength
//    */
//   public int getSegmentLength()
//   {
//      return segmentLength;
//   }
//
//   /**
//    * @param segmentLength the segmentLength to set
//    */
//   public void setSegmentLength(int segmentLength)
//   {
//      this.segmentLength = segmentLength;
//   }
//
//   /**
//    * @return the accessAttributes
//    */
//   public int getAccessAttributes()
//   {
//      return accessAttributes;
//   }
//
//   /**
//    * @param accessAttributes the accessAttributes to set
//    */
//   public void setAccessAttributes(int accessAttributes)
//   {
//      this.accessAttributes = accessAttributes;
//   }
//
//   /**
//    * @return the memoryType
//    */
//   public int getMemoryType()
//   {
//      return memoryType;
//   }
//
//   /**
//    * @param memoryType the memoryType to set
//    */
//   public void setMemoryType(int memoryType)
//   {
//      this.memoryType = memoryType;
//   }
//
//   /**
//    * @return the memoryAttributes
//    */
//   public int getMemoryAttributes()
//   {
//      return memoryAttributes;
//   }
//
//   /**
//    * @param memoryAttributes the memoryAttributes to set
//    */
//   public void setMemoryAttributes(int memoryAttributes)
//   {
//      this.memoryAttributes = memoryAttributes;
//   }
//
//   /**
//    * @return the blockData
//    */
//   public byte[] getBlockData()
//   {
//      return blockData;
//   }
//
//   /**
//    * @param blockData the blockData to set
//    */
//   public void setBlockData(byte[] blockData)
//   {
//      this.blockData = blockData;
//   }
//
//   /**
//    * @return the blockMask
//    */
//   public byte[] getBlockMask()
//   {
//      return blockMask;
//   }
//
//   /**
//    * @param blockMask the blockMask to set
//    */
//   public void setBlockMask(byte[] blockMask)
//   {
//      this.blockMask = blockMask;
//   }
//
//   /**
//    * @return The S19 paragraphs
//    */
//   public Set<S19BlockParagraph> getParagraphs()
//   {
//      return paragraphs;
//   }
//
//   /**
//    * Added in ETS4.
//    *
//    * @return the record
//    */
//   public byte[] getRecord()
//   {
//      return record;
//   }
//
//   /**
//    * @param record the record to set
//    */
//   public void setRecord(byte[] record)
//   {
//      this.record = record;
//   }
//
//   /**
//    * Added in ETS4.
//    *
//    * @return the mergeId
//    */
//   public int getMergeId()
//   {
//      return mergeId;
//   }
//
//   /**
//    * @param mergeId the mergeId to set
//    */
//   public void setMergeId(int mergeId)
//   {
//      this.mergeId = mergeId;
//   }
//
//   /**
//    * Added in ETS4.
//    *
//    * @return the proc mask
//    */
//   public int getProcMask()
//   {
//      return procMask;
//   }
//
//   /**
//    * @param procMask - the procMask to set
//    */
//   public void setProcMask(int procMask)
//   {
//      this.procMask = procMask;
//   }
}
