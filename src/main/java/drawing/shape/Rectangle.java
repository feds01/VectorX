package drawing.shape;

import common.CopyUtils;
import drawing.ToolType;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Objects;

/**
 *
 */
public class Rectangle extends Shape {
    /**
     *
     */
    public Rectangle(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);

        this.properties.addProperty(propertyFactory.createColourProperty("strokeColour", Color.BLACK));
        this.properties.addProperty(propertyFactory.createColourProperty("fillColour", Color.WHITE));
        this.properties.addProperty(new ShapeProperty<>("thickness", 1, value -> 1 <= value && value <= 16));
    }

    /**
     *
     */
    public Rectangle copy()  {
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();

        var clazz = new Rectangle(this.getX(), this.getY(), this.getX() + width, this.getY() + height);
        clazz.setProperties((ShapeProperties) CopyUtils.deepCopy(this.properties));

        return clazz;
    }

    /**
     *
     */
    @Override
    public ToolType getToolType() {
        return ToolType.RECTANGLE;
    }


    /**
     *
     */
    @Override
    public void drawSelectedBoundary(Graphics2D g) {
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) this.getPropertyMap().get("end").getValue()).getY();


        // highlight the line, we can use draw boundary
        // here because it is the same as the highlighting border
        ShapeUtility.drawSelectorRect(g, x, y, width, height);
    }


    /**
     *
     */
    @Override
    public void drawBoundary(Graphics2D g) {
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // @Improvement: add rotation
        //        int rotation = Math.toRadians((int) this.properties.get("rotation").getValue());
        //        g.rotate(rotation);

        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);

        g.drawRect(x, y, width, height);
    }

    /**
     *
     */
    @Override
    public void draw(Graphics2D g, boolean isResizing) {
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        // @Improvement: add rotation
        //        int rotation = Math.toRadians((int) this.properties.get("rotation").getValue());
        //        g.rotate(rotation);

        int thicknessValue = (Integer) this.getPropertyMap().get("thickness").getValue();
        g.setStroke(new BasicStroke(thicknessValue));

        // draw the rectangle
        g.setColor(this.getShapeFillColour());
        g.fillRect(x, y, width, height);


        g.setColor(this.getShapeStrokeColour());
        g.drawRect(x, y, width, height);
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
        int x = (int) ((Point) this.getPropertyMap().get("start").getValue()).getX();
        int y = (int) ((Point) this.getPropertyMap().get("start").getValue()).getY();
        int width = (int) ((Point) this.properties.get("end").getValue()).getX();
        int height = (int) ((Point) this.properties.get("end").getValue()).getY();

        return (
                point.getX() >= x && point.getX() <= x + width &&
                        point.getY() >= y && point.getY() <= y + height
        );
    }

    /**
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return getX() == rectangle.getX() &&
                getY() == rectangle.getY() &&
                Objects.equals(properties, rectangle.properties) &&
                Objects.equals(propertyFactory, rectangle.propertyFactory);
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), properties, propertyFactory);
    }
}
