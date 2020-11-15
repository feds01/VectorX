package drawing.tool;

import common.ImageUtils;
import drawing.ToolType;

import javax.swing.ImageIcon;
import java.awt.Cursor;

public class GenericTool implements DrawingTool {
    private final ToolType type;
    private final Cursor cursor;

    private final String resourceUri;

    public GenericTool(ToolType type, Cursor cursor, String resourceUri) {
        this.type = type;
        this.cursor = cursor;

        this.resourceUri = resourceUri;
    }

    @Override
    public Cursor getCursor() {
        return this.cursor;
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
}
