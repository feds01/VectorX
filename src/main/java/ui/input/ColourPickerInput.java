package ui.input;

import ui.input.colour.ColourPicker;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

/**
 *
 */
public class ColourPickerInput extends BaseInput<Color> {

    /**
     *
     */
    private final ColourPicker picker;

    /**
     *
     */
    public ColourPickerInput(String name, Color value, String labelText, JFrame frame) {
        super(name, value);

        // create the picker component
        this.picker = new ColourPicker(value, frame);

        this.picker.addPropertyChangeListener(e -> {
            var colourPicker = (ColourPicker) e.getSource();

            var oldValue = this.value;
            this.value = colourPicker.getColour();

            changes.firePropertyChange(name, oldValue, value);
        });

        // create the label for the picker component
        JLabel label = new JLabel(labelText);
        label.setFont(fontLoader.getFont("NotoSans"));
        label.setForeground(Color.GRAY);

        // add components to the panel component
        this.panel.add(this.picker);
        this.panel.add(Box.createHorizontalStrut(5));
        this.panel.add(label);
    }

}