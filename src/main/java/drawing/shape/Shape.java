package drawing.shape;

import java.awt.Color;

public interface Shape {
    public float getCenterX();
    public void setCenterX();

    public float getCenterY();
    public void setCenterY();

    public ShapeProperties getProperties();
    public void setProperties();
    public void setProperty();

    public Color getShapeStroke();
    public void setShapeStroke();

    public Color getShapeFill();
    public void setShapeFill();

    public void draw();
}
