package drawing.shape;

import java.util.Map;

public class ShapeProperties {
    private Map<String, ShapeProperty> properties;

    public ShapeProperties(Map<String, ShapeProperty> properties) {
        this.properties = properties;
    }

    public Map<String, ShapeProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ShapeProperty> properties) {
        this.properties = properties;
    }

    public void addProperty(ShapeProperty property) {
        var name = property.getName();

        if (this.properties.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Properties object already contains a property with '%s' name.", name));
        }

        this.properties.put(name, property);
    }
}
