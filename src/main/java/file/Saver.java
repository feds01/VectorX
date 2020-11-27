package file;

import drawing.shape.Shape;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 */
public class Saver {
    /**
     *
     */
    private final File file;

    /**
     *
     */
    private final List<Shape> data;

    /**
     *
     */
    public Saver(File file, List<Shape> data) {
        this.file = file;
        this.data = data;
    }

    /**
     *
     */
    public void save() {
        try {
            if (!file.exists()) {
                var createdFile = file.createNewFile();

                if (createdFile) {
                    System.out.println("LOG: Preparing save file...");
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
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
