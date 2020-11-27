package drawing.shape;

import common.CopyUtils;
import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 *
 */
public class TextShape extends Shape {
    /**
     *
     */
    public TextShape(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.addProperty(new ShapeProperty<>("value", "text", value -> true));

        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("fontSize", 14, value -> 8 <= value && value <= 28));
    }

    /**
     *
     */
    public TextShape copy() {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new TextShape(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     *
     */
    @Override
    public ToolType getToolType() {
        return ToolType.TEXT;
    }

    /**
     *
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
     *
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
     *
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
     *
     */
    @Override
    public boolean isFillable() {
        return true;
    }

    /**
     *
     */
    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        var shape = new Rectangle2D.Double(getX(), getY(), getX() + width, getY() + height);

        return shape.contains(point);
    }

    /**
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextShape textShape = (TextShape) o;
        return getX() == textShape.getX() &&
                getY() == textShape.getY() &&
                Objects.equals(properties, textShape.properties) &&
                Objects.equals(propertyFactory, textShape.propertyFactory);
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties, propertyFactory);
    }
}
