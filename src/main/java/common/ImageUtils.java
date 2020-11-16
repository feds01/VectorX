package common;

import javax.swing.ImageIcon;
import java.awt.Image;


/**
 *
 */
public class ImageUtils {

    /**
     *
     */
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        var image = icon.getImage();

        var resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }
}
