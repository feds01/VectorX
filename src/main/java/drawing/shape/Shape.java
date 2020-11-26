package drawing.shape;

import drawing.ToolType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

public interface Shape {
    Color SELECTOR_COLOUR = new Color(0x3399FF);

    Map<String, ShapeProperty<?>> properties = null;

    ToolType getToolType();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    Map<String, ShapeProperty<?>> getProperties();

    void setProperties(ShapeProperties properties);

    Color getShapeStrokeColour();

    void setShapeStrokeColour(Color color);

    default Color getShapeFillColour() {
        return null;
    }

    default void setShapeFillColour(Color color) {
    }


    void drawBoundary(Graphics2D g);

    void drawSelectedBoundary(Graphics2D g);

    void draw(Graphics2D g, boolean isResizing);

    boolean isFillable();

    boolean isPointWithinBounds(Point point);

    void setProperty(String name, Object value);
}
