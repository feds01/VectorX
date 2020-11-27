package drawing.shape;

import common.CopyUtils;
import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 */
public class Triangle extends Shape {
    /**
     *
     */
    private int[] yPoints;

    /**
     *
     */
    private int[] xPoints;

    /**
     *
     */
    public Triangle(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));

        this.setPoints();
    }

    /**
     *
     */
    public Triangle copy() {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new Triangle(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     *
     */
    private void setPoints() {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int x2 = this.getX() + width;
        int y2 = this.getY() + height;

        // build triangle points using our algorithm
        this.xPoints = new int[]{getX(), getX() + width / 2, x2};
        this.yPoints = new int[]{y2, getY(), y2};
    }

    /**
     *
     */
    @Override
    public ToolType getToolType() {
        return ToolType.TRIANGLE;
    }

    /**
     *
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        g.setColor(Shape.SELECTOR_COLOUR);
        g.setStroke(new BasicStroke(2));

        g.drawPolygon(xPoints, yPoints, 3);
    }

    /**
     *
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, getX(), getY(), width, height);
    }

    /**
     *
     */
    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(this.getShapeStrokeColour());
        g.drawPolygon(xPoints, yPoints, 3);

    }

    /**
     *
     */
    @Override
    public boolean isFillable() {
        return true;
    }

    /**
     *
     */
    @Override
    public boolean isPointWithinBounds(Point point) {

        // create a new polygon class with xPoints and yPoints
        var triangle = new Polygon(xPoints, yPoints, 3);

        return triangle.contains(point);
    }

    /**
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return getX() == triangle.getX() &&
                getY() == triangle.getY() &&
                Arrays.equals(yPoints, triangle.yPoints) &&
                Arrays.equals(xPoints, triangle.xPoints) &&
                Objects.equals(properties, triangle.properties) &&
                Objects.equals(propertyFactory, triangle.propertyFactory);
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getX(), getY(), properties, propertyFactory);
        result = 31 * result + Arrays.hashCode(yPoints);
        result = 31 * result + Arrays.hashCode(xPoints);
        return result;
    }
}
