package org.selfbus.sbtools.prodedit.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.global.FunctionalEntity;
import org.selfbus.sbtools.prodedit.model.global.Language;
import org.selfbus.sbtools.prodedit.model.global.Manufacturer;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.ParameterType;
import org.selfbus.sbtools.prodedit.model.prodgroup.ParameterValue;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.common.collect.ArrayListModel;

/**
 * A manager that holds the current project and notifies subscribers
 * about changes.
 */
public class ProjectService
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);
   private final Set<ProjectListener> listeners = new HashSet<ProjectListener>();
   private Project project = new Project();

   /**
    * Load a project.
    * 
    * @param file - the file to load the project from.
    */
   public void loadProject(File file)
   {
      LOGGER.info("Loading project from {}", file);

      ProjectReader reader = new ProjectReader();
      try
      {
         project = reader.read(file);
         project.setProjectService(this);

         Config.getInstance().put("project.last", file.getAbsolutePath());

         fireProjectChanged();
      }
      catch (FileNotFoundException e)
      {
         throw new RuntimeException("File not found: " + file, e);
      }
   }

   /**
    * Save the project.
    * 
    * @param file - the file to save the project to.
    */
   public void saveProject(File file)
   {
      if (!file.getName().toLowerCase().endsWith(".proj"))
         file = new File(file + ".proj");

      LOGGER.info("Saving project as {}", file);

      ProjectWriter writer = new ProjectWriter();
      writer.write(project, file);
      Config.getInstance().put("project.last", file.getAbsolutePath());

      fireProjectChanged();
   }

   /**
    * Start a new project.
    */
   public void newProject()
   {
      LOGGER.info("Creating new project");

      project = new Project();
      project.setProjectService(this);

      int manufacturerId = 76;
      project.getManufacturers().put(manufacturerId, new Manufacturer(manufacturerId, "Selfbus"));

      FunctionalEntity feIn = project.createFunctionalEntity("Inputs", null);
      project.createFunctionalEntity("Outputs", null);
      project.createFunctionalEntity("Touch", feIn);
      project.createFunctionalEntity("Switches", feIn);

      project.getLanguages().add(new Language("en", I18n.getMessage("ProjectService.langEN")));
      project.getLanguages().add(new Language("de", I18n.getMessage("ProjectService.langDE")));
      project.getLanguages().add(new Language("fr", I18n.getMessage("ProjectService.langFR")));
      project.setDefaultLanguage("de");

      ProductGroup group = new ProductGroup();
      group.setId("example-1");
      group.setName("Example 1");
      group.setManufacturer(project.getManufacturer(manufacturerId));
      project.addProductGroup(group);

      VirtualDevice device = group.createDevice();
      device.setNumber(1);
      device.setDescription("Example device #1");

      ApplicationProgram program = group.getProgram(device);
      ArrayListModel<ParameterType> paramTypes = program.getParameterTypes();

      ParameterType paramType = new ParameterType(ParameterAtomicType.ENUM, "Yes-No");
      paramType.setId(100);
      paramType.setMinValue(0);
      paramType.setMaxValue(1);
      paramType.setSize(1);
      paramType.addValue(new ParameterValue(101, "No", 0));
      paramType.addValue(new ParameterValue(102, "Yes", 1));
      paramTypes.add(paramType);

      paramType = new ParameterType(ParameterAtomicType.UNSIGNED, "Percent");
      paramType.setId(200);
      paramType.setMinValue(1);
      paramType.setMaxValue(100);
      paramType.setSize(7);
      paramTypes.add(paramType);

      Config.getInstance().remove("project.last");
      fireProjectChanged();
   }

   /**
    * @return The project.
    */
   public Project getProject()
   {
      return project;
   }

   /**
    * Add a project listener.
    * 
    * @param listener - the listener to add.
    */
   public void addListener(ProjectListener listener)
   {
      synchronized (listeners)
      {
         listeners.add(listener);         
      }
   }

   /**
    * Remove a project listener.
    * 
    * @param listener - the listener to remove.
    */
   public void removeListener(ProjectListener listener)
   {
      synchronized (listeners)
      {
         listeners.remove(listener);
      }
   }

   /**
    * Inform all listeners that the project changed.
    */
   public void fireProjectChanged()
   {
      synchronized (listeners)
      {
         for (ProjectListener listener : listeners)
            listener.projectChanged(project);
      }
   }

   /**
    * Inform all listeners that a product group was added.
    * 
    * @param group - the product group
    */
   public void fireProductGroupAdded(ProductGroup group)
   {
      synchronized (listeners)
      {
         for (ProjectListener listener : listeners)
            listener.productGroupAdded(group);
      }
   }

   /**
    * Inform all listeners that a product group was changed.
    * 
    * @param group - the product group
    */
   public void fireProductGroupChanged(ProductGroup group)
   {
      synchronized (listeners)
      {
         for (ProjectListener listener : listeners)
            listener.productGroupChanged(group);
      }
   }

   /**
    * Inform all listeners that a product group was removed.
    * 
    * @param group - the product group
    */
   public void fireProductGroupRemoved(ProductGroup group)
   {
      synchronized (listeners)
      {
         for (ProjectListener listener : listeners)
            listener.productGroupRemoved(group);
      }
   }
}
