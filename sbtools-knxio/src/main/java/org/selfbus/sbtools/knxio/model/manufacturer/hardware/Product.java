package org.selfbus.sbtools.knxio.model.manufacturer.hardware;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;
import org.selfbus.sbtools.knxio.model.common.RegistrationInfo;

@XmlAccessorType(XmlAccessType.NONE)
public class Product extends Identified
{
   private static final long serialVersionUID = -2597447372161432625L;

   @XmlAttribute(name = "Text")
   private String name;

   @XmlAttribute(name = "OrderNumber")
   private String orderNumber;

   @XmlAttribute(name = "DefaultLanguage")
   private String defaultLanguage;

   @XmlAttribute(name = "WidthInMillimeter")
   private Float widthInMillimeter;

   @XmlAttribute(name = "IsRailMounted")
   private boolean railMounted;

   @XmlAttribute(name = "NonRegRelevantDataVersion")
   private Integer nonRegRelevantDataVersion;

   @XmlElement(name = "RegistrationInfo", namespace = Namespaces.KNX)
   private RegistrationInfo registrationInfo;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getOrderNumber()
   {
      return orderNumber;
   }

   public void setOrderNumber(String orderNumber)
   {
      this.orderNumber = orderNumber;
   }

   public String getDefaultLanguage()
   {
      return defaultLanguage;
   }

   public void setDefaultLanguage(String defaultLanguage)
   {
      this.defaultLanguage = defaultLanguage;
   }

   public Float getWidthInMillimeter()
   {
      return widthInMillimeter;
   }

   public void setWidthInMillimeter(Float widthInMillimeter)
   {
      this.widthInMillimeter = widthInMillimeter;
   }

   public boolean isRailMounted()
   {
      return railMounted;
   }

   public void setRailMounted(boolean isRailMounted)
   {
      this.railMounted = isRailMounted;
   }

   public Integer getNonRegRelevantDataVersion()
   {
      return nonRegRelevantDataVersion;
   }

   public void setNonRegRelevantDataVersion(Integer nonRegRelevantDataVersion)
   {
      this.nonRegRelevantDataVersion = nonRegRelevantDataVersion;
   }

   public RegistrationInfo getRegistrationInfo()
   {
      return registrationInfo;
   }

   public void setRegistrationInfo(RegistrationInfo registrationInfo)
   {
      this.registrationInfo = registrationInfo;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this);
   }
}
