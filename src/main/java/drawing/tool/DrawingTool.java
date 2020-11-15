package drawing.tool;

import drawing.ToolType;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;

public interface DrawingTool {
    public Cursor getCursor();

    public ToolType getType();

    public ImageIcon getImageIcon(boolean selected);
}
