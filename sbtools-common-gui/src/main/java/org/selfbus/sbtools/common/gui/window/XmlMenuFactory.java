package org.selfbus.sbtools.common.gui.window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.selfbus.sbtools.common.gui.actions.ActionCreationException;
import org.selfbus.sbtools.common.gui.actions.ActionFactory;
import org.selfbus.sbtools.common.gui.actions.BasicActionFactory;
import org.selfbus.sbtools.common.gui.misc.SbToolsRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Factory class that creates {@link JMenuBar menu bars} from XML
 * configurations.
 */
public class XmlMenuFactory
{
   private static final Logger LOGGER = LoggerFactory.getLogger(XmlMenuFactory.class);
   private final ResourceBundle messagesBundle;
   private final ActionFactory actionFactory;

   /**
    * Create a menu-bar factory using the default action factory.
    *
    * @param messagesBundle - the resource bundle used to translate the menu names.
    */
   public XmlMenuFactory(ResourceBundle messagesBundle)
   {
      this(messagesBundle, BasicActionFactory.getInstance());
   }

   /**
    * Create a menu-bar factory.
    *
    * @param messagesBundle - the resource bundle used to translate the menu names.
    * @param actionFactory - the action factory to use.
    */
   public XmlMenuFactory(ResourceBundle messagesBundle, ActionFactory actionFactory)
   {
      this.messagesBundle = messagesBundle;
      this.actionFactory = actionFactory;
   }
   
   /**
    * Create a menu bar from an XML configuration file.
    * 
    * @param file - the file to parse
    * 
    * @return The created menu bar
    * @throws SbToolsRuntimeException if the XML configuration cannot be read
    */
   public JMenuBar createMenuBar(File file)
   {
      try
      {
         return createMenuBar(new FileInputStream(file));
      }
      catch (FileNotFoundException e)
      {
         throw new SbToolsRuntimeException(e);
      }
   }

   /**
    * Create a menu bar from an XML configuration file.
    * 
    * @param is - the input stream to parse
    * 
    * @return The created menu bar
    * @throws SbToolsRuntimeException if the XML configuration cannot be read
    */
   public JMenuBar createMenuBar(InputStream is)
   {
      Document doc;

      try
      {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         doc = db.parse(is);
         doc.getDocumentElement().normalize();
      }
      catch (ParserConfigurationException e)
      {
         throw new SbToolsRuntimeException(e);
      }
      catch (SAXException e)
      {
         throw new SbToolsRuntimeException(e);
      }
      catch (IOException e)
      {
         throw new SbToolsRuntimeException(e);
      }

      final NodeList menuNodes = doc.getElementsByTagName("menu");
      final String menuBarId = getNodeAttr(doc.getDocumentElement(), "id");
      final JMenuBar menuBar = new JMenuBar();

      for (int menuIdx = 0; menuIdx < menuNodes.getLength(); ++menuIdx)
      {
         final Node menuNode = menuNodes.item(menuIdx);
         final String menuId = getNodeAttr(menuNode, "id");
         String menuLabel = null;
         String menuLabelId = menuBarId + '.' + menuId;
         try
         {
            if (messagesBundle != null)
               menuLabel = messagesBundle.getString(menuLabelId);
         }
         catch (MissingResourceException e)
         {
         }

         if (menuLabel == null)
            menuLabel = '!' + menuLabelId + '!';

         final JMenu menu = createJMenu(menuLabel);
         menu.setName(menuId);
         menuBar.add(menu);

         final NodeList itemNodes = menuNode.getChildNodes();
         for (int itemIdx = 0; itemIdx < itemNodes.getLength(); ++itemIdx)
         {
            final Node itemNode = itemNodes.item(itemIdx);
            final String itemType = itemNode.getNodeName();

            if (itemType.equals("separator"))
            {
               menu.addSeparator();
            }
            else if (itemType.equals("action"))
            {
               final String actionId = getNodeAttr(itemNode, "id");
               try
               {
                  menu.add(actionFactory.getAction(actionId));
               }
               catch (ActionCreationException e)
               {
                  LOGGER.error("Failed to get action " + actionId, e);
                  menu.add("?"+actionId+"?");
               }
            }
         }
      }
      
      return menuBar;
   }

   /**
    * Get an attribute of a DOM node.
    * 
    * @param node - the node to process
    * @param name - the name of the attribute to fetch
    * 
    * @return The value of the attribute
    * 
    * @throws SbToolsRuntimeException if the node does not contain the attribute
    *         or if the attribute is empty
    */
   String getNodeAttr(final Node node, final String name)
   {
      final Node attr = node.getAttributes().getNamedItem(name);
      if (attr == null || attr.getNodeValue().isEmpty())
      {
         throw new SbToolsRuntimeException(node.getNodeName() + " has no attribute " + name + " or it is empty");
      }

      return attr.getNodeValue();
   }

   /**
    * Create a new menu with the given name and add it to the menu bar.
    * Mnemonics are properly detected if they are marked with an ampersand in
    * the name (e.g. "&File").
    * 
    * @param name - the name of the menu
    *
    * @return The created menu
    */
   public static JMenu createJMenu(String name)
   {
      final int idx = name.indexOf('&');
      char mnemonic = 0;
      if (idx >= 0)
      {
         mnemonic = name.charAt(idx + 1);
         name = name.substring(0, idx) + name.substring(idx + 1);
      }

      final JMenu menu = new JMenu(name);
      menu.setName(name);

      if (mnemonic != 0)
         menu.setMnemonic(mnemonic);

      return menu;
   }
}
