package drawing.shape;

import common.CopyUtils;
import drawing.ToolType;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 *
 */
public class ImageShape extends Shape {

    /**
     *
     */
    private transient BufferedImage image;

    /**
     *
     */
    public ImageShape(int x, int y, BufferedImage image) {
        super(x, y, x + image.getWidth(), y + image.getHeight());

        this.image = image;


        // override the start and end positions...
        this.properties.set("start", ShapePropertyFactory.createPointProperty("start", new Point(x, y)));
        this.properties.set("end", ShapePropertyFactory.createPointProperty("end", new Point(image.getWidth(), image.getHeight())));

        this.properties.addProperty(new ShapeProperty<>("grayScale", false, value -> true));
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(ShapePropertyFactory.createColourProperty("fillColour", Color.WHITE));
    }

    /**
     *
     */
    public ImageShape copy()  {
        var clazz = new ImageShape(this.getX(), this.getY(), image);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     *
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     *
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     *
     */
    @Override
    public ToolType getToolType() {
        return ToolType.IMAGE;
    }

    /**
     *
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        int xPos = getX() - image.getWidth() / 2;
        int yPos = getY() - image.getHeight() / 2;

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawRect(xPos, yPos, image.getWidth(), image.getHeight());
    }

    /**
     *
     */
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();

        // write how many images there are to follow
        ImageIO.write(this.image, "png", outputStream);
    }

    /**
     *
     */
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        this.image = ImageIO.read(inputStream);
    }

    /**
     *
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = getWidth();
        int height = getHeight();

        int xPos = getX() - getWidth() / 2;
        int yPos = getY() - getHeight() / 2;

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, xPos, yPos, width, height);
    }

    /**
     *
     */
    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        var image = this.image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

        var resized = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        var rg = resized.createGraphics();


        g.setStroke(new BasicStroke(2));

        int xPos = getX() - getWidth() / 2;
        int yPos = getY() - getHeight() / 2;

        // It the user specifies the image to be drawn with a gray scale filter,
        // then do so...

        boolean grayscale = (boolean) this.properties.get("grayScale").getValue();

        if (grayscale) {
            // fast way to apply a grayscale filter and improve app performance...
            // https://stackoverflow.com/a/9131751/9955666

            // @Improvement: make p a slider value...
            ImageFilter filter = new GrayFilter(true, 50);
            ImageProducer producer = new FilteredImageSource(image.getSource(), filter);

            rg.drawImage(Toolkit.getDefaultToolkit().createImage(producer), 0, 0, null);
        } else {
            rg.drawImage(image,0, 0, null);
        }

        // draw the resized image
        g.drawImage(resized, xPos, yPos, null);
    }

    public int getWidth() {
        return (int) ((Point) this.properties.get("end").getValue()).getX();
    }

    public int getHeight() {
        return (int) ((Point) this.properties.get("end").getValue()).getY();
    }

    /**
     *
     */
    @Override
    public boolean isFillable() {
        return false;
    }

    /**
     *
     */
    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = getWidth();
        int height = getHeight();

        int xPos = getX() - getWidth() / 2;
        int yPos = getY() - getHeight() / 2;

        return (
                point.getX() >= xPos && point.getX() <= xPos + width &&
                        point.getY() >= yPos && point.getY() <= yPos + height
        );
    }

    /**
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageShape that = (ImageShape) o;
        return getX() == that.getX() &&
                getY() == that.getY() &&
                Objects.equals(image, that.image) &&
                Objects.equals(properties, that.properties);
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), image, properties);
    }
}
