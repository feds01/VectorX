package drawing.tool;

import common.ImageUtils;
import drawing.ToolType;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

public class FillTool implements DrawingTool {
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private final ToolType type;


    public FillTool() {
        this.type = ToolType.FILL;
    }

    @Override
    public Cursor getCursor() {
        var icon = new ImageIcon(FillTool.class.getResource("/icons/fill.png"));

        // get the best width and height based on Operating System using the Toolkit
        var dimensions = toolkit.getBestCursorSize(32, 32);

        icon = ImageUtils.resizeIcon(icon, dimensions.width, dimensions.height);

        return toolkit.createCustomCursor(icon.getImage(), new Point(0, 0), "");
    }

    @Override
    public ToolType getType() {
        return this.type;
    }

    @Override
    public ImageIcon getImageIcon(boolean selected) {
        ImageIcon icon;

        if (selected) {
            icon = new ImageIcon(FillTool.class.getResource("/icons/fill_selected.png"));
        } else {
            icon = new ImageIcon(FillTool.class.getResource("/icons/fill.png"));
        }

        return ImageUtils.resizeIcon(icon, 20, 20);
    }
}
