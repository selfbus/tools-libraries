package org.selfbus.sbtools.sniffer;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.apache.commons.lang3.Validate;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.misc.LookAndFeelManager;
import org.selfbus.sbtools.common.gui.window.XmlToolBarFactory;
import org.selfbus.sbtools.sniffer.components.PortToolBar;
import org.selfbus.sbtools.sniffer.components.RecordsListCellRenderer;
import org.selfbus.sbtools.sniffer.internal.I18n;
import org.selfbus.sbtools.sniffer.layout.MultiLineFlowLayout;
import org.selfbus.sbtools.sniffer.model.Record;
import org.selfbus.sbtools.sniffer.model.SnifReader;
import org.selfbus.sbtools.sniffer.serial.Parity;
import org.selfbus.sbtools.sniffer.serial.SerialPortWrapper;
import org.selfbus.sbtools.sniffer.serial.Stop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The sniffer's application class.
 */
public final class Sniffer extends SingleFrameApplication
{
   private static final Logger LOGGER = LoggerFactory.getLogger(Sniffer.class);

   private final PortToolBar portToolBar = new PortToolBar("ports");
   private final JCheckBox viewAsciiCheck = new JCheckBox(I18n.getMessage("Sniffer.viewAscii"));
   private final JCheckBox viewHexCheck = new JCheckBox(I18n.getMessage("Sniffer.viewHex"));

   private final DefaultListModel<Record> recordsModel = new DefaultListModel<Record>();
   private final JList<Record> recordsList = new JList<Record>(recordsModel);
   private final JScrollPane recordsPane = new JScrollPane(recordsList);
   private final RecordsListCellRenderer recordsListCellRenderer = new RecordsListCellRenderer();
   
   private SerialPortWrapper sendPort = new SerialPortWrapper();
   private SerialPortWrapper recvPort = new SerialPortWrapper();

   private Config config = Config.getInstance();
   private String configFileName;
   private ArrayList<Record> records;

   /**
    * Start the sniffer.
    *
    * @param args - the command-line arguments.
    */
   public static void main(String[] args)
   {
      LookAndFeelManager.setDefaultLookAndFeel();
      Application.launch(Sniffer.class, args);
   }

   /**
    * @return The application object.
    */
   public static Sniffer getInstance()
   {
      return getInstance(Sniffer.class);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void initialize(String[] args)
   {
      try
      {
         super.initialize(args);

         Environment.setAppName(getClass().getSimpleName());
         File appDir = new File(Environment.getAppDir());
         getContext().getLocalStorage().setDirectory(appDir);

         loadConfig();
      }
      catch (Exception e)
      {
         Dialogs.showExceptionDialog(e, I18n.getMessage("Error.startup"));
         System.exit(1);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void startup()
   {
      try
      {
         FrameView mainView = getMainView();

         final JFrame mainFrame = mainView.getFrame();
         mainFrame.setName("sbtools-sniffer");
         mainFrame.setMinimumSize(new Dimension(600, 500));
         mainFrame.setTitle(I18n.getMessage("Sniffer.title"));
         mainFrame.setLayout(new BorderLayout());
         
         JPanel toolBarPanel = new JPanel();
         mainFrame.add(toolBarPanel, BorderLayout.NORTH);
         toolBarPanel.setLayout(new MultiLineFlowLayout(MultiLineFlowLayout.LEFT, 4, 2));
         toolBarPanel.add(portToolBar);
         toolBarPanel.add(createActionToolBar());
         toolBarPanel.add(createViewToolBar());

         portToolBar.addConnectListener(connectListener);
         portToolBar.addDisconnectListener(disconnectListener);

         try
         {
            Font monoFont = new Font("monospaced", Font.PLAIN, toolBarPanel.getFont().getSize());         
            recordsListCellRenderer.setFont(monoFont);
         }
         catch (RuntimeException e)
         {
            LOGGER.error("failed to create font", e);
         }

         Color baseColor = recordsList.getBackground();
         float tint = 0.92f;
         int newRed = Math.min(255, (int)(baseColor.getRed() * tint));
         int newGreen = Math.min(255, (int)(baseColor.getGreen() * tint));
         int newBlue = Math.min(255, (int)(baseColor.getBlue() * tint));
         Color sendColor = new Color(newRed, newGreen, baseColor.getBlue());
         Color recvColor = new Color(newRed, baseColor.getGreen(), newBlue);
         
         recordsListCellRenderer.setDateColor(toolBarPanel.getForeground().brighter().brighter());
         recordsListCellRenderer.setSendColor(sendColor);
         recordsListCellRenderer.setRecvColor(recvColor);

         recordsList.setCellRenderer(recordsListCellRenderer);
         mainFrame.add(recordsPane, BorderLayout.CENTER);

         show(mainView);
      }
      catch (Exception e)
      {
         Dialogs.showExceptionDialog(e, I18n.getMessage("Error.startup"));
         System.exit(1);
      }
   }

   /**
    * Create the action tool bar.
    */
   protected JToolBar createActionToolBar()
   {
      XmlToolBarFactory toolBarFactory = new XmlToolBarFactory();

      String fileName = "org/selfbus/sbtools/sniffer/main-toolbar.xml";
      InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
      Validate.notNull(in, "Toolbar configuration not found: " + fileName);

      return toolBarFactory.createToolBar(in);
   }

   /**
    * Create the view tool bar.
    */
   protected JToolBar createViewToolBar()
   {
      JToolBar toolBar = new JToolBar("view");

      viewAsciiCheck.addActionListener(changeViewListener);
      toolBar.add(viewAsciiCheck);

      viewHexCheck.addActionListener(changeViewListener);
      toolBar.add(viewHexCheck);

      return toolBar;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void ready()
   {
      try
      {
         applyConfig();

         InputStream in = getClass().getClassLoader().getResourceAsStream("example1.snif");
         if (in != null)
         {
            SnifReader reader = new SnifReader();
            records = reader.read(in);
            setRecords(records);
         }
      }
      catch (RuntimeException | IOException e)
      {
         Dialogs.showExceptionDialog(e, I18n.getMessage("Error.startup"));
      }
   }

   /**
    * Apply the configuration.
    */
   protected void applyConfig()
   {
      portToolBar.readConfig(config);

      viewAsciiCheck.setSelected(config.getIntValue("view.ascii", 0) == 1);
      viewHexCheck.setSelected(config.getIntValue("view.hex", 1) == 1);
      changeViewListener.actionPerformed(null);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void shutdown()
   {
      portToolBar.writeConfig(config);
      config.put("connected", sendPort.isOpened() && recvPort.isOpened() ? 1 : 0);
      config.put("view.ascii", viewAsciiCheck.isSelected() ? 1 : 0);
      config.put("view.hex", viewHexCheck.isSelected() ? 1 : 0);

      saveConfig();

      sendPort.close();
      recvPort.close();

      super.shutdown();
   }

   /**
    * @return The application's configuration.
    */
   public Config getConfig()
   {
      return config;
   }

   /**
    * Save the configuration.
    */
   public void saveConfig()
   {
      try
      {
         config.save(configFileName);
      }
      catch (IOException e)
      {
         Dialogs.showErrorDialog(I18n.formatMessage("Error.write", configFileName));
      }
   }

   /**
    * Load the configuration. Automatically called during application initialization.
    */
   protected void loadConfig()
   {
      configFileName = getContext().getLocalStorage().getDirectory().getPath() + File.separator + "user.config";
      try
      {
         config.load(configFileName);
      }
      catch (FileNotFoundException e)
      {
      }
      catch (IOException e)
      {
         Dialogs.showErrorDialog(I18n.formatMessage("Error.read", configFileName));
      }
   }

   /**
    * Clear the records.
    */
   public void clear()
   {
      recordsModel.clear();
   }

   /**
    * Set the records that are displayed.
    * 
    * @param records - the records to show.
    */
   public void setRecords(List<Record> records)
   {
      recordsModel.clear();

      for (Record record : records)
         recordsModel.addElement(record);
   }

   /**
    * Load a recording.
    *
    * @param file - the file to load
    */
   public void loadRecording(File file)
   {
      try
      {
         SnifReader reader = new SnifReader();
         records = reader.read(file);
         setRecords(records);
      }
      catch (RuntimeException | IOException e)
      {
         Dialogs.showExceptionDialog(e, I18n.formatMessage("Error.read", file.toString()));
      }
   }

   /**
    * Listener for changing the view settings.
    */
   protected final ActionListener changeViewListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         recordsListCellRenderer.setViewMode(viewAsciiCheck.isSelected(), viewHexCheck.isSelected());
         recordsList.setModel(recordsModel);
      }
   };

   /**
    * Connect the ports, using the values of the port tool bar.
    */
   protected final ActionListener connectListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent ev)
      {
         Validate.isTrue(!sendPort.isOpened());
         Validate.isTrue(!recvPort.isOpened());

         int baudRate = portToolBar.getBaudRate();
         int dataBits = portToolBar.getDataBits();
         Stop stop = portToolBar.getStopBits();
         Parity parity = portToolBar.getParity();

         try
         {
            String portName = portToolBar.getSendPort();
            Validate.notNull(portName, "No sender port selected");
            LOGGER.info("Opening send port {}: {}/{}/{}/{}", new Object[] { portName, baudRate, dataBits, stop, parity });
            sendPort.open(portName, baudRate, dataBits, stop.id, parity.id);
            sendPort.addEventListener(sendEventListener);

            portName = portToolBar.getRecvPort();
            Validate.notNull(portName, "No receiver port selected");
            LOGGER.info("Opening recv port {}: {}/{}/{}/{}", new Object[] { portName, baudRate, dataBits, stop, parity });
            recvPort.open(portName, baudRate, dataBits, stop.id, parity.id);
            recvPort.addEventListener(recvEventListener);
         }
         catch (TooManyListenersException | IOException e)
         {
            throw new RuntimeException(e);
         }
         finally
         {
            sendPort.close();
            recvPort.close();
         }
      }
   };

   /**
    * Disconnect the ports.
    */
   protected final ActionListener disconnectListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         LOGGER.info("Closing ports");

         sendPort.close();
         recvPort.close();
      }
   };

   /**
    * Listener for events on the sender port.
    */
   protected final SerialPortEventListener sendEventListener = new SerialPortEventListener()
   {
      @Override
      public void serialEvent(SerialPortEvent e)
      {
         // TODO Auto-generated method stub
         
      }
   };

   /**
    * Listener for events on the receiver port.
    */
   protected final SerialPortEventListener recvEventListener = new SerialPortEventListener()
   {
      @Override
      public void serialEvent(SerialPortEvent e)
      {
         // TODO Auto-generated method stub
         
      }
   };
}
