package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.manufacturer.program.memory.MemoryLocation;

/**
 * A union of multiple parameters.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class Union implements ParameterElement
{
   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @XmlElement(name = "Memory", namespace = Namespaces.KNX)
   private MemoryLocation memory;

   @XmlElement(name = "Parameter", namespace = Namespaces.KNX)
   private List<Parameter> parameters;

   public Integer getSizeInBit()
   {
      return sizeInBit;
   }

   public void setSizeInBit(Integer sizeInBit)
   {
      this.sizeInBit = sizeInBit;
   }

   public MemoryLocation getMemory()
   {
      return memory;
   }

   public void setMemory(MemoryLocation memory)
   {
      this.memory = memory;
   }

   public List<Parameter> getParameters()
   {
      return parameters;
   }

   public void setParameters(List<Parameter> parameters)
   {
      this.parameters = parameters;
   }

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("sizeInBit", sizeInBit)
         .append("memory", memory)
         .append("parameters", parameters, true)
         .toString();
   }
}
