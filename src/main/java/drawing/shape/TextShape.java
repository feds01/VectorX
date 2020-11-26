package drawing.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 */
public class TextShape implements Shape {
    private int x;
    private int y;

    /**
     *
     */
    private final ShapeProperties properties = new ShapeProperties();

    /**
     *
     */
    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    /**
     *
     */
    public TextShape(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;

        this.x = Math.min(x, x2);
        this.y = Math.min(y, y2);

        int width = Math.abs(x - x2);
        int height = Math.abs(y - y2);

        this.properties.addProperty(new ShapeProperty<>("value", "text", value -> true));

        this.properties.addProperty(new ShapeProperty<>("width", width, value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("height", height, value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));


        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));

        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));

        this.properties.addProperty(new ShapeProperty<>("fontSize", 10, value -> 4 <= value && value <= 20));
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

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int width = (int) this.properties.get("width").getValue();
        int height = (int) this.properties.get("height").getValue();

        String value = (String) this.properties.get("value").getValue();

        int fontSize = (int) this.properties.get("fontSize").getValue();
        g.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));

        // draw the font background if any...
        g.setColor(this.getShapeFillColour());
        g.fillRect(x, y, width, height);

        if (isResizing) {
            g.setColor(new Color(0x3399FF));
            g.drawRect(x, y, width, height);
        } else {
            // TODO: dynamically update box size based on font metrics
            var fm = g.getFontMetrics();

            //
            //        int width = fm.stringWidth(value);
            //        int height = fm.getHeight();

            // draw the string
            g.setColor(this.getShapeStrokeColour());
            g.drawString(value, x, y + fm.getHeight());
        }
    }

    @Override
    public boolean isFillable() {
        return true;
    }
}
