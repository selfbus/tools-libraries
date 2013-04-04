package org.selfbus.sbtools.common.gui.tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A {@link DefaultMutableTreeNode mutable tree node} that can have an icon.
 * Requires that the tree uses the {@link IconTreeCellRenderer} for rendering
 * the nodes.
 * 
 * @see IconTreeCellRenderer
 */
public class MutableIconTreeNode extends DefaultMutableTreeNode
{
   private static final long serialVersionUID = -8466796438084299137L;

   private Icon icon;

   /**
    * Creates a tree node that has no parent and no children, but which allows
    * children.
    */
   public MutableIconTreeNode()
   {
   }

   /**
    * Creates a tree node with no parent, no children, but which allows
    * children, and initializes it with the specified user object.
    *
    * @param userObject an Object provided by the user that constitutes
    *                   the node's data
    */
   public MutableIconTreeNode(Object userObject)
   {
      super(userObject);
   }

   /**
    * Creates a tree node with no parent, no children, initialized with
    * the specified user object, and that allows children only if
    * specified.
    *
    * @param userObject an Object provided by the user that constitutes
    *        the node's data
    * @param allowsChildren if true, the node is allowed to have child
    *        nodes -- otherwise, it is always a leaf node
    */
   public MutableIconTreeNode(Object userObject, boolean allowsChildren)
   {
      super(userObject, allowsChildren);
   }

   /**
    * @return The icon of the tree node.
    */
   public Icon getIcon()
   {
      return icon;
   }

   /**
    * Set the icon of the tree node.
    *
    * @param icon - the icon to set
    */
   public void setIcon(Icon icon)
   {
      this.icon = icon;
   }
}
