package drawing.shape;

import drawing.ToolType;

import javax.swing.GrayFilter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.util.Map;
import java.util.Objects;

public class ImageShape implements Shape {
    private final BufferedImage image;

    private final ShapeProperties properties = new ShapeProperties();

    private final ShapePropertyFactory propertyFactory = new ShapePropertyFactory();

    public ImageShape(int x, int y, BufferedImage image) {
        this.image = image;

        this.properties.addProperty(new ShapeProperty<>("start", new Point(x, y), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("end", new Point(image.getWidth(), image.getHeight()), value -> value.getX() >= 0 && value.getY() >= 0));

        this.properties.addProperty(new ShapeProperty<>("rotation", 0, value -> value >= 0 && value <= 360));

        this.properties.addProperty(new ShapeProperty<>("grayScale", false, value -> true));

        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));

        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));
    }

    @Override
    public ToolType getToolType() {
        return ToolType.IMAGE;
    }

    @Override
    public int getX() {
        var start = this.properties.get("start");

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
    public void setProperties(ShapeProperties properties) {

    }

    @Override
    public void setProperty(String name, Object value) {
        // get the old property and insert the new value

        var oldProperty = this.properties.get(name);
        oldProperty.setValue(value);
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
        int xPos = getX() - image.getWidth() / 2;
        int yPos = getY() - image.getHeight() / 2;

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawRect(xPos, yPos, image.getWidth(), image.getHeight());
    }

    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int xPos = getX() - image.getWidth() / 2;
        int yPos = getY() - image.getHeight() / 2;

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, xPos, yPos, width, height);
    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        var image = this.image;

        g.setStroke(new BasicStroke(2));

        int xPos = getX() - image.getWidth() / 2;
        int yPos = getY() - image.getHeight() / 2;

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

    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int xPos = getX() - image.getWidth() / 2;
        int yPos = getY() - image.getHeight() / 2;

        return (
                point.getX() >= xPos && point.getX() <= xPos + width &&
                        point.getY() >= yPos && point.getY() <= yPos + height
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageShape that = (ImageShape) o;
        return getX() == that.getX() &&
                getY() == that.getY() &&
                Objects.equals(image, that.image) &&
                Objects.equals(properties, that.properties) &&
                Objects.equals(propertyFactory, that.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), image, properties, propertyFactory);
    }
}
