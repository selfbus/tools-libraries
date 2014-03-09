package org.selfbus.sbtools.common.gui.tree;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * A {@link DefaultTreeCellRenderer tree cell renderer} that allows to set icons
 * for the cell items depending on the class of the cell items.
 */
public class DynamicIconTreeCellRenderer extends DefaultTreeCellRenderer
{
   private static final long serialVersionUID = 5829496396962225814L;
   private final Map<Class<?>,Icon> icons = new HashMap<Class<?>,Icon>();

   /**
    * {@inheritDoc}
    */
   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
         int row, boolean hasFocus)
   {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      if (value instanceof DefaultMutableTreeNode)
      {
         final Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
         if (userObject != null && icons.containsKey(userObject.getClass()))
            setIcon(icons.get(userObject.getClass()));
      }

      return this;
   }

   /**
    * Set the icon that is shown for items of the class <code>clazz</code>.
    * Previously set icons for the same class are overwritten.
    *
    * @param clazz - the class for which the icon is set.
    * @param icon - the icon to set.
    */
   public void setCellTypeIcon(final Class<?> clazz, final Icon icon)
   {
      icons.put(clazz, icon);
   }
}
