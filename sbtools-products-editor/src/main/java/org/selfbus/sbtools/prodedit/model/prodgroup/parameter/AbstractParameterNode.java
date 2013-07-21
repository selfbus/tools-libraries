package org.selfbus.sbtools.prodedit.model.prodgroup.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.prodedit.model.common.MultiLingualText;
import org.selfbus.sbtools.prodedit.model.interfaces.Identifiable;
import org.selfbus.sbtools.prodedit.model.interfaces.Orderable;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;

import com.jgoodies.binding.beans.Model;

/**
 * An abstract base class for {@link CommunicationObject} and {@link Parameter}.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class AbstractParameterNode extends Model implements Identifiable, Orderable
{
   private static final long serialVersionUID = 8492676566007169620L;

   @XmlAttribute(name = "id", required = true)
   protected int id;

   @XmlElement(name = "description")
   private MultiLingualText description;

   @XmlAttribute(name = "order", required = true)
   private int order;

   @XmlAttribute(name = "parent_id")
   private Integer parentId;

   @XmlAttribute(name = "parent_value")
   private Integer parentValue;

   // The parameter/com-object number is unique for the program and starts with 1
   @XmlAttribute(name = "number")
   private Integer number;

   protected AbstractParameterNode()
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int getId()
   {
      return id;
   }

   /**
    * @return The ID as string.
    */
   public String getIdStr()
   {
      return Integer.toString(id);
   }

   /**
    * Set the ID. Use {@link ProductGroup#getNextUniqueId()} to get a unique ID.
    * 
    * @param id - the ID to set.
    */
   @Override
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the description
    */
   public MultiLingualText getDescription()
   {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(MultiLingualText description)
   {
      this.description = description;
   }

   /**
    * @return the order
    */
   @Override
   public int getOrder()
   {
      return order;
   }

   /**
    * @param order the order to set
    */
   @Override
   public void setOrder(int order)
   {
      this.order = order;
   }

   /**
    * @return the parentId
    */
   public Integer getParentId()
   {
      return parentId;
   }

   /**
    * @param parentId the parentId to set
    */
   public void setParentId(Integer parentId)
   {
      this.parentId = parentId;
   }

   /**
    * @return the parentValue
    */
   public Integer getParentValue()
   {
      return parentValue;
   }

   /**
    * @param parentValue the parentValue to set
    */
   public void setParentValue(Integer parentValue)
   {
      this.parentValue = parentValue;
   }

   /**
    * @return the number
    */
   public Integer getNumber()
   {
      return number;
   }

   /**
    * @param number the number to set
    */
   public void setNumber(Integer number)
   {
      this.number = number;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!getClass().isInstance(o))
         return false;

      final AbstractParameterNode oo = (AbstractParameterNode) o;
      return id == oo.id;
   }
}
