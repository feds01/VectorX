package ui.widget;

import drawing.shape.Rectangle;
import drawing.shape.Shape;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Graphics;
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
    List<Shape> objects = new ArrayList<>();

    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;

    /**
     *
     */
    public Canvas() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * This overridden paint method will be called by Swing when Canvas.repaint() is called from SimpleGuiDelegate.propertyChange().
     */
    public void paint(Graphics g) {
        g.setColor(DEFAULT_BG_COLOUR);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw all of the components on the canvas
        this.objects.forEach(shape -> shape.draw(g));
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

            objects.add(new Rectangle(mouseX1, mouseY1, mouseX2, mouseY2));
            this.repaint();
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
        if (SwingUtilities.isMiddleMouseButton(e)) {
            this.getParent().dispatchEvent(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
