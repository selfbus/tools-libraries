package test.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A project.
 */
@XmlRootElement
@XmlType(name = "VD", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VD
{
   @XmlAttribute
   public String name;

   @XmlAttribute
   public String created;

   @XmlAttribute
   public String version;

   @XmlElementWrapper(name = "manufacturer")
   @XmlElement(name = "manufacturer")
   public List<Manufacturer> manufacturers;

   @XmlElementWrapper(name = "functional_entity")
   @XmlElement(name = "functional_entity")
   public List<FunctionalEntity> functionalEntities;
}
