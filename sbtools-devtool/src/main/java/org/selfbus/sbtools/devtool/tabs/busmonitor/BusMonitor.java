package org.selfbus.sbtools.devtool.tabs.busmonitor;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.components.ToolBar;
import org.selfbus.sbtools.common.gui.components.ToolBarButton;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.common.gui.models.FilteredListModel;
import org.selfbus.sbtools.devtool.DevTool;
import org.selfbus.sbtools.devtool.internal.I18n;
import org.selfbus.sbtools.devtool.project.ProjectListener;
import org.selfbus.sbtools.devtool.project.model.Project;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.application.DeviceDescriptorRead;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.L_Data_con;
import org.selfbus.sbtools.knxcom.emi.L_Data_ind;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameType;
import org.selfbus.sbtools.knxcom.telegram.Priority;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramListener;
import org.selfbus.sbtools.knxcom.telegram.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An EIB bus monitor.
 */
public class BusMonitor extends JPanel implements TelegramListener, ProjectListener
{
   private static final Logger LOGGER = LoggerFactory.getLogger(BusMonitor.class);
   private static final long serialVersionUID = -3243196694923284469L;

   private BasicAction eraseAction, filterAction, saveAction;
   private JButton filterButton;
   private int sequence;

   private final JList<BusMonitorItem> list;
   private final JScrollPane treeView;

   private final FrameFilter filter = new FrameFilter();
   private final DefaultListModel<BusMonitorItem> model = new DefaultListModel<BusMonitorItem>();
   private final FilteredListModel<BusMonitorItem> filteredModel = new FilteredListModel<BusMonitorItem>(model,
      new BusMonitorItemFilter(filter));

   private final BusInterface bus;

   /**
    * Create a bus monitor widget.
    */
   public BusMonitor()
   {
      setLayout(new BorderLayout());
      setName(I18n.getMessage("BusMonitor.title"));
      createActions();

      try
      {
         filter.setEnabled(false);
         filter.fromConfig(Config.getInstance(), "busMonitor.filter");
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

      add(createToolBar(), BorderLayout.NORTH);

      updateButtons();

      bus = BusInterfaceFactory.getBusInterface();
      bus.addListener(this);

      DevTool.getInstance().getProjectService().addListener(this);
   }

   /**
    * Create the actions.
    */
   private void createActions()
   {
      saveAction = new BasicAction(I18n.getMessage("BusMonitor.Save.name"),
         I18n.getMessage("BusMonitor.Save.toolTip"),
         ImageCache.getIcon("icons/filesave"))
      {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionEvent(ActionEvent e)
         {
            saveBusTrace();
         }
      };

      eraseAction = new BasicAction(I18n.getMessage("BusMonitor.Clear.name"),
         I18n.getMessage("BusMonitor.Clear.toolTip"),
         ImageCache.getIcon("icons/eraser"))
      {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionEvent(ActionEvent e)
         {
            synchronized (model)
            {
               sequence = 0;
               model.clear();
               updateButtons();
            }
         }
      };

      filterAction = new BasicAction(I18n.getMessage("BusMonitor.Filter.name"),
         I18n.getMessage("BusMonitor.Filter.toolTip"),
         ImageCache.getIcon("icons/filter"))
      {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionEvent(ActionEvent e)
         {
            final FilterDialog dlg = new FilterDialog(DevTool.getInstance().getMainFrame(),
               ModalityType.APPLICATION_MODAL);
            dlg.setFilter(filter);
            dlg.setVisible(true);

            if (dlg.isAccepted())
            {
               filterButton.setSelected(filter.isEnabled());
               filteredModel.update();
               try
               {
                  filter.toConfig(Config.getInstance(), "busMonitor.filter");
                  DevTool.getInstance().saveConfig();
               }
               catch (Exception ex)
               {
                  Dialogs.showExceptionDialog(ex, I18n.getMessage("Error.saveFilter"));
               }
            }
          }
      };
   }

   /**
    * Create the tool-bar.
    * 
    * @return The created tool-bar.
    */
   private ToolBar createToolBar()
   {
      final ToolBar toolBar = new ToolBar();
      toolBar.setFloatable(false);

      toolBar.add(saveAction);
      toolBar.add(eraseAction);
      filterButton = toolBar.add(filterAction);

      toolBar.addSeparator();

      final JButton btnTestTele = new ToolBarButton(ImageCache.getIcon("icons/music_32ndnote"));
      btnTestTele.setToolTipText(I18n.getMessage("BusMonitor.SendTestTelegram.ToolTip"));
      toolBar.add(btnTestTele);
      btnTestTele.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent event)
         {
            if (bus == null)
               return;

            final Telegram telegram = new Telegram();
            telegram.setDest(new PhysicalAddress(1, 1, 88));
            telegram.setPriority(Priority.SYSTEM);
            telegram.setTransport(Transport.Connect);

            try
            {
               bus.send(telegram);
            }
            catch (Exception e)
            {
               Dialogs.showExceptionDialog(e, "Failed to send test telegram");
            }
         }
      });

      final JButton btnTestTele2 = new ToolBarButton(ImageCache.getIcon("icons/music_cross"));
      btnTestTele2.setToolTipText(I18n.getMessage("BusMonitor.SendTestTelegram.ToolTip"));
      toolBar.add(btnTestTele2);
      btnTestTele2.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent event)
         {
            if (bus == null)
               return;

            final Telegram telegram = new Telegram(new DeviceDescriptorRead(0));
            telegram.setDest(new PhysicalAddress(1, 1, 88));
            telegram.setPriority(Priority.SYSTEM);
            telegram.setTransport(Transport.Connected);

            try
            {
               bus.send(telegram);
            }
            catch (Exception e)
            {
               Dialogs.showExceptionDialog(e, "Failed to send test telegram");
            }
         }
      });

      return toolBar;
   }

   /**
    * Save the entries to a trace file. Opens a file dialog to select a save file.
    */
   public void saveBusTrace()
   {
      final Config cfg = Config.getInstance();
      String lastDir = cfg.getStringValue("BusTraces.lastDir");

      final JFileChooser dlg = new JFileChooser();
      dlg.setSelectedFile(new File(lastDir));
      final FileFilter fileFilter = new TrxFileFilter();
      dlg.addChoosableFileFilter(fileFilter);
      dlg.addChoosableFileFilter(dlg.getAcceptAllFileFilter());
      dlg.setFileFilter(fileFilter);
      dlg.setDialogTitle(I18n.getMessage("BusMonitor.SaveTraceFileTitle"));
      dlg.setDialogType(JFileChooser.SAVE_DIALOG);

      if (dlg.showOpenDialog(DevTool.getInstance().getMainFrame()) != JFileChooser.APPROVE_OPTION)
         return;

      final File file = dlg.getSelectedFile();
      if (file == null)
         return;

      cfg.put("BusTraces.lastDir", file.getAbsolutePath());
      DevTool.getInstance().saveConfig();

      try
      {
         saveBusTrace(file);
      }
      catch (IOException e)
      {
         Dialogs.showExceptionDialog(e, I18n.getMessage("BusMonitor.ErrSaveTraceFile"));
      }
   }

   /**
    * Save the entries to a trace file.
    * 
    * @param file - the file to save into
    * 
    * @throws IOException
    */
   public void saveBusTrace(File file) throws IOException
   {
      final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
      FileWriter out = null;

      try
      {
         synchronized (model)
         {
            out = new FileWriter(file);

            for (Object obj : model.toArray())
            {
               final BusMonitorItem item = (BusMonitorItem) obj;

               out.write(dateFormatter.format(item.getWhen()));
               out.write("\t");
               out.write(HexString.toString(item.getFrame().toByteArray()));
               out.write("\n");
            }

            LOGGER.info("Bus trace log saved");

            out.flush();
         }
      }
      finally
      {
         if (out != null)
            out.close();
      }
   }

   /**
    * Add an entry to the list of telegrams. The telegram is cloned to avoid problems.
    * 
    * @param frame - The frame that the entry is about.
    * @param isReceived - True if the telegram was received, false else.
    */
   private void addBusMonitorItem(final EmiFrame frame)
   {
      final Date when = new Date();
      final int id = ++sequence;

      try
      {
         SwingUtilities.invokeLater(new Runnable()
         {
            @Override
            public void run()
            {
               synchronized (model)
               {
                  final boolean needUpdate = model.isEmpty();

                  model.addElement(new BusMonitorItem(id, when, frame));

                  int idx = model.getSize() - 1;
                  list.scrollRectToVisible(list.getCellBounds(idx, idx));

                  if (needUpdate)
                     updateButtons();
               }
            }
         });
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Update the tool button state
    */
   private void updateButtons()
   {
      final boolean haveFrames = !model.isEmpty();
      saveAction.setEnabled(haveFrames);
      eraseAction.setEnabled(haveFrames);
   }

   /**
    * Callback: a telegram was received.
    */
   @Override
   public void telegramReceived(Telegram telegram)
   {
      addBusMonitorItem(new L_Data_ind(EmiFrameType.EMI2_L_DATA_IND, telegram));
   }

   /**
    * Callback: a telegram was sent.
    */
   @Override
   public void telegramSent(Telegram telegram)
   {
      addBusMonitorItem(new L_Data_con(EmiFrameType.EMI2_L_DATA_CON, telegram));
   }

   /**
    * Callback: the project changed.
    */
   @Override
   public void projectChanged(Project project)
   {
      // TODO Auto-generated method stub
   }
}
