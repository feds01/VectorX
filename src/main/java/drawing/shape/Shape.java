package drawing.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public interface Shape {
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
    public default void setShapeFillColour(Color color) {}

    public void draw(Graphics2D g, boolean isResizing);

    public boolean isFillable();
}
