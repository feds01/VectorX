package common;

import drawing.shape.ImageShape;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * */
public class FileChooserDialog {

    public static File showSaveFileChooser(String extension) {
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        // filter for image files only
        FileFilter imageFilter = new FileNameExtensionFilter(
                "File Type", extension);

        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
          return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
