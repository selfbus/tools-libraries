package org.selfbus.sbtools.prodedit.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.binding.value.BindingConverter;

/**
 * A converter that converts to/from an integer value.
 */
public class IntegerValueConverter implements BindingConverter
{
   private static final Logger LOGGER = LoggerFactory.getLogger(IntegerValueConverter.class);

   /**
    * {@inheritDoc}
    */
   @Override
   public Object targetValue(Object sourceValue)
   {
      if (sourceValue == null)
         return "";

      try
      {
         return Integer.toString((Integer) sourceValue);
      }
      catch (Exception e)
      {
         return "";
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Object sourceValue(Object targetValue)
   {
      if (targetValue == null || ((String) targetValue).isEmpty())
         return null;

      try
      {
         return Integer.parseInt((String) targetValue);
      }
      catch (NumberFormatException e)
      {
         LOGGER.info("Cannot convert {} to integer", targetValue);
         return null;
      }
   }
}
