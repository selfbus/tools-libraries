package org.selfbus.sbtools.prodedit.vdio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.prodedit.ProdEdit;
import org.selfbus.sbtools.prodedit.model.ProjectService;
import org.selfbus.sbtools.prodedit.model.common.MultiLingualText;
import org.selfbus.sbtools.prodedit.model.enums.ObjectPriority;
import org.selfbus.sbtools.prodedit.model.enums.ObjectType;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.global.FunctionalEntity;
import org.selfbus.sbtools.prodedit.model.global.Language;
import org.selfbus.sbtools.prodedit.model.global.Manufacturer;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.CatalogEntry;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterContainer;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.CommunicationObject;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.Parameter;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterCategory;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterType;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterValue;
import org.selfbus.sbtools.vdio.ProductsReader;
import org.selfbus.sbtools.vdio.VdioException;
import org.selfbus.sbtools.vdio.model.VD;
import org.selfbus.sbtools.vdio.model.VdApplicationProgram;
import org.selfbus.sbtools.vdio.model.VdCatalogEntry;
import org.selfbus.sbtools.vdio.model.VdCommunicationObject;
import org.selfbus.sbtools.vdio.model.VdFunctionalEntity;
import org.selfbus.sbtools.vdio.model.VdLanguage;
import org.selfbus.sbtools.vdio.model.VdManufacturer;
import org.selfbus.sbtools.vdio.model.VdParameter;
import org.selfbus.sbtools.vdio.model.VdParameterType;
import org.selfbus.sbtools.vdio.model.VdParameterValue;
import org.selfbus.sbtools.vdio.model.VdProgramDescription;
import org.selfbus.sbtools.vdio.model.VdTextAttribute;
import org.selfbus.sbtools.vdio.model.VdVirtualDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.common.collect.ArrayListModel;

/**
 * The products importer can import ETS .vd_ products files.
 */
public class ProductsImporter
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ProductsImporter.class);

   private final ProjectService projectService;
   private String fallbackLangId = "de";

   private VD vd;
   private Project project;
   private ProductGroup group;
   private Map<FunctionalEntity, ProductGroup> groups = new HashMap<FunctionalEntity, ProductGroup>();
   private Map<Integer, FunctionalEntity> topEntities = new HashMap<Integer, FunctionalEntity>();
   private final Map<Integer, ParameterType> paramTypes = new HashMap<Integer, ParameterType>();
   private final Map<Integer, Parameter> params = new HashMap<Integer, Parameter>();

   /**
    * Text lookup cache. Always use {@link #getText(int,int)} to access the texts.
    */
   private Map<Integer, MultiLingualText> texts = new HashMap<Integer, MultiLingualText>(8191);

   /**
    * Create a products importer.
    *
    * @param projectService - the project service to use
    */
   public ProductsImporter(ProjectService projectService)
   {
      this.projectService = projectService;
   }

   /**
    * Read the VD file and create a {@link Project} from it.
    *
    * @param file - the VD or ZIP file to read.
    * @return The created project, or null if the user canceled the import.
    *
    * @throws FileNotFoundException if the file does not exist
    * @throws VdioException in case of VD parse errors
    */
   public Project read(File file) throws FileNotFoundException, VdioException
   {
      long startTime = System.currentTimeMillis();

      ProductsReader reader = new ProductsReader(ProdEdit.getInstance().getMainFrame());
      reader.setZipPassword(Config.getInstance().get("zipPassword"));

      vd = reader.read(file);
      if (vd == null) return null;
      Config.getInstance().put("zipPassword", reader.getZipPassword());

      importVd();
      project.setFile(file);

      LOGGER.debug("Import done ({} seconds)", (System.currentTimeMillis() - startTime) * 0.001);
      return project;
   }

   /**
    * Read the VD products from the input stream. This method can only read VD
    * streams and not ZIP streams.
    * 
    * @param in - the VD stream to read from.
    * @return The read project, or null if the user canceled the import.
    *
    * @throws VdioException in case of VD parse errors
    */
   public Project read(InputStream in) throws VdioException
   {
      long startTime = System.currentTimeMillis();

      ProductsReader reader = new ProductsReader(ProdEdit.getInstance().getMainFrame());
      reader.setZipPassword(Config.getInstance().get("zipPassword"));

      vd = reader.read(in);
      if (vd == null) return null;
      Config.getInstance().put("zipPassword", reader.getZipPassword());

      importVd();

      LOGGER.debug("Import done ({} seconds)", (System.currentTimeMillis() - startTime) * 0.001);
      return project;
   }

   /**
    * Process the read VD in {@link #vd}.
    * The created project is stored in {@link #project}.
    *
    * @throws VdioException in case of VD parse errors
    */
   protected void importVd() 
   {
      Validate.notNull(vd, "input stream is null");

      groups.clear();

      project = new Project();
      project.setProjectService(projectService);

      analyzeTexts();

      importManufacturers();
      importLanguages();
      importFunctionalEntities();
      importProducts();
   }

   /**
    * Analyze the translated texts and put them into a map for faster access.
    */
   protected void analyzeTexts()
   {
      texts.clear();

      for (VdTextAttribute t : vd.textAttributes)
      {
         MultiLingualText text = texts.get(t.getEntityId());
         if (text == null)
         {
            Validate.isTrue(t.getColumnId() < 1024); // or the implementation below will fail

            text = new MultiLingualText();
            texts.put((t.getEntityId() << 10) + t.getColumnId(), text);
         }

         text.setText(LanguageMapper.getLangId(t.getLanguageId()), t.getText());
      }
   }

   /**
    * Get the translated text.
    * 
    * @param id - the ID of the text
    * @param col - the column
    */
   protected MultiLingualText getText(int id, TextColumn col)
   {
      return texts.get((id << 10) + col.column);
   }

   /**
    * Get the translated text. If no text is found, a default text is
    * created with the given default in the fallback language.
    * 
    * @param id - the ID of the text
    * @param col - the column
    * @param defaultValue - the default value
    */
   protected MultiLingualText getText(int id, TextColumn col, String defaultValue)
   {
      MultiLingualText text = getText(id, col);

      if (text == null)
         text = new MultiLingualText();

      if (text.getText(fallbackLangId) == null)
         text.setText(fallbackLangId, defaultValue);

      return text;
   }


   /**
    * Import the manufacturers.
    */
   protected void importManufacturers()
   {
      Map<Integer, Manufacturer> manufacturers = project.getManufacturers();
      for (VdManufacturer m : vd.manufacturers)
      {
         manufacturers.put(m.getId(), new Manufacturer(m.getId(), m.getName()));
      }

      LOGGER.debug("Imported {} manufacturers", manufacturers.size());
   }

   /**
    * Import the languages.
    */
   protected void importLanguages()
   {
      ArrayListModel<Language> langs = project.getLanguages();
      for (VdLanguage l : vd.languages)
      {
         String langId = LanguageMapper.getLangId(l.getId());

         langs.add(new Language(langId, l.getName()));

         if (l.getDatabaseLanguage() == 1)
            project.setDefaultLangId(langId);
      }

      LOGGER.debug("Imported {} languages", langs.size());
   }

   /**
    * Import the functional entities.
    */
   protected void importFunctionalEntities()
   {
      for (VdFunctionalEntity e : vd.functionalEntities)
      {
         FunctionalEntity entity = new FunctionalEntity(e.getId(), e.getName());
         FunctionalEntity parentEntity = null;

         Integer parentId = e.getParentId();
         if (parentId != null)
         {
            parentEntity = project.getFunctionalEntity(parentId);
            Validate.notNull(parentEntity, "functional entity #" + parentId + " not found");
            parentEntity.add(entity);
         }
         else
         {
            project.getFunctionalEntities().add(entity);
         }
      }

      LOGGER.debug("Imported {} functional entities", project.getFunctionalEntities().size());
   }

   /**
    * Analyze the functional entities for faster products import. 
    */
   protected void analyzeFunctionalEntities()
   {
      topEntities.clear();

      for (VdFunctionalEntity e : vd.functionalEntities)
      {
         FunctionalEntity entity;

         if (e.getParentId() != null)
            entity = topEntities.get(e.getParentId());
         else entity = project.getFunctionalEntity(e.getId());

         Validate.notNull(entity);

         topEntities.put(e.getId(), entity);
      }
   }

   /**
    * Create a file name from a string.
    * 
    * @param str - the string to process.
    * @return The file name
    */
   String strToFileName(String str)
   {
      if (str.length() > 20)
         str = str.substring(0, 20);

      str = str.toLowerCase().replace(' ', '_').replace(':', '_').replace('.', '_');
      str = str.replace(';', '_').replaceAll("___*", "_");
      str = str.replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue");
      str = str.replaceAll("ß", "ss");

      return str;
   }

   /**
    * Import the products.
    */
   protected void importProducts()
   {
      analyzeFunctionalEntities();

      for (VdVirtualDevice d : vd.virtualDevices)
      {
         int entityId = d.getFunctionalEntityId();
         FunctionalEntity topEntity = topEntities.get(entityId);
         Validate.notNull(topEntity, "top level functional entity for functional entity #" + entityId + " not found");

         group = groups.get(topEntity);
         if (group == null)
         {
            group = new ProductGroup(strToFileName(topEntity.getName()), topEntity.getName());
            project.addProductGroup(group);
            groups.put(topEntity, group);

            LOGGER.debug("Creating products group {} \"{}\"", group.getId(), group.getName());
         }

         importVirtualDevice(d);
      }
   }

   /**
    * Import a virtual device to the current product group.
    * 
    * @param d - the device to import
    */
   protected void importVirtualDevice(VdVirtualDevice d)
   {
      VirtualDevice device = new VirtualDevice(d.getId(), d.getName(), d.getDescription(), d.getFunctionalEntityId());
      device.setDescription(d.getDescription());
      device.setNumber(d.getNumber());
      device.setProductTypeId(d.getProductTypeId());
      device.setSymbolId(d.getSymbolId());

      VdCatalogEntry ce = vd.findCatalogEntry(d.getCatalogEntryId());
      CatalogEntry entry = new CatalogEntry(ce.getId(), ce.getName(), ce.getManufacturerId(), ce.getProductId());
      device.setCatalogEntry(entry);

      if (group.getProgram(d.getProgramId()) == null)
      {
         VdApplicationProgram p = vd.findProgram(d.getProgramId());

         ApplicationProgram program = new ApplicationProgram(p.getId(), p.getName(), p.getMaskId());
         program.setVersion(p.getVersion());
         program.setDeviceType(p.getDeviceType());
         program.setAddrTabSize(p.getAddrTabSize());
         program.setAssocTabAddr(p.getAssocTabAddr());
         program.setAssocTabSize(p.getAssocTabSize());
         program.setCommsTabAddr(p.getCommsTabAddr());
         program.setCommsTabSize(p.getCommsTabSize());
         program.setEepromData(p.getEepromData());
         program.setTypeId(p.getProgramType());
         program.setProgramStyle(p.getProgramStyle());
         program.setPollingMaster(p.isPollingMaster());
         
         importProgramDescription(program);
         importParameterTypes(program);
         importParameters(program);
         importComObjects(program);
   
         group.getPrograms().add(program);
      }

      device.setProgramId(d.getProgramId());
      group.addDevice(device);

      if (group.getManufacturer() == null || group.getManufacturer() == Manufacturer.NONE)
      {
         Manufacturer m = project.getManufacturer(ce.getManufacturerId());
         group.setManufacturer(m);
      }
   }


   /**
    * Import the description of an application program.
    * 
    * @param program - the program to import the description for
    */
   protected void importProgramDescription(ApplicationProgram program)
   {
      final int programId = program.getId();

      if (vd.programDescriptions == null)
         return;

      Map<Integer,VdProgramDescription> ordered= new TreeMap<Integer,VdProgramDescription>();
      for (VdProgramDescription d : vd.programDescriptions)
      {
         if (d.getProgramId() == programId)
            ordered.put((d.getLanguageId() << 10) + d.getOrder(), d);
      }

      MultiLingualText desc = new MultiLingualText();
      for (int key : ordered.keySet())
      {
         VdProgramDescription d = ordered.get(key);
         String langId = LanguageMapper.getLangId(d.getLanguageId());

         String text = desc.getText(langId);
         if (text == null || text.isEmpty())
            text = d.getText();
         else text = text + '\n' + d.getText();

         desc.setText(langId, text);
      }

      program.setDescription(desc);
   }

   /**
    * Import the parameter types of an application program.
    * 
    * @param program - the program to import the parameter types for
    */
   protected void importParameterTypes(ApplicationProgram program)
   {
      final int programId = program.getId();
      paramTypes.clear();

      for (VdParameterType t : vd.parameterTypes)
      {
         if (t.getProgramId() != programId)
            continue;

         ParameterType paramType = new ParameterType();
         paramType.setAtomicType(ParameterAtomicType.valueOf(t.getAtomicTypeId()));
         paramType.setId(t.getId());
         paramType.setName(t.getName());
         paramType.setMinValue(t.getMinValue());
         paramType.setMaxValue(t.getMaxValue());
         paramType.setMinDoubleValue(t.getMinDoubleValue());
         paramType.setMaxDoubleValue(t.getMaxDoubleValue());
         paramType.setSize(t.getSize());

         program.addParameterType(paramType);

         paramTypes.put(t.getId(), paramType);
      }

      for (VdParameterValue v : vd.parameterValues)
      {
         ParameterType paramType = paramTypes.get(v.getParameterTypeId());
         if (paramType == null)
            continue;

         ParameterValue paramValue = new ParameterValue(v.getId());
         paramValue.setIntValue(v.getIntValue());
         paramValue.setOrder(v.getOrder());
         paramValue.setLabel(getText(v.getId(), TextColumn.PARAM_VALUE, v.getLabel()));

         paramType.addValue(paramValue);
      }
   }

   /**
    * Sort the VD parameters by order number.
    * 
    * @return The sorted parameters
    */
   protected VdParameter[] sortedParameters()
   {
      VdParameter[] params = new  VdParameter[vd.parameters.size()];
      vd.parameters.toArray(params);

      Arrays.sort(params, new Comparator<VdParameter>()
      {
         @Override
         public int compare(VdParameter a, VdParameter b)
         {
            return a.getOrder() - b.getOrder();
         }
      });

      return params; 
   }

   /**
    * Import the parameters of an application program.
    * 
    * @param program - the program to import the parameters for
    */
   protected void importParameters(ApplicationProgram program)
   {
      final int programId = program.getId();
      params.clear();

      AbstractParameterContainer pageParam = program.getParameterRoot();

      for (VdParameter p : sortedParameters())
      {
         if (p.getProgramId() != programId)
            continue;

         // Safety check
         Validate.notNull(paramTypes.get(p.getParamTypeId()));

         Parameter param = new Parameter(p.getParamTypeId());
         param.setId(p.getId());
         param.setName(p.getName());
         param.setDescription(getText(p.getId(), TextColumn.PARAM_DESCRIPTION, p.getDescription()));
         param.setAddress(p.getAddress());
         param.setBitOffset(p.getBitOffset());
         param.setSize(p.getSize());
         param.setOrder(p.getOrder());
         param.setParentId(p.getParentId());
         param.setParentValue(p.getParentValue());
         param.setDefaultInt(p.getDefaultInt());
         param.setDefaultDouble(p.getDefaultDouble());
         param.setDefaultString(p.getDefaultString());
         // TODO param.setVisible(...);

         if (p.getAddress() == null)
         {
            param.setCategory(ParameterCategory.PAGE);

            pageParam = param;
            program.addParameter(param);
         }
         else if (p.getAddress() == 0)
         {
            param.setCategory(ParameterCategory.LABEL);
            pageParam.addChild(param);
         }
         else
         {
            param.setCategory(ParameterCategory.VALUE);
            pageParam.addChild(param);
         }

//         Integer parentId = p.getParentId();
//         if (parentId != null)
//         {
//            Parameter parent = params.get(parentId);
//            Validate.notNull(parent, "Parent parameter #{0} for parameter #{1} not found", parentId, p.getId());
//            parent.addChild(param);
//         }
//         else program.addParameter(param);

         params.put(p.getId(), param);
      }
   }

   /**
    * Import the communication objects of an application program.
    * 
    * @param program - the program to import the parameters for
    */
   protected void importComObjects(ApplicationProgram program)
   {
      final int programId = program.getId();
      for (VdCommunicationObject o : vd.communicationObjects)
      {
         if (o.getProgramId() != programId)
            continue;

         CommunicationObject comObject = new CommunicationObject();
         comObject.setId(o.getId());
         comObject.setName(getText(o.getId(), TextColumn.COM_OBJECT_NAME, o.getName()));
         comObject.setFunction(getText(o.getId(), TextColumn.COM_OBJECT_FUNCTION, o.getFunction()));
         comObject.setDescription(getText(o.getId(), TextColumn.COM_OBJECT_DESCRIPTION, o.getDescription()));
         comObject.setType(ObjectType.valueOf(o.getTypeId()));
         comObject.setParentId(o.getParentParameterId());
         comObject.setParentValue(o.getParentParameterValue());
         comObject.setCommEnabled(o.isCommEnabled());
         comObject.setReadEnabled(o.isReadEnabled());
         comObject.setWriteEnabled(o.isWriteEnabled());
         comObject.setTransEnabled(o.isTransEnabled());
         comObject.setPriority(ObjectPriority.valueOf(o.getPriorityId()));

         Integer parentId = o.getParentParameterId();
         if (parentId != null)
         {
            Parameter parent = params.get(parentId);
            Validate.notNull(parent, "Parent parameter #{0} for com-object #{1} not found", parentId, o.getId());
            parent.addChild(comObject);
         }
         else program.addParameter(comObject);
      }
   }
}
