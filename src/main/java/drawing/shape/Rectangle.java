package drawing.shape;

import common.CopyUtils;
import drawing.tool.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Objects;

/**
 * Rectangle class that is used to draw shapes that are of ellipse
 * type. This class extends the base class Shape to implement
 * the methods for drawing an ellipse.
 *
 * @author 200008575
 * */
public class Rectangle extends Shape {
    /**
     * Rectangle constructor method
     */
    public Rectangle(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.addProperty(ShapePropertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    /**
     * Method to copy the current shape and make a new instance of it
     *
     * @return A new Rectangle object that holds the same properties as this object.
     */
    public Rectangle copy() {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new Rectangle(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     * This method returns the Rectangle ToolType for this method
     *
     * @return The tool type
     */
    @Override
    public ToolType getToolType() {
        return ToolType.RECTANGLE;
    }


    /**
     * This method is used to draw the boundary of the object when it
     * is being highlighted on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();


        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, x, y, width, height);
    }


    /**
     * This method is used to draw the selection boundary of the object when it
     * is selected on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // @Improvement: add rotation
        // double rotation = Math.toRadians((int) this.properties.get("rotation").getValue());
        // g.rotate(rotation);

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);

        g.drawRect(x, y, width, height);
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
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // @Improvement: add rotation
        // double rotation = Math.toRadians((int) this.properties.get("rotation").getValue());
        // g.rotate(rotation);

        int thicknessValue = (Integer) this.getPropertyMap().get("thickness").getValue();
        g.setStroke(new BasicStroke(thicknessValue));

        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillRect(x, y, width, height);


        g.setColor(this.getShapeStrokeColour());

        // Apply the thickness offset for stroke so it doesn't creep
        // out of bounds.
        g.drawRect(getX() + thicknessValue / 2,
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
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        return (
                point.getX() >= x && point.getX() <= x + width &&
                        point.getY() >= y && point.getY() <= y + height
        );
    }

    /**
     * Equality method for the the Rectangle shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return getX() == rectangle.getX() &&
                getY() == rectangle.getY() &&
                Objects.equals(properties, rectangle.properties);
    }

    /**
     * Hash method for the Rectangle shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties);
    }
}
