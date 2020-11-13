package common;

import javax.swing.*;
import java.awt.*;


/**
 *
 * */
public class ImageUtils {

    /**
     *
     * */
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        var image = icon.getImage();

        var resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }
}
