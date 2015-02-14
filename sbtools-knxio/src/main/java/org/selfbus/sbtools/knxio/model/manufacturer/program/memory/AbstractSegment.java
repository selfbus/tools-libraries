package org.selfbus.sbtools.knxio.model.manufacturer.program.memory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * Abstract base class for memory/code segments.
 */
@XmlTransient
public abstract class AbstractSegment extends Identified
{
   @XmlElement(name = "Data", namespace = Namespaces.KNX)
   private byte[] data;

   @XmlElement(name = "Mask", namespace = Namespaces.KNX)
   private byte[] mask;

   public byte[] getData()
   {
      return data;
   }

   public void setData(byte[] data)
   {
      this.data = data;
   }

   public byte[] getMask()
   {
      return mask;
   }

   public void setMask(byte[] mask)
   {
      this.mask = mask;
   }
}
