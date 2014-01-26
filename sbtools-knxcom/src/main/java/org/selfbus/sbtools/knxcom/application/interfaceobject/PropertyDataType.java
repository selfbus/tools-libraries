package org.selfbus.sbtools.knxcom.application.interfaceobject;

/**
 * Data type of an interface object property.
 */
public enum PropertyDataType
{
   /**
    * Load control.
    */
   PDT_CONTROL,

   /**
    * Character.
    */
   PDT_CHAR,

   /**
    * Unsigned character.
    */
   PDT_UNSIGNED_CHAR,

   PDT_INT,
   PDT_UNSIGNED_INT,
   PDT_EIB_FLOAT,
   PDT_DATE,
   PDT_TIME,
   PDT_LONG,
   PDT_UNSIGNED_LONG,
   PDT_FLOAT,
   PDT_DOUBLE,
   PDT_CHAR_BLOCK,
   PDT_POLL_GROUP_SETTING,
   PDT_SHORT_CHAR_BLOCK,

   PDT_DATE_TIME,
   PDT_VARIABLE_LENGTH,

   PDT_GENERIC_01,
   PDT_GENERIC_02,
   PDT_GENERIC_03,
   PDT_GENERIC_04,
   PDT_GENERIC_05,
   PDT_GENERIC_06,
   PDT_GENERIC_07,
   PDT_GENERIC_08,
   PDT_GENERIC_09,
   PDT_GENERIC_10,
   PDT_GENERIC_11,
   PDT_GENERIC_12,
   PDT_GENERIC_13,
   PDT_GENERIC_14,
   PDT_GENERIC_15,
   PDT_GENERIC_16,
   PDT_GENERIC_17,
   PDT_GENERIC_18,
   PDT_GENERIC_19,
   PDT_GENERIC_20,

   PDT_RESERVED_25,
   PDT_RESERVED_26,
   PDT_RESERVED_27,
   PDT_RESERVED_28,
   PDT_RESERVED_29,
   PDT_RESERVED_2A,
   PDT_RESERVED_2B,
   PDT_RESERVED_2C,
   PDT_RESERVED_2D,
   PDT_RESERVED_2E,

   PDT_UTF_8,
   PDT_VERSION,
   PDT_ALARM_INFO,
   PDT_BINARY_INFO,
   PDT_BITSET_8,
   PDT_BITSET_16,
   PDT_ENUM_8,
   PDT_SCALING,

   PDT_RESERVED_37,
   PDT_RESERVED_38,
   PDT_RESERVED_39,
   PDT_RESERVED_3A,
   PDT_RESERVED_3B,

   PDT_NE_VL,
   PDT_NE_FL,
   PDT_FUNCTION,
   PDT_ESCAPE
   ;

   /**
    * Get a property type by number.
    */
   public static PropertyDataType valueOf(int ordinal)
   {
      for (PropertyDataType t : values())
      {
         if (t.ordinal() == ordinal)
            return t;
      }

      return null;
   }
}
