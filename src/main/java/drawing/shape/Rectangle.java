package drawing.shape;

import java.awt.Color;
import java.awt.Point;

public class Rectangle implements Shape {
    private int x;
    private int y;

    private final ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Rectangle(int x, int y, int x2, int y2) {
        var p1 = new Point(x, y);
        var p2 = new Point(x2, y2);

        if (p2.distance(0, 0) > p1.distance(0, 0)) {
            this.x = x;
            this.y = y;
        } else {
            this.x = x2;
            this.y = y2;
        }

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
    public Color getShapeStroke() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    @Override
    public void setShapeStroke(Color color) {

    }

    @Override
    public Color getShapeFill() {
        return (Color) this.properties.get("fillColour").getValue();
    }

    @Override
    public void setShapeFill(Color fill) {
        this.properties.set("fillColour", propertyFactory.createColourProperty("fillColour", fill));
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean isFillable() {
        return true;
    }
}
