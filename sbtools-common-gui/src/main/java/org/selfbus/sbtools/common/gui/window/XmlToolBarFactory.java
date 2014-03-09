package org.selfbus.sbtools.common.gui.window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.selfbus.sbtools.common.gui.actions.ActionCreationException;
import org.selfbus.sbtools.common.gui.actions.ActionFactory;
import org.selfbus.sbtools.common.gui.actions.BasicActionFactory;
import org.selfbus.sbtools.common.gui.components.ToolBar;
import org.selfbus.sbtools.common.gui.misc.SbToolsRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Factory class that creates {@link JToolBar tool bars} from XML configurations.
 */
//@Component
public class XmlToolBarFactory
{
   private static final Logger LOGGER = LoggerFactory.getLogger(XmlToolBarFactory.class);

   private final ActionFactory actionFactory;

   /**
    * Create a tool bar factory using the default action factory.
    */
   public XmlToolBarFactory()
   {
      this(BasicActionFactory.getInstance());
   }

   /**
    * Create a tool bar factory.
    * 
    * @param actionFactory - the action factory to use.
    */
   public XmlToolBarFactory(ActionFactory actionFactory)
   {
      this.actionFactory = actionFactory;
   }

   /**
    * Create a tool bar from an XML configuration file.
    * 
    * @param file - the file to parse
    * 
    * @return The created tool bar
    * @throws SbToolsRuntimeException if the XML configuration cannot be read
    */
   public JToolBar createToolBar(File file)
   {
      try
      {
         return createToolBar(new FileInputStream(file));
      }
      catch (FileNotFoundException e)
      {
         throw new SbToolsRuntimeException(e);
      }
   }

   /**
    * Create a tool bar from an XML configuration file.
    * 
    * @param is - the input stream to parse
    * 
    * @return The created tool bar
    * @throws SbToolsRuntimeException if the XML configuration cannot be read
    */
   public JToolBar createToolBar(InputStream is)
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

      final NodeList toolBarNodes = doc.getElementsByTagName("toolbar");
      //      final String mainToolBarId = getNodeAttr(doc.getDocumentElement(), "id");
      final JToolBar toolBar = new ToolBar();

      for (int toolBarIdx = 0; toolBarIdx < toolBarNodes.getLength(); ++toolBarIdx)
      {
         final Node toolBarNode = toolBarNodes.item(toolBarIdx);
         //         final String toolBarId = getNodeAttr(toolBarNode, "id");

         if (toolBarIdx > 0)
            toolBar.addSeparator();

         final NodeList itemNodes = toolBarNode.getChildNodes();
         for (int itemIdx = 0; itemIdx < itemNodes.getLength(); ++itemIdx)
         {
            final Node itemNode = itemNodes.item(itemIdx);
            final String itemType = itemNode.getNodeName();

            if (itemType.equals("separator"))
            {
               toolBar.addSeparator();
            }
            else if (itemType.equals("action"))
            {
               final String actionId = getNodeAttr(itemNode, "id");
               try
               {
                  toolBar.add(actionFactory.getAction(actionId));
               }
               catch (ActionCreationException e)
               {
                  LOGGER.error("Failed to get action " + actionId, e);

                  final JLabel lbl = new JLabel(" [?] ");
                  lbl.setToolTipText("?" + actionId + "?");
                  toolBar.add(lbl);
               }
            }
         }
      }

      return toolBar;
   }

   /**
    * Get an attribute of a DOM node.
    * 
    * @param node - the node to process
    * @param name - the name of the attribute to fetch
    * 
    * @return The value of the attribute
    * 
    * @throws SbToolsRuntimeException if the node does not contain the attribute or if the attribute
    *            is empty
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
    * @return The action factory.
    */
   public ActionFactory getActionFactory()
   {
      return actionFactory;
   }
}
