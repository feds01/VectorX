package drawing.shape;

import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Map;
import java.util.Objects;

public class Line implements Shape {
    private ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Line(int x, int y, int x2, int y2) {
        this.properties.addProperty(new ShapeProperty<>("start", new Point(x, y), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("end", new Point(x2, y2), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));


        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));

        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));

        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    @Override
    public ToolType getToolType() {
        return ToolType.LINE;
    }

    @Override
    public int getX() {
        var start = this.properties.get("start");

        var point = (Point) (start.getValue());

        return point.x;
    }

    public int getEndX() {
        var start = this.properties.get("end");

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

    public int getEndY() {
        var start = this.properties.get("end");

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
    public void drawBoundary(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);


        g.draw(new Line2D.Double(getX(), getY(), getEndX(), getEndY()));
    }

    @Override
    public void drawSelectedBoundary(Graphics2D g) {

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        this.drawBoundary(g);

        // draw modifying circle at the start
        ShapeUtility.drawSelectorPoint(g, getX(), getY());

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, getEndX(), getEndY());

    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int thickness = (int) this.properties.get("thickness").getValue();
        g.setStroke(new BasicStroke(thickness));

        g.setColor(this.getShapeStrokeColour());
        g.draw(new Line2D.Double(getX(), getY(), getEndX(), getEndY()));
    }

    @Override
    public boolean isFillable() {
        return false;
    }

    @Override
    public boolean isPointWithinBounds(Point point) {
        double distance = Line2D.ptSegDist(getX(), getY(), getEndX(), getEndY(), point.getX(), point.getY());

        return distance < 4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.getX(), getX()) == 0 &&
                Double.compare(line.getY(), getY()) == 0 &&
                Double.compare(line.getEndX(), getEndX()) == 0 &&
                Double.compare(line.getEndY(), getEndY()) == 0 &&
                Objects.equals(properties, line.properties) &&
                Objects.equals(propertyFactory, line.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getEndX(), getEndY(), properties, propertyFactory);
    }
}
