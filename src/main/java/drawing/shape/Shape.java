package drawing.shape;

import java.awt.Color;
import java.awt.Graphics;

public interface Shape {
    public int getX();
    public void setX(int x);

    public int getY();
    public void setY(int y);

    public ShapeProperties getProperties();
    public void setProperties(ShapeProperties properties);

    public Color getShapeStroke();
    public void setShapeStroke(Color color);

    public default Color getShapeFill() {
        return null;
    }
    public default void setShapeFill(Color color) {}

    public void draw(Graphics g);

    public boolean isFillable();
}
