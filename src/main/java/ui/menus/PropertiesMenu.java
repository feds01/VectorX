package ui.menus;

import common.FontLoader;
import drawing.shape.ShapeColour;
import drawing.tool.DrawingTool;
import ui.controllers.ToolController;
import ui.input.ColourPickerInput;
import ui.input.CoordinateInput;
import ui.input.SliderInput;
import ui.input.TextFieldInput;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 */
public class PropertiesMenu {

    /**
     *
     * */
    private final FontLoader fontLoader = FontLoader.getInstance();

    /**
     *
     */
    public final JPanel panel;

    /**
     *
     */
    public JLabel toolTitle;

    /**
     *
     */
    private ToolController controller;

    /**
     *
     */
    public PropertiesMenu(ToolController controller) {
        this.panel = new JPanel();

        this.setController(controller);

        panel.setCursor(Cursor.getDefaultCursor());

        // Add the left side border
        panel.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        panel.setBackground(Color.WHITE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.toolTitle = new JLabel(controller.getCurrentTool().getType().toString());
        this.toolTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.toolTitle.setFont(fontLoader.getFont("NotoSans"));

        // set maximum property width to 200
        panel.setPreferredSize(new Dimension(240, 100000));

        panel.add("Object Properties", this.toolTitle);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new TextFieldInput("Description", "").getComponent());
        panel.add(new CoordinateInput("0", "X", "0", "Y").getComponent());
        panel.add(new SliderInput("thickness", 1, 16, 2).getComponent());
        panel.add(new SliderInput("thickness", 1, 16, 2).getComponent());
        panel.add(new ColourPickerInput(new ShapeColour(0xddeeff, 0.7f)));
    }

    /**
     *
     */
    public void setController(ToolController controller) {
        this.controller = controller;
        this.controller.addPropertyChangeListener(new PropertiesMenuListener());
    }

    /**
     *
     */
    private class PropertiesMenuListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            var currentTool = (DrawingTool) evt.getNewValue();

            toolTitle.setText(currentTool.getType().toString());

            panel.revalidate();
            panel.repaint();
        }
    }
}
