package ui.menus;

import common.ImageUtils;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;

public class ToolMenu {
    JFrame frame;

    public ToolMenu(JFrame frame) {
        this.frame = frame;
    }


    private Action setupPointerAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/selector.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("select", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    private Action setupColourPickerAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/colour-dropper.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("colour", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    private Action setupLineAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/line.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("drawLine", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    private Action setupRectangleAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/rectangle.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("drawRectangle", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    private Action setupTriangleAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/triangle.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("drawTriangle", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    private Action setupCircleAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/circle.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("drawCicrle", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    private Action setupImageAction() {
        var icon = new ImageIcon(ToolMenu.class.getResource("/icons/image.png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return new AbstractAction("drawImage", icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // do something.
            }
        };
    }

    /**
     *
     * */
    public JToolBar createMenu() {
        var toolbar = new JToolBar();
        toolbar.setOrientation(SwingConstants.VERTICAL);

        // prevent it from being floated
        toolbar.setFloatable(false);

        // essentially add a margin that will center the buttons in the middle of the
        // window vertically.
        toolbar.add(Box.createVerticalGlue());

        // Add the actions to the toolbars.
        toolbar.add(setupPointerAction());
        toolbar.add(setupColourPickerAction());
        toolbar.add(setupLineAction());
        toolbar.add(setupRectangleAction());
        toolbar.add(setupTriangleAction());
        toolbar.add(setupCircleAction());
        toolbar.add(setupImageAction());
        toolbar.add(Box.createVerticalGlue());

        return toolbar;
    }
}
