package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

import com.jgoodies.common.collect.ArrayListModel;

/**
 * A table of communication objects.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ComObjectTable
{
   @XmlAttribute(name = "CodeSegment")
   private String codeSegment;

   @XmlAttribute(name = "Offset")
   private Integer offset;

   @XmlElement(name = "ComObject", namespace = Namespaces.KNX)
   private ArrayListModel<ComObject> comObjects;

   public String getCodeSegment()
   {
      return codeSegment;
   }

   public void setCodeSegment(String codeSegment)
   {
      this.codeSegment = codeSegment;
   }

   public Integer getOffset()
   {
      return offset;
   }

   public void setOffset(Integer offset)
   {
      this.offset = offset;
   }

   public ArrayListModel<ComObject> getComObjects()
   {
      return comObjects;
   }

   public void setComObjects(ArrayListModel<ComObject> comObjects)
   {
      this.comObjects = comObjects;
   }

   @Override
   public String toString()
   {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         .append("codeSegment", codeSegment)
         .append("offset", offset)
         .append("comObjects", comObjects, false)
         .toString();
   }
}
