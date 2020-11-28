package file;

import drawing.shape.Shape;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Class that holds utility methods to load a save file from the
 * file system.
 *
 * @author 200008575
 */
public class Loader {
    /**
     * The file that the content will be loaded from
     */
    private final File file;

    /**
     * The frame of the application
     */
    private final JFrame frame;

    /**
     * Loader constructor
     */
    public Loader(File file, JFrame frame) {
        this.file = file;
        this.frame = frame;
    }

    /**
     * Method to invoke the loading procedure. On success, the method
     * will return a list of the shapes that it loaded from the saved
     * object. Those shapes will preserve all {@link drawing.shape.ShapeProperty}
     * entries that it holds. The save file will be marked as corrupted if the cast
     * to a list of shapes fails. If the file is corrupted, the method will
     * display a dialog notification and return null.
     *
     * @return A list of shapes that are loaded from the save file. Return null
     * if the save file is corrupted.
     */
    @SuppressWarnings("unchecked")
    public List<Shape> load() {
        List<Shape> shapes = null;

        try {
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this.frame, "File doesn't exist.");
            }

            // Read the VEX file and close the stream
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Attempt a cast to a list of shapes...
            shapes = (List<Shape>) objectInputStream.readObject();

            System.out.println("LOG: Loaded file " + file.toString());

            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            // display the 'File is corrupted' dialog
            JOptionPane.showMessageDialog(this.frame, "File is corrupted.");
        }

        return shapes;
    }
}
