package org.selfbus.sbtools.knxio.model.common;

/**
 * Interface for classes that have a size in bit.
 */
public interface BitSized
{
   /**
    * The standard bit sizes.
    */
   public static final Integer[] STANDARD_SIZES = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 48, 64, 80, 112 };

   /**
    * @return The size in bit.
    */
   Integer getSizeInBit();

   /**
    * Set the size in bit.
    *
    * @param size The size to set
    */
   void setSizeInBit(Integer size);
}
