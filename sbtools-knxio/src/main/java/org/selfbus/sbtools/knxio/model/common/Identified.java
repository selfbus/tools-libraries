package org.selfbus.sbtools.knxio.model.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.jgoodies.binding.beans.Model;

/**
 * Base class for items that have an Id.
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class Identified extends Model
{
   private static final long serialVersionUID = -6462939117407855180L;

   @XmlAttribute(name = "Id", required = true)
   @XmlID
   protected String id;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("id", id)
         .toString();
   }
}
