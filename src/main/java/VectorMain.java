

import common.FontLoader;
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
            var toolbarMaker = new ToolMenu(frame, toolController);
            var toolbar = toolbarMaker.createMenu();

            var propertiesPanel = new PropertiesMenu(frame, toolController);


            frame.add(toolbar, BorderLayout.WEST);
            frame.add(propertiesPanel.panel, BorderLayout.EAST);

            frame.setLocationByPlatform(true);

            frame.pack();
            frame.setSize( WIDTH, HEIGHT);
            frame.setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        });
    }
}
