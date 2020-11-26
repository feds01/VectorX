package drawing.shape;

import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class TextShape implements Shape {
    /**
     *
     */
    private ShapeProperties properties = new ShapeProperties();

    /**
     *
     */
    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    /**
     *
     */
    public TextShape(int x, int y, int x2, int y2) {
        int xMin = Math.min(x, x2);
        int yMin = Math.min(y, y2);

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        this.properties.addProperty(new ShapeProperty<>("value", "text", value -> true));

        this.properties.addProperty(new ShapeProperty<>("start", new Point(xMin, yMin), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("end", new Point(width, height), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));

        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));

        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));

        this.properties.addProperty(new ShapeProperty<>("fontSize", 10, value -> 4 <= value && value <= 20));
    }

    @Override
    public ToolType getToolType() {
        return ToolType.TEXT;
    }

    @Override
    public int getX() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.x;
    }

    @Override
    public void setX(int x) {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());
        var newPoint = new Point(x, point.y);

        start.setValue(newPoint);
    }

    @Override
    public int getY() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.y;
    }

    @Override
    public void setY(int y) {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());
        var newPoint = new Point(point.x, y);

        start.setValue(newPoint);
    }

    @Override
    public Map<String, ShapeProperty<?>> getProperties() {
        return this.properties.getProperties();
    }

    @Override
    public void setProperty(String name, Object value) {
        // get the old property and insert the new value
        var oldProperty = this.properties.get(name);

        oldProperty.setValue(value);
    }

    @Override
    public void setProperties(ShapeProperties properties) {
        this.properties = properties;
    }

    @Override
    public Color getShapeStrokeColour() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    @Override
    public void setShapeStrokeColour(Color stroke) {
        this.properties.set("fillColour", propertyFactory.createColourProperty("strokeColour", stroke));
    }


    @Override
    public Color getShapeFillColour() {
        return (Color) this.properties.get("fillColour").getValue();
    }

    @Override
    public void setShapeFillColour(Color fill) {
        this.properties.set("fillColour", propertyFactory.createColourProperty("fillColour", fill));
    }


    @Override
    public void drawBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        g.setColor(Shape.SELECTOR_COLOUR);
        g.setStroke(new BasicStroke(2));

        g.drawRect(getX(), getY(), width, height);
    }

    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, getX(), getY(), width, height);
    }

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

    @Override
    public boolean isFillable() {
        return true;
    }

    // TODO: replace with simple rect box function
    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        return (
                point.getX() >= this.getX() && point.getX() <= this.getX() + width &&
                        point.getY() >= this.getY() && point.getY() <= this.getY() + height
        );
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties, propertyFactory);
    }
}
