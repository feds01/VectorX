package drawing.shape;

import common.CopyUtils;
import drawing.ResizeEvent;
import drawing.tool.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Objects;

/**
 * Line class that is used to draw shapes that are of ellipse
 * type. This class extends the base class Shape to implement
 * the methods for drawing an ellipse.
 *
 * @author 200008575
 * */
public class Line extends Shape {
    /**
     * Line constructor method
     */
    public Line(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.set("start", ShapePropertyFactory.createPointProperty("start", new Point(x, y)));
        this.properties.set("end", ShapePropertyFactory.createPointProperty("end", new Point(x2, y2)));


        this.properties.addProperty(ShapePropertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    /**
     * Method to copy the current shape and make a new instance of it
     *
     * @return A new Line object that holds the same properties as this object.
     */
    public Line copy() {
        var clazz = new Line(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }


    /**
     * This method returns the Line ToolType for this method
     *
     * @return The tool type
     */
    @Override
    public ToolType getToolType() {
        return ToolType.LINE;
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
    @Override
    public int getResizeEventAt(Point p) {
        // we only need to check if the point is on the ellipse at
        // each end of the line.

        var ellipseN = new Ellipse2D.Double(
                getX() - 4,
                getY() - 4,
                8, 8);

        // check if the point is on the northern resize point
        if (ellipseN.contains(p)) {
            return ResizeEvent.NORTH;
        }

        var ellipseS = new Ellipse2D.Double(
                getWidth() - 4,
                getHeight() - 4,
                8, 8);

        // check if the point is on the southern resize point
        if (ellipseS.contains(p)) {
            return ResizeEvent.SOUTH;
        }


        // not within range of the point
        return -1;
    }


    /**
     * This method is used to draw the boundary of the object when it
     * is being highlighted on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);


        g.draw(new Line2D.Double(getX(), getY(), getWidth(), getHeight()));
    }

    /**
     * This method is used to draw the selection boundary of the object when it
     * is selected on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        this.drawBoundary(g);

        // draw modifying circle at the start
        ShapeUtility.drawSelectorPoint(g, getX(), getY());

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, getWidth(), getHeight());

    }

    /**
     * This method is used to draw the object when it is present on
     * the canvas.
     *
     * @param g The canvas graphical context.
     * @param isResizing a boolean representing if the shape is currently being
     *                   resized. This value can be used to display a special
     *                   style when it's being resized.
     */
    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int thickness = (int) this.properties.get("thickness").getValue();
        g.setStroke(new BasicStroke(thickness));

        g.setColor(this.getShapeStrokeColour());
        g.draw(new Line2D.Double(getX(), getY(), getWidth(), getHeight()));
    }

    /**
     * Returns whether or not this object can be filled.
     *
     * @return a boolean whether the Fill tool can be used on this
     * object.
     */
    @Override
    public boolean isFillable() {
        return false;
    }

    /**
     * Check whether or not a certain point is within the hover-able
     * boundary of the shape.
     *
     * @param point - The point to be checked whether it is within the bounds
     * @return Whether or not the given point is within the bounds
     */
    @Override
    public boolean isPointWithinBounds(Point point) {
        double distance = Line2D.ptSegDist(getX(), getY(), getWidth(), getHeight(), point.getX(), point.getY());

        // To make the line select-ability more usable check that the pointer it at least
        // four pixels near the line/
        return distance < 4;
    }

    /**
     * Equality method for the the Line shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.getX(), getX()) == 0 &&
                Double.compare(line.getY(), getY()) == 0 &&
                Double.compare(line.getWidth(), getHeight()) == 0 &&
                Double.compare(line.getWidth(), getHeight()) == 0 &&
                Objects.equals(properties, line.properties);
    }

    /**
     * Hash method for the Line shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getWidth(), getHeight(), properties);
    }
}
