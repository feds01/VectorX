package ui.input;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * */
public class ColourPickerInput extends JToggleButton {

    /**
     *
     * */
    private Popup pickerPopup = null;

    /**
     *
     * */
    private final ColourPicker picker;

    /**
     *
     * */
    private Color colour;

    /**
     *
     * */
    private final JFrame frame;

    /**
     *
     * */
    public ColourPickerInput(Color colour, JFrame frame) {
        super();

        this.colour = colour;
        this.frame = frame;

        // Create the picker.
        this.picker = new ColourPicker(this.colour);


        //this.setOpaque(true);
        this.setBackground(this.colour);
        this.setBorderPainted(false);
        this.setMaximumSize(new Dimension(24, 24));

        // create the popup that will be used to display the colour picker.
        this.addActionListener((ActionEvent e) -> {
            this.pickerPopup = this.createColourPickerPopup();
            this.pickerPopup.show();
        });

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent) {
                if (SwingUtilities.isDescendingFrom(
                        (Component) event.getSource(), frame)) {
                    MouseEvent m = (MouseEvent) event;

                    if (m.getID() == MouseEvent.MOUSE_CLICKED) {
                        if (!picker.contains(m.getPoint()) && pickerPopup != null) {
                            pickerPopup.hide();
                            pickerPopup = null;

                            // @Workaround: sometimes components aren't being re-painted after it closes
                            frame.repaint();
                        }
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }


    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }

    @Override
    public boolean isFocusPainted() {
        return false;
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.colour);

        Shape s = new RoundRectangle2D.Float(1, 1, 20, 20, 8, 8);

        g2.fill(s);
        super.paintChildren(g);

    }

    private Popup createColourPickerPopup() {
        PopupFactory pf = PopupFactory.getSharedInstance();
        Point l1 = this.getLocationOnScreen();


        return pf.getPopup(frame, picker, l1.x - 212, l1.y - 100 + 6);
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
}


/**
 *
 * */
class ColourPicker extends Box {
    private static final int HEIGHT = 210;
    private static final int WIDTH = 210;

    private static final int BORDER_THICKNESS = 1;
    private static final int BORDER_RADII = 8;
    private static final int BORDER_POINTER_SIZE = 7;
    private static final int BORDER_POINTER_PADDING = 6;

    private final Color borderColour = Color.GRAY;

    private final Color colour;
    private final Insets insets;

    /**
     *
     * */
    ColourPicker(Color colour) {
        super(BoxLayout.Y_AXIS);

        this.colour = colour;

        this.setOpaque(true);
        this.setBackground(Color.WHITE);


        var rightPadding = BORDER_RADII + BORDER_POINTER_SIZE;
        this.insets = new Insets(BORDER_RADII, rightPadding, BORDER_RADII, BORDER_RADII);

        var expectedDimension = new Dimension(WIDTH, HEIGHT);

        this.setPreferredSize(expectedDimension);
        this.setMaximumSize(expectedDimension);
        this.setMinimumSize(expectedDimension);

        JPanel rgbPanel = new JPanel();

        rgbPanel.setBackground(Color.WHITE);
        rgbPanel.setLayout(new GridLayout(1, 3, 5, 0));

        TextFieldInput red = new TextFieldInput(String.valueOf(this.colour.getRed()));
        TextFieldInput green = new TextFieldInput(String.valueOf(this.colour.getGreen()));
        TextFieldInput blue = new TextFieldInput(String.valueOf(this.colour.getBlue()));

        rgbPanel.add(red.getComponent());
        rgbPanel.add(green.getComponent());
        rgbPanel.add(blue.getComponent());

        rgbPanel.setMaximumSize(new Dimension(140, 20));

        SliderInput alphaSlider = new SliderInput("alpha", 0, 255, this.colour.getAlpha());

        alphaSlider.disableLabel();
        alphaSlider.disableTextBox();

        alphaSlider.getComponent().setMaximumSize(new Dimension(140, 20));

        JPanel colourBox = new JPanel();

        colourBox.setMinimumSize(new Dimension(140, 140));
        colourBox.setMaximumSize(new Dimension(140, 140));
        colourBox.setBackground(colour);

        this.add(Box.createVerticalStrut(5));
        this.add(colourBox);
        this.add(Box.createVerticalStrut(5));
        this.add(rgbPanel);
        this.add(Box.createVerticalStrut(5));
        this.add(alphaSlider.getComponent());
    }


    /**
     *
     * */
    @Override
    public Insets getInsets() {
        return insets;
    }

    /**
     *
     * */
    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);

        Graphics2D g2 = (Graphics2D) g;

        int endX = WIDTH - BORDER_THICKNESS - BORDER_POINTER_SIZE;
        int middleLineY = (HEIGHT - BORDER_THICKNESS) / 2;

        var hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(
                0,
                0,
                endX,
                middleLineY * 2,
                BORDER_RADII,
                BORDER_RADII);

        Polygon pointer = new Polygon();

        // top point
        pointer.addPoint(endX, middleLineY - BORDER_POINTER_PADDING);
        // bottom point
        pointer.addPoint(endX, middleLineY + BORDER_POINTER_PADDING);

        // middle point
        pointer.addPoint(WIDTH, HEIGHT / 2);

        Area area = new Area(bubble);
        area.add(new Area(pointer));

        g2.setRenderingHints(hints);

        Area spareSpace = new Area(new Rectangle(0, 0, WIDTH, HEIGHT));
        spareSpace.subtract(area);
        g2.setClip(spareSpace);
        g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.setClip(null);

        g2.setColor(this.borderColour);
        g2.setStroke(new BasicStroke(BORDER_THICKNESS));
        g2.draw(area);
    }
}