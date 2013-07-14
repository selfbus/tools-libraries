package org.selfbus.sbtools.vdio;

import org.xml.sax.Attributes;
import org.xml.sax.ext.Attributes2Impl;

/**
 * An attributes wrapper.
 */
public class VdioAttributes extends Attributes2Impl
{
   private int lastIndex;

   public VdioAttributes()
   {
      super();
   }

   public VdioAttributes(Attributes atts)
   {
      super(atts);
   }

   public String getValue(int index)
   {
      lastIndex = index;
      return super.getValue(index);
   }

   /**
    * @return The last index that was queried with {@link #getValue(int)}.
    */
   public int getLastIndex()
   {
      return lastIndex;
   }
}
