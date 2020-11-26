package ui.tool;

import drawing.shape.ImageShape;
import ui.common.WidgetFactory;
import ui.input.CheckBoxInput;
import ui.input.CoordinateInput;
import ui.input.NumberFieldInput;
import ui.input.TextFieldInput;

import javax.swing.JFrame;
import java.awt.Point;

public class ImageToolWidget extends BaseToolWidget {
    private ImageShape shape;

    public ImageToolWidget(ImageShape shape, JFrame frame) {
        super();

        var startPosition = new CoordinateInput("start", new Point(shape.getX(), shape.getY()), "X", "Y");
        this.tools.add(startPosition);

        int width = (int) ((Point) shape.getProperties().get("end").getValue()).getX();
        int height = (int) ((Point) shape.getProperties().get("end").getValue()).getY();

        var endPosition = new CoordinateInput("end", new Point(width, height), "W", "H");
        this.tools.add(endPosition);

        int rotationValue = (Integer) shape.getProperties().get("rotation").getValue();
        var rotation = new NumberFieldInput("rotation", rotationValue, "rotation", true);
        this.tools.add(rotation);

        var monochromeValue = (Boolean) shape.getProperties().get("grayScale").getValue();
        var monochrome = new CheckBoxInput("grayScale", monochromeValue, "Grayscale");


        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(startPosition.getComponent());
        this.panel.add(endPosition.getComponent());
        this.panel.add(rotation.getComponent());
        this.panel.add(WidgetFactory.createSeparator());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(monochrome.getComponent());

        // setup listeners for tools
        this.setupInputListeners();
    }
}
