package drawing.shape;

import javax.swing.GrayFilter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

public class ImageShape implements Shape {
    private int x;
    private int y;

    private final BufferedImage image;

    private final ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public ImageShape(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;

        this.properties.addProperty(new ShapeProperty<>("width", image.getWidth(), value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("height", image.getHeight(), value -> value > 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));

        this.properties.addProperty(new ShapeProperty<>("grayScale", true, value -> true));


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
        var image = this.image;

        int xPos = x - image.getWidth() / 2;
        int yPos = y - image.getHeight() / 2;

        // It the user specifies the image to be drawn with a gray scale filter,
        // then do so...
        boolean grayscale = (boolean) this.properties.get("grayScale").getValue();

        if (grayscale) {
            // fast way to apply a grayscale filter and improve app performance...
            // https://stackoverflow.com/a/9131751/9955666

            // @Improvement: make p a slider value...
            ImageFilter filter = new GrayFilter(true, 50);
            ImageProducer producer = new FilteredImageSource(image.getSource(), filter);

            g.drawImage(Toolkit.getDefaultToolkit().createImage(producer), xPos, yPos, null);
        } else {
            g.drawImage(image, xPos, yPos, null);
        }
    }

    @Override
    public boolean isFillable() {
        return false;
    }
}
