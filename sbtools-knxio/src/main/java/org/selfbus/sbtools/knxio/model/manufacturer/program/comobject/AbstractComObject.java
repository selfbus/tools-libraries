package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * Abstract base class for communication objects.
 */
public abstract class AbstractComObject extends Identified
{
   @XmlAttribute(name = "DatapointType")
   private String datapointType;

   @XmlAttribute(name = "FunctionText")
   private String functionText;

   @XmlAttribute(name = "Text")
   private String text;

   @XmlAttribute(name = "VisibleDescription")
   private String visibleDescription;

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
}
