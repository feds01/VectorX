package ui.widget;

import ui.controllers.ToolController;
import ui.controllers.WidgetController;
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

    /**
     *
     * */
    private WidgetController widgetController;

    /**
     *
     */
    public PropertiesMenu(WidgetController widgetController) {
        this.panel = new JPanel();

        this.setWidgetController(widgetController);

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
        this.toolWidget = widgetController.getCurrentToolWidget();

//        panel.add("Object Properties", this.toolTitle);
//        panel.add(Box.createVerticalStrut(10));
        panel.add(this.toolWidget.getComponent());
    }


    public void setWidgetController(WidgetController controller) {
        this.widgetController = controller;
        this.widgetController.addPropertyChangeListener(new PropertiesMenuListener());
    }

    /**
     *
     */
    private class PropertiesMenuListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("widgetChange")) {

                // remove the old component from the property menu
                panel.removeAll();

                toolWidget = (BaseToolWidget) evt.getNewValue();

                panel.add(toolWidget.getComponent());
            }


            panel.revalidate();
            panel.repaint();
        }
    }
}
