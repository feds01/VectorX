package drawing.tool;

import javax.swing.ImageIcon;
import java.awt.Cursor;

/**
 * Interface to describe a DrawingTool that can be implemented by
 * any child classes.
 *
 * @author 200008575
 * */
public interface DrawingTool {
    /**
     * Get the {@link Cursor} for the tool.
     *
     * @return the cursor
     */
    Cursor getCursor();

    /**
     * Get the tooltip for the tool, that will be displayed when a user
     * hovers over the item.
     *
     * @return the tooltip
     */
    String getToolTip();

    /**
     * Get the {@link ToolType} for the tool.
     *
     * @return the ToolType for the tool.
     */
    ToolType getType();

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
    ImageIcon getImageIcon(boolean selected);
}
