package drawing.shape;

import drawing.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Objects;

public class Rectangle implements Shape {
    private int x;
    private int y;

    private final ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Rectangle(int x, int y, int x2, int y2) {

        this.x = Math.min(x, x2);
        this.y = Math.min(y, y2);

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        this.properties.addProperty(new ShapeProperty<>("width", width, value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("height", height, value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));


        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));

        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));

        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    @Override
    public ToolType getToolType() {
        return ToolType.RECTANGLE;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public ShapeProperties getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(ShapeProperties properties) {

    }

    @Override
    public Color getShapeStrokeColour() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    @Override
    public void setShapeStrokeColour(Color color) {

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
    public void drawSelectedBoundary(Graphics2D g) {

    }


    @Override
    public void drawBoundary(Graphics2D g) {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillRect(x, y, width, height);

        g.setColor(this.getShapeStrokeColour());
        g.drawRect(x, y, width, height);
    }

    @Override
    public boolean isFillable() {
        return true;
    }

    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        return (
                point.getX() >= this.x && point.getX() <= this.x + width &&
                point.getY() >= this.y && point.getY() <= this.y + height
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return x == rectangle.x &&
                y == rectangle.y &&
                Objects.equals(properties, rectangle.properties) &&
                Objects.equals(propertyFactory, rectangle.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, properties, propertyFactory);
    }
}
