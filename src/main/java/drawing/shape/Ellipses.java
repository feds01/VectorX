package drawing.shape;

import common.CopyUtils;
import drawing.ToolType;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

public class Ellipses extends Shape {

    public Ellipses(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    public Ellipses copy()  {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new Ellipses(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }


    @Override
    public ToolType getToolType() {
        return ToolType.ELLIPSIS;
    }

    @Override
    public void drawBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawOval(getX(), getY(), width, height);
    }

    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, getX(), getY(), width, height);
    }

    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        int thicknessValue = (Integer) this.getPropertyMap().get("thickness").getValue();
        g.setStroke(new BasicStroke(thicknessValue));

        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillOval(getX(), getY(), width, height);

        g.setColor(this.getShapeStrokeColour());
        g.drawOval(getX(), getY(), width, height);
    }

    @Override
    public boolean isFillable() {
        return true;
    }

    @Override
    public boolean isPointWithinBounds(Point point) {
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        var ellipse = new Ellipse2D.Double(getX(), getY(), width, height);

        return ellipse.contains(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipses ellipses = (Ellipses) o;
        return getX() == ellipses.getX() &&
                getY() == ellipses.getY() &&
                Objects.equals(properties, ellipses.properties) &&
                Objects.equals(propertyFactory, ellipses.propertyFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties, propertyFactory);
    }
}
