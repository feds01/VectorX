package ui.widget;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

public class CanvasWidget extends JPanel {
    private double zoomFactor = 1;
    private boolean isZooming;
    private double prevZoomFactor = 1;
    private double xOffset = 0;
    private double yOffset = 0;

    public CanvasWidget() {
        this.setLayout(new GridBagLayout());

        JPanel canvas = new JPanel();

        canvas.setPreferredSize(new Dimension(600, 700));
        canvas.setMaximumSize(new Dimension(600, 700));

        canvas.setBackground(Color.white);

        this.add(canvas, new GridBagConstraints());
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

            if (this.zoomFactor > 1.0) {
                var dim = new Dimension(
                        (int) Math.round(getWidth() * this.zoomFactor),
                        (int) Math.round(getHeight() * this.zoomFactor));

                this.setPreferredSize(dim);
            } else {
                var dim = new Dimension(getParent().getWidth(), getParent().getHeight());

                this.setPreferredSize(dim);
            }
            this.revalidate();

            prevZoomFactor = zoomFactor;
            g2.transform(at);


            isZooming = false;
        }
    }

    public void handleScroll(MouseWheelEvent event) {
        if (event.isControlDown()) {
            this.isZooming = true;

            //Zoom in
            if (event.getWheelRotation() < 0) {
                zoomFactor *= 1.1;
                repaint();
            }

            //Zoom out
            if (event.getWheelRotation() > 0) {
                zoomFactor /= 1.1;
                repaint();
            }
        }
    }
}
