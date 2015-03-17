package org.selfbus.sbtools.knxio.model.manufacturer.program;

import java.util.Comparator;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.common.exception.SbToolsRuntimeException;
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

import com.jgoodies.common.collect.ArrayListModel;

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
   private ArrayListModel<ParameterType> parameterTypes;

   @XmlTransient
   private ArrayListModel<Parameter> parameters;

   @XmlElementWrapper(name = "ParameterRefs", namespace = Namespaces.KNX)
   @XmlElement(name = "ParameterRef", namespace = Namespaces.KNX)
   private List<ParameterRef> parameterRefs;

   @XmlElement(name = "ComObjectTable", namespace = Namespaces.KNX)
   private ComObjectTable comObjectTable;

   @XmlElementWrapper(name = "ComObjectRefs", namespace = Namespaces.KNX)
   @XmlElement(name = "ComObjectRef", namespace = Namespaces.KNX)
   private List<ComObjectRef> comObjectRefs;

   @XmlElementWrapper(name = "Parameters", namespace = Namespaces.KNX)
   @XmlElements(value = {
      @XmlElement(name = "Union", namespace = Namespaces.KNX, type = Union.class),
      @XmlElement(name = "Parameter", namespace = Namespaces.KNX, type = Parameter.class),
      })
   private List<ParameterElement> parameterElements;

   public List<AbstractSegment> getCodeSegments()
   {
      return codeSegments;
   }

   public void setCodeSegments(List<AbstractSegment> codeSegments)
   {
      this.codeSegments = codeSegments;
   }

   public ArrayListModel<ParameterType> getParameterTypes()
   {
      return parameterTypes;
   }

   public void setParameterTypes(ArrayListModel<ParameterType> parameterTypes)
   {
      this.parameterTypes = parameterTypes;
   }

   public ArrayListModel<Parameter> getParameters()
   {
      return parameters;
   }

   public void setParameters(ArrayListModel<Parameter> parameters)
   {
      this.parameters = parameters;
   }

   public List<ParameterRef> getParameterRefs()
   {
      return parameterRefs;
   }

   public void setParameterRefs(List<ParameterRef> parameterRefs)
   {
      this.parameterRefs = parameterRefs;
   }

   public ComObjectTable getComObjectTable()
   {
      return comObjectTable;
   }

   public void setComObjectTable(ComObjectTable comObjectTable)
   {
      this.comObjectTable = comObjectTable;
   }

   public List<ComObjectRef> getComObjectRefs()
   {
      return comObjectRefs;
   }

   public void setComObjectRefs(List<ComObjectRef> comObjectRefs)
   {
      this.comObjectRefs = comObjectRefs;
   }

   void afterUnmarshal(Unmarshaller u, Object parent)
   {
      if (parameterElements == null)
      {
         this.parameters = null;
         return;
      }

      ArrayListModel<Parameter> newParameters = new ArrayListModel<Parameter>();
      for (ParameterElement paramElem : parameterElements)
      {
         if (paramElem instanceof Parameter)
         {
            newParameters.add((Parameter) paramElem);
         }
         else if (paramElem instanceof Union)
         {
            Union union = (Union) paramElem;
            for (Parameter param : union.getParameters())
            {
               param.setMemory(union.getMemory());
               newParameters.add(param);
            }
         }
         else throw new SbToolsRuntimeException("unsupported parameter type: " + paramElem.getClass());
      }

      newParameters.sort(new Comparator<Parameter>()
      {
         @Override
         public int compare(Parameter o1, Parameter o2)
         {
            return o1.getName().compareTo(o2.getName());
         }
      });

      this.parameters = newParameters;
   }

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
