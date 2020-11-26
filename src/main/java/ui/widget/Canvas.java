package ui.widget;

import drawing.shape.Rectangle;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Canvas extends JPanel {
    private static final Color DEFAULT_BG_COLOUR = Color.WHITE;
    private static final Color DEFAULT_FG_COLOUR = Color.RED;

    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;

    Rectangle rect;


    public Canvas() {
        setupMouseEvents();
    }

    /**
     * This overridden paint method will be called by Swing when Canvas.repaint() is called from SimpleGuiDelegate.propertyChange().
     */
    public void paint(Graphics g) {
        g.setColor(DEFAULT_BG_COLOUR);
        g.fillRect(0, 0, getWidth(), getHeight());

        // get hold of rectangle and coords from model component
        if (rect != null) {
            int x1 = rect.getX();
            int width = (int) rect.getProperties().get("width").getValue();
            int y1 = rect.getY();
            int height = (int) rect.getProperties().get("height").getValue();

            // draw the rectangle
            g.setColor(DEFAULT_FG_COLOUR);
            g.fillRect(x1, y1, width, height);
        }

    }


    /**
     * Sets up mouse events for this canvas instance.
     */
    private void setupMouseEvents() {

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseX1 = e.getX();
                mouseY1 = e.getY();
                System.out.println("x1 = " + mouseX1 + ", y1 = " + mouseY1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseX2 = e.getX();
                mouseY2 = e.getY();
                System.out.println("x2 = " + mouseX2 + ", y2 = " + mouseY2);

                rect = new Rectangle(mouseX1, mouseY1, mouseX2, mouseY2);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }
        });
    }
}
