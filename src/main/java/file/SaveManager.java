package file;

import common.FileChooserDialog;
import ui.widget.CanvasWidget;

import javax.swing.JFrame;
import java.io.File;

public class SaveManager {
    /**
     *
     */
    private static final SaveManager instance = new SaveManager();

    /**
     *
     */
    private File file = null;

    /**
     *
     */
    private SaveManager() {
    }

    /**
     *
     */
    public static SaveManager getInstance() {
        return instance;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public void openFile(CanvasWidget widget, JFrame frame) {
        File file = FileChooserDialog.showSaveFileChooser("vex");

        // create the saver object and invoke a save action
        if (file != null) {
            var loader = new Loader(file, frame);
            var data = loader.load();

            if (data != null) {
                // update our file
                this.setFile(file);

                frame.setTitle("VectorX - Editing " + file.getName());
                widget.getCanvas().setShapes(data);
            }
        }
    }

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
