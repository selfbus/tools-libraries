package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.selfbus.sbtools.common.AbstractTreeModel;
import org.selfbus.sbtools.knxio.model.Namespaces;

/**
 * A catalog is a structured tree that contains catalog sections as branches
 * and catalog items as leaves.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class Catalog extends AbstractTreeModel implements CatalogNode
{
   @XmlElement(name = "CatalogSection", namespace = Namespaces.KNX)
   List<CatalogSection> sections;

   @XmlTransient
   private final RootNode rootNode = new RootNode();

   /**
    * Get the list of catalog sections.
    *
    * @return The list of catalog sections
    */
   @Override
   public List<CatalogSection> getSections()
   {
      return sections;
   }

   /**
    * Set the list of catalog sections.
    *
    * @param sections The sections to set.
    */
   public void setSections(List<CatalogSection> sections)
   {
      this.sections = sections;
   }

   @Override
   public String toString()
   {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

   @Override
   public Object getRoot()
   {
      return this;
   }

   @Override
   public Object getChild(Object parent, int index)
   {
      if (!(parent instanceof CatalogNode))
         return null;

      List<CatalogSection> sections = ((CatalogNode) parent).getSections();
      int numSections = sections == null ? 0 : sections.size();

      if (index < numSections)
         return sections == null ? null : sections.get(index);

      if (parent instanceof CatalogSection)
      {
         List<CatalogItem> items = ((CatalogSection) parent).getItems();
         return items == null ? null : items.get(index - numSections);
      }

      return null;
   }

   @Override
   public int getChildCount(Object parent)
   {
      if (!(parent instanceof CatalogNode))
         return 0;

      List<CatalogSection> sections = ((CatalogNode) parent).getSections();
      int num = sections == null ? 0 : sections.size();

      if (parent instanceof CatalogSection)
      {
         List<CatalogItem> items = ((CatalogSection) parent).getItems();
         num += items == null ? 0 : items.size();
      }

      return num;
   }

   @Override
   public boolean isLeaf(Object node)
   {
      return node instanceof CatalogItem;
   }

   @Override
   public void valueForPathChanged(TreePath path, Object newValue)
   {
      TreeModelEvent e = new TreeModelEvent(newValue, path);

      for (TreeModelListener l : eventListeners.getListeners(TreeModelListener.class))
         l.treeNodesChanged(e);
   }

   @Override
   public int getIndexOfChild(Object parent, Object child)
   {
      CatalogSection parentSection = (CatalogSection) parent;
      return parentSection.getSections().indexOf(child);
   }

   public class RootNode implements TreeNode
   {
      @Override
      public TreeNode getChildAt(int childIndex)
      {
         return (TreeNode) Catalog.this.getChild(Catalog.this, childIndex);
      }

      @Override
      public int getChildCount()
      {
         return Catalog.this.getChildCount(Catalog.this);
      }

      @Override
      public TreeNode getParent()
      {
         return null;
      }

      @Override
      public int getIndex(TreeNode child)
      {
         return Catalog.this.getIndexOfChild(Catalog.this, child);
      }

      @Override
      public boolean getAllowsChildren()
      {
         return true;
      }

      @Override
      public boolean isLeaf()
      {
         return getChildCount() == 0;
      }

      @Override
      public Enumeration children()
      {
         return Collections.enumeration(Catalog.this.getSections());
      }
   }
}
