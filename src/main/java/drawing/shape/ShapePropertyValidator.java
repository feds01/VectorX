package drawing.shape;

import java.io.Serializable;

/**
 *
 */
public interface ShapePropertyValidator<T> extends Serializable {

    /**
     *
     */
    public boolean apply(T value);
}
