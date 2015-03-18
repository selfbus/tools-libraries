package org.selfbus.sbtools.knxio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A KNX-XML document, version 12
 */
@XmlRootElement(namespace = Namespaces.KNX_V12)
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class KnxDocument12 extends KnxDocument
{
   private static final long serialVersionUID = -5843380801173447484L;
}
