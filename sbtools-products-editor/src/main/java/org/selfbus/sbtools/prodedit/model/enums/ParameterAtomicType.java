package org.selfbus.sbtools.prodedit.model.enums;

import org.selfbus.sbtools.prodedit.internal.I18n;

/**
 * The atomic type of a parameter: none, unsigned, signed, string, enum, long enum.
 */
public enum ParameterAtomicType
{
   /**
    * No parameter type. Used for labels and pages in the parameter editor.
    */
   NONE(' ', null),

   /**
    * Unsigned integer.
    */
   UNSIGNED('+', Integer.class),

   /**
    * Signed integer.
    */
   SIGNED('-', Integer.class),

   /**
    * String.
    */
   STRING('$', String.class),

   /**
    * Enumeration.
    */
   ENUM('Y', Integer.class),

   /**
    * Long enumeration.
    */
   LONG_ENUM('Z', Integer.class);

   private final char dispAttr;
   private final Class<?> parameterClass;

   /*
    * Internal constructor.
    */
   private ParameterAtomicType(char dispAttr, Class<?> parameterClass)
   {
      this.dispAttr = dispAttr;
      this.parameterClass = parameterClass;
   }

   /**
    * @return the display-attribute character.
    */
   public char getDispAttr()
   {
      return dispAttr;
   }

   /**
    * @return the translated name of the type.
    */
   public String getLabel()
   {
      return I18n.getMessage("ParameterAtomicType." + name());
   }

   /**
    * @return the class that is used for parameters of this type.
    */
   public Class<?> getParameterClass()
   {
      return parameterClass;
   }

   /**
    * Get the {@link ParameterAtomicType} for a specific ordinal.
    * 
    * @param ordinal - the ordinal to lookup.
    * 
    * @return the object for the given ordinal, or null if the ordinal
    *         is unknown.
    */
   public static ParameterAtomicType valueOf(int ordinal)
   {
      for (ParameterAtomicType o : values())
         if (o.ordinal() == ordinal)
            return o;
      return null;
   }
}
