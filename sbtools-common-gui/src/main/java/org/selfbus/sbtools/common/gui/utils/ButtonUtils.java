package org.selfbus.sbtools.common.gui.utils;

import javax.swing.AbstractButton;

/**
 * Utility functions for buttons.
 */
public final class ButtonUtils
{
   /**
    * Set the properties of the abstract button btn to match the tool-bar
    * buttons.
    *
    * @param button - the button to process.
    */
   public static void setToolButtonProperties(AbstractButton button)
   {
      button.setFocusable(false);
      button.setBorderPainted(false);
      button.setOpaque(false);
   }
}
