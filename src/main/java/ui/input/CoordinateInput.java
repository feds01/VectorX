package ui.input;

import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.Point;
import java.beans.PropertyChangeEvent;

public class CoordinateInput extends BaseInput<Point> {


    private final TextFieldInput xField;

    private final TextFieldInput yField;

    public CoordinateInput(String name, Point point, String xLabel, String yLabel) {
        super(name, point);

        this.xField = new TextFieldInput("x", String.valueOf(point.x), xLabel, true);
        this.xField.addPropertyChangeListener(this::valueChangeListener);


        this.yField = new TextFieldInput("y", String.valueOf(point.y), yLabel, true);
        this.yField.addPropertyChangeListener(this::valueChangeListener);


        this.panel.add(xField.getComponent());
        this.panel.add(Box.createHorizontalGlue());
        this.panel.add(yField.getComponent());
    }

    private void valueChangeListener(PropertyChangeEvent event) {
        JTextField input = (JTextField) event.getSource();

        Point oldPoint = this.value;

        if (input.getName().equals("x")) {
            this.value.x = Integer.parseInt(input.getText());
        } else {
            this.value.y = Integer.parseInt(input.getText());
        }

        changes.firePropertyChange(name, oldPoint, this.value);
    }

    public TextFieldInput getXField() {
        return xField;
    }

    public TextFieldInput getYField() {
        return yField;
    }
}
