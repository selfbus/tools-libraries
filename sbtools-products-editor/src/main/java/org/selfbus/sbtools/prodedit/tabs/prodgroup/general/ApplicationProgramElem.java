package org.selfbus.sbtools.prodedit.tabs.prodgroup.general;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.text.JTextComponent;

import org.selfbus.sbtools.prodedit.ProdEdit;
import org.selfbus.sbtools.prodedit.binding.IdValueConverter;
import org.selfbus.sbtools.prodedit.binding.ListValidationHandler;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.tabs.internal.CategoryElem;
import org.selfbus.sbtools.prodedit.utils.FontUtils;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.value.ConverterValueModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationResultViewFactory;

/**
 * An element that displays the {@link ApplicationProgram application program} of a device.
 */
public class ApplicationProgramElem implements CategoryElem
{
   protected final ProductGroup group;

   private final PresentationModel<ApplicationProgram> detailsModel = new PresentationModel<ApplicationProgram>();
   private final Validator<ApplicationProgram> validator = new DetailsFormValidator();
   private final ListValidationHandler<ApplicationProgram> validationHandler = new ListValidationHandler<ApplicationProgram>(detailsModel, validator);

   private final JPanel detailsPanel;
   private final JToolBar toolBar = new JToolBar();

   private final ValueModel idValue = new ConverterValueModel(detailsModel.getModel("id"), new IdValueConverter());
   private final JLabel idField = BasicComponentFactory.createLabel(idValue);

   private final ValueModel nameValue = detailsModel.getModel("name");
   private final JTextComponent nameField = BasicComponentFactory.createTextField(nameValue, false);

   /**
    * Create a {@link VirtualDevice virtual device} display element.
    * 
    * @param group - the product group
    */
   public ApplicationProgramElem(final ProductGroup group)
   {
      this.group = group;

      FormLayout layout = new FormLayout("6dlu,l:p,4dlu,f:p:g,6dlu", 
         "8dlu,p,8dlu,p,4dlu,p,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,f:p:g,p,4dlu");
      PanelBuilder builder = new PanelBuilder(layout);
      CellConstraints cc = new CellConstraints();
      int row = 2;

      builder.addLabel(I18n.getMessage("ApplicationProgramElem.caption"), cc.rcw(row, 2, 3))
         .setFont(FontUtils.getCaptionFont());

      row += 2;
      builder.addLabel(I18n.getMessage("ApplicationProgramElem.name"), cc.rc(row, 2));
      builder.add(nameField, cc.rc(row, 4));

      row += 2;
      builder.addLabel(I18n.getMessage("ApplicationProgramElem.id"), cc.rc(row, 2));
      builder.add(idField, cc.rc(row, 4));

      builder.add(Box.createVerticalGlue(), cc.rcw(++row, 2, 3));
      
      JComponent reportPane = ValidationResultViewFactory.createReportList(validationHandler.getValidationResultModel());
      builder.add(reportPane, cc.rcw(++row, 2, 3));

      detailsPanel = builder.build();

      validationHandler.setValidatedContainer(detailsPanel);

      updateContents();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName()
   {
      return I18n.getMessage("ApplicationProgramElem.title");
   }
   
   /**
    * @return Null as there is no list panel.
    */
   @Override
   public JComponent getListPanel()
   {
      return null;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public JComponent getDetailsPanel()
   {
      return detailsPanel;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public JToolBar getToolBar()
   {
      return toolBar;
   }

   /**
    * Update the contents.
    */
   protected void updateContents()
   {
      final Project project = ProdEdit.getInstance().getProjectService().getProject();
      if (project != null)
      {
//         selectionInFunctionalEntities.setList(project.getFunctionalEntities());
//         selectionInCatalogEntries.setList(project.getCatalogEntries())
      }
   }

   /**
    * Private validator for details form input.
    */
   private class DetailsFormValidator implements Validator<ApplicationProgram>
   {
      @Override
      public ValidationResult validate(ApplicationProgram program)
      {
         ValidationResult result = new ValidationResult();

         return result;
      }
   }

   /**
    * Set the virtual device.
    *
    * @param device - the device to set
    */
   public void setDevice(VirtualDevice device)
   {
      detailsModel.setBean(device == null ? null : group.getProgram(device.getProgramId()));
   }
}
