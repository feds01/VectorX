

import common.FontLoader;
import drawing.ToolType;
import ui.controllers.ToolController;
import ui.menus.PropertiesMenu;
import ui.menus.ToolMenu;
import ui.menus.TopMenu;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VectorMain extends JFrame {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public static void main(String[] args) {
        new VectorMain();
    }

    public VectorMain() {

        // Start font loader
        var fontLoader = FontLoader.getInstance();

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }

            JFrame frame = new JFrame("VectorX - New File");

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

            var menuMaker = new TopMenu();

            // make the menu
            var menu = menuMaker.createMenu();

            frame.setJMenuBar(menu);
            frame.setFont(fontLoader.getFont("NotoSans"));

            // setup the tool controller so the state can be shared between the tool chooser,
            // the canvas and the property bar. The tool controller is assuming that no tool
            // is currently under selection.
            var toolController = new ToolController();

            // create the toolbar
            var toolbar = new ToolMenu(frame, toolController);

            var propertiesPanel = new PropertiesMenu(toolController);

            frame.add(toolbar, BorderLayout.WEST);
            frame.add(propertiesPanel.panel, BorderLayout.EAST);

            // setup toolbar shortcuts on the frame
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addKeyEventDispatcher(e -> {
                        int keyCode = e.getKeyCode();

                        // Don't do anything if the event isn't a KEY_PRESSED event
                        if (e.getID() != KeyEvent.KEY_PRESSED) {
                            return false;
                        }

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
                    });


            frame.setLocationByPlatform(true);
            frame.pack();
//            frame.setSize( WIDTH, HEIGHT);
            frame.setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        });
    }
}
