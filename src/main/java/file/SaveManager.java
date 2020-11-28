package file;

import common.FileChooserDialog;
import ui.widget.CanvasWidget;

import javax.swing.JFrame;
import java.io.File;


/**
 * A singleton Class that holds the custom file saving/loading mechanism of the
 * application.
 *
 * @author 200008575
 * */
public class SaveManager {
    /**
     * Variable that holds the reference of this object that is used
     * when external callers need to access the file loading object.
     */
    private static final SaveManager instance = new SaveManager();

    /**
     * The file that the content will be save to.
     */
    private File file = null;

    /**
     * SaveManager instantiation method.
     */
    private SaveManager() {
    }

    /**
     * Method to get an instance of the SaveManager object
     *
     * @return A reference of this object.
     */
    public static SaveManager getInstance() {
        return instance;
    }

    /**
     * Method to get the current file
     *
     * @return the current file
     * */
    public File getFile() {
        return file;
    }

    /**
     * Method to get the current file
     *
     * @param file - set the current file
     * */
    public void setFile(File file) {
        this.file = file;
    }


    /**
     * Method to open a dialog which will select a file and attempt
     * to load the file using the {@link Loader} object. If the operation
     * is cancelled, the current content is not removed and the
     * operation exits. If the user does open the current file, the current
     * information is saved if a file is already opened and then overwrites
     * the current content.
     *
     * @param widget - The widget of the canvas.
     * @param frame - The frame of the application.
     *
     * */
    public void openFile(CanvasWidget widget, JFrame frame) {
        File file = FileChooserDialog.showSaveFileChooser("vex");

        // create the saver object and invoke a save action
        if (file != null) {
            var loader = new Loader(file, frame);
            var data = loader.load();

            if (data != null) {

                // Save the contents of the current frame to the current opened
                // file.
                if (this.file != null) {
                    this.saveFile(widget, frame, false);
                }

                // update our file
                this.setFile(file);

                frame.setTitle("VectorX - Editing " + file.getName());
                widget.getCanvas().setShapes(data);
            }
        }
    }

    /**
     * Method to save the content of the canvas to a file. If a file is already
     * opened, then the contents of the canvas will automatically be saved to said
     * file. However, if no file is opened or the user wishes to save to a specific
     * file, then a dialog which will select a file and attempt to save the file using
     * the {@link Saver} object. If the operation is cancelled, the current content is
     * not removed and the operation exits.
     *
     * @param widget - The widget of the canvas.
     * @param frame - The frame of the application.
     *
     * */
    public void saveFile(CanvasWidget widget, JFrame frame, boolean overrideDefault) {

        // If the user wants to save the current file to a new location
        if (file == null || overrideDefault) {
            File file = FileChooserDialog.showSaveFileChooser("vex");

            if (file != null) {
                this.file = file;
                frame.setTitle("VectorX - Editing " + file.getName());
            }
        }


        var data = widget.getCanvas().getShapes();

        // create the saver object and invoke a save action
        var saver = new Saver(file, data);
        saver.save();
    }
}
