package org.selfbus.sbtools.vdio.internal;

/**
 * Type of a VD table field.
 */
public enum TableFieldType
{
   /**
    * No type.
    */
   NONE(null),

   /**
    * A 4 byte number.
    */
   INTEGER("xs:int"),

   /**
    * A 2 byte number.
    */
   SHORT("xs:int"),

   /**
    * A string.
    */
   STRING("xs:string"),

   /**
    * Unknown.
    */
   UNKNOWN1(null),

   /**
    * A floating point number.
    */
   FLOAT("xs:double"),

   /**
    * Unknown.
    */
   UNKNOWN2(null),

   /**
    * Unknown.
    */
   UNKNOWN3(null),

   /**
    * Hex encoded binary data.
    */
   HEX_BINARY("xs:hexBinary");
   

   /**
    * The XML schema name of the type.
    */
   public final String xsType;

   /**
    * Get the type from the ordinal.
    * 
    * @param ordinal - the ordinal
    * @return The type, null if not found
    */
   public static TableFieldType valueOf(int ordinal)
   {
      for (TableFieldType t : values())
      {
         if (t.ordinal() == ordinal)
            return t;
      }

      return null;
   }

   private TableFieldType(String xsType)
   {
      this.xsType = xsType;
   }
}
