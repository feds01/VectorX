import common.FontLoader;
import drawing.ToolType;
import file.SaveManager;
import ui.controllers.ToolController;
import ui.controllers.WidgetController;
import ui.widget.CanvasWidget;
import ui.widget.PropertiesMenu;
import ui.widget.ToolMenu;
import ui.widget.TopMenu;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

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


    private JFrame frame;

    /**
     *
     */
    private JScrollPane scrollPane;

    /**
     *
     */
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

            this.frame = new JFrame("VectorX - New File");

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

            frame.setBackground(Color.GRAY);
            frame.setFont(fontLoader.getFont("NotoSans"));


            // setup the tool controller so the state can be shared between the tool chooser,
            // the canvas and the property bar. The tool controller is assuming that no tool
            // is currently under selection.
            var toolController = new ToolController();

            var widgetController = new WidgetController(frame);

            // create the toolbar
            this.toolbar = new ToolMenu(frame, toolController, widgetController);
            this.canvasWidget = new CanvasWidget(toolController, widgetController);
            this.propertiesPanel = new PropertiesMenu(widgetController);

            // make the menu
            var menu = new TopMenu(canvasWidget, frame);

            frame.setJMenuBar(menu);
            frame.add(toolbar, BorderLayout.WEST);

            // Setup the editor scroll pane.
            this.scrollPane = new JScrollPane(canvasWidget,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS) {

                @Override
                protected void paintBorder(Graphics g) {
                    // Don't paint the border
                }

                @Override
                protected void processMouseWheelEvent(MouseWheelEvent e) {
                    if (e.isControlDown()) {
                        canvasWidget.handleScroll(e);
                    } else {
                        super.processMouseWheelEvent(e);
                    }
                }
            };

            // Increase vertical and horizontal scrollbar speeds
            this.scrollPane.getVerticalScrollBar().setUnitIncrement(10);
            this.scrollPane.getHorizontalScrollBar().setUnitIncrement(10);


            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(propertiesPanel.panel, BorderLayout.EAST);

            // setup toolbar shortcuts on the frame
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this::shortcutListener);

            // Java Swing boilerplate code to initialise a window on the host os
            frame.setLocationByPlatform(true);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

            System.out.println("VectorX started...");
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

                if (e.isControlDown()) {
                    SaveManager.getInstance().saveFile(canvasWidget, frame, false);
                } else {
                    toolbar.setCurrentTool(ToolType.SELECTOR);
                }

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


            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_BACK_SPACE: {

                // Don't do anything if the tool isn't on the selector tool.
                canvasWidget.getCanvas().deleteSelectedShape();

                break;
            }

            case KeyEvent.VK_C: {
                if (!e.isControlDown()) return false;

                canvasWidget.getCanvas().setCopyOnSelectedShape(true);
                break;
            }

            case KeyEvent.VK_V: {
                if (!e.isControlDown()) return false;

                canvasWidget.getCanvas().copySelectedShape();
                break;
            }

            case KeyEvent.VK_O: {
                if (!e.isControlDown()) return false;

                SaveManager.getInstance().openFile(canvasWidget, frame);
            }
        }
        return false;
    }
}
