package file;

import drawing.shape.Shape;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Class that holds utility methods to save the current content to a
 * save file on the filesystem.
 *
 * @author 200008575
 */
public class Saver {
    /**
     * The file that the content will be save to.
     */
    private final File file;

    /**
     * The current content on the canvas.
     */
    private final List<Shape> data;

    /**
     * Saver class constructor.
     */
    public Saver(File file, List<Shape> data) {
        this.file = file;
        this.data = data;
    }

    /**
     * Method to invoke the saving procedure. The method will attempt to write
     * each shape to an {@link java.io.ObjectOutputStream} which will then be written
     * to the save file. All properties of each shape will be serialized and written
     * to the save file.
     *
     */
    public void save() {
        try {

            // check that file exists before attempting to write to it.
            if (!file.exists()) {
                var createdFile = file.createNewFile();

                if (createdFile) {
                    System.out.println("LOG: Preparing save file...");
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            // Create a new ObjectOutputStream from the file stream to write the shapes
            // to
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write the VEX file and close the stream
            objectOutputStream.writeObject(this.data);

            System.out.println("LOG: Saved file to " + file.toString());

            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
