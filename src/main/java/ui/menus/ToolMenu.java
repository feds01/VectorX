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

    /**
     *
     */
    private final Map<ToolType, JButton> buttonMap = new LinkedHashMap<>();

    /**
     *
     */
    private final Map<ToolType, DrawingTool> toolMap = new LinkedHashMap<>() {
        {
            put(ToolType.SELECTOR, new GenericTool(
                    ToolType.SELECTOR,
                    new Cursor(Cursor.DEFAULT_CURSOR),
                    "Select (S)",
                    "/icons/selector"));

            put(ToolType.FILL, new FillTool());

            put(ToolType.LINE, new GenericTool(
                    ToolType.LINE,
                    new Cursor(Cursor.CROSSHAIR_CURSOR),
                    "Line (L)",
                    "/icons/line"));

            put(ToolType.RECTANGLE, new GenericTool(
                    ToolType.RECTANGLE,
                    new Cursor(Cursor.CROSSHAIR_CURSOR),
                    "Rectangle (R)",
                    "/icons/rectangle"));

            put(ToolType.ELLIPSIS, new GenericTool(
                    ToolType.ELLIPSIS,
                    new Cursor(Cursor.CROSSHAIR_CURSOR),
                    "Ellipse (E)",
                    "/icons/circle"));

            put(ToolType.TRIANGLE, new GenericTool(
                    ToolType.TRIANGLE,
                    new Cursor(Cursor.CROSSHAIR_CURSOR),
                    "Triangle (T)",
                    "/icons/triangle"));

            put(ToolType.IMAGE, new GenericTool(
                    ToolType.IMAGE,
                    new Cursor(Cursor.HAND_CURSOR),
                    "Image (I)",
                    "/icons/image"));
            put(ToolType.TEXT, new GenericTool(
                    ToolType.TEXT,
                    new Cursor(Cursor.TEXT_CURSOR),
                    "Text (W)",
                    "/icons/text"
            ));
        }
    };

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

        this.controller.addPropertyChangeListener(l -> {
            if (l.getPropertyName().equals("toolChange")) {

                var tool = (DrawingTool) l.getNewValue();
                var oldTool = (DrawingTool) l.getOldValue();

                var icon = tool.getImageIcon(true);

                // resize the icon to 20x20
                icon = ImageUtils.resizeIcon(icon, 20, 20);
                this.buttonMap.get(tool.getType()).setIcon(icon);

                // On application start, the initial tool will be null
                if (oldTool != null) {
                    var oldIcon = oldTool.getImageIcon(false);
                    oldIcon = ImageUtils.resizeIcon(oldIcon, 20, 20);
                    this.buttonMap.get(oldTool.getType()).setIcon(oldIcon);
                }

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
        this.toolMap.keySet().forEach(key -> this.buttonMap.put(key, setupAction(this.toolMap.get(key))));

        // Add all the created buttons in button map
        this.buttonMap.values().forEach(panel::add);

        // set the current tool after the buttons have been added to the UI
        this.setCurrentTool(ToolType.SELECTOR);

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

                // update the current tool the user selected tool
                setCurrentTool(tool.getType());

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
        button.setToolTipText(tool.getToolTip());
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        if (controller.getCurrentTool() != null && controller.getCurrentTool().getType() == tool.getType()) {
            var icon = tool.getImageIcon(true);

            // resize the icon to 20x20
            icon = ImageUtils.resizeIcon(icon, 20, 20);

            button.setIcon(icon);
        }

        button.setBackground(new Color(0xFFFFFF));

        return button;
    }


    /**
     *
     * */
    public void setCurrentTool(ToolType type) {
        var currentTool = this.controller.getCurrentTool();

        // avoid setting a new tool menu if the type didn't change
        if (currentTool != null && currentTool.getType() == type) {
            return;
        }

        this.controller.setCurrentTool(this.toolMap.get(type));
    }
}
