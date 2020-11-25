package drawing.tool;

import common.ImageUtils;
import drawing.ToolType;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.util.Objects;

public class GenericTool implements DrawingTool {
    private final ToolType type;
    private final Cursor cursor;

    private final String tooltip;
    private final String resourceUri;

    public GenericTool(ToolType type, Cursor cursor, String tooltip, String resourceUri) {
        this.type = type;
        this.cursor = cursor;
        this.tooltip = tooltip;

        this.resourceUri = resourceUri;
    }

    @Override
    public Cursor getCursor() {
        return this.cursor;
    }

    @Override
    public String getToolTip() {
        return tooltip;
    }

    @Override
    public ToolType getType() {
        return this.type;
    }



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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericTool that = (GenericTool) o;
        return type == that.type &&
                Objects.equals(cursor, that.cursor) &&
                Objects.equals(resourceUri, that.resourceUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, cursor, resourceUri);
    }
}
