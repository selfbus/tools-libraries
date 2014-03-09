package org.selfbus.sbtools.common.gui.tree;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * A {@link DefaultTreeCellRenderer tree cell renderer} that renders the
 * icons of {@link MutableIconTreeNode}s. 
 */
public class IconTreeCellRenderer extends DefaultTreeCellRenderer
{
   private static final long serialVersionUID = 3951117763273665165L;

   /**
    * {@inheritDoc}
    */
   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
         int row, boolean hasFocus)
   {
      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

      if (value instanceof MutableIconTreeNode)
      {
         final Icon icon = ((MutableIconTreeNode) value).getIcon();
         setIcon(icon);
      }

      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Color getBorderSelectionColor()
   {
      return getBackgroundSelectionColor();
   }
}
