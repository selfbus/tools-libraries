package org.selfbus.sbtools.common.gui.components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * A generic {@link JButton} that has its look&feel adapted for tool-bars. 
 */
public class ToolBarButton extends JButton
{
   private static final long serialVersionUID = -6435178562438145811L;

   /**
    * Creates an empty button.
    */
   public ToolBarButton()
   {
      super();
      useToolBarStyle(this);
   }

   /**
    * Creates a button with an icon.
    * 
    * @param icon - the icon of the button.
    */
   public ToolBarButton(Icon icon)
   {
      super(icon);
      useToolBarStyle(this);
   }

   /**
    * Creates a button with a text.
    * 
    * @param text - the text that is displayed in the button.
    */
   public ToolBarButton(String text)
   {
      super(text);
      useToolBarStyle(this);
   }

   /**
    * Creates a button from an action.
    * 
    * @param a - the action to create the button from.
    */
   public ToolBarButton(Action a)
   {
      super(a);
      useToolBarStyle(this);
   }

   /**
    * Creates a button with an icon and a text.
    *
    * @param icon - the icon of the button.
    * @param text - the text that is displayed in the button.
    */
   public ToolBarButton(String text, Icon icon)
   {
      super(text, icon);
      useToolBarStyle(this);
   }

   /**
    * Change the style of the given button to fit for tool-bars.
    *
    * @param button - the button to change.
    */
   public static void useToolBarStyle(JButton button)
   {
      button.setFocusable(false);
      button.setBorderPainted(false);
      button.setOpaque(false);
   }
}
