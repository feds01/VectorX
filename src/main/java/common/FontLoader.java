package common;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton Class that holds the custom font loading mechanism of the
 * application. The font loader will load files from the resources folder.
 *
 * @author 200008575
 * */
public class FontLoader {

    /**
     * Variable that holds the reference of this object that is used
     * when external callers need to access the file loading object.
     */
    private static final FontLoader instance = new FontLoader();


    /**
     * A map of all custom fonts that are loaded by the application that
     * are held within the 'resources/font' folder.
     * */
    private final Map<String, Font> fontMap = new HashMap<>();

    /**
     * FontLoader instantiation method.
     */
    private FontLoader() {
        try (var fontStream = FontLoader.class.getResourceAsStream("/resources/font/NotoSans.ttf")) {

            this.fontMap.put("NotoSans", Font.createFont(Font.TRUETYPE_FONT, fontStream));

        } catch (IOException | FontFormatException e) {
            System.out.println("Couldn't load application fonts.");
        }
    }

    /**
     * Method to get an instance of the FontLoader object
     *
     * @return A reference of this object.
     */
    public static FontLoader getInstance() {
        return instance;
    }


    /**
     * Method to get a key that is represented in the properties object.
     *
     * @param name The name of the key to be accessed.
     * @return The Font object that is held under the name in the font map
     * @throws IllegalArgumentException if the key doesn't exist in the font map
     *                                  object.
     */
    public Font getFont(String name) throws IllegalArgumentException {
        if (!fontMap.containsKey(name)) {
            throw new IllegalArgumentException("Invalid font name.");
        }

        return this.fontMap.get(name).deriveFont(12f);
    }
}
