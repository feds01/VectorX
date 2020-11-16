package ui.menus;

import drawing.tool.DrawingTool;
import ui.controllers.ToolController;
import ui.input.Slider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 */
public class PropertiesMenu {

    /**
     *
     */
    public final JToolBar propertyBar;

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
        this.propertyBar = new JToolBar(SwingConstants.VERTICAL);

        this.setController(controller);

        // prevent it from being floated
        propertyBar.setFloatable(false);

        // add a border to the toolbar
        propertyBar.setBorderPainted(true);

        propertyBar.setCursor(Cursor.getDefaultCursor());

        propertyBar.setLayout(new BoxLayout(propertyBar, BoxLayout.Y_AXIS));
        propertyBar.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Add the left side border
        propertyBar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        // set maximum property width to 200
        propertyBar.setPreferredSize(new Dimension(240, 100000));

        this.toolTitle = new JLabel(controller.getCurrentTool().getType().toString());

        propertyBar.add(this.toolTitle);
        propertyBar.add(new Slider("thickness", 1, 16, 2).getComponent());
        propertyBar.add(new Slider("thickness", 1, 16, 2).getComponent());
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
