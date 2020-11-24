package ui.tool;

import drawing.shape.Shape;
import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FillToolWidget extends BaseToolWidget {
    public FillToolWidget(Shape shape, JFrame frame) {
        super();

        if (!shape.isFillable()) {
            throw new IllegalStateException("Can't fill an un-fillable shape.");
        }

        var fillTool = new ColourPickerInput("fill", shape.getShapeFill(), "FILL", frame);

        this.tools.add(fillTool);

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(fillTool.getComponent());
    }
}
