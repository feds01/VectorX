package ui.menus;

import common.ImageUtils;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ToolMenu {
    private final JFrame frame;

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();

    public ToolMenu(JFrame frame) {
        this.frame = frame;
    }


    private JButton setupPointerAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/selector.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("select", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                // do something.
            }
        });
    }

    private JButton setupColourPickerAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/fill.png"));


        // get the best width and height based on Operating System using the Toolkit
        var dimensions = toolkit.getBestCursorSize(32, 32);

        // Load in our custom 'fill' cursor using the same icon...
        var cursor = toolkit.createCustomCursor(
                ImageUtils.resizeIcon(icon, dimensions.width, dimensions.height).getImage(),
                new Point(frame.getX(), frame.getY()), "");

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("colour", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(cursor);
                // do something.
            }
        });
    }

    private JButton setupLineAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/line.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("drawLine", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                // do something.
            }
        });
    }

    private JButton setupRectangleAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/rectangle.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("drawRectangle", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                // do something.
            }
        });
    }

    private JButton setupTriangleAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/triangle.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("drawTriangle", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                // do something.
            }
        });
    }

    private JButton setupCircleAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/circle.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("drawCicrle", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                // do something.
            }
        });
    }

    private JButton setupImageAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/image.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("drawImage", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                // do something.
            }
        });
    }

    private JButton setupTextAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/text.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction("textBox", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.TEXT_CURSOR));

                // do something.
            }
        });
    }


    /**
     */
    private JButton createButtonFromAction(Action action) {
        var button = new JButton(action);

        button.setText("");
        button.setBorderPainted(false);

        return button;
    }

    /**
     *
     */
    public JToolBar createMenu() {
        var toolbar = new JToolBar();
        toolbar.setOrientation(SwingConstants.VERTICAL);

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

        panel.setLayout(new GridLayout(0, 1, 0, 10));

        // Add the actions to the toolbars.
        panel.add(setupPointerAction());
        panel.add(setupColourPickerAction());
        panel.add(setupLineAction());
        panel.add(setupRectangleAction());
        panel.add(setupTriangleAction());
        panel.add(setupCircleAction());
        panel.add(setupImageAction());
        panel.add(setupTextAction());

        panel.setMaximumSize(new Dimension(200, 220));

        toolbar.add(Box.createVerticalStrut(5));
        toolbar.add(panel);
        toolbar.add(Box.createVerticalGlue());

        return toolbar;
    }
}
