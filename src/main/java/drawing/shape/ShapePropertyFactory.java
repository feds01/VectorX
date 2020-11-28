package drawing.shape;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 * Class that holds utility methods to instantiate frequently used shape
 * property objects with pre-defined validators.
 *
 * @author 200008575
 * */
public class ShapePropertyFactory {

    /**
     * Create a new ShapeProperty that holds a colour object value
     *
     * @param name The name of the property
     * @param value The value of the colour that will be set for the shape property.
     *
     */
    public static ShapeProperty<Color> createColourProperty(String name, Color value) {
        return new ShapeProperty<>(name, value, any -> true);
    }

    /**
     * Create a new ShapeProperty that holds a Point object value
     *
     * @param name The name of the property
     * @param value The value of the Point that will be set for the shape property.
     *
     */
    public static ShapeProperty<Point> createPointProperty(String name, Point value) {
        return new ShapeProperty<>(name, value, p -> p.getX() >= -1000 && p.getY() >= -1000);
    }
}
