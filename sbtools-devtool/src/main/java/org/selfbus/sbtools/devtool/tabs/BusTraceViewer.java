package org.selfbus.sbtools.devtool.tabs;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.components.ToolBar;
import org.selfbus.sbtools.common.gui.components.ToolBarButton;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.common.gui.models.FilteredListModel;
import org.selfbus.sbtools.devtool.DevTool;
import org.selfbus.sbtools.devtool.internal.I18n;
import org.selfbus.sbtools.devtool.tabs.busmonitor.BusMonitorItem;
import org.selfbus.sbtools.devtool.tabs.busmonitor.BusMonitorItemFilter;
import org.selfbus.sbtools.devtool.tabs.busmonitor.BusMonitorListCellRenderer;
import org.selfbus.sbtools.devtool.tabs.busmonitor.FilterDialog;
import org.selfbus.sbtools.devtool.tabs.busmonitor.FrameFilter;
import org.selfbus.sbtools.knxcom.application.AbstractMemory;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.application.DeviceDescriptorResponse;
import org.selfbus.sbtools.knxcom.application.MemoryRead;
import org.selfbus.sbtools.knxcom.application.MemoryResponse;
import org.selfbus.sbtools.knxcom.application.MemoryWrite;
import org.selfbus.sbtools.knxcom.application.devicedescriptor.DeviceDescriptor0;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapper;
import org.selfbus.sbtools.knxcom.application.memory.MemoryAddressMapperFactory;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiFrameFactory;
import org.selfbus.sbtools.knxcom.emi.EmiTelegramFrame;
import org.selfbus.sbtools.knxcom.emi.EmiVersion;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A panel that displays the contents of a bus trace file.
 */
public class BusTraceViewer extends JPanel
{
   private static final Logger LOGGER = LoggerFactory.getLogger(BusTraceViewer.class);
   private static final long serialVersionUID = 1021873554372571138L;

   private final JList<BusMonitorItem> list;
   private final JScrollPane treeView;
   private final JLabel fileNameLabel = new JLabel();

   private final FrameFilter filter = new FrameFilter();
   private final DefaultListModel<BusMonitorItem> model = new DefaultListModel<BusMonitorItem>();
   private final FilteredListModel<BusMonitorItem> filteredModel = new FilteredListModel<BusMonitorItem>(model,
      new BusMonitorItemFilter(filter));
   private final Map<PhysicalAddress, MemoryAddressMapper> addrMappers = new HashMap<PhysicalAddress, MemoryAddressMapper>();

   /**
    * Create a bus trace viewer.
    * 
    * @see {@link #open(File)}
    */
   public BusTraceViewer()
   {
      setLayout(new BorderLayout());
      setName(I18n.getMessage("BusTraceViewer.title"));

      try
      {
         filter.setEnabled(false);
         filter.fromConfig(Config.getInstance(), "busTrace.filter");
      }
      catch (Exception e)
      {
         Dialogs.showExceptionDialog(e, I18n.getMessage("Error.loadFilter"));
         filter.reset();
      }

      list = new JList<BusMonitorItem>(filteredModel);
      list.setCellRenderer(new BusMonitorListCellRenderer<BusMonitorItem>());

      treeView = new JScrollPane(list);
      add(treeView, BorderLayout.CENTER);

      initToolBar();
   }

   /**
    * Create the tool-bar.
    */
   private void initToolBar()
   {
      final ToolBar toolBar = new ToolBar();
      add(toolBar, BorderLayout.NORTH);

      final JButton btnFilter = new ToolBarButton(ImageCache.getIcon("icons/filter"));
      toolBar.add(btnFilter);
      btnFilter.setToolTipText(I18n.getMessage("BusMonitor.Filter.toolTip"));
      btnFilter.setSelected(filter.isEnabled());
      btnFilter.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent event)
         {
            JFrame mainWin = DevTool.getInstance().getMainFrame();

            final FilterDialog dlg = new FilterDialog(mainWin, ModalityType.APPLICATION_MODAL);
            dlg.setFilter(filter);
            dlg.setVisible(true);

            if (dlg.isAccepted())
            {
               btnFilter.setSelected(filter.isEnabled());
               filteredModel.update();

               try
               {
                  filter.toConfig(Config.getInstance(), "busTrace.filter");
               }
               catch (Exception e)
               {
                  Dialogs.showExceptionDialog(e, I18n.getMessage("Error.saveFilter"));
               }
            }
         }
      });

      toolBar.addSeparator();
      toolBar.add(fileNameLabel);
   }

   /**
    * Create a frame from the trace line string.
    * 
    * @param line - the trace line to convert.
    * @return The frame of the trace line.
    * @throws IOException
    */
   public EmiFrame createFrame(final String line) throws IOException
   {
      final EmiFrame frame = EmiFrameFactory.createFrame(HexString.valueOf(line), EmiVersion.EMI2);

      if (frame instanceof EmiTelegramFrame)
      {
         final Telegram telegram = ((EmiTelegramFrame) frame).getTelegram();
         final Application app = telegram.getApplication();

         if (app instanceof DeviceDescriptorResponse)
         {
            final DeviceDescriptorResponse ddr = (DeviceDescriptorResponse) app;
            if (ddr.getDescriptorType() == 0 && !addrMappers.containsKey(telegram.getFrom()))
            {
               final DeviceDescriptor0 dd = (DeviceDescriptor0) ddr.getDescriptor();
               addrMappers.put(telegram.getFrom(),
                  MemoryAddressMapperFactory.getMemoryAddressMapper(dd.getMaskVersion()));
            }
         }
         else if (app instanceof MemoryRead || app instanceof MemoryWrite)
         {
            ((AbstractMemory) app).setAddressMapper(addrMappers.get(telegram.getDest()));
         }
         else if (app instanceof MemoryResponse)
         {
            ((AbstractMemory) app).setAddressMapper(addrMappers.get(telegram.getFrom()));
         }
      }

      return frame;
   }

   /**
    * Read the file and display it's contents.
    * 
    * @param file - the file to open
    * @throws IOException
    */
   public void open(File file) throws IOException
   {
      final BufferedReader in = new BufferedReader(new FileReader(file));
      try
      {
         final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
         EmiFrame frame;
         Date when;

         model.clear();
         addrMappers.clear();

         for (int id = 1;; ++id)
         {
            final String line = in.readLine();
            if (line == null)
               break;

            int pos = line.indexOf('\t');
            if (pos < 0)
               continue;

            try
            {
               when = dateFormatter.parse(line.substring(0, pos));
            }
            catch (ParseException e)
            {
               LOGGER.warn("failed to read file " + file, e);
               when = null;
            }

            frame = createFrame(line.substring(pos + 1));
            model.addElement(new BusMonitorItem(id, when, frame));
         }

         filteredModel.update();
         fileNameLabel.setText(file.getName());
//         setName(file.getName());  // TODO broken
      }
      finally
      {
         in.close();
      }
   }
}
