package org.selfbus.sbtools.prodedit.binding;

import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterType;

import com.jgoodies.binding.value.BindingConverter;

/**
 * A converter that converts a {@link ParameterType} to the parameter type's
 * bit size.
 */
public class ParameterTypeSizeConverter implements BindingConverter
{
   private final String fmtBits = I18n.getMessage("ParameterTypeSizeConverter.bits");
   private final String fmtBytes = I18n.getMessage("ParameterTypeSizeConverter.bytes");

   /**
    * {@inheritDoc}
    */
   @Override
   public String targetValue(Object sourceValue)
   {
      if (sourceValue instanceof ParameterType)
      {
         ParameterType paramType = (ParameterType) sourceValue;
         int size = paramType.getSize();

         if (size > 0 && (size & 7) == 0)
            return String.format(fmtBytes, size >> 3);
         return String.format(fmtBits, size);
      }
      return "";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Object sourceValue(Object targetValue)
   {
      return null;
   }
}
