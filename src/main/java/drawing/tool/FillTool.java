package drawing.tool;

import common.ImageUtils;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Objects;


/**
 * FillTool class that implements the DrawingTool class. This class
 * represents the Fill tool within the application.
 *
 * @author 200008575
 * */
public class FillTool implements DrawingTool {
    /**
     * Get the swing application toolkit.
     */
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    /**
     * The type of the tool
     */
    private final ToolType type = ToolType.FILL;

    /**
     * FillTool constructor
     */
    public FillTool() { }

    /**
     * Get the {@link Cursor} for the tool.
     *
     * @return the cursor
     */
    @Override
    public Cursor getCursor() {
        var icon = new ImageIcon(FillTool.class.getResource("/resources/icons/fill.png"));

        // get the best width and height based on Operating System using the Toolkit
        var dimensions = toolkit.getBestCursorSize(32, 32);

        icon = ImageUtils.resizeIcon(icon, dimensions.width, dimensions.height);

        return toolkit.createCustomCursor(icon.getImage(), new Point(0, 0), "");
    }

    /**
     * Get the tooltip for the tool, that will be displayed when a user
     * hovers over the item.
     *
     * @return the tooltip
     */
    @Override
    public String getToolTip() {
        return "Fill (F)";
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
            icon = new ImageIcon(FillTool.class.getResource("/resources/icons/fill_selected.png"));
        } else {
            icon = new ImageIcon(FillTool.class.getResource("/resources/icons/fill.png"));
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
