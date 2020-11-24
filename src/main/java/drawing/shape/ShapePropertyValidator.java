package drawing.shape;

public interface ShapePropertyValidator<T> {

    public boolean apply(T value);
}
