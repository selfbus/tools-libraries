package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * A hawk configuration resource.
 */
@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class Resource
{
   @XmlAttribute(name = "Name")
   private String name;

   @XmlAttribute(name = "Access")
   private String access;

   @XmlAttribute(name = "MgmtStyle")
   private String mgmtStyle;

   @XmlElement(name = "ResourceType", namespace = Namespaces.KNX)
   private ResourceType type;

   @XmlElement(name = "AccessRights", namespace = Namespaces.KNX)
   private AccessRights accessRights;

   @XmlElements(value = {
      @XmlElement(name = "Location", namespace = Namespaces.KNX, type = ResourceLocation.class),
      @XmlElement(name = "ImgLocation", namespace = Namespaces.KNX, type = ResourceImgLocation.class)
      })
   private AbstractResourceLocation location;

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
