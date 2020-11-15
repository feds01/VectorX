package ui.menus;

import drawing.tool.DrawingTool;
import ui.controllers.ToolController;

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


        // Add the left side border
        propertyBar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        // set maximum property width to 200
        propertyBar.setPreferredSize(new Dimension(200, 100000));


        var panel = new JPanel();

        this.toolTitle = new JLabel(controller.getCurrentTool().getType().toString());

        panel.add(new JTextArea("Properties"));
        panel.add(this.toolTitle);

        propertyBar.setCursor(Cursor.getDefaultCursor());

        propertyBar.add(panel);
        propertyBar.add(Box.createVerticalGlue());

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


    public JToolBar createMenu() {
        JToolBar propertyBar = new JToolBar(SwingConstants.VERTICAL);

        // prevent it from being floated
        propertyBar.setFloatable(false);


        // add a border to the toolbar
        propertyBar.setBorderPainted(true);

//        var margin = new EmptyBorder(new Insets(0, 5, 0, 5));
//        var lineBorder = new LineBorder(Color.BLACK);

        propertyBar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        var panel = new JPanel();

        panel.add(new JTextArea("Properties"));
        panel.add(new JTextArea(controller.getCurrentTool().getType().toString()));

        propertyBar.setCursor(Cursor.getDefaultCursor());

        propertyBar.add(panel);
        propertyBar.add(Box.createVerticalGlue());

        return propertyBar;
    }
}
