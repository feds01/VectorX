package drawing.shape;

import common.CopyUtils;
import drawing.tool.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Objects;

/**
 * Triangle class that is used to draw shapes that are of ellipse
 * type. This class extends the base class Shape to implement
 * the methods for drawing an ellipse.
 *
 * @author 200008575
 */
public class Triangle extends Shape {
    /**
     * An array that holds the y-points of the triangle that
     * will be drawn on the canvas
     */
    private int[] yPoints;

    /**
     * An array that holds the x-points of the triangle that
     * will be drawn on the canvas
     */
    private int[] xPoints;

    /**
     * Triangle constructor method
     */
    public Triangle(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.addProperty(ShapePropertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));

        this.setPoints(1);
    }

    /**
     * Method to copy the current shape and make a new instance of it
     *
     * @return A new Triangle object that holds the same properties as this object.
     */
    public Triangle copy() {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new Triangle(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     * A method to compute the vertices of the triangle based
     * on the width and height of the bounding box. The method
     * also takes in the thickness value of the stroke to account
     * for thickness offsetting the stroke outside of the bounding box.
     *
     * @param thickness - The thickness of the stroke for the current shape.
     */
    private void setPoints(int thickness) {
        int x = getX() + thickness / 2;
        int y = getY() + thickness / 2;

        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int x2 = getX() + width - thickness;
        int y2 = getY() + height - thickness;

        // build triangle points using our algorithm
        this.xPoints = new int[]{x, x + width / 2, x2};
        this.yPoints = new int[]{y2, y, y2};
    }

    /**
     * This method returns the Triangle ToolType for this method
     *
     * @return The tool type
     */
    @Override
    public ToolType getToolType() {
        return ToolType.TRIANGLE;
    }

    /**
     * This method is used to draw the boundary of the object when it
     * is being highlighted on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        g.setColor(Shape.SELECTOR_COLOUR);
        g.setStroke(new BasicStroke(2));

        g.drawPolygon(xPoints, yPoints, 3);
    }

    /**
     * This method is used to draw the selection boundary of the object when it
     * is selected on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, getX(), getY(), width, height);
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
    @Override
    public void draw(Graphics2D g, boolean isResizing) {

        // if the points changed, we'll need to recompute them..
        this.setPoints(1);

        int thicknessValue = (Integer) this.getPropertyMap().get("thickness").getValue();
        g.setStroke(new BasicStroke(thicknessValue));

        g.setColor(this.getShapeFillColour());
        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(this.getShapeStrokeColour());

        this.setPoints(thicknessValue);

        g.drawPolygon(xPoints, yPoints, 3);

    }

    /**
     * Returns whether or not this object can be filled.
     *
     * @return a boolean whether the Fill tool can be used on this
     * object.
     */
    @Override
    public boolean isFillable() {
        return true;
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

        // create a new polygon class with xPoints and yPoints
        var triangle = new Polygon(xPoints, yPoints, 3);

        return triangle.contains(point);
    }

    /**
     * Equality method for the the Triangle shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return getX() == triangle.getX() &&
                getY() == triangle.getY() &&
                Arrays.equals(yPoints, triangle.yPoints) &&
                Arrays.equals(xPoints, triangle.xPoints) &&
                Objects.equals(properties, triangle.properties);
    }

    /**
     * Hash method for the Triangle shape object.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getX(), getY(), properties);
        result = 31 * result + Arrays.hashCode(yPoints);
        result = 31 * result + Arrays.hashCode(xPoints);
        return result;
    }
}
