package ui.input;

import common.FontLoader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class TextFieldInput {

    private final FontLoader fontLoader = FontLoader.getInstance();

    private final JPanel panel;

    private final JTextField field;

    private String previousValue = "";

    private JLabel label = null;

    public TextFieldInput(String value, String label, boolean front) {
        this.panel = new JPanel();

        var layout = new FlowLayout(FlowLayout.LEADING);

        this.panel.setLayout(layout);
        this.panel.setBackground(Color.WHITE);


        this.panel.setPreferredSize(new Dimension(20, 20));
        this.panel.setMaximumSize(new Dimension(240, 20));


        this.field = new JTextField(value, 2);

        // slow the blinking rate to once a second
        this.field.getCaret().setBlinkRate(1000);


        // set custom font
        this.field.setFont(fontLoader.getFont("NotoSans"));

        // Add the text field border
        this.field.setBackground(Color.LIGHT_GRAY);
        this.field.setBorder(new MatteBorder(new Insets(0, 0, 1, 0), Color.GRAY));

        // Create the label and set the background of the label to white

        if (!label.equals("")) {
            this.label = new JLabel(label);

            this.label.setFont(fontLoader.getFont("NotoSans"));
            this.label.setBackground(Color.WHITE);
            this.label.setOpaque(true);

            var gap = Box.createHorizontalStrut(5);

            // Add the label at the front instead of the back
            if (front) {
                this.panel.add(this.label);
                this.panel.add(gap);
                this.panel.add(this.field);
            } else {
                this.panel.add(this.field);
                this.panel.add(gap);
                this.panel.add(this.label);
            }
        } else {
            this.panel.add(field);
        }


        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
    }

    public TextFieldInput(String value) { this(value, "", false); }

    public TextFieldInput(String value, String label) {
        this(value, label, false);
    }

    public void setValue(String value) {
        this.field.setText(value);
    }


    public JPanel getComponent() {
        return this.panel;
    }

    public JLabel getLabel() {
        return this.label;
    }

    public JTextField getField() {
        return this.field;
    }

    /**
     *
     */
    public void addChangeListener(ActionListener textFieldChangeListener) {
        Objects.requireNonNull(this.field);

        this.field.addActionListener(textFieldChangeListener);
    }
}
