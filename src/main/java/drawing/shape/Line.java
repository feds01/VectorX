package drawing.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Objects;

public class Line implements Shape {
    private double x;
    private double y;
    private double x2;
    private double y2;

    private ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Line(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        // TODO: when width or height increases, transform point 2, and not point 1
        this.properties.addProperty(new ShapeProperty<>("x2", width, value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("y2", height, value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));


        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));

        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));

        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }


    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return (int) y;
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
    public void drawBoundary(Graphics2D g) {
        g.setColor(Shape.SELECTOR_COLOUR);


        g.draw(new Line2D.Double(x, y, x2, y2));
    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int thickness = (int) this.properties.get("thickness").getValue();

        g.setColor(this.getShapeStrokeColour());
        g.setStroke(new BasicStroke(thickness));
        g.draw(new Line2D.Double(x, y, x2, y2));
    }

    @Override
    public boolean isFillable() {
        return false;
    }

    @Override
    public boolean isPointWithinBounds(Point point) {
        double m = (y2 - y) / (x2 - x);  // find line gradient
        double c = y - m * x; // find offset for c

        return point.getY() == point.getX() * m + c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.x, x) == 0 &&
                Double.compare(line.y, y) == 0 &&
                Double.compare(line.x2, x2) == 0 &&
                Double.compare(line.y2, y2) == 0 &&
                Objects.equals(properties, line.properties) &&
                Objects.equals(propertyFactory, line.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, x2, y2, properties, propertyFactory);
    }
}
