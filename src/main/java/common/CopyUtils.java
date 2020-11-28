package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class that holds utility methods to copy objects and make a new
 * instance of the object.
 *
 * @author 200008575
 * */
public class CopyUtils {
    /**
     * Makes a deep copy of any Java object that is passed.
     */
    public static Object deepCopy(Object object) {
        try {
            var outputStream = new ByteArrayOutputStream();
            var out = new ObjectOutputStream(outputStream);
            out.writeObject(object);

            var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            var objInputStream = new ObjectInputStream(inputStream);

            return objInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
