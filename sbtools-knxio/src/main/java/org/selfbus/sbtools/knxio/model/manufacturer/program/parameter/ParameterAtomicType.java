package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * The atomic type of a parameter: none, unsigned, signed, string, enum, long enum.
 */
@XmlType
@XmlEnum
public enum ParameterAtomicType
{
   /**
    * No parameter type. Used for labels and pages in the parameter editor.
    */
   NONE(TypeNone.class),

   /**
    * Enumeration.
    */
   RESTRICTION(TypeRestriction.class),

   /**
    * Integer.
    */
   NUMBER(TypeNumber.class),

   /**
    * Text string.
    */
   TEXT(TypeText.class);

   private final Class<? extends AbstractType> typeClass;

   /*
    * Internal constructor.
    */
   private ParameterAtomicType(Class<? extends AbstractType> typeClass)
   {
      this.typeClass = typeClass;
   }

   /**
    * @return the translated name of the type.
    */
   public String getLabel()
   {
      return I18n.getMessage("ParameterAtomicType." + this.name());
   }

   /**
    * @return The class that is used for the parameter type
    */
   public Class<? extends AbstractType> getTypeClass()
   {
      return typeClass;
   }
}
