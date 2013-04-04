package org.selfbus.sbtools.common.gui.models;

import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * FilteredListModel filters an unsorted ListModel to provide a filtered model.
 * You can create a FilteredListModel from models you already have. Place the
 * FilteredListModel into a JList, for example, to provide a filtered view of
 * your underlying model.
 * <p>
 * The filter includes the elements - the elements that do not match the filter
 * are hidden.
 * @param <E>
 */
public class FilteredListModel<E> extends AbstractListModel<E>
{
   private static final long serialVersionUID = 6890531963375522035L;

   private Vector<E> filteredList;
   private ListModel<E> unfilteredModel;
   private Filter filter;

   /**
    * Create an empty FilteredListModel.
    */
   @SuppressWarnings("unused")
   private FilteredListModel()
   {
   }

   /**
    * Create a FilteredListModel from an existing model using a specific filter.
    *
    * @param model - the unfiltered list model
    * @param filter - the filter that should be used for filtering
    */
   public FilteredListModel(ListModel<E> model, Filter filter)
   {
      unfilteredModel = model;
      this.filter = filter;

      update();

      unfilteredModel.addListDataListener(new ListDataListener()
      {
         public void intervalAdded(ListDataEvent e)
         {
            update();
         }

         public void intervalRemoved(ListDataEvent e)
         {
            update();
         }

         public void contentsChanged(ListDataEvent e)
         {
            update();
         }

      });
   }

   /**
    * Update the filtered list model. Call this method if the filter changed or
    * if elements in the unfiltered model changed.
    */
   public void update()
   {
      final int filteredCap = filteredList == null ? -1 : filteredList.capacity();
      final int unfilteredSize = unfilteredModel == null ? 0 : unfilteredModel.getSize();
      if (unfilteredSize > filteredCap)
         filteredList = new Vector<E>(unfilteredSize + 100);

      filteredList.clear();
      for (int i = 0; i < unfilteredSize; ++i)
      {
         final E elem = unfilteredModel.getElementAt(i);
         if (filter == null || filter.matches(elem))
            filteredList.add(elem);
      }

      fireContentsChanged(ListDataEvent.CONTENTS_CHANGED, 0, filteredList.size() - 1);
   }

   /**
    * Retrieve a filtered entry.
    *
    * @param index index of an entry in the filtered model
    * @return the element
    * @throws IndexOutOfBoundsException if the index is out of bounds.
    */
   public E getElementAt(int index) throws IndexOutOfBoundsException
   {
      return filteredList.elementAt(index);
   }

   /**
    * Retrieve the size of the underlying model.
    *
    * @return size of the model
    */
   public int getSize()
   {
      int size = filteredList == null ? 0 : filteredList.size();
      return size;
   }

   /**
    * @return the unfiltered model
    */
   public ListModel<E> getUnfilteredModel()
   {
      return unfilteredModel;
   }

   /**
    * Set the unfiltered model.
    *
    * @param unfilteredModel - the unfiltered model to set
    */
   public void setUnfilteredModel(ListModel<E> unfilteredModel)
   {
      this.unfilteredModel = unfilteredModel;
      update();
   }

   /**
    * @return the filter.
    */
   public Filter getFilter()
   {
      return filter;
   }

   /**
    * Set the filter.
    *
    * @param filter - the filter to set
    */
   public void setFilter(Filter filter)
   {
      this.filter = filter;
      update();
   }
}
