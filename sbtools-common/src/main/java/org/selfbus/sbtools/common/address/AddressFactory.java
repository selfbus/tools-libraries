package org.selfbus.sbtools.common.address;

/**
 * Factory for creating KNX/EIB bus address objects.
 */
public final class AddressFactory
{
   /**
    * Create an address from a string. This can be a {@link GroupAddress group
    * address}, a {@link PhysicalAddress physical address}, or null if the
    * string is null or empty.
    *
    * @param str - the string to process.
    *
    * @return an address or null if the string was empty or null.
    */
   static public Address createAddress(String str)
   {
      if (str == null)
         return null;

      str = str.trim();
      if (str.isEmpty())
         return null;

      if (str.indexOf('/') > 0)
         return GroupAddress.valueOf(str);

      return PhysicalAddress.valueOf(str);
   }

   /**
    * Disabled constructor.
    */
   private AddressFactory()
   {
   }
}
