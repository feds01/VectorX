package ui.tool;

import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;

import javax.swing.JFrame;
import java.awt.Color;

public class FillToolWidget extends BaseToolWidget {
    public FillToolWidget(JFrame frame) {
        super();

        var fillTool = new ColourPickerInput("fill", Color.WHITE, "FILL", frame);

        this.tools.add(fillTool);

        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(fillTool.getComponent());
    }
}
