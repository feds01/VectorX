package drawing.shape;

import drawing.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.Map;

/**
 *
 */
public abstract class Shape implements Serializable {

    /**
     *
     */
    protected static final Color SELECTOR_COLOUR = new Color(0x3399FF);


    /**
     *
     */
    public ShapeProperties properties = new ShapeProperties();


    /**
     *
     */
    public ShapePropertyFactory propertyFactory = new ShapePropertyFactory();


    /**
     *
     */
    public Shape(int x, int y, int x2, int y2) {
        int xMin = Math.min(x, x2);
        int yMin = Math.min(y, y2);

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        this.properties.addProperty(propertyFactory.createPointProperty("start", new Point(xMin, yMin)));
        this.properties.addProperty(propertyFactory.createPointProperty("end", new Point(width, height)));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));
    }

    /**
     *
     */
    public Shape copy() {
        return null;
    }

    /**
     *
     */
    public ToolType getToolType() {
        return null;
    }

    /**
     *
     */
    public int getX() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.x;
    }


    /**
     *
     */
    public void setX(int x) {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());
        var newPoint = new Point(x, point.y);

        start.setValue(newPoint);
    }

    /**
     *
     */
    public int getY() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.y;
    }

    /**
     *
     */
    public void setY(int y) {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());
        var newPoint = new Point(point.x, y);

        start.setValue(newPoint);
    }

    /**
     *
     */
    public Map<String, ShapeProperty<?>> getPropertyMap() {
        return this.properties.getProperties();
    }

    /**
     *
     */
    public ShapeProperties getProperties() {
        return this.properties;
    }

    /**
     *
     */
    void setProperties(ShapeProperties properties) {
        this.properties = properties;
    }

    /**
     *
     */
    public Color getShapeStrokeColour() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    /**
     *
     */
    public void setShapeStrokeColour(Color stroke) {
        this.properties.set("strokeColour", propertyFactory.createColourProperty("strokeColour", stroke));

    }

    /**
     *
     */
    public Color getShapeFillColour() {
        return (Color) this.properties.get("fillColour").getValue();
    }

    /**
     *
     */
    public void setShapeFillColour(Color fill) {
        this.properties.set("fillColour", propertyFactory.createColourProperty("fillColour", fill));
    }

    /**
     *
     */
    public void drawBoundary(Graphics2D g) {
    }

    /**
     *
     */
    public void drawSelectedBoundary(Graphics2D g) {
    }

    /**
     *
     */
    public void draw(Graphics2D g, boolean isResizing) {
    }

    /**
     *
     */
    boolean isFillable() {
        return false;
    }

    /**
     *
     */
    public boolean isPointWithinBounds(Point point) {
        return false;
    }

    /**
     *
     */
    public void setProperty(String name, Object value) {
        // get the old property and insert the new value

        var oldProperty = this.properties.get(name);
        oldProperty.setValue(value);
    }

    public Object getProperty(String name) {
        return this.properties.get(name).getValue();
    }
}
