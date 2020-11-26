package ui.tool;

import drawing.shape.Ellipses;
import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;
import ui.input.CoordinateInput;
import ui.input.NumberFieldInput;
import ui.input.SliderInput;
import ui.input.TextFieldInput;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Point;

public class EllipsisToolWidget extends BaseToolWidget {
    public EllipsisToolWidget(Ellipses shape, JFrame frame) {
        super();

        var startPosition = new CoordinateInput("start", new Point(shape.getX(), shape.getY()), "X", "Y");
        this.tools.add(startPosition);

        int width = (int) ((Point) shape.getProperties().get("end").getValue()).getX();
        int height = (int) ((Point) shape.getProperties().get("end").getValue()).getY();

        var endPosition = new CoordinateInput("end", new Point(width, height), "W", "H");
        this.tools.add(endPosition);

        int rotationValue =  (Integer) shape.getProperties().get("rotation").getValue();
        var rotation = new NumberFieldInput("rotation", rotationValue, "rotation", true);
        this.tools.add(rotation);


        int thicknessValue = (Integer) shape.getProperties().get("thickness").getValue();
        var lineThickness = new SliderInput("thickness", thicknessValue, "thickness", 1, 16);
        this.tools.add(lineThickness);

        Color strokeValue = (Color) shape.getProperties().get("strokeColour").getValue();
        var strokeColour = new ColourPickerInput("strokeColour", strokeValue, "STROKE", frame);

        this.tools.add(strokeColour);

        Color fillValue = (Color) shape.getProperties().get("fillColour").getValue();
        var fillColour = new ColourPickerInput("fillColour", fillValue, "FILL", frame);

        this.tools.add(fillColour);

        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(startPosition.getComponent());
        this.panel.add(endPosition.getComponent());
        this.panel.add(rotation.getComponent());
        this.panel.add(WidgetFactory.createSeparator());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(lineThickness.getComponent());
        this.panel.add(strokeColour.getComponent());
        this.panel.add(fillColour.getComponent());

        // setup listeners for tools
        this.setupInputListeners();
    }
}
