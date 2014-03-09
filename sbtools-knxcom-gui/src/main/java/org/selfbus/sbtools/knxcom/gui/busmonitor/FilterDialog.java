package org.selfbus.sbtools.knxcom.gui.busmonitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.common.gui.components.AddressField;
import org.selfbus.sbtools.common.gui.components.Dialog;
import org.selfbus.sbtools.common.gui.utils.SwingUtils;
import org.selfbus.sbtools.knxcom.gui.internal.I18n;
import org.selfbus.sbtools.knxcom.telegram.Transport;

/**
 * A dialog for configuring a telegram filter.
 */
public class FilterDialog extends Dialog
{
   private static final long serialVersionUID = -2530779340004442143L;

   private final Map<Transport, JCheckBox> transTypeBoxes = new HashMap<Transport, JCheckBox>();
   private final JCheckBox cbxEnable = new JCheckBox(I18n.getMessage("FilterDialog.enabled"));
   private final AddressField adfFrom = new AddressField(AddressField.PHYSICAL);
   private final AddressField adfDest = new AddressField(AddressField.ANY);

   private final JPanel optionsPane = new JPanel();
   private FrameFilter filter;

   /**
    * Create a dialog for configuring a telegram filter.
    *
    * @param owner - the owning window.
    */
   public FilterDialog(Window owner)
   {
      this(owner, ModalityType.MODELESS);
   }

   /**
    * Create a dialog for configuring a telegram filter.
    *
    * @param owner - the owning window.
    * @param modalityType - the modality type.
    */
   public FilterDialog(Window owner, ModalityType modalityType)
   {
      super(owner, modalityType);
      setTitle(I18n.getMessage("FilterDialog.title"));

      final Container body = getBodyPane();
      body.setLayout(new BorderLayout(0, 2));

      body.add(cbxEnable, BorderLayout.NORTH);
      cbxEnable.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            SwingUtils.setEnable(optionsPane, cbxEnable.isSelected());
         }
      });

      body.add(optionsPane, BorderLayout.CENTER);
      optionsPane.setLayout(new BoxLayout(optionsPane, BoxLayout.Y_AXIS));
      optionsPane.add(createAddressGroup());
      optionsPane.add(createAppTypeGroup());

      addButton(new JButton(I18n.getMessage("Button.ok")), Dialog.ACCEPT);
      addButton(new JButton(I18n.getMessage("Button.cancel")), Dialog.REJECT);

      setSize(getPreferredSize());
      center();
   }

   /**
    * Create a GUI component for editing the sender and destination addresses of
    * a filter.
    */
   protected Component createAddressGroup()
   {
      final JPanel panel = createFilterGroup(I18n.getMessage("FilterDialog.addressGroup"));
      panel.setLayout(new GridBagLayout());

      final GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets(2, 2, 2, 2);

      c.anchor = GridBagConstraints.EAST;
      c.gridx = 0;
      c.gridy = 0;
      panel.add(new JLabel(I18n.getMessage("FilterDialog.from")), c);

      c.gridy = 1;
      panel.add(new JLabel(I18n.getMessage("FilterDialog.dest")), c);

      c.anchor = GridBagConstraints.WEST;
      c.weightx = 2;
      c.gridx = 1;

      c.gridy = 0;
      panel.add(adfFrom, c);

      c.gridy = 1;
      panel.add(adfDest, c);

      return panel;
   }

   /**
    * Create a GUI component for editing the application types of a filter.
    */
   protected Component createAppTypeGroup()
   {
      final JPanel panel = createFilterGroup(I18n.getMessage("FilterDialog.transportTypeGroup"));
      panel.setLayout(new GridBagLayout());

      transTypeBoxes.clear();

      final Transport[] values = Transport.values();
      final int cols = 2;
      final int rows = (values.length + cols - 1) / cols;

      final GridBagConstraints c = new GridBagConstraints();
      c.anchor = GridBagConstraints.WEST;
      c.insets = new Insets(0, 2, 0, 2);

      for (int row = 0; row < rows; ++row)
      {
         for (int col = 0; col < cols; ++col)
         {
            final int idx = row + col * rows;
            if (idx >= values.length)
               break;

            final Transport type = values[idx];
            final JCheckBox cbox = new JCheckBox(type.toString());

            c.gridx = col;
            c.gridy = row;
            panel.add(cbox, c);

            transTypeBoxes.put(type, cbox);
         }
      }

      return panel;
   }

   /**
    * Create a captioned panel for a group of filter options.
    */
   private JPanel createFilterGroup(final String caption)
   {
      final JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createTitledBorder(caption));

      return panel;
   }

   /**
    * Set the filter to configure.
    */
   public void setFilter(FrameFilter filter)
   {
      this.filter = filter;
      updateContents();
   }

   /**
    * @return the edited filter.
    */
   public FrameFilter getFilter()
   {
      return filter;
   }

   /**
    * Update the dialog's contents from the filter. Called by
    * {@link #setFilter(FrameFilter)}.
    */
   public void updateContents()
   {
      cbxEnable.setSelected(filter.isEnabled());

      SwingUtils.setEnable(optionsPane, filter.isEnabled());

      adfFrom.setAddress(filter.getFrom());
      adfDest.setAddress(filter.getDest());

      final Set<Transport> filterTrans = filter.getTransportTypes();
      for (final Transport type : Transport.values())
         transTypeBoxes.get(type).setSelected(filterTrans.contains(type));
   }

   /**
    * Apply the dialog's contents to the filter.
    */
   @Override
   public void accept()
   {
      filter.setEnabled(cbxEnable.isSelected());

      filter.setFrom((PhysicalAddress) adfFrom.getAddress());
      filter.setDest(adfDest.getAddress());

      final Set<Transport> filterTrans = filter.getTransportTypes();
      for (final Transport type : Transport.values())
      {
         final JCheckBox cbox = transTypeBoxes.get(type);
         if (!cbox.isSelected())
            filterTrans.remove(type);
         else filterTrans.add(type);
      }
   }
}
