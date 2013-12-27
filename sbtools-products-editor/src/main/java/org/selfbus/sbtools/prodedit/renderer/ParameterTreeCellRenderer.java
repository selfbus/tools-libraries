package org.selfbus.sbtools.prodedit.renderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.prodedit.model.enums.ParameterAtomicType;
import org.selfbus.sbtools.prodedit.model.prodgroup.ApplicationProgram;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.CommunicationObject;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.Parameter;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterCategory;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterRoot;
import org.selfbus.sbtools.prodedit.model.prodgroup.parameter.ParameterType;

public class ParameterTreeCellRenderer implements TreeCellRenderer
{
   private static final Icon comObjectIcon = ImageCache.getIcon("icons/connect");
   private static final Icon paramPageIcon = ImageCache.getIcon("icons/parameter");
   private static final Icon paramLabelIcon = ImageCache.getIcon("icons/param_label");
   private static final Icon paramFieldIcon = ImageCache.getIcon("icons/param_field");
   private static final Icon paramEnumIcon = ImageCache.getIcon("icons/param_enum");
   private static final Icon paramRootIcon = ImageCache.getIcon("icons/param_root");

   private final TreeCellRenderer defaultRenderer;
   private ApplicationProgram program;

   public ParameterTreeCellRenderer(TreeCellRenderer defaultRenderer)
   {
      this.defaultRenderer = defaultRenderer;
   }

   /**
    * Set the {@link ApplicationProgram} that is used for lookups.
    *
    * @param program - the program to set
    */
   public void setProgram(ApplicationProgram program)
   {
      this.program = program;
   }

   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
      int row, boolean hasFocus)
   {
      String label = null;
      Icon icon = null;

      if (value instanceof Parameter)
      {
         Parameter param = (Parameter) value;

         if (param.getId() == 11)
            icon = null;

         ParameterCategory category = param.getCategory();
         if (category == ParameterCategory.LABEL)
            icon = paramLabelIcon;
         else if (category == ParameterCategory.PAGE)
            icon = paramPageIcon;
         else if (program != null)
         {
            ParameterType paramType = program.getParameterType(param.getTypeId());
            ParameterAtomicType atomicType = paramType.getAtomicType();

            if (atomicType == ParameterAtomicType.ENUM || atomicType == ParameterAtomicType.LONG_ENUM)
               icon = paramEnumIcon;
            else icon = paramFieldIcon;
         }

         label = param.getDescription().getDefaultText();
         if (label == null || label.isEmpty())
            label = param.getName();

         label += "  [" + param.getId() + ']';
      }
      else if (value instanceof CommunicationObject)
      {
         CommunicationObject comObject = (CommunicationObject) value;

         icon = comObjectIcon;
         label = ((CommunicationObject) value).getName().getDefaultText();
         label += '.';
         label += ((CommunicationObject) value).getFunction().getDefaultText();
         label += "  [" + comObject.getId() + ']';
      }
      else if (value instanceof ParameterRoot)
      {
         label = "/";
         icon = paramRootIcon;
      }

      if (label != null && !label.isEmpty())
         value = label;

      Component c = defaultRenderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      if (c instanceof JLabel)
      {
         ((JLabel) c).setIcon(icon);
      }

      return c;
   }
}
