package ui.menus;

import common.ImageUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * */
public class ToolMenu {

    /**
     *
     * */
    private final JFrame frame;

    /**
     *
     * */
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();


    /**
     *
     * */
    public static final Map<String, String> iconMap = new HashMap<>();

    static {
        iconMap.put("select", "/icons/selector");
        iconMap.put("fill", "/icons/fill");
        iconMap.put("line", "/icons/line");
        iconMap.put("rectangle", "/icons/rectangle");
        iconMap.put("ellipsis", "/icons/circle");
        iconMap.put("image", "/icons/image");
        iconMap.put("triangle", "/icons/triangle");
        iconMap.put("text", "/icons/text");
    }

    public ToolMenu(JFrame frame) {
        this.frame = frame;
    }

    private JButton setupAction(String command) {
        var icon = new ImageIcon(ToolMenu.class.getResource(iconMap.get(command) + ".png"));

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction(command, icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                // do something.
            }
        }, command);
    }

    private JButton setupColourPickerAction() {
        String command = "fill";

        var icon = new ImageIcon(ToolMenu.class.getResource(iconMap.get(command) + ".png"));

        // get the best width and height based on Operating System using the Toolkit
        var dimensions = toolkit.getBestCursorSize(32, 32);

        // Load in our custom 'fill' cursor using the same icon...
        var cursor = toolkit.createCustomCursor(
                ImageUtils.resizeIcon(icon, dimensions.width, dimensions.height).getImage(),
                new Point(frame.getX(), frame.getY()), "");

        // resize the icon to 20x20
        icon = ImageUtils.resizeIcon(icon, 20, 20);

        return this.createButtonFromAction(new AbstractAction(command, icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setCursor(cursor);
                // do something.
            }
        }, command);
    }

    /**
     */
    private JButton createButtonFromAction(Action action, String command) {
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


                var icon = new ImageIcon(ToolMenu.class.getResource(iconMap.get(command) + ".png"));

                // resize the icon to 20x20
                icon = ImageUtils.resizeIcon(icon, 20, 20);

                btn.setIcon(icon);
            }

            @Override
            public void focusGained(FocusEvent e) {
                AbstractButton btn = (AbstractButton) e.getSource();

                var icon = new ImageIcon(ToolMenu.class.getResource(iconMap.get(command) + "_selected.png"));

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
        panel.add(setupAction("select"));
        panel.add(setupColourPickerAction());
        panel.add(setupAction("line"));
        panel.add(setupAction("rectangle"));
        panel.add(setupAction("ellipsis"));
        panel.add(setupAction("triangle"));
        panel.add(setupAction("image"));
        panel.add(setupAction("text"));

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
