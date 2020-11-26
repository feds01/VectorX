package drawing.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public interface Shape {
    public static final Color SELECTOR_COLOUR = new Color(0x3399FF);

    public int getX();

    public void setX(int x);

    public int getY();

    public void setY(int y);

    public ShapeProperties getProperties();

    public void setProperties(ShapeProperties properties);

    public Color getShapeStrokeColour();

    public void setShapeStrokeColour(Color color);

    public default Color getShapeFillColour() {
        return null;
    }

    public default void setShapeFillColour(Color color) {
    }


    public void drawBoundary(Graphics2D g);

    public void draw(Graphics2D g, boolean isResizing);

    public boolean isFillable();

    boolean isPointWithinBounds(Point point);
}
