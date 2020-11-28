package drawing.shape;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 */
public class ShapePropertyFactory implements Serializable {

    /**
     *
     */
    public static ShapeProperty<Color> createColourProperty(String name, Color value) {
        return new ShapeProperty<>(name, value, any -> true);
    }

    /**
     *
     */
    public static ShapeProperty<Point> createPointProperty(String name, Point value) {
        return new ShapeProperty<>(name, value, p -> p.getX() >= -1000 && p.getY() >= -1000);
    }
}
