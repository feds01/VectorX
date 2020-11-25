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
 *
 * */
public class TextFieldInput extends BaseInput<String> {
    /**
     *
     * */
    private final JTextField field;

    /**
     *
     * */
    private JLabel label = null;

    private String oldValue;

    /**
     *
     * */
    public TextFieldInput(String name, String value, String label, boolean front) {
        super(name, value);

        this.oldValue = value;

        this.panel.setPreferredSize(new Dimension(100, 20));

        this.field = new JTextField(value, 2);

        this.field.setPreferredSize(new Dimension(40, 20));
        this.field.setMaximumSize(new Dimension(40, 20));

        this.field.setName(name);

        // slow the blinking rate to once a second
        this.field.getCaret().setBlinkRate(1000);

        this.field.addActionListener(e -> {
            for (PropertyChangeListener l : this.field.getPropertyChangeListeners()) {
                l.propertyChange(new PropertyChangeEvent(this.field, name, oldValue, this.field.getText()));
            }

            this.oldValue = this.field.getText();
        });

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
    public TextFieldInput(String name, String value) { this(name, value, "", false); }

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

    public void setValue(String value) {
        this.field.setText(value);
    }

    public String getValue() {
        return this.field.getText();
    }

    public JLabel getLabel() {
        return this.label;
    }

    public JTextField getField() {
        return this.field;
    }
}
