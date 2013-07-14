package org.selfbus.sbtools.prodedit.tabs.prodgroup;

import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.Box;
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
import org.selfbus.sbtools.prodedit.binding.ValidationHandler;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.prodgroup.ParameterType;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.tabs.internal.CategoryElem;
import org.selfbus.sbtools.prodedit.utils.FontUtils;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
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
public class ParametersElem implements CategoryElem, CloseableComponent
{
   private final ProductGroup group;

   private SelectionInList<ParameterType> selectionInList = new SelectionInList<ParameterType>(new LinkedList<ParameterType>());
   private final PresentationModel<ParameterType> detailsModel = new PresentationModel<ParameterType>(selectionInList);
   private final Validator<ParameterType> validator = new DetailsFormValidator();
   private final ValidationHandler<ParameterType> validationHandler = new ValidationHandler<ParameterType>(detailsModel, validator);

   @SuppressWarnings("unchecked")
   private final JList<ParameterType> paramTypeList = BasicComponentFactory.createList(selectionInList);
   private final JScrollPane paramTypeScrollPane = new JScrollPane(paramTypeList);

   private final JPanel detailsPanel;
   private final JToolBar toolBar = new JToolBar();
   
   private final JLabel idField = BasicComponentFactory.createLabel(detailsModel.getModel("idStr"));
   private final JTextComponent nameField = BasicComponentFactory.createTextField(validationHandler.getModel("name"), false);

   private SelectionInList<ParameterAtomicType> selectionInAtomicType = new SelectionInList<ParameterAtomicType>(ParameterAtomicType.values());
   @SuppressWarnings("unchecked")
   private final JComboBox<ParameterAtomicType> atomicTypeCombo = BasicComponentFactory.createComboBox(selectionInAtomicType);

   /**
    * Create a {@link Product products} display element.
    * 
    * @param group - the products group to display.
    */
   public ParametersElem(ProductGroup group)
   {
      this.group = group;

      FormLayout layout = new FormLayout("6dlu,l:p,4dlu,f:p:g,6dlu", 
         "8dlu,p,8dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,f:p:g,p,4dlu");
      PanelBuilder builder = new PanelBuilder(layout);
      CellConstraints cc = new CellConstraints();
      int row = 2;
      
      builder.addLabel(I18n.getMessage("ParametersElem.caption"), cc.rcw(row, 2, 3))
         .setFont(FontUtils.getCaptionFont());

      row += 2;
      builder.addLabel(I18n.getMessage("ParametersElem.name"), cc.rc(row, 2));
      builder.add(nameField, cc.rc(row, 4));
      ValidationComponentUtils.setMandatoryBackground(nameField);

      row += 2;
      builder.addLabel(I18n.getMessage("ParametersElem.type"), cc.rc(row, 2));
      builder.add(atomicTypeCombo, cc.rc(row, 4));

      row += 2;
      builder.addLabel(I18n.getMessage("ParametersElem.id"), cc.rc(row, 2));
      builder.add(idField, cc.rc(row, 4));

      builder.add(Box.createVerticalGlue(), cc.rcw(++row, 2, 3));
      
      JComponent reportPane = ValidationResultViewFactory.createReportList(validationHandler.getValidationResultModel());
      builder.add(reportPane, cc.rcw(++row, 2, 3));

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
      toolBar.add(new BasicAction("add", I18n.getMessage("ParametersElem.addTip"), ImageCache.getIcon("icons/add"))
      {
         private static final long serialVersionUID = 1;

         @Override
         public void actionEvent(ActionEvent event)
         {
            // TODO
         }
      });

      //
      //  Action: remove the current virtual device
      //
      toolBar.add(new RemoveSelectionInListAction(selectionInList, I18n.getMessage("ParametersElem.removeTip"))); 
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
      return I18n.getMessage("ParametersElem.title");
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public JComponent getListPanel()
   {
      return paramTypeScrollPane;
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
    * Set the virtual device.
    *
    * @param device - the device to set
    */
   public void setDevice(VirtualDevice device)
   {
//      detailsModel.setBean(device == null ? null : group.getProgram(device.getProgramId()));
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

         return result;
      }
   }
}
