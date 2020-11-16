package ui.input;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class TextField {
    private final JPanel panel;

    private final JTextField field;

    private final JLabel label;

    public TextField(String value, String label, boolean front) {
        this.panel = new JPanel();

        this.field = new JTextField(value,2);

        // Add the text field border
        this.field.setBorder(new MatteBorder(new Insets(0, 0, 1, 0), Color.GRAY));

        this.label = new JLabel(label);


        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));


        // Add the label at the front instead of the back
        if (front) {
            this.panel.add(this.label);
            this.panel.add(this.field);
        } else {
            this.panel.add(this.field);
            this.panel.add(this.label);
        }
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
}
