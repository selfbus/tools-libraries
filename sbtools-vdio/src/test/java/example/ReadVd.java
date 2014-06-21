package example;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.selfbus.sbtools.vdio.ProductsReader;
import org.selfbus.sbtools.vdio.VdioException;
import org.selfbus.sbtools.vdio.model.VD;

/**
 * Small example program that reads a VD.
 * Used to test VD reading.
 */
public class ReadVd
{
   public static void main(String[] args) throws VdioException, FileNotFoundException
   {
      final JFrame frame = new JFrame();
      frame.setLayout(new BorderLayout());
      frame.setSize(300, 300);
      frame.setTitle("Read VD");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      final Label lbl = new Label();
      lbl.setText("Click \"Open\" to open a VD");
      frame.add(lbl, BorderLayout.CENTER);

      final Button btn = new Button("Open...");
      frame.add(btn, BorderLayout.SOUTH);

      btn.addActionListener(new ActionListener()
      {
         File file;
         String password;

         @Override
         public void actionPerformed(ActionEvent e)
         {
            final JFileChooser dlg = new JFileChooser();
            dlg.setDialogTitle("Select VD to read");
            dlg.setSelectedFile(file);

            if (dlg.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
              return;

            ProductsReader reader = new ProductsReader(frame);
            reader.setZipPassword(password);

            VD vd;
            try
            {
               file = dlg.getSelectedFile();
               vd = reader.read(file);
               lbl.setText("VD: " + vd.name + ", created: " + vd.created);
            }
            catch (FileNotFoundException | VdioException e1)
            {
               e1.printStackTrace();
               lbl.setText(e1.getMessage());
            }
            finally
            {
               password = reader.getZipPassword();
            }
         }
      });
 
      frame.setVisible(true);
   }
}
