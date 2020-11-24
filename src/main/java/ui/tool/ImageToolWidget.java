package ui.tool;

import drawing.shape.ImageShape;
import ui.common.WidgetFactory;
import ui.input.CheckBoxInput;
import ui.input.CoordinateInput;
import ui.input.TextFieldInput;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Point;

public class ImageToolWidget extends BaseToolWidget {
    private ImageShape shape;

    public ImageToolWidget(ImageShape shape, JFrame frame) {
        super();

        var startPosition = new CoordinateInput("start", new Point(shape.getX(), shape.getY()), "X", "Y");
        this.tools.add(startPosition);

        // TODO: replace with endX and endY properties.
        var endPosition = new CoordinateInput("end", new Point(shape.getX(), shape.getY()), "W", "H");
        this.tools.add(endPosition);

        int rotationValue =  (Integer) shape.getProperties().get("rotation").getValue();
        var rotation = new TextFieldInput("rotation", String.valueOf(rotationValue), "rotation", true);
        this.tools.add(rotation);

        var monochromeValue = (Boolean) shape.getProperties().get("monochrome").getValue();
        var monochrome = new CheckBoxInput("monochrome", monochromeValue, "monochrome");


        this.panel.add(WidgetFactory.createTitleWidget("TRANSFORM"));
        this.panel.add(startPosition.getComponent());
        this.panel.add(endPosition.getComponent());
        this.panel.add(rotation.getComponent());

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(monochrome.getComponent());
    }
}
