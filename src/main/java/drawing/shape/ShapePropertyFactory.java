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
    public ShapeProperty<Color> createColourProperty(String name, Color value) {
        return new ShapeProperty<>(name, value, any -> true);
    }

    /**
     *
     */
    public ShapeProperty<Point> createPointProperty(String name, Point value) {
        return new ShapeProperty<>(name, value, p -> p.getX() >= 0 && p.getY() >= 0);
    }
}
