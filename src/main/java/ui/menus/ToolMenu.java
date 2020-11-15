package ui.menus;

import common.ImageUtils;
import drawing.ToolType;
import drawing.tool.DrawingTool;
import drawing.tool.FillTool;
import drawing.tool.GenericTool;
import ui.controllers.ToolController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 */
public class ToolMenu {

    // the selector tool is considered to be the default tool
    private static GenericTool selectorTool = new GenericTool(ToolType.SELECTOR, new Cursor(Cursor.DEFAULT_CURSOR), "/icons/selector");

    /**
     *
     */
    private final JFrame frame;

    /**
     *
     */
    private final ToolController controller;

    public ToolMenu(JFrame frame, ToolController controller) {
        this.frame = frame;
        this.controller = controller;

        this.controller.setCurrentTool(selectorTool);
    }

    private JButton setupAction(DrawingTool tool) {
        return this.createButtonFromAction(new AbstractAction(tool.getType().toString(), tool.getImageIcon(false)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cursor cursor = tool.getCursor();
                frame.setCursor(cursor);

                controller.setCurrentTool(tool);

                // do something.
            }
        }, tool);
    }

    /**
     *
     */
    private JButton createButtonFromAction(Action action, DrawingTool tool) {
        var button = new JButton(action);

        button.setText("");
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBackground(new Color(0xFFFFFF));

        button.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                AbstractButton btn = (AbstractButton) e.getSource();

                // prevent the change in icon if the cause of the event is activation
                if (e.getCause() != FocusEvent.Cause.ACTIVATION) {
                    var icon = tool.getImageIcon(false);

                    // resize the icon to 20x20
                    icon = ImageUtils.resizeIcon(icon, 20, 20);

                    btn.setIcon(icon);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                AbstractButton btn = (AbstractButton) e.getSource();

                var icon = tool.getImageIcon(true);

                // resize the icon to 20x20
                icon = ImageUtils.resizeIcon(icon, 20, 20);

                btn.setIcon(icon);
            }
        });

        return button;
    }

    /**
     *
     */
    public JToolBar createMenu() {
        var toolbar = new JToolBar(SwingConstants.VERTICAL);

        // prevent it from being floated
        toolbar.setFloatable(false);


        // add a border to the toolbar
        toolbar.setBorderPainted(true);

//        var margin = new EmptyBorder(new Insets(0, 5, 0, 5));
//        var lineBorder = new LineBorder(Color.BLACK);

        toolbar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 0, 1, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        var panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1, 0, 20));

        // Add the actions to the toolbars.
        panel.add(setupAction(selectorTool));
        panel.add(setupAction(new FillTool()));
        panel.add(setupAction(new GenericTool(ToolType.LINE, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/line")));
        panel.add(setupAction(new GenericTool(ToolType.RECTANGLE, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/rectangle")));
        panel.add(setupAction(new GenericTool(ToolType.ELLIPSIS, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/circle")));
        panel.add(setupAction(new GenericTool(ToolType.TRIANGLE, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/triangle")));
        panel.add(setupAction(new GenericTool(ToolType.IMAGE, new Cursor(Cursor.HAND_CURSOR), "/icons/image")));
        panel.add(setupAction(new GenericTool(ToolType.TEXT, new Cursor(Cursor.TEXT_CURSOR), "/icons/text")));


        panel.setMaximumSize(new Dimension(200, 300));
        panel.setBackground(new Color(0xFFFFFF));

        toolbar.setCursor(Cursor.getDefaultCursor());

        toolbar.setBackground(new Color(0xFFFFFF));

        toolbar.add(Box.createVerticalStrut(5));
        toolbar.add(panel);
        toolbar.add(Box.createVerticalGlue());

        return toolbar;
    }
}
