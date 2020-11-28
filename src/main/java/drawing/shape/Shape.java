package drawing.shape;

import drawing.ResizeEvent;
import drawing.tool.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Map;

/**
 * Base class that is used to implement shapes that can be
 * drawn on the canvas.
 *
 * @author 200008575
 * */
public abstract class Shape implements Serializable {

    /**
     * Default colour that is used to represent if shapes are selected or
     * hovered on the canvas object.
     */
    protected static final Color SELECTOR_COLOUR = new Color(0x3399FF);


    /**
     * A ShapeProperty object that is used for the property object
     */
    public ShapeProperties properties = new ShapeProperties();


    /**
     * Base Shape constructor method
     */
    public Shape(int x, int y, int x2, int y2) {

        // Get the minimum x and y coordinates from the coordinate pair
        // to accurately draw the object on the canvas.
        int xMin = Math.min(x, x2);
        int yMin = Math.min(y, y2);

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        this.properties.addProperty(ShapePropertyFactory.createPointProperty("start", new Point(xMin, yMin)));
        this.properties.addProperty(ShapePropertyFactory.createPointProperty("end", new Point(width, height)));
        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));
    }

    /**
     * Method to copy the current shape and make a new instance of it
     *
     * @return null since this method must be implemented by classes that
     * extend the shape
     */
    public Shape copy() {
        return null;
    }

    /**
     * This method returns the null ToolType for this object since
     * classes must implement this method.
     *
     * @return The tool type
     */
    public ToolType getToolType() {
        return null;
    }

    /**
     * Get the starting x point of this shape.
     *
     * @return the starting x coordinate
     */
    public int getX() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.x;
    }


    /**
     * Set the starting x point of this shape.
     *
     * @param x the new starting x coordinate
     */
    public void setX(int x) {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());
        var newPoint = new Point(x, point.y);

        start.setValue(newPoint);
    }

    /**
     * Returns the width of the current object.
     *
     * @return the width of the current object on the canvas.
     */
    public int getWidth() {
        return (int) ((Point) this.properties.get("end").getValue()).getX();
    }

    /**
     * Returns the height of the current object.
     *
     * @return the height of the current object on the canvas.
     */
    public int getHeight() {
        return (int) ((Point) this.properties.get("end").getValue()).getY();
    }


    /**
     * Returns the width of the current object.
     *
     * @param x Set the height of the object
     */
    public void setWidth(int x) {
        var end = this.properties.get("end");

        var point = (Point) (end.getValue());
        var newPoint = new Point(x, point.y);

        end.setValue(newPoint);
    }


    /**
     * Returns the height of the current object.
     *
     * @param y Set the height of the object
     */
    public void setHeight(int y) {
        var end = this.properties.get("end");

        var point = (Point) (end.getValue());
        var newPoint = new Point(point.x, y);

        end.setValue(newPoint);
    }

    /**
     * Get the starting y point of this shape.
     *
     * @return the starting y coordinate
     */
    public int getY() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.y;
    }

    /**
     * Set the starting y point of this shape.
     *
     * @param y the new starting y coordinate
     */
    public void setY(int y) {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());
        var newPoint = new Point(point.x, y);

        start.setValue(newPoint);
    }

    /**
     * Method to retrieve a map that will map a name of a property to the
     * shape property object.
     *
     * @return A map of name properties to ShapeProperty objects
     */
    public Map<String, ShapeProperty<?>> getPropertyMap() {
        return this.properties.getProperties();
    }

    /**
     * Method to get the ShapeProperties object for the current shape.
     *
     * @return the properties object for the current shape.
     */
    public ShapeProperties getProperties() {
        return this.properties;
    }

    /**
     * Method to get the ShapeProperties object for the current shape.
     *
     * @param properties the new properties object for the current shape.
     */
    void setProperties(ShapeProperties properties) {
        this.properties = properties;
    }

    /**
     * Method to get the Color object of the stroke for the current shape.
     *
     * @return the current colour of the stroke for the current shape.
     */
    public Color getShapeStrokeColour() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    /**
     * Method to set the Color object of the stroke for the current shape.
     *
     * @param stroke the new colour of the stroke for the current shape.
     */
    public void setShapeStrokeColour(Color stroke) {
        this.properties.set("strokeColour", ShapePropertyFactory.createColourProperty("strokeColour", stroke));

    }

    /**
     * Method to get the Color object of the fill for the current shape.
     *
     * @return the current colour of the fill for the current shape.
     */
    public Color getShapeFillColour() {
        return (Color) this.properties.get("fillColour").getValue();
    }

    /**
     * Method to set the Color object of the fill for the current shape
     *
     * @param fill the new colour of the fill for the current shape.
     */
    public void setShapeFillColour(Color fill) {
        this.properties.set("fillColour", ShapePropertyFactory.createColourProperty("fillColour", fill));
    }

    /**
     * This method is used to draw the boundary of the object when it
     * is being highlighted on the canvas.
     *
     * @param g The canvas graphical context.
     */
    public void drawBoundary(Graphics2D g) {
    }

    /**
     * This method is used to draw the selection boundary of the object when it
     * is selected on the canvas.
     *
     * @param g The canvas graphical context.
     */
    public void drawSelectedBoundary(Graphics2D g) {
    }

    /**
     * This method is used to draw the object when it is present on
     * the canvas.
     *
     * @param g          The canvas graphical context.
     * @param isResizing a boolean representing if the shape is currently being
     *                   resized. This value can be used to display a special
     *                   style when it's being resized.
     */
    public void draw(Graphics2D g, boolean isResizing) {
    }

    /**
     * Method to get the locations of the resize points on the
     * shape. The line method will override the default shape
     * method so that the object can be resized.
     *
     * @param p The point to get the resize event at.
     *
     * @return the resize event type from the current point. If the current
     *         point is not present on any resize point, the method will return
     *         negative one.
     */
    public int getResizeEventAt(Point p) {
        int x = getX();
        int y = getY();

        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // The array will be created with points in a clockwise order.
        Point[] resizingPoints = ShapeUtility.createResizePoints(x, y, width, height);

        // loop over points and find point that is being hovered
        for (var index = 0; index < resizingPoints.length; index++) {
            var ellipse = new Ellipse2D.Double(
                    resizingPoints[index].x - 8,
                    resizingPoints[index].y - 8,
                    16, 16);

            if (ellipse.contains(p)) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Returns whether or not this object can be filled.
     *
     * @return a boolean whether the Fill tool can be used on this
     * object.
     */
    boolean isFillable() {
        return false;
    }

    /**
     * Check whether or not a certain point is within the hover-able
     * boundary of the shape.
     *
     * @param point - The point to be checked whether it is within the bounds
     * @return Whether or not the given point is within the bounds
     */
    public boolean isPointWithinBounds(Point point) {
        return false;
    }

    /**
     * Method to set a property of the current shape with a new value
     *
     * @param name The name of the property that will be set with the new value
     * @param value - new value of the property
     */
    public void setProperty(String name, Object value) {
        // get the old property and insert the new value
        var oldProperty = this.properties.get(name);

        oldProperty.setValue(value);
    }

    /**
     * Method to get a property of the current shape
     *
     * @param name The name of the property that will be retrieved.
     * @return The value stored by the shape property
     */
    public Object getProperty(String name) {
        return this.properties.get(name).getValue();
    }


    /**
     * Method to resize the shape based on the 'onResize' event and the change
     * in x and y coordinates between the drag events. Depending on the resize
     * event, the start or end point will be modified by the change in x and y.
     *
     * @param onResize A resize event type, integer ranging between 0 to 7.
     * @param dx The change in x for dragging the shape
     * @param dy The change in y for dragging the shape
     * */
    public void resizeShape(int onResize, int dx, int dy) {
        var start = ((Point) this.properties.get("start").getValue());
        var lengths = ((Point) this.properties.get("end").getValue());
        var end = new Point(start.x + lengths.x, start.y + lengths.y);

        // depending on which point the resize event was instantiated, apply
        // the dx and dy components to the starting and ending coordinates of
        // the shapes bounding box
        switch (onResize) {
            case ResizeEvent.NORTH: {
                start.y += dy;
                break;
            }
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
                start.x += dx;
                start.y += dy;
                break;
            }
        }

        if (start.x != end.x || start.y != end.y) {
            // determine which coordinates should be used for the 'resized' shape
            int xMin = Math.min(start.x, end.x);
            int yMin = Math.min(start.y, end.y);

            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);

            // overwrite the new components with the calculated coordinates
            this.setProperty("start", new Point(xMin, yMin));
            this.setProperty("end", new Point(width, height));
        }
    }
}
