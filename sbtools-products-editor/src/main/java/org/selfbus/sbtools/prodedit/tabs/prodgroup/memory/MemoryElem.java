package org.selfbus.sbtools.prodedit.tabs.prodgroup.memory;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.selfbus.sbtools.common.gui.utils.TableUtils;
import org.selfbus.sbtools.prodedit.ProdEdit;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.global.Mask;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.model.prodgroup.VirtualDevice;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterContainer;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.AbstractParameterNode;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.CommunicationObject;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.Parameter;
import org.selfbus.sbtools.prodedit.renderer.ParameterMemoryListCellRenderer;
import org.selfbus.sbtools.prodedit.tabs.internal.AbstractCategoryElem;
import org.selfbus.sbtools.prodedit.utils.FontUtils;

import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * An element that displays the memory layout of a device.
 */
public class MemoryElem extends AbstractCategoryElem
{
   private final ProductGroup group;
   private VirtualDevice device;

   private final DefaultListModel<AbstractParameterNode> paramListModel = new DefaultListModel<AbstractParameterNode>();
   private final JList<AbstractParameterNode> paramList = new JList<AbstractParameterNode>(paramListModel);
   private final Set<MemoryRange> ranges = new TreeSet<MemoryRange>();
   private MemoryTableModel tableModel = new MemoryTableModel(0, 512);
   private JTable table = new JTable(tableModel);
   private JScrollPane tableScrollPane = new JScrollPane(table);
   private Color backgroundColor;

   /**
    * Create an element that displays the memory layout of a device.
    * 
    * @param group - the product group
    */
   public MemoryElem(final ProductGroup group)
   {
      this.group = group;

      ParameterMemoryListCellRenderer paramListCellRenderer =
         new ParameterMemoryListCellRenderer(paramList.getCellRenderer());
      paramList.setCellRenderer(paramListCellRenderer);
      listScrollPane = new JScrollPane(paramList);

      table.setDefaultRenderer(MemoryCell.class, new MemoryCellRenderer());
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      table.setCellSelectionEnabled(true);
      table.setDoubleBuffered(false);
      TableUtils.pack(table, 2);

      FormLayout layout = new FormLayout("6dlu,f:p:g,6dlu", "4dlu,p,4dlu,f:p:g,p,4dlu");
      PanelBuilder builder = new PanelBuilder(layout);
      CellConstraints cc = new CellConstraints();
      int row = 2;

      builder.addLabel(I18n.getMessage("MemoryElem.caption"), cc.rc(row, 2))
         .setFont(FontUtils.getCaptionFont());

      row += 2;
      builder.add(tableScrollPane, cc.rc(row, 2));

      builder.add(Box.createVerticalGlue(), cc.rc(++row, 2));

      detailsPanel = builder.build();
      backgroundColor = table.getBackground();

      updateContents();

      paramList.addListSelectionListener(new ListSelectionListener()
      {
         @Override
         public void valueChanged(ListSelectionEvent e)
         {
            AbstractParameterNode node = paramList.getSelectedValue();
            if (node != null)
            {
               int row = node.getAddress() >> 4;
               int col = (node.getAddress() & 15) + 1;

               table.setRowSelectionInterval(row, row);
               table.setColumnSelectionInterval(col, col);
            }
         }
      });
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName()
   {
      return I18n.getMessage("MemoryElem.title");
   }

   /**
    * Set the virtual device.
    *
    * @param device - the device to set
    */
   public void setDevice(VirtualDevice device)
   {
      this.device = device;
      updateContents();
   }

   /**
    * Update the contents.
    */
   protected void updateContents()
   {
      ranges.clear();
      tableModel.clear();
      paramListModel.removeAllElements();

      Project project = ProdEdit.getInstance().getProjectService().getProject();
      if (project == null || device == null)
      {
         return;
      }

      ApplicationProgram program = group.getProgram(device);
      Mask mask = project.getMask(program.getMaskVersion());

      int maxAddr = Math.max(mask.getUserRamEnd(), mask.getUserEepromEnd());
      tableModel.setSize((maxAddr + 15) & ~15);

      int start;

      start = mask.getUserRamStart();
      createRange(start, mask.getUserRamEnd() - start, I18n.getMessage("MemoryElem.userRam"));

      start = mask.getUserEepromStart();
      createRange(start, mask.getUserEepromEnd() - start, I18n.getMessage("MemoryElem.userEeprom"));

      start = program.getAssocTabAddr();
      createRange(start, program.getAssocTabSize(), I18n.getMessage("MemoryElem.assocTab"));

      start = program.getCommsTabAddr();
      createRange(start, program.getCommsTabSize(), I18n.getMessage("MemoryElem.commsTab"));

      start = program.getEepromData()[mask.getAddressTabAddress() - mask.getUserEepromStart()] + mask.getUserEepromStart();
      createRange(start, program.getAddrTabSize(), I18n.getMessage("MemoryElem.addrTab"));

      updateMemoryCellRanges();
      addParameterRanges();

      sortParamList();
   }

   /**
    * Sort the list of parameters.
    */
   protected void sortParamList()
   {
      AbstractParameterNode[] arr = new AbstractParameterNode[paramListModel.size()];
      paramListModel.copyInto(arr);

      Arrays.sort(arr, new Comparator<AbstractParameterNode>()
      {
         @Override
         public int compare(AbstractParameterNode a, AbstractParameterNode b)
         {
            return a.getAddress() - b.getAddress();
         }
      });

      paramListModel.removeAllElements();
      for (AbstractParameterNode elem : arr)
         paramListModel.addElement(elem);
   }

   /**
    * Add memory ranges for parameters and com-objects.
    */
   protected void addParameterRanges()
   {
      MemoryRange paramRange = createRange(0, 0, I18n.getMessage("MemoryElem.param"));
      MemoryRange comObjRange = createRange(0, 0, I18n.getMessage("MemoryElem.comObject"));

      ApplicationProgram program = group.getProgram(device);
      addParameterRanges(program.getParameterTreeModel().getRoot().getChilds(), paramRange, comObjRange);
   }

   /**
    * Add memory ranges for parameters and com-objects of params and its children.
    * 
    * @param params - the parameter container to process
    * @param paramRange - the memory range for parameters
    * @param comObjRange - the memory range for com-objects
    */
   protected void addParameterRanges(ArrayListModel<AbstractParameterNode> params,
      MemoryRange paramRange, MemoryRange comObjRange)
   {
      for (AbstractParameterNode node : params)
      {
         if (node instanceof AbstractParameterContainer)
         {
            addParameterRanges(((AbstractParameterContainer) node).getChilds(), paramRange, comObjRange);
         }

         Integer addr = node.getAddress();
         if (addr == null || addr == 0)
            continue;

         MemoryRange range = null;
         String label = null;
         int size = 1;

         if (node instanceof Parameter)
         {
            Parameter param = (Parameter) node;

            label = param.getDescription().getDefaultText()
               + ", #" + param.getNumber()+ " ID:" + param.getId();

            range = paramRange;
            size = (param.getSize() + 7) >> 3;

            paramListModel.addElement(node);
         }
         else if (node instanceof CommunicationObject)
         {
            CommunicationObject comObj = (CommunicationObject) node;

            label = comObj.getName().getDefaultText()
               + ", " + comObj.getType()+ ", #" + comObj.getNumber()
               + " ID:" + comObj.getId();

            range = comObjRange;
            size = (comObj.getType().getBitLength() + 7) >> 3;

            paramListModel.addElement(node);
         }

         if (range != null)
         {
            for (int i = 0; i < size; ++i)
            {
               MemoryCell cell = tableModel.getValueAt(addr + i);
               cell.setRange(range);

               String cellLabel = cell.getLabel();
               if (cellLabel == null)
                  cellLabel = label;
               else cellLabel += "<br>" + label;

               cell.setLabel(cellLabel);
            }
         }
      }
   }
   
   /**
    * Update the memory ranges of all memory cells. Call when the memory ranges
    * have changed.
    */
   public void updateMemoryCellRanges()
   {
      final int addr0 = tableModel.getStartAddr();
      final int sz = tableModel.getSize();

      // Clear the memory range of all memory cells
      for (int i = sz - 1; i >= 0; --i)
         tableModel.getValueAt(addr0 + i).setRange(null);

      // Apply the memory ranges to the corresponding memory cells
      for (MemoryRange range : ranges)
      {
         final int start = range.getStart();
         for (int j = range.getSize() - 1; j >= 0; --j)
         {
            MemoryCell cell = tableModel.getValueAt(start + j);

            if (cell.getRange() == null)
               cell.setRange(range);
         }
      }
   }

   /**
    * Create a memory range and add it to the memory ranges.
    *
    * @param start - the start address of the range.
    * @param size - the size of the range.
    * @param name - the name of the range.
    * @return The created memory range.
    */
   public MemoryRange createRange(int start, int size, String name)
   {
      final int idx = ranges.size();

      int r = (idx & 1) | ((idx & 8) >> 2) | (idx & 64) >> 4;
      int g = ((idx & 2) >> 1) | ((idx & 16) >> 3) | (idx & 128) >> 5;
      int b = ((idx & 4) >> 2) | ((idx & 32) >> 4) | (idx & 256) >> 6;

      r = (int) (backgroundColor.getRed() * 0.8f) + (r << 6) - 32;
      if (r > 255)
         r -= 255;
      else if (r < 0)
         r = 0;

      g = (int) (backgroundColor.getGreen() * 0.8f) + (g << 6) - 32;
      if (g > 255)
         g = 255;
      else if (g < 0)
         g = 0;

      b = (int) (backgroundColor.getBlue() * 0.8f) + (b << 6) - 32;
      if (b > 255)
         b = 255;
      else if (r < 0)
         b = 0;

      final MemoryRange range = new MemoryRange(start, size, name, new Color(r, g, b));
      ranges.add(range);
      return range;
   }
}
