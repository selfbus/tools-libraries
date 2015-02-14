package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * Parameter data type: enumeration
 */
public class TypeRestriction extends AbstractType
{
   @XmlAttribute(name = "Base")
   private String base;

   @XmlAttribute(name = "SizeInBit")
   private Integer sizeInBit;

   @XmlElement(name = "Enumeration", namespace = Namespaces.KNX)
   private List<Enumeration> enumerations;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

   /**
    * An enumeration value of a {@link TypeRestriction restriction type}.
    */
   public static class Enumeration extends Identified
   {
      @XmlAttribute(name = "Text")
      private String text;

      @XmlAttribute(name = "DisplayOrder")
      private int displayOrder;

      @XmlAttribute(name = "Value")
      private Integer value;

      @Override
      public String toString()
      {
         return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
      }
   }
}
