package drawing.shape;

import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import java.util.Objects;

public class Rectangle implements Shape {
    private final ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Rectangle(int x, int y, int x2, int y2) {
        int xMin = Math.min(x, x2);
        int yMin = Math.min(y, y2);

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        this.properties.addProperty(new ShapeProperty<>("start", new Point(xMin, yMin), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("end", new Point(width, height),  value -> value.getX() >= 0 && value.getY() >= 0));

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
        int x = (int) ((Point) this.getProperties().get("start").getValue()).getX();
        int y = (int) ((Point) this.getProperties().get("start").getValue()).getY();
        int width = (int) ((Point) this.getProperties().get("end").getValue()).getX();
        int height = (int) ((Point) this.getProperties().get("end").getValue()).getY();
        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, x, y, width, height);
    }


    @Override
    public void drawBoundary(Graphics2D g) {
        int x = (int) ((Point) this.getProperties().get("start").getValue()).getX();
        int y = (int) ((Point) this.getProperties().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);

        g.drawRect(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int x = (int) ((Point) this.getProperties().get("start").getValue()).getX();
        int y = (int) ((Point) this.getProperties().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int thicknessValue = (Integer) this.getProperties().get("thickness").getValue();
        g.setStroke(new BasicStroke(thicknessValue));

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
        int x = (int) ((Point) this.getProperties().get("start").getValue()).getX();
        int y = (int) ((Point) this.getProperties().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        return (
                point.getX() >= x && point.getX() <= x + width &&
                point.getY() >= y && point.getY() <= y + height
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return getX() == rectangle.getX() &&
                getY() == rectangle.getY() &&
                Objects.equals(properties, rectangle.properties) &&
                Objects.equals(propertyFactory, rectangle.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties, propertyFactory);
    }
}
