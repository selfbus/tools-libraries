package org.selfbus.sbtools.prodedit;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListDataEvent;

import org.apache.commons.lang3.Validate;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.Environment;
import org.selfbus.sbtools.common.exception.SbToolsRuntimeException;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.components.ExtTabbedPane;
import org.selfbus.sbtools.common.gui.misc.LookAndFeelManager;
import org.selfbus.sbtools.common.gui.window.XmlMenuFactory;
import org.selfbus.sbtools.prodedit.components.StatusBar;
import org.selfbus.sbtools.prodedit.internal.I18n;
import org.selfbus.sbtools.prodedit.model.AbstractListDataListener;
import org.selfbus.sbtools.prodedit.model.AbstractProjectListener;
import org.selfbus.sbtools.prodedit.model.ProjectListener;
import org.selfbus.sbtools.prodedit.model.ProjectService;
import org.selfbus.sbtools.prodedit.model.global.Project;
import org.selfbus.sbtools.prodedit.model.prodgroup.ProductGroup;
import org.selfbus.sbtools.prodedit.tabs.prodgroup.ProductGroupTab;
import org.selfbus.sbtools.prodedit.tabs.project.ProjectTab;
import org.selfbus.sbtools.prodedit.vdio.ProductsImporter;

import com.jgoodies.common.collect.ArrayListModel;

/**
 * The application class.
 */
public class ProdEdit extends SingleFrameApplication
{
   //   private static final Logger LOGGER = LoggerFactory.getLogger(ProdEdit.class);

   private Config config = Config.getInstance();
   private String configFileName;
   private final ProjectService projectService = new ProjectService();
   private final Map<Class<? extends JComponent>, JComponent> tabPanels = new HashMap<Class<? extends JComponent>, JComponent>();
   private final Map<Object, JComponent> tabComponents = new HashMap<Object, JComponent>();
   private final ExtTabbedPane tabbedPane = new ExtTabbedPane();
   private final StatusBar statusBar = new StatusBar();
   private final JLabel statusMessagePanel = new JLabel(" ");
   private Timer clearStatusTimer;

   /**
    * Start the application.
    * 
    * @param args - the command line arguments
    * @throws UnsupportedLookAndFeelException 
    */
   public static void main(String[] args) throws UnsupportedLookAndFeelException
   {
      LookAndFeelManager.setDefaultLookAndFeel();
      Application.launch(ProdEdit.class, args);
   }

   /**
    * @return The application object.
    */
   public static ProdEdit getInstance()
   {
      return getInstance(ProdEdit.class);
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
    * @return The project service.
    */
   public ProjectService getProjectService()
   {
      return projectService;
   }

   /**
    * @return The project. This is a shortcut for getProjectService().getProject().
    */
   public Project getProject()
   {
      return projectService.getProject();
   }

   /**
    * Get a specific tab panel. The tab panel is created if it does not exist.
    * 
    * @param panelClass - the class of the tab panel.
    */
   public JComponent getPanel(final Class<? extends JComponent> panelClass)
   {
      synchronized (tabPanels)
      {
         JComponent tabPanel = tabPanels.get(panelClass);
         if (tabPanel == null)
         {
            try
            {
               tabPanel = panelClass.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e)
            {
               Dialogs.showExceptionDialog(e, I18n.formatMessage("Error.newInstance", panelClass.getSimpleName()));
               throw new SbToolsRuntimeException(e);
            }

            tabPanels.put(panelClass, tabPanel);
            JMenuItem menuItem = new JMenuItem(tabPanel.getName());
            menuItem.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent e)
               {
                  showPanel(panelClass);
               }
            });

            JMenu menu = getMenu("View");
            if (menu != null)
               menu.add(menuItem);
         }

         return tabPanel;
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
    * Ensure that a specific tab panel exists and is shown.
    * 
    * @param panelClass - the class of the tab panel.
    */
   public JComponent showPanel(Class<? extends JComponent> panelClass)
   {
      synchronized (tabPanels)
      {
         JComponent tabPanel = getPanel(panelClass);
         // TODO ensure that the panel is not yet in the tabbed pane
         tabbedPane.add(tabPanel);
         // TODO ensure that the panel is 
         return tabPanel;
      }
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

         projectService.addListener(projectListener);
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

      projectService.removeListener(projectListener);

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

         mainFrame.setName("sbtools-prodedit");
         mainFrame.setMinimumSize(new Dimension(800, 600));
         mainFrame.setTitle(I18n.getMessage("ProdEdit.title"));

         String fileName = "org/selfbus/sbtools/prodedit/main-menubar.xml";
         InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
         Validate.notNull(in, "menubar configuration not found: " + fileName);
         mainView.setMenuBar(new XmlMenuFactory(I18n.BUNDLE).createMenuBar(in));

         statusBar.add(Box.createHorizontalStrut(2), 0, false);
         statusBar.add(statusMessagePanel, 100, true);
         mainView.setStatusBar(statusBar);

         clearStatusTimer = new Timer(5000, clearStatusHandler);
         clearStatusTimer.setRepeats(false);
         
//         fileName = "org/selfbus/sbtools/prodedit/main-toolbar.xml";
//         in = getClass().getClassLoader().getResourceAsStream(fileName);
//         Validate.notNull(in, "toolbar configuration not found: " + fileName);
//         mainView.setToolBar(new XmlToolBarFactory().createToolBar(in));

         mainFrame.add(tabbedPane);

         showPanel(ProjectTab.class);

         updateViewMenu();
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

         String lastProjectPath = Config.getInstance().getStringValue("project.last");
         if (lastProjectPath != null && !lastProjectPath.isEmpty() && (new File(lastProjectPath)).exists())
         {
            projectService.loadProject(new File(lastProjectPath));
         }
         else
         {
            ProductsImporter importer = new ProductsImporter(projectService);
            String fileName = "Bosch-Freebus12.vd_";
            Project project = importer.read(getClass().getClassLoader().getResourceAsStream(fileName));
            project.setName(fileName);
            projectService.setProject(project);
         }

         setStatusMessage(I18n.formatMessage("Project.loaded", projectService.getProject().getName()));
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
    * Update the title of the main application window.
    */
   protected void updateMainTitle()
   {
      final JFrame mainFrame = getMainView().getFrame();

      Project project = projectService.getProject();
      File projFile = project == null ? null : project.getFile();

      if (projFile != null)
      {
         mainFrame.setTitle(I18n.formatMessage("ProdEdit.titleLoaded", projFile.getName()));
         
      }
      else if (project != null && !project.getName().isEmpty())
      {
         mainFrame.setTitle(I18n.formatMessage("ProdEdit.titleLoaded", project.getName()));
      }
      else
      {
         mainFrame.setTitle(I18n.getMessage("ProdEdit.title"));
      }
   }

   /**
    * Lazily update the view menu.
    */
   public void updateViewMenu()
   {
      SwingUtilities.invokeLater(new Runnable()
      {
         @Override
         public void run()
         {
            final JMenu viewMenu = getMenu("View");
            if (viewMenu == null)
               return;

            viewMenu.removeAll();
            for (int idx = 0; idx < tabbedPane.getTabCount(); ++idx)
            {
               final Component comp = tabbedPane.getComponentAt(idx);

               JMenuItem menuItem = new JMenuItem(comp.getName());
               menuItem.addActionListener(new ActionListener()
               {
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                     tabbedPane.setSelectedComponent(comp);
                  }
               });

               viewMenu.add(menuItem);
            }
         }
      });
   }

   /**
    * Open a tab with the specified product group.
    *
    * @param group - the product group to edit.
    */
   public void editProductGroup(final ProductGroup group)
   {
      JComponent comp = tabComponents.get(group);
      if (comp == null)
      {
         final ProductGroupTab prodGroupTab = new ProductGroupTab(group);
         comp = prodGroupTab;

         tabComponents.put(group, prodGroupTab);
         tabbedPane.add(group.toString(), prodGroupTab);

         group.addPropertyChangeListener(new PropertyChangeListener()
         {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
               int idx = tabbedPane.indexOfComponent(tabComponents.get(group));
               if (idx >= 0)
               {
                  String title = group.toString();
                  tabbedPane.setTitleAt(idx, title);
                  tabbedPane.getTabComponentAt(idx).setName(title);
                  prodGroupTab.setName(title);
                  updateViewMenu();
               }
            }
         });
      }

      tabbedPane.setSelectedComponent(comp);
      updateViewMenu();
   }

   /**
    * Close all product group tabs.
    */
   public void closeAllProductGroups()
   {
      Set<Object> keys = new HashSet<Object>();
      keys.addAll(tabComponents.keySet());

      for (Object key : keys)
      {
         if (key instanceof ProductGroup)
         {
            JComponent comp = tabComponents.get(key);
            tabbedPane.remove(comp);
            comp.setVisible(false);

            tabComponents.remove(key);
         }
      }
   }

   /**
    * Private project listener
    */
   private final ProjectListener projectListener = new AbstractProjectListener()
   {
      @Override
      public void projectLoaded(Project project)
      {
         closeAllProductGroups();
      }

      @Override
      public void projectChanged(final Project project)
      {
         updateMainTitle();

         for (JComponent tab : tabComponents.values())
         {
            tab.setVisible(false);
         }
         tabComponents.clear();

         project.getProductGroups().addListDataListener(new AbstractListDataListener()
         {
            @Override
            public void intervalRemoved(ListDataEvent e)
            {
            }

            @Override
            public void contentsChanged(ListDataEvent e)
            {
               ArrayListModel<ProductGroup> groups = project.getProductGroups();
               for (int listIndex = e.getIndex0(); listIndex <= e.getIndex1(); ++listIndex)
               {
                  ProductGroup group = groups.get(listIndex);

                  int idx = tabbedPane.indexOfComponent(tabComponents.get(group));
                  if (idx >= 0)
                  {
                     tabbedPane.setTitleAt(idx, group.getName());
                     tabbedPane.getTabComponentAt(idx).setName(group.toString());
                     updateViewMenu();
                  }
               }
            }
         });
      }

      @Override
      public void productGroupRemoved(ProductGroup group)
      {
         JComponent comp = tabComponents.get(group);
         if (comp == null)
            return;
         
         tabComponents.remove(group);
         tabbedPane.remove(comp);
         updateViewMenu();
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
