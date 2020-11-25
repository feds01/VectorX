package ui.menus;

import common.ImageUtils;
import drawing.ToolType;
import drawing.shape.Ellipses;
import drawing.shape.ImageShape;
import drawing.shape.Line;
import drawing.shape.Rectangle;
import drawing.shape.TextShape;
import drawing.shape.Triangle;
import drawing.tool.DrawingTool;
import drawing.tool.FillTool;
import drawing.tool.GenericTool;
import ui.controllers.ToolController;
import ui.tool.EllipsisToolWidget;
import ui.tool.FillToolWidget;
import ui.tool.ImageToolWidget;
import ui.tool.LineToolWidget;
import ui.tool.RectangleToolWidget;
import ui.tool.TextToolWidget;
import ui.tool.TriangleToolWidget;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class ToolMenu extends JToolBar {

    // the selector tool is considered to be the default tool
    private static final GenericTool selectorTool = new GenericTool(ToolType.SELECTOR, new Cursor(Cursor.DEFAULT_CURSOR), "/icons/selector");

    /**
     *
     */
    Map<ToolType, JButton> tools = new LinkedHashMap<>();

    /**
     *
     */
    private final JFrame frame;

    /**
     *
     */
    private final ToolController controller;

    public ToolMenu(JFrame frame, ToolController controller) {
        super(SwingConstants.VERTICAL);

        this.frame = frame;
        this.controller = controller;

        this.controller.setCurrentTool(selectorTool);

        this.controller.addPropertyChangeListener(l -> {
            if (l.getPropertyName().equals("toolChange")) {
                var tool = (DrawingTool) l.getNewValue();
                var oldTool = (DrawingTool) l.getOldValue();

                var icon = tool.getImageIcon(true);
                var oldIcon = oldTool.getImageIcon(false);

                // resize the icon to 20x20
                icon = ImageUtils.resizeIcon(icon, 20, 20);
                oldIcon = ImageUtils.resizeIcon(oldIcon, 20, 20);

                this.tools.get(tool.getType()).setIcon(icon);
                this.tools.get(oldTool.getType()).setIcon(oldIcon);

                this.revalidate();
                this.repaint();
            }
        });

        // prevent it from being floated
        this.setFloatable(false);

        // add a border to the toolbar
        this.setBorderPainted(true);

        this.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 0, 1, Color.GRAY),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        var panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1, 0, 20));

        // Add the actions to the toolbars.
        this.tools.put(ToolType.SELECTOR, setupAction(selectorTool));
        this.tools.put(ToolType.FILL, setupAction(new FillTool()));
        this.tools.put(ToolType.LINE, setupAction(new GenericTool(ToolType.LINE, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/line")));
        this.tools.put(ToolType.RECTANGLE, setupAction(new GenericTool(ToolType.RECTANGLE, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/rectangle")));
        this.tools.put(ToolType.ELLIPSIS, setupAction(new GenericTool(ToolType.ELLIPSIS, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/circle")));
        this.tools.put(ToolType.TRIANGLE, setupAction(new GenericTool(ToolType.TRIANGLE, new Cursor(Cursor.CROSSHAIR_CURSOR), "/icons/triangle")));
        this.tools.put(ToolType.IMAGE, setupAction(new GenericTool(ToolType.IMAGE, new Cursor(Cursor.HAND_CURSOR), "/icons/image")));
        this.tools.put(ToolType.TEXT, setupAction(new GenericTool(ToolType.TEXT, new Cursor(Cursor.TEXT_CURSOR), "/icons/text")));

        this.tools.values().forEach(panel::add);

        panel.setMaximumSize(new Dimension(200, 300));
        panel.setBackground(new Color(0xFFFFFF));

        this.setCursor(Cursor.getDefaultCursor());

        this.setBackground(new Color(0xFFFFFF));

        this.add(Box.createVerticalStrut(5));
        this.add(panel);
        this.add(Box.createVerticalGlue());

    }

    private JButton setupAction(DrawingTool tool) {
        return this.createButtonFromAction(new AbstractAction(tool.getType().toString(), tool.getImageIcon(false)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cursor cursor = tool.getCursor();
                frame.setCursor(cursor);

                // update the button icon to 'blue' when it's selected
                var button = (JButton) e.getSource();

                // resize the icon to 20x20
                var icon = ImageUtils.resizeIcon(tool.getImageIcon(true), 20, 20);

                button.setIcon(icon);

                controller.setCurrentTool(tool);


                // TODO: temporary!
                switch (tool.getType()) {
                    case SELECTOR:
                        break;
                    case FILL:
                        var fillObj = new Rectangle(10, 20, 40, 50);

                        controller.setCurrentWidget(new FillToolWidget(fillObj, frame));
                        break;
                    case LINE:
                        var line = new Line(10, 20, 40, 50);

                        controller.setCurrentWidget(new LineToolWidget(line, frame));
                        break;
                    case TRIANGLE:
                        var triangle = new Triangle(10, 20, 40, 50);

                        controller.setCurrentWidget(new TriangleToolWidget(triangle, frame));
                        break;
                    case RECTANGLE:
                        var rect = new Rectangle(10, 20, 40, 50);

                        controller.setCurrentWidget(new RectangleToolWidget(rect, frame));
                        break;
                    case ELLIPSIS:
                        var ellipsis = new Ellipses(10, 20, 40, 50);

                        controller.setCurrentWidget(new EllipsisToolWidget(ellipsis, frame));
                        break;
                    case IMAGE:
                        var image = new ImageShape(10, 20, 40, 50);

                        controller.setCurrentWidget(new ImageToolWidget(image, frame));
                        break;
                    case TEXT:
                        var text = new TextShape(10, 20, 40, 50);

                        controller.setCurrentWidget(new TextToolWidget(text, frame));
                        break;
                }
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

        if (controller.getCurrentTool().getType() == tool.getType()) {
            var icon = tool.getImageIcon(true);

            // resize the icon to 20x20
            icon = ImageUtils.resizeIcon(icon, 20, 20);

            button.setIcon(icon);
        }

        button.setBackground(new Color(0xFFFFFF));

        return button;
    }
}
