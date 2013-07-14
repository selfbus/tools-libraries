package org.selfbus.sbtools.prodedit.model.prodgroup.parameter;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;

/**
 * Tree model for {@link Parameter}s and {@link CommunicationObject}s of an
 * {@link ApplicationProgram}.
 */
@XmlType(name = "param_tree", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterTreeModel implements TreeModel
{
   @XmlElement(name = "root")
   private ParameterRootNode root = new ParameterRootNode();

   public ParameterTreeModel()
   {
   }

   @Override
   public ParameterRootNode getRoot()
   {
      return root;
   }

   @Override
   public AbstractParameterNode getChild(Object parent, int index)
   {
      ParameterContainer parentCont = (ParameterContainer) parent;
      return parentCont.getChild(index);
   }

   @Override
   public int getChildCount(Object parent)
   {
      if (parent instanceof ParameterContainer)
      {
         ParameterContainer parentCont = (ParameterContainer) parent;
         return parentCont.getChilds().size();
      }

      return 0;
   }

   @Override
   public boolean isLeaf(Object node)
   {
      return !(node instanceof ParameterContainer) || ((ParameterContainer) node).getChilds().isEmpty();
   }

   @Override
   public void valueForPathChanged(TreePath path, Object newValue)
   {
      // TODO Auto-generated method stub
   }

   @Override
   public int getIndexOfChild(Object parent, Object child)
   {
      if (parent == null || child == null)
         return -1;

      ParameterContainer parentCont = (ParameterContainer) parent;
      for (int index = parentCont.getChilds().size() - 1; index >= 0; --index)
      {
         if (parentCont.getChild(index) == child)
            return index;
      }

      return -1;
   }

   /**
    * Recursively search the tree for a parameter with the given ID.
    * 
    * @param id - the ID of the parameter to find.
    * 
    * @return The parameter, or null if not found.
    */
   public AbstractParameterNode findById(int id)
   {
      return findById(root, id);
   }

   /**
    * Recursively search the tree for a parameter with the given ID. The search is started with the
    * children of the given parent parameter.
    * 
    * @param parent - search the parent node and it's children
    * @param id - the ID of the parameter to find.
    * 
    * @return The parameter, or null if not found.
    */
   public AbstractParameterNode findById(ParameterContainer parent, int id)
   {
      for (AbstractParameterNode node : parent.getChilds())
      {
         if (node.id == id)
            return node;

         if (node instanceof ParameterContainer)
         {
            node = findById((ParameterContainer) node, id);
            if (node != null)
               return node;
         }
      }

      return null;
   }

   @Override
   public void addTreeModelListener(TreeModelListener l)
   {
//      Validate.isTrue(false, "not implemented");
   }

   @Override
   public void removeTreeModelListener(TreeModelListener l)
   {
//      Validate.isTrue(false, "not implemented");
   }
}
