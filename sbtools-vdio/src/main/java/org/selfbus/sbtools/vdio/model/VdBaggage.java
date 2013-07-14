package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Baggage.
 */
@XmlType(name = "Baggage", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdBaggage
{
   @XmlAttribute(name = "id", required = true)
   private int id;

   @XmlAttribute(name = "manufacturerid", required = true)
   private int manufacturerId;

   @XmlAttribute(name = "localfiledir")
   private String localFileDir;

   @XmlAttribute(name = "localfilename")
   private String localFileName;

   @XmlAttribute(name = "localfileattributes")
   private Integer localFileAttributes;

   @XmlAttribute(name = "localfiletime")
   private String localFileTime;

   @XmlAttribute(name = "localfileversionhi")
   private Integer localFileVersionHi;

   @XmlAttribute(name = "localfileversionlo")
   private Integer localFileVersionLo;

   @XmlAttribute(name = "compression")
   private String compression;

   @XmlAttribute(name = "data")
   private byte[] data;

   @XmlAttribute(name = "lastused")
   private String lastUsed;

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
      if (!(o instanceof VdBaggage))
         return false;
      final VdBaggage oo = (VdBaggage) o;
      return id == oo.id;
   }
}
