package org.selfbus.sbtools.vdviewer.vdx;

/**
 * Types of VDX fields.
 */
public enum VdxFieldType
{
   /**
    * A 4-byte integer.
    */
   INTEGER(1, int.class),

   /**
    * A 2-byte integer.
    */
   SHORT(2, short.class),

   /**
    * A string.
    */
   STRING(3, String.class),

   /**
    * Not sure if this is the correct type. Found e.g. in table
    * "parametercalculation", field "LRTransformation", with length 32768
    */
   LONG_STRING(4, String.class),

   /**
    * A floating point number.
    */
   FLOAT(5, float.class),

   /**
    * A 8-bit integer.
    */
   BYTE(6, short.class),

   /**
    * A byte-array (?)
    * Maybe LONG_STRING and BYTE_ARRAY should be swapped.
    */
   BYTE_ARRAY(8, byte.class, true);

   /**
    * The VD_ type id.
    */
   public final int id;

   /**
    * The corresponding Java class.
    */
   public final Class<?> clazz;

   /**
    * The Java type is an array of {@link #clazz}.
    */
   public final boolean isArray;

   /**
    * Get the field type for the given VD_ type.
    * 
    * @param id - the VD_ type.
    * 
    * @return the field type for the VD_ type id.
    * @throws IllegalArgumentException if the given id is unknown.
    */
   public static VdxFieldType valueOfTypeId(int id)
   {
      for (final VdxFieldType type : values())
         if (type.id == id)
            return type;

      throw new IllegalArgumentException("Invalid VD_ field type " + id);
   }

   /*
    * Internal constructor.
    */
   private VdxFieldType(int id, Class<?> clazz)
   {
      this.id = id;
      this.clazz = clazz;
      this.isArray = false;
   }

   /*
    * Internal constructor.
    */
   private VdxFieldType(int id, Class<?> clazz, boolean isArray)
   {
      this.id = id;
      this.clazz = clazz;
      this.isArray = isArray;
   }
}
