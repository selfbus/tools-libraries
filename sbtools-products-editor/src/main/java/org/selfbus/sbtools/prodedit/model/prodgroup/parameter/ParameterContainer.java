package org.selfbus.sbtools.prodedit.model.prodgroup.parameter;

import com.jgoodies.common.collect.ArrayListModel;

public interface ParameterContainer
{

   /**
    * Add a child parameter or com-object.
    *
    * @param child - the parameter or com-object to add
    */
   public void addChild(AbstractParameterNode child);

   /**
    * @return The child parameters / com-object.
    */
   public ArrayListModel<AbstractParameterNode> getChilds();

   /**
    * @param childs the childs to set
    */
   public void setChilds(ArrayListModel<AbstractParameterNode> childs);

   /**
    * Get a child.
    *
    * @param index - the index of the child to get
    * @return The child
    */
   public AbstractParameterNode getChild(int index);
}
