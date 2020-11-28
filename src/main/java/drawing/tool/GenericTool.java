package drawing.tool;

import common.ImageUtils;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.util.Objects;

/**
 * A GenericTool class that implements the DrawingTool
 * class.
 *
 * @author 200008575
 * */
public class GenericTool implements DrawingTool {
    /**
     * The type of the tool
     */
    private final ToolType type;

    /**
     * the cursor that is used when the tool is active.
     */
    private final Cursor cursor;

    /**
     * The tooltip that is displayed when the tool
     * button is hovered.
     */
    private final String tooltip;

    /**
     * The location of the icon that will be used for the
     * button of the tool.
     */
    private final String resourceUri;

    /**
     * GenericTool constructor
     */
    public GenericTool(ToolType type, Cursor cursor, String tooltip, String resourceUri) {
        this.type = type;
        this.cursor = cursor;
        this.tooltip = tooltip;
        this.resourceUri = resourceUri;
    }

    /**
     * Get the {@link Cursor} for the tool.
     *
     * @return the cursor
     */
    @Override
    public Cursor getCursor() {
        return this.cursor;
    }

    /**
     * Get the tooltip for the tool, that will be displayed when a user
     * hovers over the item.
     *
     * @return the tooltip
     */
    @Override
    public String getToolTip() {
        return tooltip;
    }

    /**
     * Get the {@link ToolType} for the tool.
     *
     * @return the ToolType for the tool.
     */
    @Override
    public ToolType getType() {
        return this.type;
    }

    /**
     * Get the ImageIcon for the tool. This icon is used
     * within the ToolWidget to display the button to enable
     * the tool.
     *
     * @param selected Whether or not the icon should be displayed
     *                 as selected.
     *
     * @return the icon for the current tool.
     */
    @Override
    public ImageIcon getImageIcon(boolean selected) {
        ImageIcon icon;

        if (selected) {
            icon = new ImageIcon(GenericTool.class.getResource(resourceUri + "_selected.png"));
        } else {
            icon = new ImageIcon(GenericTool.class.getResource(resourceUri + ".png"));
        }

        return ImageUtils.resizeIcon(icon, 20, 20);
    }

    /**
     * Equality method for the GenericTool object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericTool that = (GenericTool) o;
        return type == that.type &&
                Objects.equals(cursor, that.cursor) &&
                Objects.equals(resourceUri, that.resourceUri);
    }

    /**
     * hash method for the GenericTool object
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, cursor, resourceUri);
    }
}
