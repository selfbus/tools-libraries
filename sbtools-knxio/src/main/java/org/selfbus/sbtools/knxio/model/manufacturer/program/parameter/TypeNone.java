package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * Parameter data type: none
 */
@XmlAccessorType(XmlAccessType.NONE)
public class TypeNone extends AbstractType
{
   private static final long serialVersionUID = 6267433476602548193L;

   @Override
   public ParameterAtomicType getAtomicType()
   {
      return ParameterAtomicType.NONE;
   }

   @Override
   public String toString()
   {
      return "TypeNone";
   }
}
