package drawing.tool;

import common.ImageUtils;
import drawing.ToolType;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Objects;

/**
 *
 */
public class FillTool implements DrawingTool {
    /**
     *
     */
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    /**
     *
     */
    private final ToolType type;

    /**
     *
     */
    public FillTool() {
        this.type = ToolType.FILL;
    }

    /**
     *
     */
    @Override
    public Cursor getCursor() {
        var icon = new ImageIcon(FillTool.class.getResource("/icons/fill.png"));

        // get the best width and height based on Operating System using the Toolkit
        var dimensions = toolkit.getBestCursorSize(32, 32);

        icon = ImageUtils.resizeIcon(icon, dimensions.width, dimensions.height);

        return toolkit.createCustomCursor(icon.getImage(), new Point(0, 0), "");
    }

    /**
     *
     */
    @Override
    public String getToolTip() {
        return "Fill (F)";
    }

    /**
     *
     */
    @Override
    public ToolType getType() {
        return this.type;
    }

    /**
     *
     */
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

    /**
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FillTool fillTool = (FillTool) o;
        return Objects.equals(toolkit, fillTool.toolkit) &&
                type == fillTool.type;
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(toolkit, type);
    }
}
