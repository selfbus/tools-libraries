package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.common.exception.SbToolsRuntimeException;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.BitSized;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.common.Named;

/**
 * A parameter type.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterType extends Identified implements Named
{
   private static final long serialVersionUID = -4167679233410538005L;

   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "InternalDescription")
   private String internalDescription;

   @XmlElements(value = {
      @XmlElement(name = "TypeRestriction", namespace = Namespaces.KNX, type = TypeRestriction.class),
      @XmlElement(name = "TypeNumber", namespace = Namespaces.KNX, type = TypeNumber.class),
      @XmlElement(name = "TypeNone", namespace = Namespaces.KNX, type = TypeNone.class),
      @XmlElement(name = "TypeText", namespace = Namespaces.KNX, type = TypeText.class)
      })
   private AbstractType type;

   @Override
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getInternalDescription()
   {
      return internalDescription;
   }

   public void setInternalDescription(String internalDescription)
   {
      this.internalDescription = internalDescription;
   }

   public AbstractType getType()
   {
      return type;
   }

   public void setType(AbstractType type)
   {
      this.type = type;
   }

   /**
    * @return The parameter's atomic type.
    */
   public ParameterAtomicType getAtomicType()
   {
      return type == null ? null : type.getAtomicType();
   }

   /**
    * Set the parameter's atomic type. This replaces the {@link #getType() type} object
    * with a new, empty object of the corresponding type.
    *
    * @param atomicType The atomic type to set
    */
   public void setAtomicType(ParameterAtomicType atomicType)
   {
      if (atomicType == getAtomicType())
         return;

      AbstractType oldType = type;

      if (atomicType == null)
      {
         type = null;
      }
      else
      {
         try
         {
            type = atomicType.getTypeClass().newInstance();
         }
         catch (InstantiationException | IllegalAccessException e)
         {
            throw new SbToolsRuntimeException("cannot create parameter-type type-object", e);
         }

         if (type instanceof BitSized && oldType instanceof BitSized)
            ((BitSized) type).setSizeInBit(((BitSized) oldType).getSizeInBit());
      }

      firePropertyChange("atomicType",
         oldType == null ? null : oldType.getAtomicType(), type == null ? null : type.getAtomicType());
      firePropertyChange("type", oldType, type);
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
