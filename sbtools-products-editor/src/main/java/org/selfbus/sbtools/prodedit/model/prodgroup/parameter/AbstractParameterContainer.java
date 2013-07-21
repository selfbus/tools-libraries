package org.selfbus.sbtools.prodedit.model.prodgroup.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.jgoodies.common.collect.ArrayListModel;

@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
@XmlSeeAlso({ Parameter.class, CommunicationObject.class })
public class AbstractParameterContainer extends AbstractParameterNode implements ParameterContainer
{
   private static final long serialVersionUID = 4327394838211456868L;

   //@XmlMixed
   @XmlElementWrapper(name = "childs")
   @XmlElementRefs({
      @XmlElementRef(name = "parameter", type = Parameter.class),
      @XmlElementRef(name = "communication_object", type = CommunicationObject.class)
   })
   private ArrayListModel<AbstractParameterNode> childs;

   protected AbstractParameterContainer()
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
