package org.selfbus.sbtools.prodedit.binding;

import org.selfbus.sbtools.prodedit.model.enums.ObjectType;

import com.jgoodies.binding.value.BindingConverter;

/**
 * A converter that converts between the numeric ID in the model and 
 * the {@link ObjectType}.
 */
public class ObjectTypeConverter implements BindingConverter
{
   /**
    * {@inheritDoc}
    */
   @Override
   public Object targetValue(Object sourceValue)
   {
      if (sourceValue == null)
         return null;

      return ((ObjectType) sourceValue).getLabel();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Object sourceValue(Object targetValue)
   {
      return targetValue;
   }
}
