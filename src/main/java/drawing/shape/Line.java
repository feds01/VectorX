package drawing.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Line implements Shape {
    private int x;
    private int y;
    private int x2;
    private int y2;

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
    public void draw(Graphics2D g, boolean isResizing) {
        int thickness = (int) this.properties.get("thickness").getValue();

        g.setColor(this.getShapeStrokeColour());
        g.setStroke(new BasicStroke(thickness));
        g.drawLine(x, y, x2, y2);
    }

    @Override
    public boolean isFillable() {
        return false;
    }
}
