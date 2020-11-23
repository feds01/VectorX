package ui.input;

import common.FontLoader;

import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class CoordinateInput {

    private final FontLoader fontLoader = FontLoader.getInstance();

    private final JPanel panel;

    private final TextFieldInput xField;

    private final TextFieldInput yField;

    public CoordinateInput(String xValue, String xLabel, String yValue, String yLabel) {
        this.panel = new JPanel();

        this.panel.setBackground(Color.WHITE);
        this.panel.setFont(fontLoader.getFont("NotoSans"));

        this.panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.panel.setPreferredSize(new Dimension(120, 20));
        this.panel.setMaximumSize(new Dimension(240, 20));

        this.xField = new TextFieldInput(xValue, xLabel, true);
        this.yField = new TextFieldInput(yValue, yLabel, true);


        this.panel.add(xField.getComponent());
        this.panel.add(Box.createHorizontalGlue());
        this.panel.add(yField.getComponent());
    }

    public JPanel getComponent() {
        return this.panel;
    }

    public TextFieldInput getXField() {
        return xField;
    }

    public TextFieldInput getYField() {
        return yField;
    }
}
