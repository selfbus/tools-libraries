package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A program plugin.
 */
@XmlType(name = "ProgramPlugin", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdProgramPlugin
{
   @XmlAttribute(name = "program_plugin_id", required = true)
   private int id;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "plugin_type", required = true)
   private int type;

   @XmlAttribute(name = "plugin_clsid")
   private String classId;

   @XmlAttribute(name = "plugin_codebase")
   private String codeBase;

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
      if (!(o instanceof VdProgramPlugin))
         return false;
      final VdProgramPlugin oo = (VdProgramPlugin) o;
      return id == oo.id;
   }
}
