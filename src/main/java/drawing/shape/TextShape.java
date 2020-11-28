package drawing.shape;

import common.CopyUtils;
import drawing.tool.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * TextShape class that is used to draw shapes that are of ellipse
 * type. This class extends the base class Shape to implement
 * the methods for drawing an ellipse.
 *
 * @author 200008575
 */
public class TextShape extends Shape {
    /**
     * TextShape constructor method
     */
    public TextShape(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        // set the text value for the object
        this.properties.addProperty(new ShapeProperty<>("value", "", value -> true));

        this.properties.addProperty(ShapePropertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("fontSize", 14, value -> 8 <= value && value <= 28));
    }

    /**
     * Method to copy the current shape and make a new instance of it
     *
     * @return A new TextShape object that holds the same properties as this object.
     */
    public TextShape copy() {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new TextShape(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     * This method returns the Text ToolType for this method
     *
     * @return The tool type
     */
    @Override
    public ToolType getToolType() {
        return ToolType.TEXT;
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

        g.setColor(Shape.SELECTOR_COLOUR);
        g.setStroke(new BasicStroke(2));

        g.drawRect(getX(), getY(), width, height);
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

        String value = (String) this.properties.get("value").getValue();

        int fontSize = (int) this.properties.get("fontSize").getValue();
        g.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));

        g.setStroke(new BasicStroke(2));

        // draw the font background if any...
        g.setColor(this.getShapeFillColour());
        g.fillRect(getX(), getY(), width, height);

        if (isResizing) {
            g.setColor(Shape.SELECTOR_COLOUR);
            g.drawRect(getX(), getY(), width, height);
        } else {
            // TODO: dynamically update box size based on font metrics
            var fm = g.getFontMetrics();

            //
            //        int width = fm.stringWidth(value);
            //        int height = fm.getHeight();

            // draw the string
            g.setColor(this.getShapeStrokeColour());
            g.drawString(value, getX(), getY() + fm.getHeight());
        }
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

        var shape = new Rectangle2D.Double(getX(), getY(), getX() + width, getY() + height);

        return shape.contains(point);
    }

    /**
     * Equality method for the the TextShape shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextShape textShape = (TextShape) o;
        return getX() == textShape.getX() &&
                getY() == textShape.getY() &&
                Objects.equals(properties, textShape.properties);
    }

    /**
     * Hash method for the TextShape shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties);
    }
}
