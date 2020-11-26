package drawing.shape;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShapeProperties implements Serializable {
    private Map<String, ShapeProperty<?>> properties;

    public ShapeProperties(Map<String, ShapeProperty<?>> properties) {
        this.properties = properties;
    }

    public ShapeProperties() {
        this(new HashMap<>());
    }

    public Map<String, ShapeProperty<?>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ShapeProperty<?>> properties) {
        this.properties = properties;
    }

    public void addProperty(ShapeProperty<?> property) {
        var name = property.getName();

        if (this.properties.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Properties object already contains a property with '%s' name.", name));
        }

        this.properties.put(name, property);
    }

    public ShapeProperty<?> get(String name) {
        return properties.get(name);
    }

    public void set(String name, ShapeProperty<?> value) {
        this.properties.put(name, value);
    }
}
