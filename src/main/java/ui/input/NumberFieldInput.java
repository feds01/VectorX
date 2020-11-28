package ui.input;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * NumberFieldInput class that is used to render a text field input which
 * is derived from the BaseInput class.
 *
 * @author 200008575
 * */
public class NumberFieldInput extends BaseInput<Integer> {
    /**
     *
     * */
    private final JTextField field;

    /**
     *
     * */
    private JLabel label;

    /**
     *
     */
    private Integer oldValue;

    /**
     *
     * */
    public NumberFieldInput(String name, Integer value, String label, boolean front) {
        super(name, value);

        this.oldValue = value;

        this.panel.setPreferredSize(new Dimension(100, 20));

        this.field = new JTextField(String.valueOf(value), 2);

        this.field.setPreferredSize(new Dimension(40, 20));
        this.field.setMaximumSize(new Dimension(40, 20));

        this.field.setName(name);

        // slow the blinking rate to once a second
        this.field.getCaret().setBlinkRate(1000);
        this.field.addActionListener(e -> this.getComponent().requestFocus());

        this.field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                var field = (JTextField) e.getSource();

                field.setBorder(new MatteBorder(new Insets(0, 0, 1, 0), new Color(0x3399FF)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                var field = (JTextField) e.getSource();

                field.setBorder(new MatteBorder(new Insets(0, 0, 1, 0), Color.GRAY));

                try {
                    int value = Integer.parseInt(field.getText());

                    // ensure that the value is not a negative value
                    if (value < 0) {
                        throw new IllegalStateException("Cannot have negative number");
                    }

                    for (PropertyChangeListener l : field.getPropertyChangeListeners()) {
                        l.propertyChange(new PropertyChangeEvent(field, name, oldValue, field.getText()));
                    }

                    oldValue = Integer.valueOf(field.getText());
                } catch (IllegalStateException | NumberFormatException ignored) {
                    field.setText(String.valueOf(oldValue));
                }
            }
        });


        // set custom font
        this.field.setFont(fontLoader.getFont("NotoSans"));

        // Add the text field border
        this.field.setBorder(new MatteBorder(new Insets(0, 0, 1, 0), Color.GRAY));

        // Create the label and set the background of the label to white
        if (!label.equals("")) {
            this.label = new JLabel(label);

            this.label.setFont(fontLoader.getFont("NotoSans"));
            this.label.setForeground(Color.GRAY);
            this.label.setBackground(Color.WHITE);
            this.label.setOpaque(true);

            // Add the label at the front instead of the back
            if (front) {
                this.panel.add(this.label);
                this.panel.add(Box.createHorizontalStrut(5));
                this.panel.add(this.field);
                this.panel.add(Box.createHorizontalGlue());
            } else {
                this.panel.add(this.field);
                this.panel.add(Box.createHorizontalStrut(5));
                this.panel.add(this.label);
                this.panel.add(Box.createHorizontalGlue());
            }
        } else {
            this.panel.add(field);
        }

        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
    }

    /**
     *
     * */
    public NumberFieldInput(String name, Integer value) { this(name, value, "", false); }

    /**
     *
     * */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.field.addPropertyChangeListener(listener);
    }

    /**
     *
     * */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.field.removePropertyChangeListener(listener);
    }

    /**
     *
     */
    public void setValue(String value) {
        this.field.setText(value);
    }

    /**
     *
     */
    public Integer getValue() {
        return Integer.parseInt(this.field.getText());
    }

    /**
     *
     */
    public JLabel getLabel() {
        return this.label;
    }

    /**
     *
     */
    public JTextField getField() {
        return this.field;
    }
}
