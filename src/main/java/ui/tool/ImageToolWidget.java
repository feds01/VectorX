package ui.tool;

import drawing.shape.ImageShape;
import ui.common.WidgetFactory;
import ui.input.CheckBoxInput;
import ui.input.CoordinateInput;
import ui.input.NumberFieldInput;

import java.awt.Point;

public class ImageToolWidget extends BaseToolWidget {
    public ImageToolWidget(ImageShape shape) {
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

        var grayScaleValue = (Boolean) shape.getPropertyMap().get("grayScale").getValue();
        var grayScale = new CheckBoxInput("grayScale", grayScaleValue, "Grayscale");
        this.tools.add(grayScale);

        // setup listeners for tools
        this.setupInputListeners();
    }

    public void constructUI() {
        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(this.tools.get(0).getComponent());
        this.panel.add(this.tools.get(1).getComponent());
        this.panel.add(this.tools.get(2).getComponent());
        this.panel.add(WidgetFactory.createSeparator());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(this.tools.get(3).getComponent());
    }
}
