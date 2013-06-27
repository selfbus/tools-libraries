package org.selfbus.sbtools.prodedit.model.prodgroup;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.common.types.ObjectType;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.global.Manufacturer;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.collect.ArrayListModel;

/**
 * A products group.
 */
@XmlRootElement
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class ProductGroup extends Model implements Comparable<ProductGroup>
{
   private static final long serialVersionUID = 7542620207078765362L;

   private File file;

   @XmlAttribute
   private String id;

   @XmlAttribute
   private String name;

   @XmlAttribute
   private String description;

   @XmlElement
   private Manufacturer manufacturer = Manufacturer.NONE;

   @XmlElementWrapper(name = "application_programs")
   @XmlElement(name = "application_program")
   private ArrayListModel<ApplicationProgram> programs = new ArrayListModel<ApplicationProgram>();

   @XmlElementWrapper(name = "virtual_devices")
   @XmlElement(name = "virtual_device")
   private ArrayListModel<VirtualDevice> devices = new ArrayListModel<VirtualDevice>();

   @XmlElementWrapper(name = "object_types")
   @XmlElement(name = "object_type")
   private ArrayListModel<ObjectType> objectTypes = new ArrayListModel<ObjectType>();

   /**
    * @return The manufacturer. This manufacturer is used for all devices and other stuff in the
    *         product group.
    */
   public Manufacturer getManufacturer()
   {
      return manufacturer;
   }

   /**
    * Set the manufacturer. This manufacturer is used for all devices and other stuff in the product
    * group.
    * 
    * @param manufacturer - the manufacturer to set
    */
   public void setManufacturer(Manufacturer manufacturer)
   {
      this.manufacturer = manufacturer;
   }

   /**
    * @return The name of the manufacturer.
    */
   public String getManufacturerName()
   {
      return manufacturer == null ? null : manufacturer.getName();
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
    * @return The ID
    */
   public String getId()
   {
      return id;
   }

   /**
    * Set the ID.
    * 
    * @param id - the id to set
    */
   public void setId(String id)
   {
      String oldValue = this.id;
      this.id = id;
      firePropertyChange("id", oldValue, id);
   }

   /**
    * @return The name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name
    * 
    * @param name - the name to set
    */
   public void setName(String name)
   {
      String oldValue = this.name;
      this.name = name;
      firePropertyChange("name", oldValue, name);
   }

   /**
    * @return The description
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * Set the description.
    * 
    * @param description - the description to set
    */
   public void setDescription(String description)
   {
      this.description = description;
   }

   /**
    * Add a {@link VirtualDevice virtual device} to the product group.
    * 
    * @param device - the device to add
    */
   public void addDevice(VirtualDevice device)
   {
      Validate.notNull(device);
      devices.add(device);
   }

   /**
    * @return The virtual devices.
    */
   public ArrayListModel<VirtualDevice> getDevices()
   {
      return devices;
   }

   /**
    * Create a virtual device.
    */
   public VirtualDevice createDevice()
   {
      String name = null;
      int idx;

      Set<String> usedNames = new HashSet<String>();
      int maxId = 0;
      for (VirtualDevice device : devices)
      {
         usedNames.add(device.getName());
         maxId = Math.max(maxId, device.getId());
      }

      for (idx = 1; idx < 1000; ++idx)
      {
         name = I18n.formatMessage("ProductGroup.newDeviceName", Integer.toString(idx, 36));

         if (!usedNames.contains(name))
            break;
      }

      // Ensure that a unique name was found
      Validate.isTrue(idx < 1000);

      VirtualDevice device = new VirtualDevice(maxId + 1, name, null, 0, 0);
      ApplicationProgram program = createProgram(name);

      device.setProgramId(program.getId());

      devices.add(device);
      return device;
   }

   /**
    * Create an application program.
    * 
    * @param name - the name of the program
    */
   public ApplicationProgram createProgram(String name)
   {
      int maxId = 0;
      for (ApplicationProgram program : programs)
      {
         maxId = Math.max(maxId, program.getId());
      }

      ApplicationProgram program = new ApplicationProgram(maxId + 1, name, manufacturer.getId(), 0);
      programs.add(program);

      return program;
   }

   /**
    * @return The programs.
    */
   public ArrayListModel<ApplicationProgram> getPrograms()
   {
      return programs;
   }

   /**
    * Get a program by ID
    * 
    * @param id - the program ID
    * @return The program or null if not found
    */
   public ApplicationProgram getProgram(int id)
   {
      for (ApplicationProgram program : programs)
      {
         if (program.getId() == id)
            return program;
      }
      return null;
   }

   /**
    * Get a program by device.
    * 
    * @param device - the device to which the program belongs.
    * @return The program or null if not found
    */
   public ApplicationProgram getProgram(VirtualDevice device)
   {
      return getProgram(device.getProgramId());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof ProductGroup))
         return false;
      return id.equals(((ProductGroup) o).id);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(ProductGroup o)
   {
      return id.compareTo(o.id);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      if (name != null && !name.isEmpty())
         return name;

      if (id != null && !id.isEmpty())
         return '[' + id + ']';

      return I18n.getMessage("unnamed");
   }
}
