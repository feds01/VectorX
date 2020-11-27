package ui.tool;

import drawing.shape.TextShape;
import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;
import ui.input.CoordinateInput;
import ui.input.NumberFieldInput;
import ui.input.SliderInput;
import ui.input.TextFieldInput;

import javax.swing.Box;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Point;

/**
 *
 */
public class TextToolWidget extends BaseToolWidget {
    public TextToolWidget(TextShape shape, JFrame frame) {
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


        int fontSizeValue = (Integer) shape.getPropertyMap().get("fontSize").getValue();
        var fontSize = new SliderInput("fontSize", fontSizeValue, "font size", 4, 20);
        this.tools.add(fontSize);

        Color strokeValue = (Color) shape.getPropertyMap().get("strokeColour").getValue();
        var strokeColour = new ColourPickerInput("strokeColour", strokeValue, "STROKE", frame);
        this.tools.add(strokeColour);

        Color fillValue = (Color) shape.getPropertyMap().get("fillColour").getValue();
        var fillColour = new ColourPickerInput("fillColour", fillValue, "FILL", frame);
        this.tools.add(fillColour);

        String textValue = (String) shape.getPropertyMap().get("value").getValue();
        var text = new TextFieldInput("value", textValue, "Content", true);

        this.tools.add(text);

        // setup listeners for tools
        this.setupInputListeners();
        this.constructUI();
    }

    /**
     *
     */
    public void constructUI() {
        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(this.tools.get(0).getComponent());
        this.panel.add(this.tools.get(1).getComponent());
        this.panel.add(this.tools.get(2).getComponent());
        this.panel.add(Box.createVerticalStrut(5));
        this.panel.add(WidgetFactory.createSeparator());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(this.tools.get(3).getComponent());
        this.panel.add(this.tools.get(4).getComponent());
        this.panel.add(this.tools.get(5).getComponent());
        this.panel.add(Box.createVerticalStrut(5));
        this.panel.add(WidgetFactory.createSeparator());

        this.panel.add(WidgetFactory.createTitleWidget("CONTENT"));
        this.panel.add(this.tools.get(6).getComponent());
    }
}
