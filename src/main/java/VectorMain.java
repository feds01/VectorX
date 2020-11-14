

import ui.menus.PropertiesMenu;
import ui.menus.ToolMenu;
import ui.menus.TopMenu;

import javax.swing.*;
import java.awt.*;

public class VectorMain extends JFrame {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public static void main(String[] args) {
        new VectorMain();
    }

    public VectorMain() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }

            JFrame frame = new JFrame("VectorX - Editing ${file}");

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

            var menuMaker = new TopMenu();

            // make the menu
            var menu = menuMaker.createMenu();

            frame.setJMenuBar(menu);

            // create the toolbar
            var toolbarMaker = new ToolMenu(frame);
            var toolbar = toolbarMaker.createMenu();

            var propertiesPanelMaker = new PropertiesMenu(frame);
            var propertiesMenu = propertiesPanelMaker.createMenu();


            frame.add(toolbar, BorderLayout.WEST);
            frame.add(propertiesMenu, BorderLayout.EAST);


//                frame.setUndecorated(true);
            frame.setLocationByPlatform(true);

            frame.pack();
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        });
    }
}
