package org.selfbus.sbtools.prodedit.model.prodgroup.parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.jgoodies.common.collect.ArrayListModel;

public abstract class AbstractParameterContainer extends AbstractParameterNode implements ParameterContainer
{
   private static final long serialVersionUID = 4327394838211456868L;

   @XmlElementWrapper(name = "children")
   @XmlElement(name = "parameter")
   private ArrayListModel<AbstractParameterNode> childs;

   public AbstractParameterContainer()
   {
      super();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void addChild(AbstractParameterNode child)
   {
      if (childs == null)
         childs = new ArrayListModel<AbstractParameterNode>();
   
      childs.add(child);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public AbstractParameterNode getChild(int index)
   {
      if (childs == null)
         return null;
   
      return childs.get(index);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ArrayListModel<AbstractParameterNode> getChilds()
   {
      if (childs == null)
         childs = new ArrayListModel<AbstractParameterNode>();
   
      return childs;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setChilds(ArrayListModel<AbstractParameterNode> childs)
   {
      this.childs = childs;
   }

   /**
    * Remove a child.
    * 
    * @param child - the child to remove
    */
   public void removeChild(AbstractParameterNode child)
   {
      childs.remove(child);
   }

   /**
    * Remove all children.
    */
   public void removeChilds()
   {
      childs.clear();
   }
}
