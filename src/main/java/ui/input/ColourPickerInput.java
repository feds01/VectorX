package ui.input;

import ui.input.colour.ColourPicker;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

/**
 * ColourPickerInput class that is used to render a text field input which
 * is derived from the BaseInput class.
 *
 * @author 200008575
 * */
public class ColourPickerInput extends BaseInput<Color> {

    /**
     *
     */
    public ColourPickerInput(String name, Color value, String labelText, JFrame frame) {
        super(name, value);

        // create the picker component
        ColourPicker picker = new ColourPicker(value, frame);

        picker.addPropertyChangeListener(e -> {
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
        this.panel.add(picker);
        this.panel.add(Box.createHorizontalStrut(5));
        this.panel.add(label);
    }

}