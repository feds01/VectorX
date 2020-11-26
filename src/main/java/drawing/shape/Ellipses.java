package drawing.shape;

import drawing.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

public class Ellipses implements Shape {
    private int x;
    private int y;

    private ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();


    public Ellipses(int x, int y, int x2, int y2) {
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
        return ToolType.ELLIPSIS;
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
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawOval(x, y, width, height);
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
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillOval(x, y, width, height);

        g.setColor(this.getShapeStrokeColour());
        g.drawOval(x, y, width, height);
    }

    @Override
    public boolean isFillable() {
        return true;
    }

    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        var ellipse = new Ellipse2D.Double(x, y, width, height);

        return ellipse.contains(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipses ellipses = (Ellipses) o;
        return x == ellipses.x &&
                y == ellipses.y &&
                Objects.equals(properties, ellipses.properties) &&
                Objects.equals(propertyFactory, ellipses.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, properties, propertyFactory);
    }
}
