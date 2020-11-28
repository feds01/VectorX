package ui.tool;

import ui.common.WidgetFactory;
import ui.input.ColourPickerInput;

import javax.swing.JFrame;
import java.awt.Color;

/**
 * FillToolWidget widget class that is used to edit and display the properties
 * for the Fill tool.
 *
 * @author 200008575
 * */
public class FillToolWidget extends BaseToolWidget {

    /**
     * EmptyToolWidget constructor
     * */
    public FillToolWidget(JFrame frame) {
        super(null);

        var fillTool = new ColourPickerInput("fill", Color.WHITE, "FILL", frame);
        this.tools.add(fillTool);

        this.constructUI();

    }

    /**
     * Method to build the properties menu widget ui
     */
    public void constructUI() {
        this.panel.add(WidgetFactory.createTitleWidget("APPEARANCE"));
        this.panel.add(this.tools.get(0).getComponent());
    }
}
