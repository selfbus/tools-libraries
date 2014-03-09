package org.selfbus.sbtools.common.gui.utils;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Utility methods for {@link JTree} trees.
 */
public final class TreeUtils
{
   /**
    * Expand all nodes of a tree.
    * 
    * @param tree - the tree to process.
    */
   public static void expandAll(JTree tree)
   {
      TreeNode root = (TreeNode) tree.getModel().getRoot();

      // Traverse tree from root
      expandOrCollapse(tree, new TreePath(root), true);
   }

   /**
    * Collapse all nodes of a tree.
    * 
    * @param tree - the tree to process.
    */
   public static void collapseAll(JTree tree)
   {
      TreeNode root = (TreeNode) tree.getModel().getRoot();

      // Traverse tree from root
      expandOrCollapse(tree, new TreePath(root), false);
   }

   /**
    * Internal worker method.
    * 
    * @param tree - the tree to process.
    * @param parent - the parent tree path.
    * @param expand - true if expand, false if collapse.
    */
   private static void expandOrCollapse(JTree tree, TreePath parent, boolean expand)
   {
      // Traverse children
      TreeNode node = (TreeNode) parent.getLastPathComponent();
      if (node.getChildCount() >= 0)
      {
         for (Enumeration<?> e = node.children(); e.hasMoreElements();)
         {
            TreeNode n = (TreeNode) e.nextElement();
            TreePath path = parent.pathByAddingChild(n);
            expandOrCollapse(tree, path, expand);
         }
      }

      // Expansion or collapse must be done bottom-up
      if (expand) tree.expandPath(parent);
      else tree.collapsePath(parent);
   }

   /**
    * Create a list of all children (and their children) of the given tree node.
    * 
    * @param node - the node who'se children are collected.
    * @return a list of all children of the given tree node.
    */
   public static List<TreeNode> getChildTreeNodes(TreeNode node)
   {
      final List<TreeNode> childs = new LinkedList<TreeNode>();
      collectTreeNodeChildren(node, childs);
      return childs;
   }

   /**
    * Internal worker of {@link getTreeNodeChildren}.
    * 
    * @param node - the tree node to process.
    * @param result - the list for the nodes.
    */
   @SuppressWarnings("unchecked")
   private static void collectTreeNodeChildren(TreeNode node, List<TreeNode> result)
   {
      final Enumeration<TreeNode> childs = node.children();

      while (childs.hasMoreElements())
      {
         final TreeNode child = childs.nextElement();
         result.add(child);
         collectTreeNodeChildren(child, result);
      }
   }
}
