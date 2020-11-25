package ui.widget;

import ui.controllers.ToolController;
import ui.tool.BaseToolWidget;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

//    private final FontLoader fontLoader = FontLoader.getInstance();

    /**
     *
     */
    public final JPanel panel;

    /**
     *
     */
    private BaseToolWidget toolWidget;


//    public JLabel toolTitle;

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
                new MatteBorder(0, 1, 0, 0, Color.GRAY),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        panel.setBackground(Color.WHITE);

        // set maximum property width to 240
        panel.setPreferredSize(new Dimension(240, 100000));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);


//        this.toolTitle = new JLabel(controller.getCurrentTool().getType().toString());
//        this.toolTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
//        this.toolTitle.setFont(fontLoader.getFont("NotoSans"));
//
        this.toolWidget = controller.getCurrentToolWidget();

//        panel.add("Object Properties", this.toolTitle);
//        panel.add(Box.createVerticalStrut(10));
        panel.add(this.toolWidget.getComponent());
    }

    /**
     *
     */
    public void setController(ToolController controller) {
        this.controller = controller;
        this.controller.addPropertyChangeListener(new PropertiesMenuListener());
    }

    public ToolController getController() {
        return this.controller;
    }

    /**
     *
     */
    private class PropertiesMenuListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

//            if (evt.getPropertyName().equals("titleChange")) {
//                var currentTool = (DrawingTool) evt.getNewValue();
//
//                toolTitle.setText(currentTool.getType().toString());
//            }

            if (evt.getPropertyName().equals("widgetChange")) {

                // remove the old component from the property menu
                panel.removeAll();

                toolWidget = (BaseToolWidget) evt.getNewValue();

//                panel.add(toolTitle);
                panel.add(toolWidget.getComponent());
            }


            panel.revalidate();
            panel.repaint();
        }
    }
}
