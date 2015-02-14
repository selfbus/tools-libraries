package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.model.Namespaces;
import org.selfbus.sbtools.knxio.model.common.Identified;

@XmlType(namespace = Namespaces.KNX, propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class CatalogItem extends Identified
{
   @XmlAttribute(name = "Name", required = true)
   private String name;

   @XmlAttribute(name = "Number")
   private String number;

   @XmlAttribute(name = "ProductRefId")
   private String productRefId;

   @XmlAttribute(name = "Hardware2ProgramRefId")
   private String hardware2ProgramRefId;

   @XmlAttribute(name = "DefaultLanguage")
   private String defaultLanguage;

   @XmlAttribute(name = "NonRegRelevantDataVersion")
   private String nonRegRelevantDataVersion;
}
