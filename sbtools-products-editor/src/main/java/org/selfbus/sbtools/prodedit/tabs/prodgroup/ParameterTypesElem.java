package org.selfbus.sbtools.prodedit.tabs.prodgroup;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.text.JTextComponent;

import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.components.CloseableComponent;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.prodedit.actions.RemoveSelectionInListAction;
import org.selfbus.sbtools.prodedit.binding.IntegerValueConverter;
import org.selfbus.sbtools.prodedit.binding.ValidationHandler;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.ParameterType;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.renderer.ParameterAtomicTypeComboBoxRenderer;
import org.selfbus.sbtools.prodedit.tabs.internal.AbstractCategoryElem;
import org.selfbus.sbtools.prodedit.utils.FontUtils;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.AbstractValueModel;
import com.jgoodies.binding.value.ConverterValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.jgoodies.validation.view.ValidationResultViewFactory;

/**
 * An element that displays the {@link ParameterType parameter types} of a {@link VirtualDevice device}.
 */
public class ParameterTypesElem extends AbstractCategoryElem implements CloseableComponent
{
   private final ProductGroup group;
   private ApplicationProgram program;

   private SelectionInList<ParameterType> selectionInList = new SelectionInList<ParameterType>(new LinkedList<ParameterType>());
   private final PresentationModel<ParameterType> detailsModel = new PresentationModel<ParameterType>(selectionInList);
   private final Validator<ParameterType> validator = new DetailsFormValidator();
   private final ValidationHandler<ParameterType> validationHandler = new ValidationHandler<ParameterType>(detailsModel, validator);
   private final IntegerValueConverter intValueConverter = new IntegerValueConverter();

   @SuppressWarnings("unchecked")
   private final JList<ParameterType> paramTypeList = BasicComponentFactory.createList(selectionInList);

   private final JPanel typeDetailsPanel;

   private final JLabel idField = BasicComponentFactory.createLabel(detailsModel.getModel("idStr"));

   private final AbstractValueModel nameValue =  validationHandler.getModel("name");
   private final JTextComponent nameField = BasicComponentFactory.createTextField(nameValue, false);

   private final AbstractValueModel sizeValue = validationHandler.getModel("size");
   private final JTextComponent sizeField = BasicComponentFactory.createTextField(new ConverterValueModel(sizeValue, intValueConverter), false);

   private final AbstractValueModel minValueValue = validationHandler.getModel("minValue");
   private final JTextComponent minValueField = BasicComponentFactory.createTextField(new ConverterValueModel(minValueValue, intValueConverter), false);

   private final AbstractValueModel maxValueValue = validationHandler.getModel("maxValue");
   private final JTextComponent maxValueField = BasicComponentFactory.createTextField(new ConverterValueModel(maxValueValue, intValueConverter), false);

   private SelectionInList<ParameterAtomicType> selectionInAtomicType = new SelectionInList<ParameterAtomicType>(ParameterAtomicType.values(), detailsModel.getModel("atomicType"));
   @SuppressWarnings("unchecked")
   private final JComboBox<ParameterAtomicType> atomicTypeCombo = BasicComponentFactory.createComboBox(selectionInAtomicType);

   /**
    * Create a {@link Product products} display element.
    * 
    * @param group - the products group to display.
    */
   public ParameterTypesElem(ProductGroup group)
   {
      this.group = group;

      listScrollPane = new JScrollPane(paramTypeList);
      toolBar = new JToolBar();

      atomicTypeCombo.setRenderer(new ParameterAtomicTypeComboBoxRenderer(atomicTypeCombo.getRenderer()));
      
      FormLayout layout = new FormLayout("6dlu, l:p, 4dlu, f:p:g, 4dlu, l:p, 4dlu, f:p:g, 6dlu", 
         "8dlu,p,8dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,f:p:g,f:p:g,4dlu");
      PanelBuilder builder = new PanelBuilder(layout);
      CellConstraints cc = new CellConstraints();

      int row = 2;      
      builder.addLabel(I18n.getMessage("ParameterTypesElem.caption"), cc.rcw(row, 2, 7))
         .setFont(FontUtils.getCaptionFont());

      row += 2;
      builder.addLabel(I18n.getMessage("ParameterTypesElem.name"), cc.rc(row, 2));
      builder.add(nameField, cc.rcw(row, 4, 5));
      ValidationComponentUtils.setMandatoryBackground(nameField);

      row += 2;
      builder.addLabel(I18n.getMessage("ParameterTypesElem.id"), cc.rc(row, 2));
      builder.add(idField, cc.rcw(row, 4, 5));

      row += 2;
      builder.addLabel(I18n.getMessage("ParameterTypesElem.type"), cc.rc(row, 2));
      builder.add(atomicTypeCombo, cc.rcw(row, 4, 5));

      row += 2;
      builder.addLabel(I18n.getMessage("ParameterTypesElem.size"), cc.rc(row, 2));
      builder.add(sizeField, cc.rc(row, 4));
      builder.addLabel(I18n.getMessage("ParameterTypesElem.sizeUnit"), cc.rcw(row, 6, 3));

      row += 2;
      builder.addLabel(I18n.getMessage("ParameterTypesElem.minValue"), cc.rc(row, 2));
      builder.add(minValueField, cc.rc(row, 4));
      builder.addLabel(I18n.getMessage("ParameterTypesElem.maxValue"), cc.rc(row, 6));
      builder.add(maxValueField, cc.rc(row, 8));

      row += 2;
      typeDetailsPanel = new JPanel();
      typeDetailsPanel.setLayout(new BorderLayout());
      builder.add(typeDetailsPanel, cc.rcw(row, 2, 7));
      
      JComponent reportPane = ValidationResultViewFactory.createReportList(validationHandler.getValidationResultModel());
      builder.add(reportPane, cc.rcw(++row, 2, 7));

      detailsPanel = builder.build();

      validationHandler.setValidatedContainer(detailsPanel);
      validationHandler.observeSelectionChange(selectionInList);

//      ProdEdit.getInstance().getProjectService().addListener(projectListener);
      setupToolBar();
   }

   /**
    * Setup the tool bar.
    */
   private void setupToolBar()
   {
      //
      //  Action: add a virtual device
      //
      toolBar.add(new BasicAction("add", I18n.getMessage("ParameterTypesElem.addTip"), ImageCache.getIcon("icons/add"))
      {
         private static final long serialVersionUID = 1;

         @Override
         public void actionEvent(ActionEvent event)
         {
            selectionInList.setSelection(program.createParameterType());
         }
      });

      //
      //  Action: remove the current virtual device
      //
      toolBar.add(new RemoveSelectionInListAction(selectionInList, I18n.getMessage("ParameterTypesElem.removeTip"))); 
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName()
   {
      return I18n.getMessage("ParameterTypesElem.title");
   }

   /**
    * Set the virtual device.
    *
    * @param device - the device to set
    */
   public void setDevice(VirtualDevice device)
   {
      program = group.getProgram(device);
      selectionInList.setList(program != null ? program.getParameterTypes() : new LinkedList<ParameterType>());
   }

   /**
    * Private validator for details form input.
    */
   private class DetailsFormValidator implements Validator<ParameterType>
   {
      @Override
      public ValidationResult validate(ParameterType paramType)
      {
         ValidationResult result = new ValidationResult();

         String name = nameValue.getString();
         if (name == null || name.isEmpty())
            result.addError(I18n.getMessage("ParameterTypesElem.errNameEmpty"), nameField);

         Integer size = (Integer) sizeValue.getValue();
         if (size == null || size < 1)
         {
            result.addError(I18n.getMessage("ParameterTypesElem.errSize"), sizeField);
         }
         else
         {
            int minValueLimit, maxValueLimit;
            if (ParameterAtomicType.SIGNED == paramType.getAtomicType())
            {
               minValueLimit = -(1 << (size - 1));
               maxValueLimit = 1 << (size - 1) - 1;
            }
            else
            {
               minValueLimit = 0;
               maxValueLimit = (1 << size) - 1;
            }

            Integer minValue = (Integer) minValueValue.getValue();
            if (minValue == null)
               result.addError(I18n.getMessage("ParameterTypesElem.errMinValueEmpty"), minValueField);
            else if (minValue < minValueLimit)
               result.addError(I18n.formatMessage("ParameterTypesElem.errMinValueBounds", Integer.toString(minValueLimit)), minValueField);

            Integer maxValue = (Integer) maxValueValue.getValue();
            if (maxValue == null)
               result.addError(I18n.getMessage("ParameterTypesElem.errMaxValueEmpty"), maxValueField);
            else if (maxValue > maxValueLimit)
               result.addError(I18n.formatMessage("ParameterTypesElem.errMaxValueBounds", Integer.toString(maxValueLimit)), maxValueField);

            if (minValue != null && maxValue != null && minValue > maxValue)
               result.addError(I18n.getMessage("ParameterTypesElem.errMaxValueLessMinValue"), maxValueField);
         }

         return result;
      }
   }
}
