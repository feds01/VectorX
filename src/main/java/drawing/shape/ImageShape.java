package drawing.shape;

import common.CopyUtils;
import drawing.tool.ToolType;

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
 * ImageShape class that is used to draw shapes that are of ellipse
 * type. This class extends the base class Shape to implement
 * the methods for drawing an ellipse.
 *
 * @author 200008575
 * */
public class ImageShape extends Shape {

    /**
     * The image object that is used for drawing on the canvas. This
     * variable must be transient since it can't be serialized.
     */
    private transient BufferedImage image;

    /**
     * ImageShape constructor method
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
     * Method to copy the current shape and make a new instance of it
     *
     * @return A new ImageShape object that holds the same properties as this object.
     */
    public ImageShape copy()  {
        var clazz = new ImageShape(this.getX(), this.getY(), image);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     * Method to get the image for this object
     *
     * @return the buffered image of the current ImageShape.
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Method to get the image for this object
     *
     * @param image set a new buffered image of the current ImageShape.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * This method returns the ImageShape ToolType for this method
     *
     * @return The tool type
     */
    @Override
    public ToolType getToolType() {
        return ToolType.IMAGE;
    }

    /**
     * This method is used to draw the boundary of the object when it
     * is being highlighted on the canvas.
     *
     * @param g The canvas graphical context.
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        int xPos = getX() - getWidth() / 2;
        int yPos = getY() - getHeight() / 2;

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawRect(xPos, yPos, getWidth(), getHeight());
    }

    /**
     * Override the writing method for the Serializable interface
     * so that the image object can be written to the save file.
     */
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();

        // write how many images there are to follow
        ImageIO.write(this.image, "png", outputStream);
    }

    /**
     * Override the reading method for the Serializable interface
     * so that the image object can be read from the save file.
     */
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();

        this.image = ImageIO.read(inputStream);
    }

    /**
     * This method is used to draw the selection boundary of the object when it
     * is selected on the canvas.
     *
     * @param g The canvas graphical context.
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
     * This method is used to draw the object when it is present on
     * the canvas.
     *
     * @param g The canvas graphical context.
     * @param isResizing a boolean representing if the shape is currently being
     *                   resized. This value can be used to display a special
     *                   style when it's being resized.
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

    /**
     * Returns whether or not this object can be filled.
     *
     * @return a boolean whether the Fill tool can be used on this
     * object.
     */
    @Override
    public boolean isFillable() {
        return false;
    }

    /**
     * Check whether or not a certain point is within the hover-able
     * boundary of the shape.
     *
     * @param point - The point to be checked whether it is within the bounds
     * @return Whether or not the given point is within the bounds
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
     * Equality method for the the ImageShape shape object.
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
     * Hash method for the ImageShape shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), image, properties);
    }
}
