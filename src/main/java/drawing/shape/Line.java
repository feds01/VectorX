package drawing.shape;

import java.awt.Color;
import java.awt.Graphics;

public class Line implements Shape {
    private int x;
    private int y;

    private ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public Line(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

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
        this.properties = properties;
    }

    @Override
    public Color getShapeStroke() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    @Override
    public void setShapeStroke(Color stroke) {
        this.properties.set("fillColour", propertyFactory.createColourProperty("strokeColour", stroke));
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public boolean isFillable() {
        return false;
    }
}
