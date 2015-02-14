package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * A procedure type.
 */
@XmlType
@XmlEnum
public enum ProcedureType
{
   /**
    * Load procedure.
    */
   @XmlEnumValue("Load")
   LOAD,

   /**
    * Unload procedure.
    */
   @XmlEnumValue("Unload")
   UNLOAD;

   private final String name;

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Constructor.
    */
   private ProcedureType()
   {
      this.name = I18n.getMessage("ProcedureType." + name());
   }
}
