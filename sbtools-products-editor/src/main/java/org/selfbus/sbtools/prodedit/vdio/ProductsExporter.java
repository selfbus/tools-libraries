package org.selfbus.sbtools.prodedit.vdio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.types.ObjectPriority;
import org.selfbus.sbtools.prodedit.ProdEdit;
import org.selfbus.sbtools.prodedit.model.common.MultiLingualText;
import org.selfbus.sbtools.prodedit.model.common.MultiLingualText.Element;
import org.selfbus.sbtools.prodedit.model.enums.ObjectType;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.global.FunctionalEntity;
import org.selfbus.sbtools.prodedit.model.global.Language;
import org.selfbus.sbtools.prodedit.model.global.Manufacturer;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterContainer;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterNode;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.CommunicationObject;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.Parameter;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterType;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterValue;
import org.selfbus.sbtools.vdio.ProductsWriter;
import org.selfbus.sbtools.vdio.VdioException;
import org.selfbus.sbtools.vdio.model.VD;
import org.selfbus.sbtools.vdio.model.VdApplicationProgram;
import org.selfbus.sbtools.vdio.model.VdAssembler;
import org.selfbus.sbtools.vdio.model.VdBcuType;
import org.selfbus.sbtools.vdio.model.VdCatalogEntry;
import org.selfbus.sbtools.vdio.model.VdCommunicationObject;
import org.selfbus.sbtools.vdio.model.VdDeviceInfo;
import org.selfbus.sbtools.vdio.model.VdDeviceInfoValue;
import org.selfbus.sbtools.vdio.model.VdDeviceObject;
import org.selfbus.sbtools.vdio.model.VdDeviceParameter;
import org.selfbus.sbtools.vdio.model.VdFunctionalEntity;
import org.selfbus.sbtools.vdio.model.VdHwProduct;
import org.selfbus.sbtools.vdio.model.VdLanguage;
import org.selfbus.sbtools.vdio.model.VdManufacturer;
import org.selfbus.sbtools.vdio.model.VdMask;
import org.selfbus.sbtools.vdio.model.VdMaskEntry;
import org.selfbus.sbtools.vdio.model.VdMediumType;
import org.selfbus.sbtools.vdio.model.VdObjectPriority;
import org.selfbus.sbtools.vdio.model.VdObjectType;
import org.selfbus.sbtools.vdio.model.VdParameter;
import org.selfbus.sbtools.vdio.model.VdParameterAtomicType;
import org.selfbus.sbtools.vdio.model.VdParameterType;
import org.selfbus.sbtools.vdio.model.VdParameterValue;
import org.selfbus.sbtools.vdio.model.VdProductDescription;
import org.selfbus.sbtools.vdio.model.VdProductToProgram;
import org.selfbus.sbtools.vdio.model.VdProductToProgramToMT;
import org.selfbus.sbtools.vdio.model.VdProgramDescription;
import org.selfbus.sbtools.vdio.model.VdSymbol;
import org.selfbus.sbtools.vdio.model.VdTextAttribute;
import org.selfbus.sbtools.vdio.model.VdVirtualDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The products exporter exports a {@link Project} as an ETS .vd_ products files.
 */
public class ProductsExporter
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ProductsExporter.class);

   private VD vd;
   private Project project;
   private ProductGroup group;
   private ApplicationProgram program;
   private int defaultManufacturerId;
   private int mediumTypeIdTP = 0;
   private int bitmapId = 5507;
   private int deviceParamId = 10000;
   private int maskId, maskEntryId, textId, comObjectUniqueNumber;
   private int uniqueId, programId, deviceId, deviceInfoId, productDescriptionId;
   private int productToProgramId, programDescriptionId;
   private String defaultLangId;

   private final Map<Integer,Integer> deviceIdMap = new HashMap<Integer,Integer>();
   private final Map<Integer,Integer> programIdMap = new HashMap<Integer,Integer>();
   private final Map<Integer,Integer> paramTypeIdMap = new HashMap<Integer,Integer>();
   private final Map<Integer,Integer> paramIdMap = new HashMap<Integer,Integer>();

   /**
    * Create a products exporter.
    */
   public ProductsExporter()
   {
   }

   /**
    * Write a project to the file in VD_ or ZIP format.
    *
    * @param project - the project to write
    * @param file - the target file
    *
    * @throws FileNotFoundException if the directory of the file does not exist
    * @throws VdioException 
    */
   public void write(Project project, File file) throws FileNotFoundException, VdioException
   {
      long startTime = System.currentTimeMillis();

      ProductsWriter writer = new ProductsWriter(ProdEdit.getInstance().getMainFrame());
      writer.setZipPassword(Config.getInstance().get("zipPassword"));

      writer.write(file, createVD(project));

      Config.getInstance().put("zipPassword", writer.getZipPassword());
      LOGGER.debug("Export done ({} seconds)", (System.currentTimeMillis() - startTime) * 0.001);
   }

   /**
    * Write a project to the output stream in VD_ format.
    *
    * @param project - the project to write
    * @param out - the target output stream
    * @throws VdioException 
    */
   public void write(Project project, OutputStream out) throws VdioException
   {
      long startTime = System.currentTimeMillis();

      ProductsWriter writer = new ProductsWriter(ProdEdit.getInstance().getMainFrame());
      writer.write(out, createVD(project));

      LOGGER.debug("Export done ({} seconds)", (System.currentTimeMillis() - startTime) * 0.001);
   }

   /**
    * Create a {@link VD} from the project.
    *
    * @param project - the project to process
    * @return The created VD
    * @throws VdioException 
    */
   VD createVD(Project project) throws VdioException
   {
      this.project = project;

      uniqueId = 22000;
      textId = 1;
      deviceInfoId = uniqueId++;
      maskId = 100;
      maskEntryId = maskId + 1;
      comObjectUniqueNumber = 1;
      productDescriptionId = 1;
      productToProgramId = 1;
      programDescriptionId = 1;
      defaultLangId = project.getDefaultLangId();

      defaultManufacturerId = project.getDefaultManufacturerId();
      if (defaultManufacturerId == 0) defaultManufacturerId = 76;

      vd = new VD();
      vd.comment = "ProdEdit";
      vd.name = "ets2.vd_";
      vd.version = "1.0";
      vd.created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

      vd.virtualDevices = new LinkedList<VdVirtualDevice>();
      vd.products = new LinkedList<VdHwProduct>();
      vd.catalogEntries = new LinkedList<VdCatalogEntry>();
      vd.programs = new LinkedList<VdApplicationProgram>();
      vd.parameterTypes = new LinkedList<VdParameterType>();
      vd.parameters = new LinkedList<VdParameter>();
      vd.parameterValues = new LinkedList<VdParameterValue>();
      vd.communicationObjects = new LinkedList<VdCommunicationObject>();
      vd.textAttributes = new LinkedList<VdTextAttribute>();
      vd.deviceParameters = new LinkedList<VdDeviceParameter>();
      vd.deviceObjects = new LinkedList<VdDeviceObject>();
      vd.productDescriptions = new LinkedList<VdProductDescription>();
      vd.product2programs = new LinkedList<VdProductToProgram>();
      vd.prod2prog2mts = new LinkedList<VdProductToProgramToMT>();
      vd.programDescriptions = new LinkedList<VdProgramDescription>();

      exportManufacturers();
      exportLanguages();
      exportFunctionalEntities();
      exportBcuTypes();
      exportAssemblerTypes();
      exportMediumTypes();
      exportSymbols();
      exportMasks();
      exportAtomicTypes();
      exportObjectTypes();
      exportObjectPriorities();
      exportDeviceInfos();

      exportProductGroups();

      return vd;
   }

   /**
    * Export the manufacturers.
    */
   void exportManufacturers()
   {
      vd.manufacturers = new LinkedList<VdManufacturer>();

      for (int manufacturerId : project.getManufacturers().keySet())
      {
         Manufacturer manufacturer = project.getManufacturer(manufacturerId);

         VdManufacturer m = new VdManufacturer();
         m.setId(manufacturerId);
         m.setName(manufacturer.getName());

         vd.manufacturers.add(m);

         if (defaultManufacturerId == 0)
            defaultManufacturerId = manufacturerId;
      }
   }

   /**
    * Export the languages.
    */
   void exportLanguages()
   {
      vd.languages = new LinkedList<VdLanguage>();

      for (Language lang : project.getLanguages())
      {
         int etsId = LanguageMapper.getEtsId(lang.getId());

         VdLanguage l = new VdLanguage(etsId, lang.getName());
         l.setDatabaseLanguage(defaultLangId.equals(lang.getId()) ? 1 : 0);

         vd.languages.add(l);
      }
   }

   /**
    * Export the functional entities.
    */
   void exportFunctionalEntities()
   {
      vd.functionalEntities = new LinkedList<VdFunctionalEntity>();
      exportFunctionalEntities(project.getFunctionalEntities(), null);
   }

   /**
    * Export the given functional entities and it's children.
    * 
    * @param entities - the entities to export
    * @param parentId - the ID of the parent entity, may be null
    */
   void exportFunctionalEntities(List<FunctionalEntity> entities, Integer parentId)
   {
      int idx = 0;
      for (FunctionalEntity entity : entities)
      {
         VdFunctionalEntity e = new VdFunctionalEntity();
         e.setId(entity.getId());
         e.setParentId(parentId);
         e.setManufacturerId(defaultManufacturerId);
         e.setName(entity.getName());
         e.setShortName(Integer.toString(++idx));
         e.setDescription(entity.getDescription());

         vd.functionalEntities.add(e);
      }      

      for (FunctionalEntity entity : entities)
      {
         exportFunctionalEntities(entity.getChilds(), entity.getId());
      }
   }

   /**
    * Export the BCU types.
    */
   void exportBcuTypes()
   {
      vd.bcuTypes = new LinkedList<VdBcuType>();

      vd.bcuTypes.add(new VdBcuType(1, "BCU 1", "68HCO5B6"));
      vd.bcuTypes.add(new VdBcuType(2, "BIM M112", "68HC11"));
      vd.bcuTypes.add(new VdBcuType(3, "BCU 2", "68HCO5BE12"));
   }

   /**
    * Export the atomic parameter types.
    */
   void exportAtomicTypes()
   {
      vd.parameterAtomicTypes = new LinkedList<VdParameterAtomicType>();
      VdParameterAtomicType t;

      for (ParameterAtomicType type : ParameterAtomicType.values())
      {
         t = new VdParameterAtomicType(type.ordinal(), type.getName(), type.getDispAttr());
         vd.parameterAtomicTypes.add(t);
      }
   }

   /**
    * Export the object types.
    */
   void exportObjectTypes()
   {
      vd.objectTypes = new LinkedList<VdObjectType>();
      VdObjectType t;

      for (ObjectType type : ObjectType.values())
      {
         t = new VdObjectType(type.ordinal(), type.getVdName());
         vd.objectTypes.add(t);
      }
   }

   /**
    * Export the object priorities.
    */
   void exportObjectPriorities()
   {
      vd.objectPriorities = new LinkedList<VdObjectPriority>();

      for (ObjectPriority prio : ObjectPriority.values())
      {
         String name = prio.name();
         name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

         vd.objectPriorities.add(new VdObjectPriority(prio.ordinal(), name));
      }
   }

   /**
    * Export the assembler types.
    */
   void exportAssemblerTypes()
   {
      vd.assemblers = new LinkedList<VdAssembler>();

      vd.assemblers.add(new VdAssembler(1, "IASM", 1));
//      vd.assemblers.add(new VdAssembler(2, "IAR", 2));
//      vd.assemblers.add(new VdAssembler(3, "AD", 3));
   }

   /**
    * Export the medium types.
    */
   void exportMediumTypes()
   {
      vd.mediumTypes = new LinkedList<VdMediumType>();

      vd.mediumTypes.add(new VdMediumType(mediumTypeIdTP, "Twisted Pair", "TP"));
   }

   /**
    * Export the device infos.
    */
   void exportDeviceInfos()
   {
      vd.deviceInfos = new LinkedList<VdDeviceInfo>();

      VdDeviceInfo di = new VdDeviceInfo();
      di.setId(deviceInfoId);
      di.setMaskId(maskId);
      di.setName("BCU 1");
      di.setVisible(2);
      vd.deviceInfos.add(di);

      vd.deviceInfoValues = new LinkedList<VdDeviceInfoValue>();

      VdDeviceInfoValue dv = new VdDeviceInfoValue();
      dv.setId(uniqueId++);
      dv.setDeviceInfoId(deviceInfoId);
      dv.setBitmapId(bitmapId);
      vd.deviceInfoValues.add(dv);
   }

   /**
    * Export the symbols.
    */
   void exportSymbols()
   {
      vd.symbols = new LinkedList<VdSymbol>();

      // TODO
   }

   /**
    * Load a properties file that is searched in the directory "org.selfbus.sbtools.prodedit"
    * in the class path.
    * 
    * @param name - name of the properties file.
    * @return The properties
    * @throws VdioException if the properties file could not be loaded
    */
   Properties getProperties(String name) throws VdioException
   {
      String fileName = "org/selfbus/sbtools/prodedit/" + name;
      Properties prop = new Properties();
      try
      {
         InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
         if (in == null)
            throw new VdioException("property file not found in class path: " + fileName);

         prop.load(in);
      }
      catch (IOException e)
      {
         throw new VdioException("failed to read property file: " + fileName, e);
      }
      return prop;
   }

   Integer getIntValue(Properties prop, String key)
   {
      String v = prop.getProperty(key);
      if (v == null || v.isEmpty()) return null;
      return Integer.parseInt(v);
   }

   /**
    * Export the masks.
    * @throws VdioException 
    */
   void exportMasks() throws VdioException
   {
      vd.masks = new LinkedList<VdMask>();
      vd.maskEntries = new LinkedList<VdMaskEntry>();

      exportMask(maskId, 17);
      exportMaskEntries(maskId, 17);
   }

   /**
    * Export mask of a specific mask version.
    * 
    * @param maskId - the ID of the mask.
    * @param version - the mask version to export.
    *
    * @throws VdioException 
    */
   void exportMask(int maskId, int version) throws VdioException
   {
      Properties mask = getProperties("mask_" + version + ".properties");

      VdMask m = new VdMask();
      m.setId(maskId);
      m.setVersion(getIntValue(mask, "mask_version"));
      m.setUserRamStart(getIntValue(mask, "user_ram_start"));
      m.setUserRamEnd(getIntValue(mask, "user_ram_end"));
      m.setUserEepromStart(getIntValue(mask, "user_eeprom_start"));
      m.setUserEepromEnd(getIntValue(mask, "user_eeprom_end"));
      m.setRunErrorAddress(getIntValue(mask, "run_error_address"));
      m.setAddressTabAddress(getIntValue(mask, "address_tab_address"));
      m.setAssocTabPtrAddress(getIntValue(mask, "assoctabptr_address"));
      m.setCommsTabPtrAddress(getIntValue(mask, "commstabptr_address"));
      m.setManufacturerDataAddress(getIntValue(mask, "manufacturer_data_address"));
      m.setManufacturerDataSize(getIntValue(mask, "manufacturer_data_size"));
      m.setManufacturerIdAddress(getIntValue(mask, "manufacturer_id_address"));
      m.setRouteCountAddress(getIntValue(mask, "routecnt_address"));
      m.setManufacturerIdProtected("true".equalsIgnoreCase(mask.getProperty("manufacturer_id_protected")));
      m.setMaskVersionName(mask.getProperty("mask_version_name"));
      m.setMaskEepromData(DatatypeConverter.parseHexBinary(mask.getProperty("mask_eeprom_data")));
      //getIntValue(mask, "mask_data_length");
      m.setAddressTabLCS(getIntValue(mask, "address_tab_lcs"));
      m.setAssocTabLCS(getIntValue(mask, "assoc_tab_lcs"));
      m.setApplicationProgramLCS(getIntValue(mask, "application_program_lcs"));
      m.setPeiProgramLCS(getIntValue(mask, "pei_program_lcs"));
      m.setLoadControlAddress(getIntValue(mask, "load_control_address"));
      m.setRunControlAddress(getIntValue(mask, "run_control_address"));
      m.setExternalMemoryStart(getIntValue(mask, "external_memory_start"));
      m.setExternalMemoryEnd(getIntValue(mask, "external_memory_end"));
      m.setApplicationProgramRCS(getIntValue(mask, "application_prgroam_rcs"));
      m.setPeiProgramRCS(getIntValue(mask, "pei_program_rcs"));
      m.setPortADdr(getIntValue(mask, "port_a_ddr"));
      m.setPortAddressProtected("true".equalsIgnoreCase(mask.getProperty("port_address_protected")));

      vd.masks.add(m);
   }

   /**
    * Export mask entries of a specific mask version.
    * 
    * @param maskId - the ID of the mask.
    * @param version - the mask version to export.
    *
    * @throws VdioException 
    */
   void exportMaskEntries(int maskId, int version) throws VdioException
   {
      Properties props = getProperties("mask_entries_" + version + ".properties");

      for (Object key : props.keySet())
      {
         String name = (String) key;
         VdMaskEntry e = new VdMaskEntry(maskEntryId++, name);
         e.setMaskId(maskId);
         e.setAddress(Integer.parseInt(props.getProperty(name)));

         vd.maskEntries.add(e);
      }
   }

   /**
    * Export the product groups.
    */
   void exportProductGroups()
   {
      for (ProductGroup group : project.getProductGroups())
      {
         exportProductGroup(group);
      }
   }

   /**
    * Export a product group.
    * 
    * @param group - the group to export
    */
   void exportProductGroup(ProductGroup group)
   {
      this.group = group;

      deviceIdMap.clear();
      programIdMap.clear();

      for (ApplicationProgram program : group.getPrograms())
      {
         exportProgram(program);
      }

      for (VirtualDevice device : group.getDevices())
      {
         exportDevice(device);
      }
   }

   /**
    * Export a virtual device.
    * 
    * @param device - the virtual device to export
    */
   void exportDevice(VirtualDevice device)
   {
      deviceId = uniqueId++;
      deviceIdMap.put(device.getId(), deviceId);

      programId = programIdMap.get(device.getProgramId());

      VdVirtualDevice d = new VdVirtualDevice();
      d.setId(deviceId);
      d.setSymbolId(null); // TODO implement symbols
      d.setCatalogEntryId(device.getCatalogEntry().getId());
      d.setProgramId(programId);
      d.setName(device.getName());
      d.setDescription(device.getDescription());
      d.setFunctionalEntityId(device.getFunctionalEntityId());
      d.setProductTypeId(device.getFunctionalEntityId());
      d.setNumber(1);
      d.setMediumTypes("TP");
      vd.virtualDevices.add(d);

      int productId = deviceId;
      VdHwProduct p = new VdHwProduct();
      p.setId(productId);
      p.setManufacturer(defaultManufacturerId);
      p.setSymbolId(null); // TODO implement symbols
      p.setName(device.getName());
      p.setVersion(device.getVersion());
      p.setComponentAttributes(30);
      p.setBcuTypeId(1);
      p.setHandling(0);
      vd.products.add(p);

      int entryId = deviceId;
      VdCatalogEntry e = new VdCatalogEntry();
      e.setId(entryId);
      e.setProductId(productId);
      e.setManufacturer(defaultManufacturerId);
      e.setSymbolId(null); // TODO implement symbols
      e.setOrderNumber(Integer.toString(vd.virtualDevices.size()));
      e.setName(d.getName());
      e.setDIN(true);
      e.setDesignationType("A");
      e.setEntryStatusCode(10);
      vd.catalogEntries.add(e);

      String description = device.getDescription();
      if (description != null && !description.isEmpty())
      {
         int order = 1;
         for (String line : description.split("\n"))
         {
            VdProductDescription pd = new VdProductDescription();
            pd.setId(productDescriptionId);
            pd.setCatalogEntryId(deviceId);
            pd.setText(line);
            pd.setOrder(order++);
            pd.setLanguageId(LanguageMapper.getEtsId(defaultLangId));
            vd.productDescriptions.add(pd);
         }

         productDescriptionId++;
      }

      int ppId = productToProgramId++;
      VdProductToProgram pp = new VdProductToProgram();
      pp.setId(ppId);
      pp.setProductId(productId);
      pp.setProgramId(programId);
      pp.setStatusCode(10);  // FIXME: observed values: 5, 10
      pp.setRegistrationTS(new SimpleDateFormat("2010-MM-dd HH:mm:ss").format(new Date()));
      pp.setRegistrationYear(110);
      vd.product2programs.add(pp);

      VdProductToProgramToMT ppm = new VdProductToProgramToMT();
      ppm.setId(productToProgramId++);
      ppm.setProd2progId(ppId);
      ppm.setMediumTypeId(mediumTypeIdTP);
      vd.prod2prog2mts.add(ppm);
   }

   /**
    * Export an application program.
    * 
    * @param program - the program to export
    */
   void exportProgram(ApplicationProgram program)
   {
      this.program = program;

      programId = uniqueId++;
      programIdMap.put(program.getId(), programId);

      VdApplicationProgram p = new VdApplicationProgram();
      p.setId(programId);
      p.setSymbolId(null); // TODO implement symbols
      p.setMaskId(maskId);
      p.setName(program.getName());
      p.setVersion(program.getVersion());
      p.setLinkable(true);
      p.setDeviceType(program.getDeviceType());
      p.setPeiType(0);
      p.setAddrTabSize(program.getAddrTabSize());
      p.setAssocTabAddr(program.getAssocTabAddr());
      p.setAssocTabSize(program.getAssocTabSize());
      p.setCommsTabAddr(program.getCommsTabAddr());
      p.setCommsTabSize(program.getCommsTabSize());
      p.setManufacturerId(defaultManufacturerId);
      p.setEepromData(program.getEepromData());
      p.setAssemblerId(1);
      p.setDynamicManagement(true);
      p.setProgramType(program.getTypeId());
      p.setProgramStyle(program.getProgramStyle());
      p.setPollingMaster(false);
      vd.programs.add(p);

      MultiLingualText description = program.getDescription();
      if (description != null)
      {
         for (Element textElem : description.getTexts())
         {
            if (textElem.text == null || textElem.text.isEmpty())
               continue;

            int order = 1;
            int langId = LanguageMapper.getEtsId(textElem.langId);

            for (String line : textElem.text.split("\n"))
            {
               VdProgramDescription pd = new VdProgramDescription();
               pd.setId(programDescriptionId++);
               pd.setProgramId(programId);
               pd.setText(line);
               pd.setOrder(order++);
               pd.setLanguageId(langId);
               vd.programDescriptions.add(pd);
            }
         }
      }

      paramTypeIdMap.clear();
      for (ParameterType paramType : program.getParameterTypes())
      {
         exportParameterType(paramType);
      }

      paramIdMap.clear();
      exportParameters(program.getParameterRoot());
   }

   /**
    * Export a parameter type.
    * 
    * @param paramType - the parameter type to export
    */
   void exportParameterType(ParameterType paramType)
   {
      int typeId = uniqueId++;
      paramTypeIdMap.put(paramType.getId(), typeId);

      VdParameterType t = new VdParameterType();
      t.setId(typeId);
      t.setAtomicTypeId(paramType.getAtomicType().ordinal());
      t.setProgramId(programId);
      t.setName(paramType.getName());
      t.setMinValue(paramType.getMinValue());
      t.setMaxValue(paramType.getMaxValue());
      // t.setDescription(null);
      t.setLowAccess(2);
      t.setHighAccess(2);
      t.setSize(paramType.getSize());
      t.setMinDoubleValue(paramType.getMinDoubleValue());
      t.setMaxDoubleValue(paramType.getMaxDoubleValue());

      vd.parameterTypes.add(t);

      if (paramType.getValues() != null)
      {
         for (ParameterValue value : paramType.getValues())
         {
            int valueId = uniqueId++;
   
            VdParameterValue v = new VdParameterValue();
            v.setParameterTypeId(typeId);
            v.setIntValue(value.getIntValue());
            v.setLabel(value.getLabel().getText(defaultLangId));
            v.setOrder(value.getOrder());
            v.setId(valueId);
            v.setBinaryValue(value.getBinaryValue());
            v.setDoubleValue(value.getDoubleValue());
   
            vd.parameterValues.add(v);
   
            exportText(valueId, value.getLabel(), TextColumn.PARAM_VALUE);
         }
      }
   }

   /**
    * Export the parameter's children.
    * 
    * @param cont - the container to process
    */
   void exportParameters(AbstractParameterContainer cont)
   {
      for (AbstractParameterNode paramNode : cont.getChilds())
      {
         if (paramNode instanceof Parameter)
            exportParameter((Parameter) paramNode);
         else if (paramNode instanceof CommunicationObject)
            exportComObject((CommunicationObject) paramNode);
      }

      for (AbstractParameterNode paramNode : cont.getChilds())
      {
         if (paramNode instanceof AbstractParameterContainer)
            exportParameters((AbstractParameterContainer) paramNode);
      }
   }

   /**
    * Export a parameter.
    * 
    * @param param - the parameter to export
    */
   void exportParameter(Parameter param)
   {
      int id = uniqueId++;
      paramIdMap.put(param.getId(), id);

      VdParameter p = new VdParameter();
      p.setProgramId(programId);
      p.setParamTypeId(paramTypeIdMap.get(param.getTypeId()));
      p.setNumber(param.getNumber());
      p.setName(param.getName());
      p.setParentValue(param.getParentValue());
      p.setSize(param.getSize());
      p.setOrder(param.getOrder());
      p.setAddress(param.getAddress());
      p.setBitOffset(param.getBitOffset());
      p.setDescription(param.getDescription().getText(defaultLangId));
      p.setId(id);
      p.setParentId(paramIdMap.get(param.getParentId()));
      p.setLabel(null); // TODO null ok here?
      p.setDefaultInt(param.getDefaultInt());
      p.setDefaultString(param.getDefaultString());
      p.setDefaultDouble(param.getDefaultDouble());
      p.setPatchAlways(false);

      if (!param.isVisible())
      {
         p.setLowAccess(0);
         p.setHighAccess(0);
      }
      else
      {
         p.setLowAccess(2);
         p.setHighAccess(2);
      }

      vd.parameters.add(p);

      exportText(id, param.getDescription(), TextColumn.PARAM_DESCRIPTION);

      VdDeviceParameter dp = new VdDeviceParameter();
      dp.setDeviceId(deviceId);
      dp.setId(deviceParamId++);
      dp.setParameterId(id);
      dp.setNumber(p.getNumber());
      dp.setVisible(param.isVisible());
      dp.setIntValue(p.getDefaultInt());
      dp.setStrValue(p.getDefaultString());
      dp.setProgramType(0);
      dp.setDoubleValue(p.getDefaultDouble());

      vd.deviceParameters.add(dp);
   }

   /**
    * Export a communication object.
    * 
    * @param comObject - the communication object to export
    */
   void exportComObject(CommunicationObject comObject)
   {
      int id = uniqueId++;

      VdCommunicationObject c = new VdCommunicationObject();
      c.setProgramId(programId);
      c.setName(comObject.getName().getText(defaultLangId));
      c.setFunction(comObject.getFunction().getText(defaultLangId));
      c.setReadEnabled(comObject.isReadEnabled());
      c.setWriteEnabled(comObject.isWriteEnabled());
      c.setCommEnabled(comObject.isCommEnabled());
      c.setTransEnabled(comObject.isTransEnabled());
      c.setOrder(comObject.getOrder());
      c.setParentParameterValue(comObject.getParentValue());
      c.setId(id);
      c.setParentParameterId(paramIdMap.get(comObject.getParentId()));
      c.setNumber(comObject.getNumber());
      c.setDescription(comObject.getDescription().getText(defaultLangId));
      c.setTypeId(comObject.getType().getId());
      c.setPriorityId(comObject.getPriority().getId());
      c.setUpdateEnabled(false);
      c.setUniqueNumber(comObjectUniqueNumber++);  // FIXME

      vd.communicationObjects.add(c);

      exportText(id, comObject.getName(), TextColumn.COM_OBJECT_NAME);
      exportText(id, comObject.getFunction(), TextColumn.COM_OBJECT_FUNCTION);
      exportText(id, comObject.getDescription(), TextColumn.COM_OBJECT_DESCRIPTION);

      // FIXME need to call this once per VirtualDevice not once per ApplicationProgram
     xxx VdDeviceObject cd = new VdDeviceObject();
      cd.setDeviceId(deviceId);
      cd.setObjectPriority(c.getPriorityId());
      cd.setObjectRead(comObject.isReadEnabled());
      cd.setObjectWrite(comObject.isWriteEnabled());
      cd.setObjectComm(comObject.isCommEnabled());
      cd.setObjectTrans(comObject.isTransEnabled());
      cd.setId(deviceParamId++);
      cd.setNumber(c.getNumber());
      cd.setVisible(true);
      cd.setUpdate(false);
      cd.setUniqueNumber(c.getUniqueNumber());
      cd.setObjectTypeId(c.getTypeId());

      vd.deviceObjects.add(cd);
   }

   /**
    * Export a multi lingual text
    * 
    * @param id - the unique text ID
    * @param text - the text to export
    * @param col - the text column
    */
   void exportText(int id, MultiLingualText text, TextColumn col)
   {
      for (Element elem : text.getTexts())
      {
         if (elem.text != null && !elem.text.isEmpty())
         {
            VdTextAttribute t = new VdTextAttribute();
            t.setId(textId++);
            t.setEntityId(id);
            t.setLanguageId(LanguageMapper.getEtsId(elem.langId));
            t.setText(elem.text);
            t.setColumnId(col.column);
   
            vd.textAttributes.add(t);
         }
      }
   }
}
