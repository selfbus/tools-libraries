package org.selfbus.sbtools.knxio.model.manufacturer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.model.Namespaces;

@XmlType(name = "Manufacturer", namespace = Namespaces.KNX, propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class Languages
{
   @XmlAttribute(name = "RefId")
   private String refId;
}
