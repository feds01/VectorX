package ui.input;

import common.FontLoader;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class SliderInput {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private final FontLoader fontLoader = FontLoader.getInstance();

    /**
     *
     */
    private final JPanel panel;

    /**
     *
     */
    private final JSlider slider;

    /**
     *
     */
    private final TextFieldInput field;

    /**
     *
     */
    private final JLabel label;

    /**
     *
     */
    public SliderInput(String label, int min, int max, int value) {
        if (min >= max) {
            throw new IllegalArgumentException("Minimum value cannot be larger or equal to maximum value.");
        }

        if (value < min || value > max) {
            throw new IllegalArgumentException("Value out of range of minimum and maximum.");
        }


        this.panel = new JPanel();

        this.panel.setFont(fontLoader.getFont("NotoSans"));

        // Set the BoxLayout to be X_AXIS: from left to right
        GridBagLayout layout = new GridBagLayout();

        GridBagConstraints gbc = new GridBagConstraints();

        // Set the Boxayout to be Y_AXIS from top to down
        //BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(layout);
        panel.setBackground(new Color(0xFFFFFF));


        // Add slider label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        this.label = new JLabel(label);

        this.panel.add(this.label, gbc);

        // Create the slider
        this.slider = new JSlider(JSlider.HORIZONTAL, min, max, value) {
            @Override
            public void updateUI() {
                setUI(new SliderUI(this));
            }
        };

        this.slider.setBorder(new EmptyBorder(new Insets(5, 0, 5, 0)));
        this.slider.setBackground(Color.WHITE);


        // Add slider label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;


        // Add the slider to the general component
        this.panel.add(this.slider, gbc);

        // Add a text field and a unit label to ship with the slider
        // Add slider label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.gridy = 0;

        this.field = new TextFieldInput(String.valueOf(value), "px", false);

        // Add right hand-side border to separate the slider and the text field.
        this.field.getComponent().setBorder(new MatteBorder(new Insets(0, 20, 0, 0), Color.white));

        this.panel.add(field.getComponent(), gbc);
        this.panel.setMaximumSize(new Dimension(240, 20));


        // Add change listener to the slider and the text field
        this.slider.addChangeListener(this::sliderChangeListener);

        this.field.addChangeListener(this::textFieldChangeListener);
//        this.field
    }

    /**
     *
     */
    public JPanel getComponent() {
        return this.panel;
    }

    /**
     *
     */
    public int getValue() {
        return this.slider.getValue();
    }


    /**
     *
     */
    public void setValue(int value) {
        this.slider.setValue(value);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    private void sliderChangeListener(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();

        // Graceful value retrieving. Get the value of the slider
        // when the user stops dragging the slider.
        if (!slider.getValueIsAdjusting()) {
            int sliderValue = slider.getValue();
            int textValue = Integer.parseInt(this.field.getValue());

            // set the text field input value to the slider value
            changes.firePropertyChange("slider", textValue, sliderValue);
            this.field.setValue(String.valueOf(sliderValue));

        }
    }

    private void textFieldChangeListener(ActionEvent event) {
        JTextField field = (JTextField) event.getSource();

        // if the text is equal to an empty string, don't change the value since we
        // don't know if the user is still editing the value
        try {
            int textValue = Integer.parseInt(field.getText());

            // TODO: use caller specified validation method to check
            //       whether the value is valid or not.

            // set the text field input value to the slider value
            changes.firePropertyChange("slider", this.slider.getValue(), textValue);
            this.slider.setValue(textValue);

        } catch (NumberFormatException e) {
            field.setText(String.valueOf(this.slider.getValue()));
        }
    }

    public void disableLabel() {
        this.label.setVisible(false);
    }

    public void disableTextBox() {
        this.panel.remove(this.field.getComponent());
    }
}


/**
 *
 */
class SliderUI extends BasicSliderUI {

    private static final Color sliderColour = new Color(0, 0, 36);

    private static final int TRACK_HEIGHT = 4;
    private static final int TRACK_WIDTH = 8;
    private static final int TRACK_ARC = 5;
    private static final Dimension THUMB_SIZE = new Dimension(12, 12);
    private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

    public SliderUI(final JSlider b) {
        super(b);
    }

    @Override
    protected void calculateTrackRect() {
        super.calculateTrackRect();
        if (isHorizontal()) {
            trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
            trackRect.height = TRACK_HEIGHT;
        } else {
            trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
            trackRect.width = TRACK_WIDTH;
        }
        trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
    }

    @Override
    protected void calculateThumbLocation() {
        super.calculateThumbLocation();
        if (isHorizontal()) {
            thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
        } else {
            thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
        }
    }

    @Override
    protected Dimension getThumbSize() {
        return THUMB_SIZE;
    }

    private boolean isHorizontal() {
        return slider.getOrientation() == JSlider.HORIZONTAL;
    }

    @Override
    public void paint(final Graphics g, final JComponent c) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintTrack(final Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Shape clip = g2.getClip();

        boolean horizontal = isHorizontal();
        boolean inverted = slider.getInverted();

        // Paint shadow.
        g2.setColor(new Color(170, 170, 170));
        g2.fill(trackShape);

        // Paint track background.
        g2.setColor(new Color(200, 200, 200));
        g2.setClip(trackShape);
        trackShape.y += 1;
        g2.fill(trackShape);
        trackShape.y = trackRect.y;

        g2.setClip(clip);

        // Paint selected track.
        if (horizontal) {
            boolean ltr = slider.getComponentOrientation().isLeftToRight();
            if (ltr) inverted = !inverted;
            int thumbPos = thumbRect.x + thumbRect.width / 2;
            if (inverted) {
                g2.clipRect(0, 0, thumbPos, slider.getHeight());
            } else {
                g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
            }

        } else {
            int thumbPos = thumbRect.y + thumbRect.height / 2;
            if (inverted) {
                g2.clipRect(0, 0, slider.getHeight(), thumbPos);
            } else {
                g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
            }
        }
        g2.setColor(sliderColour);
        g2.fill(trackShape);
        g2.setClip(clip);
    }

    @Override
    public void paintThumb(final Graphics g) {
        g.setColor(sliderColour);
        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    @Override
    public void paintFocus(final Graphics g) {
    }
}
