package ui.input;

import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * CoordinateInput class that is used to render a text field input which
 * is derived from the BaseInput class.
 *
 * @author 200008575
 * */
public class CoordinateInput extends BaseInput<Point> {

    /**
     *
     */
    private final NumberFieldInput xField;

    /**
     *
     */
    private final NumberFieldInput yField;

    /**
     *
     */
    public CoordinateInput(String name, Point point, String xLabel, String yLabel) {
        super(name, point);

        this.xField = new NumberFieldInput("x", point.x, xLabel, true);

        this.xField.getComponent().setMaximumSize(new Dimension(60, 20));
        this.xField.getComponent().setPreferredSize(new Dimension(60, 20));
        this.xField.getLabel().setPreferredSize(new Dimension(20, 20));
        this.xField.getLabel().setMaximumSize(new Dimension(20, 20));

        this.xField.addPropertyChangeListener(this::valueChangeListener);


        this.yField = new NumberFieldInput("y", point.y, yLabel, true);
        this.yField.getComponent().setMaximumSize(new Dimension(60, 20));
        this.yField.getComponent().setPreferredSize(new Dimension(60, 20));
        this.yField.getLabel().setPreferredSize(new Dimension(20, 20));
        this.yField.getLabel().setMaximumSize(new Dimension(20, 20));

        this.yField.addPropertyChangeListener(this::valueChangeListener);


        this.panel.add(xField.getComponent());
        this.panel.add(Box.createHorizontalGlue());
        this.panel.add(yField.getComponent());
    }

    /**
     *
     */
    private void valueChangeListener(PropertyChangeEvent event) {
        JTextField input = (JTextField) event.getSource();

        Point oldPoint = this.value;

        try {
            if (input.getName().equals("x")) {
                this.value.x = Integer.parseInt(input.getText());
            } else {
                this.value.y = Integer.parseInt(input.getText());
            }

            // check if any of the values are negative, we don't allow negatives in the
            // CoordinateInput
            if (this.value.x < 0 || this.value.y < 0) {
                throw new IllegalStateException("Invalid coordinates");
            }

            // @Workaround: Weird bug where if multiple listeners are registered within lambdas,
            // events aren't being detected.
            for (PropertyChangeListener l : this.changes.getPropertyChangeListeners()) {
                l.propertyChange(new PropertyChangeEvent(this, name, oldPoint, this.value));
            }
        } catch (NumberFormatException | IllegalStateException e) {
            this.value = oldPoint;

            // reset the old value
            input.setText(String.valueOf(event.getOldValue()));
        }
    }

    /**
     *
     */
    @Override
    public void setValue(Object value) {
        Point p = (Point) value;

        this.xField.setValue(String.valueOf(p.x));
        this.yField.setValue(String.valueOf(p.y));
    }

    /**
     *
     */
    public NumberFieldInput getXField() {
        return xField;
    }

    /**
     *
     */
    public NumberFieldInput getYField() {
        return yField;
    }
}
