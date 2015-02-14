package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.procedure.AbstractProcedure;

/**
 * A procedure.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class Procedure extends AbstractProcedure
{
   @XmlAttribute(name = "ProcedureType")
   private ProcedureType type;

   @XmlAttribute(name = "ProcedureSubType")
   private String subType;

   @XmlAttribute(name = "Access")
   private String access;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
