package test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A functional entity.
 */
@XmlRootElement
@XmlType(name = "FunctionalEntity", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class FunctionalEntity
{
   @XmlAttribute(name = "functional_entity_id")
   public int id;

   @XmlAttribute(name = "fun_functional_entity_id")
   public int parentId;

   @XmlAttribute(name = "functional_entity_name")
   public String name;
}
