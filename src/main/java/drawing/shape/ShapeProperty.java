package drawing.shape;

public class ShapeProperty<T> {
    private final String name;
    private T value;
    private final ShapePropertyValidator<T> validator;

    public ShapeProperty(String name, T value, ShapePropertyValidator<T> validator) {
        this.name = name;
        this.validator = validator;

        if (!validator.apply(value)) {
            throw new IllegalArgumentException("Invalid property value.");
        }

        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ShapePropertyValidator<T> getValidator() {
        return validator;
    }

    public T getValue() {
        return value;
    }

    public void setValue(Object value) throws IllegalArgumentException {

        // @Workaround: java generics workaround to set the type externally.
        T reflectedValue = (T) value;

        if (!validator.apply(reflectedValue)) {
            throw new IllegalArgumentException("Invalid property value.");
        }

        this.value = reflectedValue;
    }
}
