package drawing.shape;

import drawing.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Objects;

public class Triangle implements Shape {
    private int[] yPoints;
    private int[] xPoints;
    private int x;
    private int y;

    private final ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Triangle(int x, int y, int x2, int y2) {
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

        this.setPoints();
    }

    @Override
    public ToolType getToolType() {
        return ToolType.TRIANGLE;
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

    private void setPoints() {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        int x2 = this.x + width;
        int y2 = this.y + height;

        // build triangle points using our algorithm
        this.xPoints = new int[]{x, x + width / 2, x2};
        this.yPoints = new int[]{y2, y, y2};
    }

    @Override
    public void drawBoundary(Graphics2D g) {
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(this.getShapeStrokeColour());
        g.drawPolygon(xPoints, yPoints, 3);

    }

    @Override
    public boolean isFillable() {
        return true;
    }

    @Override
    public boolean isPointWithinBounds(Point point) {

        // create a new polygon class with xPoints and yPoints
        var triangle = new Polygon(xPoints, yPoints, 3);

        return triangle.contains(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return x == triangle.x &&
                y == triangle.y &&
                Arrays.equals(yPoints, triangle.yPoints) &&
                Arrays.equals(xPoints, triangle.xPoints) &&
                Objects.equals(properties, triangle.properties) &&
                Objects.equals(propertyFactory, triangle.propertyFactory);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(x, y, properties, propertyFactory);
        result = 31 * result + Arrays.hashCode(yPoints);
        result = 31 * result + Arrays.hashCode(xPoints);
        return result;
    }
}
