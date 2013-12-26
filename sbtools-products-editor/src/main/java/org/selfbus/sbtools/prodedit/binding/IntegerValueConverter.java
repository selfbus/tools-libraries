package org.selfbus.sbtools.prodedit.binding;

import com.jgoodies.binding.value.BindingConverter;

/**
 * A converter that converts to/from an integer value.
 */
public class IntegerValueConverter implements BindingConverter
{
//   private static final Logger LOGGER = LoggerFactory.getLogger(IntegerValueConverter.class);

   private final int radix;
   
   /**
    * Create an integer value converter for decimal values.
    */
   public IntegerValueConverter()
   {
      this(10);
   }

   /**
    * Create an integer value converter.
    * 
    * @param radix - the radix of the values.
    */
   public IntegerValueConverter(int radix)
   {
      this.radix = radix;
   }
   
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
         return Integer.toString((Integer) sourceValue, radix);
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
         return Integer.parseInt((String) targetValue, radix);
      }
      catch (NumberFormatException e)
      {
         return null;
      }
   }
}
