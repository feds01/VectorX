package drawing.shape;

import common.CopyUtils;
import drawing.ResizeEvent;
import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Objects;

/**
 *
 */
public class Line extends Shape {
    /**
     *
     */
    public Line(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.set("start", propertyFactory.createPointProperty("start", new Point(x, y)));
        this.properties.set("end", propertyFactory.createPointProperty("end", new Point(x2, y2)));


        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    /**
     *
     */
    public Line copy() {
        var clazz = new Line(this.getX(), this.getY(), this.getEndX(), this.getEndY());
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     *
     */
    @Override
    public ToolType getToolType() {
        return ToolType.LINE;
    }


    /**
     *
     */
    @Override
    public int getResizePoint(Point p) {
        // we only need to check if the point is on the ellipse at
        // each end of the line.

        var ellipseN = new Ellipse2D.Double(
                getX() - 4,
                getY() - 4,
                8, 8);

        if (ellipseN.contains(p)) {
            return ResizeEvent.NORTH;
        }

        var ellipseS = new Ellipse2D.Double(
                getEndX() - 4,
                getEndY() - 4,
                8, 8);

        if (ellipseS.contains(p)) {
            return ResizeEvent.SOUTH;
        }


        // not within range of the point
        return -1;
    }


    /**
     *
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);


        g.draw(new Line2D.Double(getX(), getY(), getEndX(), getEndY()));
    }

    /**
     *
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        this.drawBoundary(g);

        // draw modifying circle at the start
        ShapeUtility.drawSelectorPoint(g, getX(), getY());

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, getEndX(), getEndY());

    }

    /**
     *
     */
    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int thickness = (int) this.properties.get("thickness").getValue();
        g.setStroke(new BasicStroke(thickness));

        g.setColor(this.getShapeStrokeColour());
        g.draw(new Line2D.Double(getX(), getY(), getEndX(), getEndY()));
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
        double distance = Line2D.ptSegDist(getX(), getY(), getEndX(), getEndY(), point.getX(), point.getY());

        return distance < 4;
    }

    /**
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.getX(), getX()) == 0 &&
                Double.compare(line.getY(), getY()) == 0 &&
                Double.compare(line.getEndX(), getEndX()) == 0 &&
                Double.compare(line.getEndY(), getEndY()) == 0 &&
                Objects.equals(properties, line.properties) &&
                Objects.equals(propertyFactory, line.propertyFactory);
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getEndX(), getEndY(), properties, propertyFactory);
    }
}
