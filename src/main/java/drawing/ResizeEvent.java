package drawing;

import java.awt.Cursor;

/**
 * Class that holds utility methods to dictate ResizeEvent behaviours
 * for objects.
 *
 * @author 200008575
 * */
public class ResizeEvent {

    /**
     * The collection of ids corresponding to the visual
     * representation of where the ResizeEvent occurs.
     * */
    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;

    /**
     * A method to return a {@link Cursor} based on the id of the
     * resize event.
     *
     * @param resizeEvent - The id of the resize event.
     * @return the cursor for the the resize event
     *
     * @throws IllegalArgumentException if the resizeEvent is out of the defined bounds.
     */
    public static Cursor getCursorFromResizeEvent(int resizeEvent) {
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
            default:
                throw new IllegalArgumentException("Invalid resize event.");
        }
    }
}
