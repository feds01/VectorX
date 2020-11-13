package drawing.shape;

public class ShapeProperty {
    private final String name;
    private Object value;
    private final ShapePropertyValidator validator;

    public ShapeProperty(String name, Object value, ShapePropertyValidator validator) {
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

    public ShapePropertyValidator getValidator() {
        return validator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) throws IllegalArgumentException {
        if (!validator.apply(value)) {
            throw new IllegalArgumentException("Invalid property value.");
        }

        this.value = value;
    }
}
