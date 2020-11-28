package drawing.shape;

import drawing.ResizeEvent;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * Class that holds utility methods to instantiate frequently used shapes
 * by any Object that implements the Shape class.
 *
 * @author 200008575
 * */
public class ShapeUtility {

    /**
     * A method to draw a selector point at the specified coordinates. A selector
     * point is a visual representation of a point that the user can interact with
     * the shape. A white circle will be painted with a blue selector colour border.
     *
     * @param g The canvas graphical context.
     * @param x - The x coordinate of where the selector point should be drawn.
     * @param y - The y coordinate of where the selector point should be drawn.
     */
    public static void drawSelectorPoint(Graphics2D g, double x, double y) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        g.fill(new Ellipse2D.Double(x - 4, y - 4, 8, 8));

        g.setColor(Shape.SELECTOR_COLOUR);
        g.draw(new Ellipse2D.Double(x - 4, y - 4, 8, 8));
    }

    /**
     * A method to draw a selector point at the specified coordinates. A selector
     * point is a visual representation of a point that the user can interact with
     * the shape. A white circle will be painted with a blue selector colour border.
     *
     * @param g The canvas graphical context.
     * @param x - The x coordinate of where the selector point should be drawn.
     * @param y - The y coordinate of where the selector point should be drawn.
     */
    public static void drawSelectorPoint(Graphics2D g, int x, int y) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        g.fillOval(x - 4, y - 4, 8, 8);

        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawOval(x - 4, y - 4, 8, 8);
    }

    /**
     * A method to draw a selector rectangle for a given bounding box. The method will
     * draw selector points at each corner of the box, and at each midpoint of the selector
     * bounding box. A visual representation of a point that the user can interact with
     * the shape.
     *
     * @param x - The x coordinate of start of the bounding box.
     * @param y - The y coordinate of start of the bounding box.
     * @param width - The width of shapes bounding box.
     * @param height - The width of shapes bounding box.
     */
    public static void drawSelectorRect(Graphics2D g, int x, int y, int width, int height) {
        // draw the rectangle first
        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawRect(x, y, width, height);

        Point[] selectorPoints = createResizePoints(x, y, width, height);

        // Draw each selector point
        for (Point point : selectorPoints) {
            ShapeUtility.drawSelectorPoint(g, point.x, point.y);
        }
    }


    /**
     * A method to draw a selector point at the specified coordinates. A selector
     * point is a visual representation of a point that the user can interact with
     * the shape. A white circle will be painted with a blue selector colour border.
     *
     * @param x - The x coordinate of where the selector point should be drawn.
     * @param y - The y coordinate of where the selector point should be drawn.
     * @param width - The width of shapes bounding box.
     * @param height - The width of shapes bounding box.
     *
     * @return an Array of points to where the selector points should be drawn.
     */
    public static Point[] createResizePoints(int x, int y, int width, int height) {
        var points = new Point[8];

        points[ResizeEvent.NORTH] = new Point(x + width / 2, y); // Northern point
        points[ResizeEvent.NORTH_EAST] = new Point(x + width, y); // North eastern point
        points[ResizeEvent.EAST] = new Point(x + width, y + height / 2); // Eastern point
        points[ResizeEvent.SOUTH_EAST] = new Point(x + width, y + height); // South eastern point
        points[ResizeEvent.SOUTH] = new Point(x + width / 2, y + height); // Southern point
        points[ResizeEvent.SOUTH_WEST] = new Point(x, y + height); // South western point
        points[ResizeEvent.WEST] = new Point(x, y + height / 2); // western point
        points[ResizeEvent.NORTH_WEST] = new Point(x, y); // north western point

        return points;
    }
}
