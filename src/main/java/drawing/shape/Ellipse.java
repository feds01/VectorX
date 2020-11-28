package drawing.shape;

import common.CopyUtils;
import drawing.tool.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

/**
 * Ellipse class that is used to draw shapes that are of ellipse
 * type. This class extends the base class Shape to implement
 * the methods for drawing an ellipse.
 *
 * @author 200008575
 */
public class Ellipse extends Shape {

    /**
     * Ellipse constructor method
     */
    public Ellipse(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        // set the basic ellipse proper
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    /**
     * Method to copy the current shape and make a new instance of it
     *
     * @return A new Ellipse object that holds the same properties as this object.
     */
    public Ellipse copy() {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new Ellipse(this.getX(), this.getY(), this.getX() + width, this.getY() + height);

        // copy the properties over from this object
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     * This method returns the Ellipse ToolType for this method
     *
     * @return The tool type
     */
    @Override
    public ToolType getToolType() {
        return ToolType.ELLIPSE;
    }

    /**
     * This method is used to draw the boundary of the object when it
     * is being highlighted on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawOval(getX(), getY(), width, height);
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
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int thicknessValue = (Integer) this.getPropertyMap().get("thickness").getValue();
        g.setStroke(new BasicStroke(thicknessValue));


        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillOval(getX(), getY(), width, height);

        g.setColor(this.getShapeStrokeColour());

        // Apply a stroke thickness offset to prevent it from
        // being drawn outside of the boundary
        g.drawOval(getX() + thicknessValue / 2,
                getY() + thicknessValue / 2,
                width - thicknessValue,
                height - thicknessValue
        );
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
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // construct a new ellipse with the same value as this object which can
        // then use the contains method to check whether or not a point is present
        // within it's bounds.
        var ellipse = new Ellipse2D.Double(getX(), getY(), width, height);

        return ellipse.contains(point);
    }

    /**
     * Equality method for the the Ellipse shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipse ellipses = (Ellipse) o;
        return getX() == ellipses.getX() &&
                getY() == ellipses.getY() &&
                Objects.equals(properties, ellipses.properties);
    }

    /**
     * Hash method for the Ellipse shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties);
    }
}
