package ui.input;


import common.FontLoader;
import ui.input.colour.ColourPicker;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class ColourPickerInput {
    private final FontLoader fontLoader = FontLoader.getInstance();

    private final JPanel panel;

    private final ColourPicker picker;

    public ColourPickerInput(Color value, String labelText, JFrame frame) {
        this.panel = new JPanel();

        this.panel.setBackground(Color.WHITE);
        this.panel.setFont(fontLoader.getFont("NotoSans"));

        this.panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.panel.setPreferredSize(new Dimension(120, 28));
        this.panel.setMaximumSize(new Dimension(240, 28));

        // create the picker component
        this.picker = new ColourPicker(value, frame);

        // create the label for the picker component
        JLabel label = new JLabel(labelText);
        label.setFont(fontLoader.getFont("NotoSans"));

        // add components to the panel component
        this.panel.add(this.picker);

        this.panel.add(label);
    }

    public JPanel getComponent() {
        return this.panel;
    }
}