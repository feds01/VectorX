package drawing.shape;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that holds the properties of a shape object. This object implements
 * the serializable interface since it will be saved to a file when requested.
 *
 * @author 200008575
 * */
public class ShapeProperties implements Serializable {

    /**
     * A map of property names to ShapeProperty objects
     */
    private Map<String, ShapeProperty<?>> properties;

    /**
     * ShapeProperties constructor
     */
    public ShapeProperties(Map<String, ShapeProperty<?>> properties) {
        this.properties = properties;
    }

    /**
     * ShapeProperties constructor to initialise the object with an empty property map
     */
    public ShapeProperties() {
        this(new HashMap<>());
    }

    /**
     * Retrieves the shape property map
     *
     * @return property map
     */
    public Map<String, ShapeProperty<?>> getProperties() {
        return properties;
    }

    /**
     * Method to set the property map
     *
     * @param properties New property map
     */
    public void setProperties(Map<String, ShapeProperty<?>> properties) {
        this.properties = properties;
    }

    /**
     * Method to add a property to the current property map. The property
     * should not exist within the property map since this could cause a
     * mismatching of properties.
     *
     * @param property The property to be added to the property map.
     *
     * @throws IllegalArgumentException if the property name is already registered.
     */
    public void addProperty(ShapeProperty<?> property) {
        var name = property.getName();

        if (this.properties.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Properties object already contains a property with '%s' name.", name));
        }

        this.properties.put(name, property);
    }

    /**
     * Method to get a property from the property map.
     *
     * @param name - Name of the property that is to be retrieved.
     *
     * @return The property under the specified name. If no property exists
     *         under the specified name, the method will return null.
     */
    public ShapeProperty<?> get(String name) {
        return properties.get(name);
    }

    /**
     * Method to overwrite a property within the property map.
     *
     * @param name - Name of the property that is to be retrieved.
     * @param value - The ShapeProperty that will be put into the map.
     *
     * @implNote If the name of the property is not registered in the map, the
     * method will simply add the property.
     */
    public void set(String name, ShapeProperty<?> value) {
        this.properties.put(name, value);
    }

    /**
     * Equality method for the the ShapeProperties shape object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeProperties that = (ShapeProperties) o;
        return Objects.equals(properties, that.properties);
    }

    /**
     * Hash method for the ShapeProperties shape object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    /**
     * Method to print the object in a readable format
     * */
    @Override
    public String toString() {
        return "ShapeProperties{" +
                "properties=" + properties +
                '}';
    }
}
