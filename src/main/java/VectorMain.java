import common.FontLoader;
import drawing.ToolType;
import ui.controllers.ToolController;
import ui.widget.CanvasWidget;
import ui.widget.PropertiesMenu;
import ui.widget.ToolMenu;
import ui.widget.TopMenu;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 *
 */
public class VectorMain extends JFrame {

    /**
     *
     */
    private static final int WIDTH = 1024;

    /**
     *
     */
    private static final int HEIGHT = 768;

    /**
     *
     */
    private final FontLoader fontLoader = FontLoader.getInstance();

    /**
     *
     * */
    private CanvasWidget canvasWidget;

    /**
     *
     */
    private PropertiesMenu propertiesPanel;

    /**
     *
     */
    private ToolMenu toolbar;

    /**
     *
     */
    public static void main(String[] args) {
        new VectorMain();
    }

    /**
     *
     */
    public VectorMain() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }

            JFrame frame = new JFrame("VectorX - New File");

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

            frame.setBackground(Color.GRAY);
            frame.setFont(fontLoader.getFont("NotoSans"));

            var menuMaker = new TopMenu();

            // make the menu
            var menu = menuMaker.createMenu();

            frame.setJMenuBar(menu);

            // setup the tool controller so the state can be shared between the tool chooser,
            // the canvas and the property bar. The tool controller is assuming that no tool
            // is currently under selection.
            var toolController = new ToolController();

            // create the toolbar
            this.toolbar = new ToolMenu(frame, toolController);
            this.canvasWidget = new CanvasWidget();
            this.propertiesPanel = new PropertiesMenu(toolController);

            frame.add(toolbar, BorderLayout.WEST);
            frame.add(canvasWidget, BorderLayout.CENTER);
            frame.add(propertiesPanel.panel, BorderLayout.EAST);

            // setup toolbar shortcuts on the frame
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this::shortcutListener);

            // Java Swing boilerplate code to initialise a window on the host os
            frame.setLocationByPlatform(true);
            frame.pack();
            frame.setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        });
    }

    /**
     *
     */
    private boolean shortcutListener(KeyEvent e) {
        // Ignore events that come from other components than a button
        if (e.getSource() instanceof JTextField) {
            return false;
        }

        // Don't do anything if the event isn't a KEY_PRESSED event
        if (e.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_S: {
                toolbar.setCurrentTool(ToolType.SELECTOR);
                break;
            }
            case KeyEvent.VK_F: {
                toolbar.setCurrentTool(ToolType.FILL);
                break;
            }
            case KeyEvent.VK_L: {
                toolbar.setCurrentTool(ToolType.LINE);
                break;
            }
            case KeyEvent.VK_R: {
                toolbar.setCurrentTool(ToolType.RECTANGLE);
                break;
            }
            case KeyEvent.VK_E: {
                toolbar.setCurrentTool(ToolType.ELLIPSIS);
                break;
            }
            case KeyEvent.VK_T: {
                toolbar.setCurrentTool(ToolType.TRIANGLE);
                break;
            }
            case KeyEvent.VK_I: {
                toolbar.setCurrentTool(ToolType.IMAGE);
                break;
            }
            case KeyEvent.VK_W: {
                toolbar.setCurrentTool(ToolType.TEXT);
                break;
            }
        }
        return false;
    }
}
