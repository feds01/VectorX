package drawing.shape;

import java.io.Serializable;

/**
 * Interface to implement a validator function for any shape property.
 * The ShapeProperty that utilises the ShapePropertyValidator will be
 * able to validate properties before and when they are being set.
 *
 * @author 200008575
 * */
public interface ShapePropertyValidator<T> extends Serializable {

    /**
     * Method to apply the ShapePropertyValidator validator method.
     *
     * @param value - The value to be checked against the validator.
     *
     * @return whether or not the value passes the validator function.
     */
    boolean apply(T value);
}
