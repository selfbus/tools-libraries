package org.selfbus.sbtools.knxio.model.manufacturer.program;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.manufacturer.program.comobject.ComObjectRef;
import org.selfbus.sbtools.knxio.model.manufacturer.program.comobject.ComObjectTable;
import org.selfbus.sbtools.knxio.model.manufacturer.program.memory.AbsoluteSegment;
import org.selfbus.sbtools.knxio.model.manufacturer.program.memory.AbstractSegment;
import org.selfbus.sbtools.knxio.model.manufacturer.program.parameter.Parameter;
import org.selfbus.sbtools.knxio.model.manufacturer.program.parameter.ParameterElement;
import org.selfbus.sbtools.knxio.model.manufacturer.program.parameter.ParameterRef;
import org.selfbus.sbtools.knxio.model.manufacturer.program.parameter.ParameterType;
import org.selfbus.sbtools.knxio.model.manufacturer.program.parameter.Union;

/**
 * Static part of an application program.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class StaticPart
{
   @XmlElementWrapper(name = "Code", namespace = Namespaces.KNX)
   @XmlElements(value = {
      @XmlElement(name = "AbsoluteSegment", namespace = Namespaces.KNX, type = AbsoluteSegment.class)
      })
   private List<AbstractSegment> codeSegments;

   @XmlElementWrapper(name = "ParameterTypes", namespace = Namespaces.KNX)
   @XmlElement(name = "ParameterType", namespace = Namespaces.KNX)
   private List<ParameterType> parameterTypes;

   @XmlElementWrapper(name = "Parameters", namespace = Namespaces.KNX)
   @XmlElements(value = {
      @XmlElement(name = "Union", namespace = Namespaces.KNX, type = Union.class),
      @XmlElement(name = "Parameter", namespace = Namespaces.KNX, type = Parameter.class),
      })
   private List<ParameterElement> parameters;

   @XmlElementWrapper(name = "ParameterRefs", namespace = Namespaces.KNX)
   @XmlElement(name = "ParameterRef", namespace = Namespaces.KNX)
   private List<ParameterRef> parameterRefs;

   @XmlElement(name = "ComObjectTable", namespace = Namespaces.KNX)
   private ComObjectTable comObjectTable;

   @XmlElementWrapper(name = "ComObjectRefs", namespace = Namespaces.KNX)
   @XmlElement(name = "ComObjectRef", namespace = Namespaces.KNX)
   private List<ComObjectRef> comObjectRefs;

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
         .append("codeSegments", codeSegments, false)
         .append("parameterTypes", parameterTypes, false)
         .append("parameters", parameters, false)
         .append("parameterRefs", parameterRefs, false)
         .append("comObjectTable", comObjectTable)
         .append("comObjectRefs", comObjectRefs, false)
         .toString();
   }
}
