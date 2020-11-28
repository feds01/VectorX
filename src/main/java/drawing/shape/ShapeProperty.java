package drawing.shape;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class that implements a shape property holder of a shape object. This object
 * implements the serializable interface since it will be saved to a file when
 * requested. This class utilises Java generics since properties  can store any
 * value.
 *
 * @author 200008575
 * */
public class ShapeProperty<T> implements Serializable {
    /**
     * The name of the property
     */
    private String name;

    /**
     * The current value of the property
     */
    private T value;

    /**
     * The shape property validator that will be used to check whether setting
     * a value in the current Shape property is a valid operation.
     */
    private ShapePropertyValidator<T> validator;

    /**
     * Constructor method for the shapes property
     *
     * @implNote Maybe check if type T is serializable
     */
    public ShapeProperty(String name, T value, ShapePropertyValidator<T> validator) {
        this.name = name;
        this.validator = validator;

        if (!validator.apply(value)) {
            throw new IllegalArgumentException("Invalid property value.");
        }

        this.value = value;
    }

    /**
     * Method to copy the current ShapeProperty and make a new instance of it
     *
     * @return A new ShapeProperty object that holds the same properties as this object.
     */
    protected ShapeProperty<T> copy() {

        return new ShapeProperty<>(this.name, this.value, this.validator);
    }

    /**
     * Method to set the name of this shape property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the name of this shape property
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the validator for this shape property
     */
    public ShapePropertyValidator<T> getValidator() {
        return validator;
    }

    /**
     * Method to set the validator for this shape property
     */
    public void setValidator(ShapePropertyValidator<T> validator) {
        this.validator = validator;
    }

    /**
     * Method to get the value of this shape property
     */
    public T getValue() {
        return value;
    }

    /**
     * Method to set the name of this shape property. The new value will be
     * checked by the shape validator so that it constrains under the validator
     * specification.
     *
     * @param value The new value of the property
     */
    @SuppressWarnings("unchecked")
    public void setValue(Object value) throws IllegalArgumentException {

        // @Workaround: java generics workaround to set the type externally.
        T reflectedValue = (T) value;

        if (!validator.apply(reflectedValue)) {
            throw new IllegalArgumentException("Invalid property value.");
        }

        this.value = reflectedValue;
    }

    /**
     * Equality method for the the ShapeProperty shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeProperty<?> that = (ShapeProperty<?>) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    /**
     * Hash method for the ShapeProperty shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, value, validator);
    }

    /**
     * Method to print the object in a readable format
     * */
    @Override
    public String toString() {
        return "ShapeProperty{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
