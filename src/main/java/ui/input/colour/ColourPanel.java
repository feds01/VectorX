package ui.input.colour;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * */
public class ColourPanel extends JPanel {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private int x1 = 5;
    private int y1 = 7;

    protected Point slider;

    private Color colour;

    private int red;
    private int green;
    private int blue;
    private int alpha;


    /**
     * */
    public ColourPanel(Color colour) {
        this.colour = colour;

        this.red = colour.getRed();
        this.green = colour.getGreen();
        this.blue = colour.getBlue();
        this.alpha = colour.getAlpha();


        // set the box border
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    /**
     *
     * */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(140, 140);
    }

    /**
     *
     * */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(140, 140);
    }

    /**
     *
     * */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        var bufferedImage = new BufferedImage(getWidth() - 2, getHeight() - 2, BufferedImage.TYPE_INT_RGB);

        var g2 = (Graphics2D) bufferedImage.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        LinearGradientPaint p = new LinearGradientPaint(0, 0, getWidth() - 2, 0,
                new float[]{0.0f, 0.1f, 0.2f, 0.5f, 0.6f, 0.7f, 0.9f, 1.0f},
                new Color[]{Color.white, Color.red, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta, Color.BLACK},
                RadialGradientPaint.CycleMethod.NO_CYCLE);

        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth() - 2, getHeight() - 2);


        if (slider != null) {
            g2.setColor(Color.GRAY);
            g2.fillOval(slider.x, slider.y, 11, 11);

            g2.setColor(Color.WHITE);
            g2.fillOval(slider.x, slider.y, 10, 10);
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                super.mousePressed(event);

                int x = event.getX();
                int y = event.getY();

                int currentColour = bufferedImage.getRGB(x1 + 5, y1 + 5);

                if (x > 0 && x <= getWidth()) x1 = x - 5;
                if (y > 0 && y <= getHeight()) y1 = y - 5;

                red = (currentColour >> 16) & 0xFF;
                green = (currentColour >> 8) & 0xFF;
                blue = currentColour & 0xFF;

                var newColour = new Color(red, green, blue, alpha);
                changes.firePropertyChange("colour", colour, newColour );
                colour = newColour;

                slider = null;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                slider = event.getPoint();
                repaint();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                super.mouseDragged(event);

                int x = event.getX();
                int y = event.getY();

                try {
                    int currentColour = bufferedImage.getRGB(x1 + 5, y1 + 5);


                    if (x > 0 && x <= getWidth()) x1 = x - 5;
                    if (y > 0 && y <= getHeight()) y1 = y - 5;


                    red = (currentColour >> 16) & 0xFF;
                    green = (currentColour >> 8) & 0xFF;
                    blue = currentColour & 0xFF;

                    var newColour = new Color(red, green, blue, alpha);
                    changes.firePropertyChange("colour", colour, newColour );
                    colour = newColour;

                    slider = null;
                    repaint();

                } catch (ArrayIndexOutOfBoundsException ignored) {
                    event.consume();
                }
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                super.mouseMoved(event);
            }
        });

        g.drawImage(bufferedImage, 1, 1, null);
    }


    /**
     *
     * */
    public int getAlpha() {
        return alpha;
    }

    /**
     *
     * */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    /**
     *
     * */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changes.removePropertyChangeListener(listener);
    }

    /**
     *
     * */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
        this.colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), alpha);
    }

    public Color getColour() {
        return colour;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
