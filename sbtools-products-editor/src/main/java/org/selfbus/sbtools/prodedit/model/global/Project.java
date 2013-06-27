package org.selfbus.sbtools.prodedit.model.global;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.Namespaces;
import org.selfbus.sbtools.prodedit.model.ProjectService;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;

import com.jgoodies.common.collect.ArrayListModel;

/**
 * A project.
 */
@XmlRootElement
@XmlType(name = "Project", namespace = Namespaces.PROJECT, propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class Project
{
   private final Map<Integer, Manufacturer> manufacturers = new TreeMap<Integer, Manufacturer>();
   private final ArrayListModel<ProductGroup> productGroups = new ArrayListModel<ProductGroup>();
   private ProjectService projectService;
   private File file;

   @XmlElementWrapper(name = "functional_entities")
   @XmlElement(name = "functional_entity")
   private ArrayListModel<FunctionalEntity> funcEntities = new ArrayListModel<FunctionalEntity>();

   @XmlElementWrapper(name = "languages")
   @XmlElement(name = "language")
   private ArrayListModel<Language> languages = new ArrayListModel<Language>();

   @XmlAttribute
   private String name;

   @XmlAttribute
   private String defaultLanguage;

   @XmlAttribute
   private int defaultManufacturerId;

   @XmlAttribute
   private int nextFuncEntityId = 1;

   /**
    * @return The default manufacturer ID
    */
   public int getDefaultManufacturerId()
   {
      return defaultManufacturerId;
   }

   /**
    * Set the default manufacturer ID.
    *
    * @param id - the manufacturer ID
    */
   public void setDefaultManufacturerId(int id)
   {
      this.defaultManufacturerId = id;
   }

   /**
    * @return The name of the project.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name of the project.
    * 
    * @param name - the name to set.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return The file that contains the project, may be null.
    */
   public File getFile()
   {
      return file;
   }

   /**
    * Set the file that contains the project.
    * 
    * @param file - the file to set, may be null.
    */
   public void setFile(File file)
   {
      this.file = file;
   }

   /**
    * Set the project service.
    * 
    * @param projectService - the project service.
    */
   public void setProjectService(ProjectService projectService)
   {
      this.projectService = projectService;
   }

   /**
    * Get a manufacturer by ID
    * 
    * @param id - the ID of the searched manufacturer
    * @return The manufacturer, or null if not found.
    */
   public Manufacturer getManufacturer(int id)
   {
      return manufacturers.get(id);
   }

   /**
    * @return The manufacturers.
    */
   public Map<Integer, Manufacturer> getManufacturers()
   {
      return manufacturers;
   }

   /**
    * @return The manufacturers as list, for marshalling.
    */
   @XmlElement(name = "manufacturer")
   @XmlElementWrapper(name = "manufacturers")
   public List<Manufacturer> getManufacturersList()
   {
      List<Manufacturer> result = new LinkedList<Manufacturer>();
      result.addAll(manufacturers.values());
      return result;
   }

   /**
    * Set the manufacturers from a list, for unmarshalling.
    */
   public void setManufacturersList(List<Manufacturer> manufacturers)
   {
      this.manufacturers.clear();
      for (Manufacturer m : manufacturers)
      {
         this.manufacturers.put(m.id, m);
      }
   }

   /**
    * Create a new functional entity.
    * 
    * @param name - the name
    * @param parent - the parent functional entity, may be null
    * 
    * @return The new functional entity.
    */
   public FunctionalEntity createFunctionalEntity(String name, FunctionalEntity parent)
   {
      FunctionalEntity e = new FunctionalEntity(nextFuncEntityId++, name);

      if (parent == null)
      {
         funcEntities.add(e);
      }
      else
      {
         parent.childs.add(e);
      }

      projectService.fireProjectChanged();
      return e;
   }

   /**
    * Remove a functional entity. Also removes all child entities.
    * 
    * @param funcEntity - the functional entity to remove
    */
   public void removeFunctionalEntity(FunctionalEntity funcEntity)
   {
      funcEntities.remove(funcEntity.id);
      projectService.fireProjectChanged();
   }

   /**
    * @return The top-level functional entities.
    */
   public ArrayListModel<FunctionalEntity> getFunctionalEntities()
   {
      return funcEntities;
   }

   /**
    * Get a functional entity by ID
    * 
    * @param id - the ID of the functional entity
    * @return The functional entity or null if not found
    */
   public FunctionalEntity getFunctionalEntity(int id)
   {
      return findFunctionalEntity(funcEntities, id);
   }

   /**
    * Recursively search for a functional entity in the given entities and it's children.
    *
    * @param funcEntities - the entities to search
    * @param id - the ID of the functional entity
    * @return The functional entity or null if not found
    */
   private FunctionalEntity findFunctionalEntity(ArrayListModel<FunctionalEntity> funcEntities, int id)
   {
      for (FunctionalEntity e : funcEntities)
      {
         if (e.id == id)
            return e;

         e = findFunctionalEntity(e.childs, id);
         if (e != null)
            return e;
      }

      return null;
   }

   /**
    * Get a specific language.
    * 
    * @param id - the ID of the language.
    *
    * @return The language, or null if not found
    */
   public Language getLanguage(String id)
   {
      for (Language lang : languages)
      {
         if (lang.getId().equals(id))
            return lang;
      }
      return null;
   }

   /**
    * @return The languages.
    */
   public ArrayListModel<Language> getLanguages()
   {
      return languages;
   }

   /**
    * @return The default language
    */
   public String getDefaultLanguage()
   {
      return defaultLanguage;
   }

   /**
    * Set the default language.
    * 
    * @param langID - the ID of the language
    */
   public void setDefaultLanguage(String langID)
   {
      this.defaultLanguage = langID;
   }

   /**
    * Create a new language.
    */
   public Language createLanguage()
   {
      Language lang = new Language();
      languages.add(lang);
      return lang;
   }

   /**
    * @return The product groups. Warning: do not manipulate the returned collection. Use the access
    *         methods of this class for manipulation.
    */
   public ArrayListModel<ProductGroup> getProductGroups()
   {
      return productGroups;
   }

   /**
    * Get a specific product group by ID.
    * 
    * @param id - the ID of the product group.
    * 
    * @return The product group or null if not found
    */
   public ProductGroup getProductGroup(String id)
   {
      for (ProductGroup group : productGroups)
      {
         if (group.getId().equals(id))
            return group;
      }

      return null;
   }

   /**
    * Get a specific product group by name.
    * 
    * @param name - the name of the product group.
    * 
    * @return The product group or null if not found
    */
   public ProductGroup getProductGroupByName(String name)
   {
      for (ProductGroup group : productGroups)
      {
         if (group.getName().equals(name))
            return group;
      }

      return null;
   }

   /**
    * Create a new product group.
    */
   public ProductGroup createProductGroup()
   {
      String id = null, name = null;
      int idx;

      Set<String> usedNames = new HashSet<String>();
      Set<String> usedIds = new HashSet<String>();
      for (ProductGroup group : productGroups)
      {
         usedNames.add(group.getName());
         usedIds.add(group.getId());
      }
      
      for (idx = 1; idx < 1000; ++idx)
      {
         id = I18n.formatMessage("ProductGroup.newID", Integer.toString(idx, 36));
         name = I18n.formatMessage("ProductGroup.newName", Integer.toString(idx, 36));

         if (!usedNames.contains(name) && !usedIds.contains(id))
            break;
      }

      // Ensure that a unique ID was found
      Validate.isTrue(idx < 1000);

      ProductGroup group = new ProductGroup();
      group.setId(id);
      group.setName(name);
      group.setManufacturer(manufacturers.get(defaultManufacturerId));

      addProductGroup(group);
      return group;
   }

   /**
    * Add a product group.
    * 
    * @param group - the product group to add.
    */
   public void addProductGroup(ProductGroup group)
   {
      Validate.notEmpty(group.getId());
      productGroups.add(group);
      projectService.fireProductGroupAdded(group);
   }

   /**
    * Remove a product group.
    * 
    * @param group - the product group to remove.
    */
   public void removeProductGroup(ProductGroup group)
   {
      productGroups.remove(group);
      projectService.fireProductGroupRemoved(group);
   }

   //   /**
   //    * Initialize the project after loading.
   //    * 
   //    * @param unmarshaller - the unmarshaller that was used for loading
   //    * @param parent - the parent object.
   //    */
   //   void afterUnmarshal(Unmarshaller unmarshaller, Object parent)
   //   {
   //   }
}
