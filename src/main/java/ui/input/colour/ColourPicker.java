package ui.input.colour;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 *
 */
public class ColourPicker extends JToggleButton {
    /**
     *
     */
    private Popup pickerPopup = null;

    /**
     *
     */
    private final ColourPickerPopup picker;

    /**
     *
     */
    private Color colour;

    /**
     *
     */
    private final JFrame frame;

    /**
     *
     * */
    AWTEventListener listener = new AWTEventListener() {
        @Override
        public void eventDispatched(AWTEvent event) {
            if (event instanceof MouseEvent) {
                MouseEvent m = (MouseEvent) event;

                if (SwingUtilities.isDescendingFrom((Component) event.getSource(), frame)) {
                    if (m.getID() == MouseEvent.MOUSE_CLICKED || m.getID() == MouseEvent.MOUSE_PRESSED) {
                        var point = m.getLocationOnScreen();

                        var bounds = picker.getBounds();
                        var location = picker.getLocationOnScreen();

                        // prevent any event that propagates outside of the picker bounds
                        if (!(point.getX() >= location.getX() && point.getY() >= location.getY() &&
                                point.getX() <= location.getX() + bounds.width &&
                                point.getY() <= location.getY() + bounds.height)) {

                            m.consume();
                        }


                        if (!picker.contains(m.getPoint())) {
                            pickerPopup.hide();

                            // @Workaround: sometimes components aren't being re-painted after it closes
                            frame.repaint();

                            // Remove the listener since we're done with the popup
                            Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
                        }
                    }
                }
            }
        }
    };


    /**
     *
     */
    public ColourPicker(Color initialColour, JFrame frame) {
        super();

        this.colour = initialColour;
        this.frame = frame;

        // Create the picker.
        this.picker = new ColourPickerPopup(this.colour);

        this.setBackground(this.colour);
        this.setBorderPainted(false);


        this.setPreferredSize(new Dimension(20, 20));
        this.setMaximumSize(new Dimension(20, 20));

        // create the popup that will be used to display the colour picker.
        this.addActionListener((ActionEvent e) -> {
            this.pickerPopup = this.createColourPickerPopup();
            this.pickerPopup.show();

            Toolkit.getDefaultToolkit().addAWTEventListener(listener, ActionEvent.MOUSE_EVENT_MASK);
        });

        this.picker.addPropertyChangeListener(evt -> {
            colour = (Color) evt.getNewValue();

            this.setBackground(colour);
            this.repaint();
        });
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

        Shape s = new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 6, 6);

        g2.fill(s);

        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);

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
