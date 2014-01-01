package org.selfbus.sbtools.vdviewer.tabs;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;

import org.selfbus.sbtools.common.gui.components.CloseableComponent;
import org.selfbus.sbtools.common.gui.utils.TableUtils;
import org.selfbus.sbtools.vdviewer.VdViewer;
import org.selfbus.sbtools.vdviewer.vdx.VdxTable;

/**
 * A tab panel that contains the contents of a {@link VdxTable}.
 */
public class TableTab extends JPanel implements CloseableComponent
{
   private static final long serialVersionUID = -6162689610307314918L;

   private final JTable table;
   private final JScrollPane scrollPane;
   private final VdxTable vdxTable;

   /**
    * Create a tab panel that contains the contents of a {@link VdxTable}.
    *
    * @param vdxTable - the table to show.
    */
   public TableTab(VdxTable vdxTable)
   {
      super(new BorderLayout());

      this.vdxTable = vdxTable;

      table = new JTable(vdxTable);
      table.setAutoCreateRowSorter(true);
      table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      TableUtils.pack(table, 2);

      scrollPane = new JScrollPane(table);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

      // There is a strange bug in Ubuntu with OpenJDK 7 that disorts the
      // contents of the table when scrolling horizontally. Updating the scroll
      // pane after scrolling fixes the disortion after scrolling.
      //
      // Possible fix: add -Dsun.java2d.xrender=True to the java VM args. 
      scrollPane.getHorizontalScrollBar().addMouseListener(new MouseAdapter()
      {
         @Override
         public void mouseReleased(MouseEvent e)
         {
            table.invalidate();
            scrollPane.repaint();
         }
      });

      add(scrollPane);
   }

   /**
    * @return The {@link VdxTable table} that the tab contains.
    */
   public VdxTable getTable()
   {
      return vdxTable;
   }

   /**
    * @return The indices of all selected rows.
    */
   public int[] getSelectedRows()
   {
      return table.getSelectedRows();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
      VdViewer.getInstance().closePanel(this);
   }
}
