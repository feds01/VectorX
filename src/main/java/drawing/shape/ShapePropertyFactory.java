package drawing.shape;

import java.awt.Color;

public class ShapePropertyFactory {

    public ShapeProperty<Color> createColourProperty(String name, Color value) {
        return new ShapeProperty<>(name, value, any -> true);
    }
}
