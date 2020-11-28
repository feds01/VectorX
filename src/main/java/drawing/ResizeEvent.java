package drawing;

import java.awt.Cursor;

public class ResizeEvent {
    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;

    /**
     * @return
     */
    public static Cursor getCursorFromResizeEvent(int resizeEvent) {
        if (resizeEvent < 0 || resizeEvent > 7) {
            throw new IllegalStateException("Invalid resize event.");
        }

        switch (resizeEvent) {
            case 0:
                return new Cursor(Cursor.N_RESIZE_CURSOR);
            case 1:
                return new Cursor(Cursor.NE_RESIZE_CURSOR);
            case 2:
                return new Cursor(Cursor.E_RESIZE_CURSOR);
            case 3:
                return new Cursor(Cursor.SE_RESIZE_CURSOR);
            case 4:
                return new Cursor(Cursor.S_RESIZE_CURSOR);
            case 5:
                return new Cursor(Cursor.SW_RESIZE_CURSOR);
            case 6:
                return new Cursor(Cursor.W_RESIZE_CURSOR);
            case 7:
                return new Cursor(Cursor.NW_RESIZE_CURSOR);
        }

        return null;
    }
}
