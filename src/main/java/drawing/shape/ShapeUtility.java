package drawing.shape;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 *
 */
public class ShapeUtility {

    /**
     *
     */
    public static void drawSelectorPoint(Graphics2D g, double x, double y) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        g.fill(new Ellipse2D.Double(x - 4, y - 4, 8, 8));

        g.setColor(Shape.SELECTOR_COLOUR);
        g.draw(new Ellipse2D.Double(x - 4, y - 4, 8, 8));
    }

    /**
     *
     */
    public static void drawSelectorPoint(Graphics2D g, int x, int y) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        g.fillOval(x - 4, y - 4, 8, 8);

        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawOval(x - 4, y - 4, 8, 8);
    }

    /**
     *
     */
    public static void drawSelectorRect(Graphics2D g, int x, int y, int width, int height) {
        g.setStroke(new BasicStroke(2));
        g.setColor(Shape.SELECTOR_COLOUR);
        g.drawRect(x, y, width, height);

        // draw modifying circle at the start
        ShapeUtility.drawSelectorPoint(g, x, y);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x, y + height / 2);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x, y + height);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x + width / 2, y + height);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x + width / 2, y);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x + width, y);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x + width / 2, y + height);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x + width, y + height / 2);

        // draw modifying circle at the end of line
        ShapeUtility.drawSelectorPoint(g, x + width, y + height);
    }


    public static void setAlpha(Graphics2D g, int alpha) {
        float a = (float) alpha / 255.0f;

        var alphaComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, a);

        g.setComposite(alphaComposite);
    }
}
