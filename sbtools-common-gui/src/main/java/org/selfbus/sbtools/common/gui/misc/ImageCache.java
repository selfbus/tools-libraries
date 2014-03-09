package org.selfbus.sbtools.common.gui.misc;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.slf4j.LoggerFactory;

/**
 * A global class that loads and caches the images.
 */
public final class ImageCache
{
   private static final Map<String, ImageIcon> ICON_CACHE = new HashMap<String, ImageIcon>();

   /**
    * Loads the icon with the given name.
    *
    * @param iconName - the name of the icon, without extension. The path to the
    *           icon shall be a resource path that can be resolved by
    *           {@link ClassLoader#getResource(String)}.
    *
    * @return the icon
    */
   public static synchronized ImageIcon getIcon(String iconName)
   {
      ImageIcon icon = ICON_CACHE.get(iconName);
      if (icon == null)
      {
         final ClassLoader classLoader = ImageCache.class.getClassLoader();

         final URL imgURL = classLoader.getResource("images/" + iconName + ".png");
         if (imgURL != null)
         {
            icon = new ImageIcon(imgURL);
         }
         else
         {
            LoggerFactory.getLogger(ImageCache.class).error("Could not find icon: " + iconName + ".png");
         }
         ICON_CACHE.put(iconName, icon);
      }
      return icon;
   }

   /**
    * Load the icon with the given name, and add the overlay icon with the given
    * name.
    *
    * @param imageName - the name of the main icon, without extension. The path to the
    *           icon shall be a resource path that can be resolved by
    *           {@link ClassLoader#getResource(String)}.
    * @param overlayName - the name of the overlay icon, without extension. The
    *           path to the overlay icon shall be a resource path that can be
    *           resolved by {@link ClassLoader#getResource(String)}.
    *
    * @return the combined icon
    */
   public static synchronized ImageIcon getIcon(String imageName, String overlayName)
   {
      final String cacheKey = imageName + '|' + overlayName;
      ImageIcon icon = ICON_CACHE.get(cacheKey);
      if (icon != null)
         return icon;

      final ImageIcon imageIcon = getIcon(imageName);
      final ImageIcon overlayIcon = getIcon(overlayName);

      if (overlayIcon == null)
         return imageIcon;
      if (imageIcon == null)
         return overlayIcon;

      final int iw = imageIcon.getIconWidth();
      final int ih = imageIcon.getIconHeight();

//      final int ow = overlayIcon.getIconWidth();
//      final int oh = overlayIcon.getIconHeight();

      final BufferedImage img = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = img.createGraphics();
      g2d.drawImage(imageIcon.getImage(), null, null);

      g2d.setComposite(AlphaComposite.SrcOver);
      g2d.drawImage(overlayIcon.getImage(), null, null);

      icon = new ImageIcon(img);
      ICON_CACHE.put(cacheKey, icon);
      return icon;
   }
}
