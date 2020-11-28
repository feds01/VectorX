package ui.widget;

import ui.controllers.ToolController;
import ui.controllers.WidgetController;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

/**
 * CanvasWidget widget class that is used to create and display the canvas
 * container. The widget also listens for mouse events that are passed to
 * the canvas for it to either be moved or zoomed in.
 *
 * @author 200008575
 * */
public class CanvasWidget extends JPanel implements MouseListener, MouseMotionListener {

    /**
     *
     */
    private final CanvasContainer canvas;

    /**
     *
     */
    private double zoomFactor = 1;

    /**
     *
     */
    private double prevZoomFactor = 1;

    /**
     *
     */
    private boolean isZooming;

    /**
     *
     */
    private boolean released;

    /**
     *
     */
    private boolean isDragging;

    /**
     *
     */
    private double xOffset = 0;

    /**
     *
     */
    private double yOffset = 0;

    /**
     *
     */
    private int xDiff;

    /**
     *
     */
    private int yDiff;

    /**
     *
     */
    private Point startPoint;

    public static final Dimension CANVAS_SIZE = new Dimension(600, 700);

    /**
     *
     */
    public CanvasWidget(ToolController controller, WidgetController widgetController) {
        this.setLayout(new GridBagLayout());

        this.canvas = new CanvasContainer(controller, widgetController);

        canvas.setPreferredSize(CANVAS_SIZE);
        canvas.setMaximumSize(CANVAS_SIZE);

        canvas.setBackground(Color.white);

        this.add(canvas, new GridBagConstraints());

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     *
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (isZooming) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);

//             TODO: fix buggy scale code
//            Dimension dim;
//            if (this.zoomFactor > 1.0) {
//                dim = new Dimension(
//                        (int) Math.round(CANVAS_SIZE.width * this.zoomFactor),
//                        (int) Math.round(CANVAS_SIZE.height * this.zoomFactor));
//
//            } else {
//                dim = CANVAS_SIZE;
//
//            }
//            this.canvas.setPreferredSize(dim);

            this.revalidate();

            prevZoomFactor = zoomFactor;
            g2.transform(at);

            isZooming = false;
        }

        if (isDragging) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFactor, zoomFactor);
            g2.transform(at);

            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                isDragging = false;
            }

        }
    }

    /**
     *
     */
    public CanvasContainer getCanvas() {
        return this.canvas;
    }

    /**
     *
     */
    public void handleScroll(MouseWheelEvent event) {
        double MAX_SCALE_ZOOM = 2.0;
        double MIN_SCALE_ZOOM = 0.25;

        if (event.isControlDown()) {
            this.isZooming = true;

            //Zoom in
            if (event.getWheelRotation() < 0 && zoomFactor < MAX_SCALE_ZOOM) {
                zoomFactor *= 1.1;
                repaint();
            }

            //Zoom out
            if (event.getWheelRotation() > 0 && zoomFactor > MIN_SCALE_ZOOM) {
                zoomFactor /= 1.1;
                repaint();
            }
        }
    }

    /**
     *
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();

        // @Heisenbug: sometimes startPoint can be null, this must be a
        // miss-firing of events.
        if (startPoint == null) {
            startPoint = e.getLocationOnScreen();
        }


        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        if (SwingUtilities.isMiddleMouseButton(e)) {
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));

            isDragging = true;
            repaint();
        }
    }

    /**
     *
     */
    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }


    /**
     *
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
        released = true;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
