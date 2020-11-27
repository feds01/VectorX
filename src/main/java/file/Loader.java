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
 *
 */
public class Loader {
    /**
     *
     */
    private final File file;

    /**
     *
     */
    private final JFrame frame;

    /**
     *
     */
    public Loader(File file, JFrame frame) {
        this.file = file;
        this.frame = frame;
    }

    /**
     *
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


            shapes = (List<Shape>) objectInputStream.readObject();

            System.out.println("LOG: Loaded file " + file.toString());

            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this.frame, "File is corrupted.");
        }

        return shapes;
    }
}
