package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.common.ObjectPriority;

/**
 * A communication object.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class ComObject extends Identified
{
   @XmlAttribute(name = "DatapointType")
   private String datapointType;

   @XmlAttribute(name = "FunctionText")
   private String functionText;

   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "Text")
   private String text;

   @XmlAttribute(name = "VisibleDescription")
   private String visibleDescription;

   @XmlAttribute(name = "Number")
   private Integer number;

   @XmlAttribute(name = "ObjectSize")
   private ComObjectSize size;

   @XmlAttribute(name = "Priority")
   private ObjectPriority priority = ObjectPriority.LOW;

   @XmlAttribute(name = "CommunicationFlag")
   @XmlJavaTypeAdapter(EnablementAdapter.class)
   private Boolean communicationFlag = true;

   @XmlAttribute(name = "ReadFlag")
   @XmlJavaTypeAdapter(EnablementAdapter.class)
   private Boolean readFlag = false;

   @XmlAttribute(name = "ReadOnInitFlag")
   @XmlJavaTypeAdapter(EnablementAdapter.class)
   private Boolean readOnInitFlag = false;

   @XmlAttribute(name = "TransmitFlag")
   @XmlJavaTypeAdapter(EnablementAdapter.class)
   private Boolean transmitFlag = false;

   @XmlAttribute(name = "UpdateFlag")
   @XmlJavaTypeAdapter(EnablementAdapter.class)
   private Boolean updateFlag = false;

   @XmlAttribute(name = "WriteFlag")
   @XmlJavaTypeAdapter(EnablementAdapter.class)
   private Boolean writeFlag = false;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
