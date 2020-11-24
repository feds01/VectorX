package ui.tool;

import drawing.shape.TextShape;
import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;
import ui.input.CoordinateInput;
import ui.input.SliderInput;
import ui.input.TextFieldInput;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Point;

public class TextToolWidget extends BaseToolWidget {
    public TextToolWidget(TextShape shape, JFrame frame) {
        super();

        var startPosition = new CoordinateInput("start", new Point(shape.getX(), shape.getY()), "X", "Y");
        this.tools.add(startPosition);

        // TODO: replace with endX and endY properties.
        var endPosition = new CoordinateInput("end", new Point(shape.getX(), shape.getY()), "W", "H");
        this.tools.add(endPosition);

        int rotationValue =  (Integer) shape.getProperties().get("rotation").getValue();
        var rotation = new TextFieldInput("rotation", String.valueOf(rotationValue), "rotation", true);
        this.tools.add(rotation);


        int fontSizeValue = (Integer) shape.getProperties().get("fontSize").getValue();

        var fontSize = new SliderInput("fontSize", fontSizeValue, "font size", 4, 20);

        this.tools.add(fontSize);

        Color strokeValue = (Color) shape.getProperties().get("strokeColour").getValue();
        var strokeColour = new ColourPickerInput("strokeColour", strokeValue, "STROKE", frame);

        this.tools.add(strokeColour);

        Color fillValue = (Color) shape.getProperties().get("fillColour").getValue();
        var fillColour = new ColourPickerInput("fillColour", fillValue, "FILL", frame);

        this.tools.add(strokeColour);

        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(startPosition.getComponent());
        this.panel.add(endPosition.getComponent());
        this.panel.add(rotation.getComponent());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(fontSize.getComponent());
        this.panel.add(strokeColour.getComponent());
        this.panel.add(fillColour.getComponent());
    }
}
