package drawing.shape;

public interface Shape {
    public float getCenterX();
    public void setCenterX();

    public float getCenterY();
    public void setCenterY();

    public ShapeProperties getProperties();
    public void setProperties();
    public void setProperty();

    public ShapeColour getShapeStroke();
    public void setShapeStroke();

    public ShapeColour getShapeFill();
    public void setShapeFill();

    public void draw();
}
