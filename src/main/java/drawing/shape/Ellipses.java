package drawing.shape;

import java.awt.Color;
import java.awt.Graphics;

public class Ellipses implements Shape {
    private int x;
    private int y;

    private ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();


    public Ellipses(int x, int y, int base, int height) {
        this.x = x;
        this.y = y;

        this.properties.addProperty(new ShapeProperty<>("base", base, value -> value > 0));

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


//    @Override
//    public void setProperty(String name, Object value) {
//        ShapeProperty<?> oldProperty = this.properties.get(name);
//
//        if (oldProperty == null) {
//            throw new IllegalStateException("Cannot set property that doesn't exist");
//        }
//
//
//        oldProperty.setValue(value);
//        this.properties.set(name, oldProperty);
//    }

    @Override
    public Color getShapeStroke() {
        return (Color) this.properties.get("strokeColour").getValue();
    }

    @Override
    public void setShapeStroke(Color stroke) {
        this.properties.set("fillColour", propertyFactory.createColourProperty("strokeColour", stroke));

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
    public void draw(Graphics g) {
    }

    @Override
    public boolean isFillable() {
        return true;
    }
}
