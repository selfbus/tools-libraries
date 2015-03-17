package org.selfbus.sbtools.knxio.model.manufacturer.program.comobject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.selfbus.sbtools.knxio.model.common.Identified;

/**
 * Abstract base class for communication objects.
 */
public abstract class AbstractComObject extends Identified
{
   private static final long serialVersionUID = 4290473520529698236L;

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

   public String getDatapointType()
   {
      return datapointType;
   }

   public void setDatapointType(String datapointType)
   {
      this.datapointType = datapointType;
   }

   public String getFunctionText()
   {
      return functionText;
   }

   public void setFunctionText(String functionText)
   {
      this.functionText = functionText;
   }

   public String getText()
   {
      return text;
   }

   public void setText(String text)
   {
      this.text = text;
   }

   public String getVisibleDescription()
   {
      return visibleDescription;
   }

   public void setVisibleDescription(String visibleDescription)
   {
      this.visibleDescription = visibleDescription;
   }

   public Boolean getCommunicationFlag()
   {
      return communicationFlag;
   }

   public void setCommunicationFlag(Boolean communicationFlag)
   {
      this.communicationFlag = communicationFlag;
   }

   public Boolean getReadFlag()
   {
      return readFlag;
   }

   public void setReadFlag(Boolean readFlag)
   {
      this.readFlag = readFlag;
   }

   public Boolean getReadOnInitFlag()
   {
      return readOnInitFlag;
   }

   public void setReadOnInitFlag(Boolean readOnInitFlag)
   {
      this.readOnInitFlag = readOnInitFlag;
   }

   public Boolean getTransmitFlag()
   {
      return transmitFlag;
   }

   public void setTransmitFlag(Boolean transmitFlag)
   {
      this.transmitFlag = transmitFlag;
   }

   public Boolean getUpdateFlag()
   {
      return updateFlag;
   }

   public void setUpdateFlag(Boolean updateFlag)
   {
      this.updateFlag = updateFlag;
   }

   public Boolean getWriteFlag()
   {
      return writeFlag;
   }

   public void setWriteFlag(Boolean writeFlag)
   {
      this.writeFlag = writeFlag;
   }
}
