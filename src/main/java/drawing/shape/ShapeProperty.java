package drawing.shape;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
public class ShapeProperty<T> implements Serializable {
    /**
     *
     */
    private String name;

    /**
     *
     */
    private T value;

    /**
     *
     */
    private ShapePropertyValidator<T> validator;

    /**
     *
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
     *
     */
    protected ShapeProperty<T> copy() {

        return new ShapeProperty<>(this.name, this.value, this.validator);
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public ShapePropertyValidator<T> getValidator() {
        return validator;
    }

    /**
     *
     */
    public void setValidator(ShapePropertyValidator<T> validator) {
        this.validator = validator;
    }

    /**
     *
     */
    public T getValue() {
        return value;
    }

    /**
     *
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeProperty<?> that = (ShapeProperty<?>) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, validator);
    }

    @Override
    public String toString() {
        return "ShapeProperty{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
