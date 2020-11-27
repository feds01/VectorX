package ui.tool;

import drawing.shape.Line;
import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;
import ui.input.CoordinateInput;
import ui.input.NumberFieldInput;
import ui.input.SliderInput;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Point;

/**
 *
 */
public class LineToolWidget extends BaseToolWidget {
    /**
     *
     */
    public LineToolWidget(Line shape, JFrame frame) {
        super(shape);

        var startPosition = new CoordinateInput("start", new Point(shape.getX(), shape.getY()), "X", "Y");
        this.tools.add(startPosition);

        int width = (int) ((Point) shape.getPropertyMap().get("end").getValue()).getX();
        int height = (int) ((Point) shape.getPropertyMap().get("end").getValue()).getY();

        var endPosition = new CoordinateInput("end", new Point(width, height), "W", "H");
        this.tools.add(endPosition);

        int rotationValue = (Integer) shape.getPropertyMap().get("rotation").getValue();
        var rotation = new NumberFieldInput("rotation", rotationValue, "rotation", true);
        this.tools.add(rotation);


        int thicknessValue = (Integer) shape.getPropertyMap().get("thickness").getValue();

        var lineThickness = new SliderInput("thickness", thicknessValue, "thickness", 1, 16);

        this.tools.add(lineThickness);

        Color strokeValue = (Color) shape.getPropertyMap().get("strokeColour").getValue();
        var strokeColour = new ColourPickerInput("strokeColour", strokeValue, "STROKE", frame);

        this.tools.add(strokeColour);

        // setup listeners for tools
        this.setupInputListeners();
    }

    /**
     *
     */
    @Override
    protected void constructUI() {
        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(this.tools.get(0).getComponent());
        this.panel.add(this.tools.get(1).getComponent());
        this.panel.add(this.tools.get(2).getComponent());
        this.panel.add(WidgetFactory.createSeparator());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(this.tools.get(3).getComponent());
        this.panel.add(this.tools.get(4).getComponent());

    }
}
