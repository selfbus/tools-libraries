package org.selfbus.sbtools.vdviewer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang3.Validate;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.common.gui.actions.BasicActionFactory;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.components.ExtTabbedPane;
import org.selfbus.sbtools.common.gui.misc.LookAndFeelManager;
import org.selfbus.sbtools.common.gui.window.XmlMenuFactory;
import org.selfbus.sbtools.common.gui.window.XmlToolBarFactory;
import org.selfbus.sbtools.vdviewer.components.StatusBar;
import org.selfbus.sbtools.vdviewer.internal.I18n;
import org.selfbus.sbtools.vdviewer.tabs.TableTab;
import org.selfbus.sbtools.vdviewer.vdx.VdxFile;
import org.selfbus.sbtools.vdviewer.vdx.VdxTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The application class.
 */
public class VdViewer extends SingleFrameApplication
{
   private static final Logger LOGGER = LoggerFactory.getLogger(VdViewer.class);

   private Config config = Config.getInstance();
   private String configFileName;
   private final DefaultListModel<String> tablesListModel = new DefaultListModel<String>();
   private final JList<String> tablesList = new JList<String>(tablesListModel);
   private final JScrollPane tablesScrollPane = new JScrollPane(tablesList);
   private final ExtTabbedPane tabbedPane = new ExtTabbedPane();
   private final StatusBar statusBar = new StatusBar();
   private final JLabel statusMessagePanel = new JLabel(" ");
   private Timer clearStatusTimer;
   private VdxFile vdxFile;

   /**
    * Start the application.
    * 
    * @param args - the command line arguments
    * @throws UnsupportedLookAndFeelException 
    */
   public static void main(String[] args) throws UnsupportedLookAndFeelException
   {
      LookAndFeelManager.setDefaultLookAndFeel();
      Application.launch(VdViewer.class, args);
   }

   /**
    * @return The application object.
    */
   public static VdViewer getInstance()
   {
      return getInstance(VdViewer.class);
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
    * Get a specific menu of the main menu bar.
    * 
    * @param name - the name of the menu.
    * 
    * @return The requested menu or null if not found.
    */
   protected JMenu getMenu(String name)
   {
      JMenuBar menuBar = getMainView().getMenuBar();
      for (int i = menuBar.getMenuCount() - 1; i >= 0; --i)
      {
         JMenu menu = menuBar.getMenu(i);

         if (name.equalsIgnoreCase(menu.getName()))
            return menu;
      }

      return null;
   }

   /**
    * Set a message that is displayed in the status line.
    * 
    * @param message - the status message
    */
   public void setStatusMessage(String message)
   {
      statusMessagePanel.setText(message);

      clearStatusTimer.stop();
      clearStatusTimer.start();
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
   protected void shutdown()
   {
      saveConfig();

      super.shutdown();
   }

   /**
    * Responsible for starting the application; for creating and showing the initial GUI.
    * 
    * This method is called by the static launch method, subclasses must override it. It runs on the
    * event dispatching thread.
    */
   @Override
   protected void startup()
   {
      try
      {
         FrameView mainView = getMainView();
         final JFrame mainFrame = mainView.getFrame();

         mainFrame.setName("sbtools-vdviewer");
         mainFrame.setMinimumSize(new Dimension(800, 600));
         updateMainTitle();

         String fileName = "org/selfbus/sbtools/vdviewer/main-menubar.xml";
         InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
         Validate.notNull(in, "menubar configuration not found: " + fileName);
         mainView.setMenuBar(new XmlMenuFactory(I18n.BUNDLE).createMenuBar(in));

         statusBar.add(Box.createHorizontalStrut(2), 0, false);
         statusBar.add(statusMessagePanel, 100, true);
         mainView.setStatusBar(statusBar);

         clearStatusTimer = new Timer(5000, clearStatusHandler);
         clearStatusTimer.setRepeats(false);
         
         fileName = "org/selfbus/sbtools/vdviewer/main-toolbar.xml";
         in = getClass().getClassLoader().getResourceAsStream(fileName);
         Validate.notNull(in, "toolbar configuration not found: " + fileName);
         mainView.setToolBar(new XmlToolBarFactory().createToolBar(in));

         tablesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         tablesList.addMouseListener(tablesListMouseListener);

         final JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);
         mainSplitPane.setRightComponent(tabbedPane);
         mainSplitPane.setLeftComponent(tablesScrollPane);

         SwingUtilities.invokeLater(new Runnable()
         {
            @Override
            public void run()
            {
               mainSplitPane.setDividerLocation(250);
            }
         });

         mainFrame.add(mainSplitPane);

         show(mainView);
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
   protected void ready()
   {
      try
      {
         getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

         final Config cfg = Config.getInstance();
         String lastFile = cfg.getStringValue("products.lastFile");

         if (lastFile != null && !lastFile.isEmpty())
            openProducts(new File(lastFile));
            
      }
      catch (Exception e)
      {
         Dialogs.showExceptionDialog(e, I18n.getMessage("Error.startup"));
      }
      finally
      {
         getMainFrame().setCursor(Cursor.getDefaultCursor());
      }
   }

   /**
    * Open a products file.
    *
    * @param file - the VD file to read.
    *
    * @throws IOException if the file cannot be read
    */
   public void openProducts(File file) throws IOException
   {
      long startTime = System.currentTimeMillis();

      vdxFile = new VdxFile(file);
      LOGGER.debug("Import done ({} seconds)", (System.currentTimeMillis() - startTime) * 0.001);

      update();
      setStatusMessage(I18n.formatMessage("VdViewer.productsLoaded", file.getName()));
   }

   /**
    * @return The products file.
    */
   public VdxFile getProducts()
   {
      return vdxFile;
   }

   /**
    * Update the title of the main application window.
    */
   protected void updateMainTitle()
   {
      final JFrame mainFrame = getMainView().getFrame();

      if (vdxFile == null)
         mainFrame.setTitle(I18n.getMessage("VdViewer.emptyTitle"));
      else if (vdxFile.getFile() == null)
         mainFrame.setTitle(I18n.formatMessage("VdViewer.title", "?unnamed?"));
      else mainFrame.setTitle(I18n.formatMessage("VdViewer.title", vdxFile.getFile().getName()));
   }

   /**
    * Update the window's contents,
    */
   public void update()
   {
      updateMainTitle();
      closeAllPanels();
      tablesListModel.removeAllElements();

      Action action = BasicActionFactory.getInstance().getAction("org.selfbus.sbtools.vdviewer.actions.OpenTableAction");
      if (action != null)
         action.setEnabled(false);

      if (vdxFile == null)
         return;

      Set<String> tableNames = new TreeSet<String>();
      tableNames.addAll(vdxFile.getTableNames());
      for (String tableName : tableNames)
      {
         tablesListModel.addElement(tableName);
      }
   }

   /**
    * Get the {@link VdxTable} that is currently visible in the active panel.
    * 
    * @return The currently visible table, null if no table is visible.
    */
   public VdxTable getCurrentTable()
   {
      int sel = tabbedPane.getSelectedIndex();
      if (sel < 0) return null;

      Component c = tabbedPane.getComponentAt(sel);
      if (c instanceof TableTab)
         return ((TableTab) c).getTable();

      return null;
   }

   /**
    * @return The tab that is currently visible. Null if none.
    */
   public Component getCurrentTab()
   {
      int sel = tabbedPane.getSelectedIndex();
      if (sel < 0) return null;
      return tabbedPane.getComponentAt(sel);
   }

   /**
    * Open a tab with the contents of the table that is selected in the list of tables.
    * Does nothing if no table is selected.
    */
   public void showSelectedTable()
   {
      String tableName = tablesList.getSelectedValue();
      if (tableName == null) return;

      try
      {
         getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

         TableTab tab = new TableTab(vdxFile.getTable(tableName));
         tabbedPane.addTab(tableName, tab);
         tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      } 
      finally
      {
         getMainFrame().setCursor(Cursor.getDefaultCursor());
      }
   }

   /**
    * Close a panel in the tabbedPane.
    *
    * @param panel - the panel to close.
    */
   public void closePanel(JPanel panel)
   {
      tabbedPane.remove(panel);
      panel.setVisible(false);
   }

   /**
    * Close all panels in the tabbedPane.
    */
   public void closeAllPanels()
   {
      while (tabbedPane.getTabCount() > 0)
         tabbedPane.remove(0);
   }

   /**
    * A mouse listener for the {@link tablesList}.
    */
   private final MouseListener tablesListMouseListener = new MouseAdapter()
   {
      @Override
      public void mouseClicked(MouseEvent e)
      {
         int index = tablesList.locationToIndex(e.getPoint());

         if (e.getClickCount() == 2 && index >= 0)
         {
            showSelectedTable();
         }

         Action action = BasicActionFactory.getInstance().getAction("org.selfbus.sbtools.vdviewer.actions.OpenTableAction");
         if (action != null)
            action.setEnabled(index >= 0);
      }
   };
   
   /**
    * Clear the status message after a delay.
    */
   private final ActionListener clearStatusHandler = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         statusMessagePanel.setText(" ");
      }
   };
}
