package ui.menus;

import drawing.tool.DrawingTool;
import ui.controllers.ToolController;
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
     */
    public final JPanel propertyBar;

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
        this.propertyBar = new JPanel();

        this.setController(controller);

        propertyBar.setCursor(Cursor.getDefaultCursor());

        // Add the left side border
        propertyBar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        propertyBar.setBackground(Color.WHITE);

        propertyBar.setLayout(new BoxLayout(propertyBar, BoxLayout.Y_AXIS));
        propertyBar.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.toolTitle = new JLabel(controller.getCurrentTool().getType().toString());
        this.toolTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // set maximum property width to 200
        propertyBar.setPreferredSize(new Dimension(240, 100000));

        propertyBar.add("Object Properties", this.toolTitle);
        propertyBar.add(Box.createVerticalStrut(10));

        propertyBar.add(new TextFieldInput("Description", "").getComponent());
        propertyBar.add(new CoordinateInput("0", "X", "0", "Y").getComponent());
        propertyBar.add(new SliderInput("thickness", 1, 16, 2).getComponent());
        propertyBar.add(new SliderInput("thickness", 1, 16, 2).getComponent());
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

            propertyBar.revalidate();
            propertyBar.repaint();
        }
    }
}
