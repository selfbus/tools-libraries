package org.selfbus.sbtools.common.gui.utils;

import java.awt.Component;
import java.awt.Container;

/**
 * Utility functions for general swing components.
 */
public final class SwingUtils
{
   /**
    * Recursively enable or disable a container and its children.
    *
    * @param container - the container to process.
    * @param enabled - set enable of the components.
    */
   public static void setEnable(final Container container, boolean enabled)
   {
      container.setEnabled(enabled);

      for (final Component comp: container.getComponents())
      {
         if (comp instanceof Container)
            setEnable((Container) comp, enabled);
         else comp.setEnabled(enabled);
      }
   }
}
