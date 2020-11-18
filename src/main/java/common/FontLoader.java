package common;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontLoader {
    private static final FontLoader instance = new FontLoader();


    private final Map<String, Font> fontMap = new HashMap<>();

    private FontLoader() {
        try (var fontStream = FontLoader.class.getResourceAsStream("/font/NotoSans.ttf")) {

            this.fontMap.put("NotoSans", Font.createFont(Font.TRUETYPE_FONT, fontStream));

        } catch (IOException | FontFormatException e) {
            System.out.println("Couldn't load application fonts.");
        }
    }

    public static FontLoader getInstance() {
        return instance;
    }


    public Font getFont(String name) throws IllegalArgumentException {
        if (!fontMap.containsKey(name)) {
            throw new IllegalArgumentException("Invalid font name.");
        }

        return this.fontMap.get(name).deriveFont(12f);
    }
}
