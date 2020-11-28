package drawing.shape;

import drawing.ResizeEvent;
import drawing.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
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

    public int getEndX() {
        var end = this.properties.get("end");

        var point = (Point) (end.getValue());

        return point.x;
    }

    public void setEndX(int x) {
        var end = this.properties.get("end");

        var point = (Point) (end.getValue());
        var newPoint = new Point(x, point.y);

        end.setValue(newPoint);
    }


    public void setEndY(int y) {
        var end = this.properties.get("end");

        var point = (Point) (end.getValue());
        var newPoint = new Point(point.x, y);

        end.setValue(newPoint);
    }


    /**
     *
     */
    public int getEndY() {
        var end = this.properties.get("end");

        var point = (Point) (end.getValue());

        return point.y;
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
    public int getResizePoint(Point p) {
        int x = getX();
        int y = getY();

        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();


        // The array will be created with points in a clockwise order.
        Point[] resizingPoints = ShapeUtility.createResizePoints(x, y, width, height);

        // loop over points and find index if any
        for (var index = 0; index < resizingPoints.length; index++) {
            var ellipse = new Ellipse2D.Double(
                    resizingPoints[index].x - 6,
                    resizingPoints[index].y - 6,
                    12, 12);

            if (ellipse.contains(p)) {
                return index;
            }
        }

        return -1;
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

    public void resizeShape(int onResize, int dx, int dy) {
        var start = ((Point) this.properties.get("start").getValue());
        var lengths = ((Point) this.properties.get("end").getValue());
        var end = new Point(start.x + lengths.x, start.y + lengths.y);

        switch (onResize) {
            case ResizeEvent.NORTH: {
                start.y += dy;
                break;
            }

            // Get the NORTH_EASTERN and SOUTHERN_WESTERN points
            case ResizeEvent.NORTH_EAST: {
                end.x += dx;
                start.y += dy;
                break;
            }

            case ResizeEvent.EAST: {
                end.x += dx;
                break;
            }
            case ResizeEvent.SOUTH_EAST: {
                // add dy to y2
                // add dx to x2
                end.x += dx;
                end.y += dy;
                break;
            }
            case ResizeEvent.SOUTH: {
                end.y += dy;
                break;
            }
            case ResizeEvent.SOUTH_WEST: {
                start.x += dx;
                end.y += dy;
                break;
            }
            case ResizeEvent.WEST: {
                start.x += dx;
                break;
            }
            case ResizeEvent.NORTH_WEST: {
                // add dy to y1
                // add dx to x1
                start.x += dx;
                start.y += dy;
                break;
            }
        }

        int xMin = Math.min(start.x, end.x);
        int yMin = Math.min(start.y, end.y);

        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);

        this.setProperty("start", new Point(xMin, yMin));
        this.setProperty("end", new Point(width, height));

    }
}
