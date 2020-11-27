package ui.input.colour;

import ui.input.SliderInput;
import ui.input.TextFieldInput;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 */
public class ColourPickerPopup extends Box {
    /**
     *
     */
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);


    /**
     *
     */
    private static final int HEIGHT = 210;

    /**
     *
     */
    private static final int WIDTH = 210;

    /**
     *
     */
    private static final int BORDER_THICKNESS = 1;

    /**
     *
     */
    private static final int BORDER_RADII = 8;

    /**
     *
     */
    private static final int BORDER_POINTER_SIZE = 7;

    /**
     *
     */
    private static final int BORDER_POINTER_PADDING = 6;

    /**
     *
     */
    private final Color borderColour = Color.GRAY;

    /**
     *
     */
    private final TextFieldInput green;

    /**
     *
     */
    private final TextFieldInput red;

    /**
     *
     */
    private final TextFieldInput blue;

    /**
     *
     */
    private final ColourPanel colourBox;

    /**
     *
     */
    private final Insets insets;

    /**
     *
     */
    private final SliderInput alphaSlider;

    /**
     *
     */
    ColourPickerPopup(Color initialColour) {
        super(BoxLayout.Y_AXIS);

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
        rgbPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.red = new TextFieldInput("red", String.valueOf(initialColour.getRed()));
        this.red.addPropertyChangeListener(this::textFieldChangeListener);
        this.green = new TextFieldInput("green", String.valueOf(initialColour.getGreen()));
        this.green.addPropertyChangeListener(this::textFieldChangeListener);
        this.blue = new TextFieldInput("blue", String.valueOf(initialColour.getBlue()));
        this.blue.addPropertyChangeListener(this::textFieldChangeListener);


        rgbPanel.add(red.getComponent());
        rgbPanel.add(green.getComponent());
        rgbPanel.add(blue.getComponent());

        rgbPanel.setMaximumSize(new Dimension(140, 20));

        this.alphaSlider = new SliderInput("alpha", initialColour.getAlpha(), 0, 255);

        alphaSlider.disableLabel();
        alphaSlider.disableTextBox();

        alphaSlider.addPropertyChangeListener(this::alphaSliderListener);

        alphaSlider.getComponent().setMaximumSize(new Dimension(140, 20));

        this.colourBox = new ColourPanel(initialColour);
        this.colourBox.addPropertyChangeListener(this::colourPanelListener);

        this.add(Box.createVerticalStrut(5));
        this.add(this.colourBox);
        this.add(Box.createVerticalStrut(5));
        this.add(rgbPanel);
        this.add(Box.createVerticalStrut(5));
        this.add(alphaSlider.getComponent());
    }

    /**
     *
     * */
    private void alphaSliderListener(PropertyChangeEvent propertyChangeEvent) {
        var oldColour = colourBox.getColour();
        colourBox.setAlpha((Integer) propertyChangeEvent.getNewValue());

        changes.firePropertyChange("colour", oldColour, colourBox.getColour());
    }

    /**
     *
     * */
    private void colourPanelListener(PropertyChangeEvent propertyChangeEvent) {
        Color oldColor = (Color) propertyChangeEvent.getOldValue();
        Color color = (Color) propertyChangeEvent.getNewValue();

        red.setValue(String.valueOf(color.getRed()));
        green.setValue(String.valueOf(color.getGreen()));
        blue.setValue(String.valueOf(color.getBlue()));
        alphaSlider.setValue(color.getAlpha());


        changes.firePropertyChange("colour", oldColor, color);
    }

    /**
     *
     * */
    private void textFieldChangeListener(PropertyChangeEvent event) {
        JTextField field = (JTextField) event.getSource();

        // if the text is equal to an empty string, don't change the value since we
        // don't know if the user is still editing the value
        try {
            int value = Integer.parseInt(field.getText());

            // set the text field input value to the slider value
            Color oldColour = this.colourBox.getColour();
            Color newColour;

            if (value < 0 || value > 255) {
                throw new IllegalArgumentException("Colour cant be greater than 255 or smaller than 0");
            }

            switch (event.getPropertyName()) {
                case "red":
                    newColour = new Color(value, oldColour.getGreen(), oldColour.getBlue(), oldColour.getAlpha());

                    this.colourBox.setColour(newColour);
                    break;
                case "green":
                    newColour = new Color(oldColour.getRed(), value, oldColour.getBlue(), oldColour.getAlpha());

                    this.colourBox.setColour(newColour);

                    break;
                case "blue":
                    newColour = new Color(oldColour.getRed(), oldColour.getGreen(), value, oldColour.getAlpha());

                    this.colourBox.setColour(newColour);
                    break;
                default:
                    return;
            }

            changes.firePropertyChange("colour", oldColour, newColour);
        } catch (Exception e) {
            field.setText((String) event.getOldValue());
        }
    }

    /**
     *
     * */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changes.addPropertyChangeListener(listener);
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
     */
    @Override
    public Insets getInsets() {
        return insets;
    }

    /**
     *
     */
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