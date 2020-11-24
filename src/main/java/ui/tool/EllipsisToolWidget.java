package ui.tool;

import drawing.shape.Ellipses;
import ui.input.ColourPickerInput;
import ui.input.CoordinateInput;
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

        // TODO: replace with endX and endY properties.
        var endPosition = new CoordinateInput("end", new Point(shape.getX(), shape.getY()), "EndX", "EndY");
        this.tools.add(endPosition);

        int rotationValue =  (Integer) shape.getProperties().get("rotation").getValue();
        var rotation = new TextFieldInput("rotation", String.valueOf(rotationValue));
        this.tools.add(rotation);


        int thicknessValue = (Integer) shape.getProperties().get("thickness").getValue();
        var lineThickness = new SliderInput("thickness", thicknessValue, "thickness", 1, 16);
        this.tools.add(lineThickness);

        Color strokeValue = (Color) shape.getProperties().get("strokeColour").getValue();
        var strokeColour = new ColourPickerInput("strokeColour", strokeValue, "STROKE", frame);

        this.tools.add(strokeColour);

        Color fillValue = (Color) shape.getProperties().get("fillColour").getValue();
        var fillColour = new ColourPickerInput("fillColour", fillValue, "FILL", frame);

        this.tools.add(strokeColour);

        this.panel.add(startPosition.getComponent());
        this.panel.add(endPosition.getComponent());
        this.panel.add(rotation.getComponent());
        this.panel.add(lineThickness.getComponent());
        this.panel.add(strokeColour.getComponent());
        this.panel.add(fillColour.getComponent());
    }
}
