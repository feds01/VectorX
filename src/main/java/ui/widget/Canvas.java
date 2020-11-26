package ui.widget;

import drawing.shape.Ellipses;
import drawing.shape.Line;
import drawing.shape.Rectangle;
import drawing.shape.Shape;
import drawing.shape.TextShape;
import drawing.shape.Triangle;
import ui.controllers.ToolController;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Canvas extends JPanel implements MouseMotionListener, MouseInputListener {
    private static final Color DEFAULT_BG_COLOUR = Color.WHITE;

    private double zoomFactor = 1;

    /**
     *
     */
    private List<Shape> objects = new ArrayList<>();

    /**
     * The temporary object that is being used to display a pre-emptive shape that
     * will be drawn when the user let's go of the mouse.
     * */
    private Shape currentObject = null;


    private final ToolController toolController;

    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;

    /**
     *
     */
    public Canvas(ToolController toolController) {
        this.toolController = toolController;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * This overridden paint method will be called by Swing when Canvas.repaint() is called from SimpleGuiDelegate.propertyChange().
     */
    public void paint(Graphics g) {
        g.setColor(DEFAULT_BG_COLOUR);
        g.fillRect(0, 0, getWidth(), getHeight());

        var g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all of the components on the canvas
        this.objects.forEach(shape -> shape.draw(g2, false));

        // Draw the current shape to add interactivity to the canvas. The user
        // can see what they are about to draw.
        if (currentObject != null) {
            currentObject.draw(g2, true);
        }

        g.dispose();
    }


    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseX1 = e.getX();
            mouseY1 = e.getY();
            System.out.println("x1 = " + mouseX1 + ", y1 = " + mouseY1);
        } else {
            this.getParent().dispatchEvent(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseX2 = e.getX();
            mouseY2 = e.getY();
            System.out.println("x2 = " + mouseX2 + ", y2 = " + mouseY2);

            if (currentObject != null) {
                objects.add(currentObject);
                this.repaint();

                currentObject = null;
            }
        } else {
            this.getParent().dispatchEvent(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)) {
            var endX = e.getX();
            var endY = e.getY();

            this.currentObject = this.createNewObject(mouseX1, mouseY1, endX, endY);

            if (this.currentObject != null) {
                this.repaint();
            }


        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            this.getParent().dispatchEvent(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private Shape createNewObject(int x1, int y1, int x2, int y2) {
        try {
            return new TextShape(x1, y1, x2, y2);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
