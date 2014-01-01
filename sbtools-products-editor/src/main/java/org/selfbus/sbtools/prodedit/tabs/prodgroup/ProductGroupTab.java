package org.selfbus.sbtools.prodedit.tabs.prodgroup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.prodedit.ProdEdit;
import org.selfbus.sbtools.prodedit.actions.RemoveSelectionInListAction;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.AbstractProjectListener;
import org.selfbus.sbtools.prodedit.model.ProjectListener;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterNode;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterType;
import org.selfbus.sbtools.prodedit.tabs.internal.AbstractCloseableAccordionDetailsTab;
import org.selfbus.sbtools.prodedit.tabs.internal.MixedCategoryElem;
import org.selfbus.sbtools.prodedit.tabs.internal.ObjectActivatedListener;
import org.selfbus.sbtools.prodedit.tabs.prodgroup.general.ApplicationProgramElem;
import org.selfbus.sbtools.prodedit.tabs.prodgroup.general.VirtualDeviceElem;
import org.selfbus.sbtools.prodedit.tabs.prodgroup.memory.MemoryElem;
import org.selfbus.sbtools.prodedit.tabs.prodgroup.parameter.ParametersElem;
import org.selfbus.sbtools.prodedit.utils.FontUtils;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;

/**
 * A tab panel for editing a {@link ProductGroup products group}.
 */
public class ProductGroupTab extends AbstractCloseableAccordionDetailsTab
{
   private static final long serialVersionUID = -7440697803186717305L;

   // The product group that the tab displays
   private final ProductGroup group;

   private SelectionInList<VirtualDevice> selectionInList = new SelectionInList<VirtualDevice>(new LinkedList<VirtualDevice>());
   @SuppressWarnings("unchecked")
   private final JComboBox<VirtualDevice> currentDeviceCombo = BasicComponentFactory.createComboBox(selectionInList);

   private JButton addDeviceButton, duplicateDeviceButton, removeDeviceButton;

   private final VirtualDeviceElem virtualDeviceElem;
   private final ApplicationProgramElem applicationProgramElem;
   private final ParameterTypesElem parameterTypesElem;
   private final ParametersElem parametersElem;
   private final MemoryElem memoryElem;


   /**
    * Create a tab panel for editing a {@link ProductGroup}.
    * 
    * @param group - the product group to edit.
    */
   public ProductGroupTab(ProductGroup group)
   {
      super();

      setupTopToolBar();

      this.group = group;
      setTitle(group.getName());

      MixedCategoryElem mixed = new MixedCategoryElem(I18n.getMessage("ProductGroupTab.generalName"));
      addCategory(mixed);

      virtualDeviceElem = new VirtualDeviceElem(group, selectionInList); 
      mixed.addCategory(virtualDeviceElem);

      applicationProgramElem = new ApplicationProgramElem(group);
      mixed.addCategory(applicationProgramElem);

      parameterTypesElem = new ParameterTypesElem(group);
      addCategory(parameterTypesElem);

      parametersElem = new ParametersElem(group);
      addCategory(parametersElem);

      memoryElem = new MemoryElem(group);
      addCategory(memoryElem);

      ProdEdit.getInstance().getProjectService().addListener(projectListener);

      selectionInList.getSelectionHolder().addValueChangeListener(new PropertyChangeListener()
      {
         @Override
         public void propertyChange(PropertyChangeEvent e)
         {
            VirtualDevice device = selectionInList.getSelection();

            applicationProgramElem.setDevice(device);
            parameterTypesElem.setDevice(device);
            parametersElem.setDevice(device);
            memoryElem.setDevice(device);
         }
      });

      memoryElem.setParamActivatedListener(objectActivatedListener);

      updateContents();

      SwingUtilities.invokeLater(new Runnable()
      {
         @Override
         public void run()
         {
            if (currentDeviceCombo.getItemCount() > 0)
               currentDeviceCombo.setSelectedIndex(0);
         }
      });
   }

   /**
    * A listener for object activation.
    */
   private final ObjectActivatedListener objectActivatedListener = new ObjectActivatedListener()
   {
      @Override
      public void objectActivated(Object o)
      {
         if (o instanceof AbstractParameterNode)
         {
            setVisibleCategory(parametersElem);
            parametersElem.setSelected((AbstractParameterNode) o);
         }
//         else if (o instanceof ParameterType)
//         {
//            setVisibleCategory(parameterTypesElem);
//            parameterTypesElem.setSelected((ParameterType) o);
//         }
      }
   };
   
   /**
    * Setup the top tool bar.
    */
   protected void setupTopToolBar()
   {
      JToolBar toolBar = new JToolBar();
      add(toolBar, BorderLayout.NORTH);

      JLabel lbl = new JLabel(I18n.getMessage("ProductGroupTab.currentDevice") + ':');
      lbl.setFont(FontUtils.getCaptionFont());
      toolBar.add(lbl);
      toolBar.add(Box.createHorizontalStrut(8));

      currentDeviceCombo.setPreferredSize(new Dimension(400, currentDeviceCombo.getPreferredSize().height));
      toolBar.add(currentDeviceCombo);

      addDeviceButton = new JButton(addDeviceAction);
      addDeviceButton.setText(null);
      toolBar.add(addDeviceButton);

      duplicateDeviceButton = new JButton(duplicateDeviceAction);
      duplicateDeviceButton.setText(null);
      toolBar.add(duplicateDeviceButton);

      removeDeviceButton = new JButton(new RemoveSelectionInListAction(selectionInList, I18n.getMessage("ProductGroupTab.removeDeviceTip"), ImageCache.getIcon("icons/editdelete")));
      removeDeviceButton.setText(null);
      toolBar.add(removeDeviceButton);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
      ProdEdit.getInstance().getProjectService().removeListener(projectListener);
      super.close();
   }

   /**
    * Update the contents.
    */
   protected void updateContents()
   {
      selectionInList.setList(group.getDevices());
   }

   /**
    * Switch to the parameter types category and show the given parameter type.
    *
    * @param type - the parameter type to make visible.
    */
   public void showParameterType(ParameterType type)
   {
      setVisibleCategory(parameterTypesElem);
      parameterTypesElem.setSelected(type);
   }

   /**
    * Action: add a new device.
    */
   private final BasicAction addDeviceAction = new BasicAction("add", I18n.getMessage("ProductGroupTab.addDeviceTip"), ImageCache.getIcon("icons/filenew"))
   {
      private static final long serialVersionUID = 1;

      @Override
      public void actionEvent(ActionEvent event)
      {
         Project project = ProdEdit.getInstance().getProject();
         if (project != null)
            selectionInList.setSelection(group.createDevice());
      }
   };

   /**
    * Action: duplicate a device.
    */
   private final BasicAction duplicateDeviceAction = new BasicAction("duplicate", I18n.getMessage("ProductGroupTab.duplicateDeviceTip"), ImageCache.getIcon("icons/editcopy"))
   {
      private static final long serialVersionUID = 1;

      @Override
      public void actionEvent(ActionEvent event)
      {
         Project project = ProdEdit.getInstance().getProject();
         if (project != null)
         {
            VirtualDevice device = group.createDevice();
            selectionInList.setSelection(device);
         }
      }
   };

   /**
    * Private project listener
    */
   private final ProjectListener projectListener = new AbstractProjectListener()
   {
      @Override
      public void productGroupChanged(ProductGroup group)
      {
         if (ProductGroupTab.this.group == group)
         {
            setName(group.getName());
         }
      }
   };
}
