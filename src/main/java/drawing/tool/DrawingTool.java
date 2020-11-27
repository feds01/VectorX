package drawing.tool;

import drawing.ToolType;

import javax.swing.ImageIcon;
import java.awt.Cursor;

/**
 *
 */
public interface DrawingTool {
    /**
     *
     */
    public Cursor getCursor();

    /**
     *
     */
    public String getToolTip();

    /**
     *
     */
    public ToolType getType();

    /**
     *
     */
    public ImageIcon getImageIcon(boolean selected);
}
