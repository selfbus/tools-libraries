package org.selfbus.sbtools.common.gui.components;

import javax.swing.JTextField;

import org.selfbus.sbtools.common.address.Address;
import org.selfbus.sbtools.common.address.GroupAddress;
import org.selfbus.sbtools.common.address.PhysicalAddress;

/**
 * An input field for entering a {@link PhysicalAddress physical address} or a
 * {@link GroupAddress group address}.
 */
public class AddressField extends JTextField
{
   private static final long serialVersionUID = -1228199654641373414L;

   /**
    * Allow group address input only.
    */
   public final static int GROUP = 1;

   /**
    * Allow physical address input only.
    */
   public final static int PHYSICAL = 2;

   /**
    * All group and physical address input.
    */
   public final static int ANY = 3;

   private final int type;

   /**
    * Create an address input field that allows group and physical
    * addresses to be entered.
    */
   public AddressField()
   {
      this(ANY);
   }

   /**
    * Create an address input field.
    *
    * @param type - the type of the address that is allowed. Can be
    *           {@link #GROUP}, {@link #PHYSICAL}, or {@link #ANY}.
    */
   public AddressField(int type)
   {
      super(9);
      this.type = type;
   }

   /**
    * Set the address.
    *
    * @param addr - the address to set.
    */
   public void setAddress(Address addr)
   {
      if (addr == null)
         setText("");
      else setText(addr.toString());
   }

   /**
    * @return the address that the field contains.
    */
   public Address getAddress()
   {
      final String str = getText().trim();

      if (str.isEmpty())
         return null;

      int type = this.type;
      if (type == ANY)
         type = str.indexOf('/') >= 0 ? GROUP : PHYSICAL;

      if (type == GROUP)
         return GroupAddress.valueOf(str);
      else if (type == PHYSICAL)
         return PhysicalAddress.valueOf(str);

      return null;
   }
}
