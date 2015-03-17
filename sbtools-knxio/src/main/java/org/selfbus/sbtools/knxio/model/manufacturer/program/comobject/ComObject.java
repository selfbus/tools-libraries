package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.ObjectPriority;

/**
 * A communication object.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ComObject extends AbstractComObject
{
   private static final long serialVersionUID = 7493578424294404383L;

   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "Number")
   private Integer number;

   @XmlAttribute(name = "ObjectSize")
   private ComObjectSize size;

   @XmlAttribute(name = "Priority")
   private ObjectPriority priority = ObjectPriority.LOW;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Integer getNumber()
   {
      return number;
   }

   public void setNumber(Integer number)
   {
      this.number = number;
   }

   public ComObjectSize getSize()
   {
      return size;
   }

   public void setSize(ComObjectSize size)
   {
      this.size = size;
   }

   public ObjectPriority getPriority()
   {
      return priority;
   }

   public void setPriority(ObjectPriority priority)
   {
      this.priority = priority;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
