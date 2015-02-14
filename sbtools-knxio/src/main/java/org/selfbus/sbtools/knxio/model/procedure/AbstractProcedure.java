package org.selfbus.sbtools.knxio.model.procedure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * Abstract base class for procedures.
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractProcedure
{
   @XmlElements(value = {
      @XmlElement(name="LdCtrlAbsSegment", namespace = Namespaces.KNX, type = LdCtrlAbsSegment.class),
      @XmlElement(name="LdCtrlClearLCFilterTable", namespace = Namespaces.KNX, type = LdCtrlClearLCFilterTable.class),
      @XmlElement(name="LdCtrlConnect", namespace = Namespaces.KNX, type = LdCtrlConnect.class),
      @XmlElement(name="LdCtrlCompareMem", namespace = Namespaces.KNX, type = LdCtrlCompareMem.class),
      @XmlElement(name="LdCtrlCompareProp", namespace = Namespaces.KNX, type = LdCtrlCompareProp.class),
      @XmlElement(name="LdCtrlDelay", namespace = Namespaces.KNX, type = LdCtrlDelay.class),
      @XmlElement(name="LdCtrlDisconnect", namespace = Namespaces.KNX, type = LdCtrlDisconnect.class),
      @XmlElement(name="LdCtrlLoad", namespace = Namespaces.KNX, type = LdCtrlLoad.class),
      @XmlElement(name="LdCtrlLoadCompleted", namespace = Namespaces.KNX, type = LdCtrlLoadCompleted.class),
      @XmlElement(name="LdCtrlLoadImageMem", namespace = Namespaces.KNX, type = LdCtrlLoadImageMem.class),
      @XmlElement(name="LdCtrlLoadImageProp", namespace = Namespaces.KNX, type = LdCtrlLoadImageProp.class),
      @XmlElement(name="LdCtrlMapError", namespace = Namespaces.KNX, type = LdCtrlMapError.class),
      @XmlElement(name="LdCtrlMerge", namespace = Namespaces.KNX, type = LdCtrlMerge.class),
      @XmlElement(name="LdCtrlRelSegment", namespace = Namespaces.KNX, type = LdCtrlRelSegment.class),
      @XmlElement(name="LdCtrlRestart", namespace = Namespaces.KNX, type = LdCtrlRestart.class),
      @XmlElement(name="LdCtrlSetControlVariable", namespace = Namespaces.KNX, type = LdCtrlSetControlVariable.class),
      @XmlElement(name="LdCtrlTaskPtr", namespace = Namespaces.KNX, type = LdCtrlTaskPtr.class),
      @XmlElement(name="LdCtrlTaskCtrl1", namespace = Namespaces.KNX, type = LdCtrlTaskCtrl1.class),
      @XmlElement(name="LdCtrlTaskCtrl2", namespace = Namespaces.KNX, type = LdCtrlTaskCtrl2.class),
      @XmlElement(name="LdCtrlTaskSegment", namespace = Namespaces.KNX, type = LdCtrlTaskSegment.class),
      @XmlElement(name="LdCtrlUnload", namespace = Namespaces.KNX, type = LdCtrlUnload.class),
      @XmlElement(name="LdCtrlWriteMem", namespace = Namespaces.KNX, type = LdCtrlWriteMem.class),
      @XmlElement(name="LdCtrlWriteProp", namespace = Namespaces.KNX, type = LdCtrlWriteProp.class),
      @XmlElement(name="LdCtrlWriteRelMem", namespace = Namespaces.KNX, type = LdCtrlWriteRelMem.class),
      })
   private List<AbstractLdCtrl> commands;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
