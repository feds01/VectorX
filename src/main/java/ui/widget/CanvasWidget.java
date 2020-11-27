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

public class CanvasWidget extends JPanel implements MouseListener, MouseMotionListener {
    private final Canvas canvas;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;

    private boolean isZooming;
    private boolean released;
    private boolean isDragging;

    private double xOffset = 0;
    private double yOffset = 0;

    private int xDiff;
    private int yDiff;
    private Point startPoint;

    /**
     *
     */
    private final double MAX_SCALE_ZOOM = 2.0;

    /**
     *
     */
    private final double MIN_SCALE_ZOOM = 0.25;


    public CanvasWidget(ToolController controller, WidgetController widgetController) {
        this.setLayout(new GridBagLayout());

        this.canvas = new Canvas(controller, widgetController);

        canvas.setPreferredSize(new Dimension(600, 700));
        canvas.setMaximumSize(new Dimension(600, 700));

        canvas.setBackground(Color.white);

        this.add(canvas, new GridBagConstraints());

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

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

            // TODO: fix buggy scale code
//            Dimension dim;

//            if (this.zoomFactor > 1.0) {
//                dim = new Dimension(
//                        (int) Math.round(getWidth() * this.zoomFactor),
//                        (int) Math.round(getHeight() * this.zoomFactor));
//
//            } else {
//                dim = new Dimension(getParent().getWidth(), getParent().getHeight());
//
//            }
//            this.setPreferredSize(dim);
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

    public void handleScroll(MouseWheelEvent event) {
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

    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        if (SwingUtilities.isMiddleMouseButton(e)) {
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));

            isDragging = true;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
        released = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    /**
     *
     * */
    public Canvas getCanvas() {
        return this.canvas;
    }

}
