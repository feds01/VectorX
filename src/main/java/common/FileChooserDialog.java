package common;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * Class that holds utility methods to instantiate file dialogs to
 * choose files to open or to save files objects to.
 *
 * @author 200008575
 * */
public class FileChooserDialog {

    /**
     * Utility method to create a JFileChooser which enables the user
     * to select a file from the file system.
     *
     * @param extension - Filter files by a specific file extension.
     * @return The chosen file from the dialog, if the user doesnt choose any
     * file the methods returns null.
     */
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
