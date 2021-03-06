package example;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.xml.bind.DatatypeConverter;

import org.selfbus.sbtools.vdio.SymbolUtil;
import org.selfbus.sbtools.vdio.VdioException;

/**
 * Small example program that shows a VD symbol in a window.
 */
public class SymbolViewer
{
   public static final String symbolDataStr =
      "424d56030400000000003600040028000000140000001400000001001000000000002003000000000000000000000000000000000000ff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7f0000000000000000000000000000000000000000000000000000000000000000000000000000ff7f0000ff7fff7fff7f00000000ff7fff7fff7fff7fff7fff7fff7f000000000000ff7fff7f0000ff7f0000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7fff7f0000ff7fff7fff7f0000ff7f0000ff7f00000000000000000000ff7fff7f0000ff7fff7fff7fff7f0000ff7f0000ff7f0000ff7f0000ff7f00000000000000000000ff7fff7fff7f0000ff7fff7fff7f0000ff7fff7fff7f0000ff7f0000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7f0000ff7fff7fff7f000000000000ff7fff7f0000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7fff7f0000ff7fff7fff7fff7f000000000000000000000000ff7f0000ff7f0000ff7fff7fff7fff7fff7fff7f0000ff7fff7fff7fff7f0000ff7f0000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7fff7fff7fff7f0000ff7fff7fff7fff7f00000000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7fff7fff7fff7fff7f0000ff7f00000000ff7f0000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7fff7fff7fff7fff7fff7f0000ff7fff7fff7f0000ff7f00000000000000000000ff7fff7fff7fff7fff7fff7fff7fff7fff7fff7f0000ff7fff7f0000ff7f00000000000000000000ff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7f0000ff7f0000ff7f0000ff7f0000ff7f0000ff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7f00000000ff7f0000ff7fff7fff7f0000ff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7f0000ff7f0000000000000000000000000000000000000000000000000000000000000000000000000000ff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7f";
   public static final byte[] symbolData = DatatypeConverter.parseHexBinary(symbolDataStr);

   public static void main(String[] args) throws VdioException
   {
      JFrame frame = new JFrame();
      frame.setLayout(new BorderLayout());
      frame.setSize(200, 200);
      frame.setTitle("Example Symbol");

      JLabel lbl = new JLabel(new ImageIcon(SymbolUtil.toImage(symbolData)));
      frame.add(lbl, BorderLayout.CENTER);

      frame.setVisible(true);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   }
}
