package common;

import javax.swing.ImageIcon;
import java.awt.Image;


/**
 * Class that holds utility methods to apply methods on image objects.
 *
 * @author 200008575
 * */
public class ImageUtils {

    /**
     * Method used to resize an image icon to a specified width and height.
     *
     * @param icon - The original image icon that will be resized.
     * @param width - The width of the new width icon.
     * @param height - The height of the new height icon.
     *
     * @return The newly resized image icon
     */
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        var image = icon.getImage();

        var resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }
}
