package ui.input.colour;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 */
public class ColourPanel extends JPanel {

    private final transient BufferedImage image;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private int x1 = 5;
    private int y1 = 7;

    protected Point slider;

    private Color colour;

    private int red;
    private int green;
    private int blue;
    private int alpha;

    private static final int SIZE = 130;


    /**
     *
     */
    public ColourPanel(Color colour) {
        this.colour = colour;

        this.red = colour.getRed();
        this.green = colour.getGreen();
        this.blue = colour.getBlue();
        this.alpha = colour.getAlpha();


        this.setColour(colour);
        this.image = updateImage();

        // set the box border
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    /**
     *
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(140, 140);
    }

    /**
     *
     */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(140, 140);
    }


    private BufferedImage updateImage() {

        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        int[] row = new int[SIZE];
        float size = (float) SIZE;
        float radius = size / 2f;

        for (int yidx = 0; yidx < SIZE; yidx++) {
            float y = yidx - size / 2f;

            for (int xidx = 0; xidx < SIZE; xidx++) {
                float x = xidx - size / 2f;

                double theta = Math.atan2(y, x) - 3d * Math.PI / 2d;

                if (theta < 0) {
                    theta += 2d * Math.PI;
                }

                double r = Math.sqrt(x * x + y * y);

                float hue = (float) (theta / (2d * Math.PI));
                float sat = Math.min((float) (r / radius), 1f);
                float bri = 1f;

                row[xidx] = Color.HSBtoRGB(hue, sat, bri);
            }
            image.getRaster().setDataElements(0, yidx, SIZE, 1, row);
        }
        return image;
    }


    /**
     *
     */
    // https://java-swing-tips.blogspot.com/2019/05/draw-color-wheel-on-jpanel.html
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        GraphicsConfiguration gc = g2.getDeviceConfiguration();
        BufferedImage buf = gc.createCompatibleImage(SIZE, SIZE, Transparency.TRANSLUCENT);
        Graphics2D g2d = buf.createGraphics();


        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fill(new Ellipse2D.Float(0f, 0f, SIZE, SIZE));

        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        g2.drawImage(buf, null, (getWidth() - SIZE) / 2, (getHeight() - SIZE) / 2);

        if (slider != null) {
            g2.setColor(Color.GRAY);
            g2.fillOval(slider.x, slider.y, 11, 11);

            g2.setColor(Color.WHITE);
            g2.fillOval(slider.x, slider.y, 10, 10);
        }

        g2.dispose();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                super.mousePressed(event);

                handleColourChange(event);
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                int rad = SIZE / 2;

                Point eventPoint = event.getPoint();

                if (Math.sqrt(Math.pow((eventPoint.x - rad), 2) + Math.pow((eventPoint.y - rad), 2)) <= rad) {
                    slider = eventPoint;
                } else {
                    int length = (int) Math.round(eventPoint.distance(rad, rad));

                    slider = new Point((rad * eventPoint.x / length), (rad * eventPoint.y / length));
                }

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                super.mouseDragged(event);

                handleColourChange(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                super.mouseMoved(event);
            }
        });
    }

    private void handleColourChange(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        int currentColour;


        int rad = SIZE / 2;

        // don't track the mouse if the x and y are not within the circle
        if (Math.sqrt(Math.pow((x - rad), 2) + Math.pow((y - rad), 2)) > rad) {
            int length = (int) Math.round(event.getPoint().distance(rad, rad));

            var point = new Point((rad * event.getX() / length), (rad * event.getY() / length));

            x = point.x;
            y = point.y;
        }

        try {
            if (x > 0 && x <= getWidth()) x1 = x - 5;
            if (y > 0 && y <= getHeight()) y1 = y - 5;

            currentColour = image.getRGB(x1 + 5, y1 + 5);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }


        red = (currentColour >> 16) & 0xFF;
        green = (currentColour >> 8) & 0xFF;
        blue = currentColour & 0xFF;

        var newColour = new Color(red, green, blue, alpha);
        changes.firePropertyChange("colour", colour, newColour);
        colour = newColour;

        slider = null;
        repaint();
    }


    /**
     *
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     *
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    /**
     *
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changes.removePropertyChangeListener(listener);
    }

    /**
     *
     */
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

    public void setColour(Color newColour) {
        this.colour = newColour;

        float[] hsb = new float[3];

        Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);

        // Hue is the theta on the polar plain
        float hue = (float) (hsb[0] - Math.floor(hsb[0]));

        double theta = hue * 2 * Math.PI - Math.PI / 2;
        if (theta < 0)
            theta += 2 * Math.PI;

        // Saturation is the radius from the origin on the HSB spectrum
        double r = hsb[1] * SIZE / 2;

        // Convert the polar coordinate to cartesian coordinate and transform it to the center of
        // the circle
        slider = new Point(
                (int) (r * Math.cos(theta) + .5 + SIZE / 2.0),
                (int) (r * Math.sin(theta) + .5 + SIZE / 2.0)
        );

        this.repaint();
    }
}
