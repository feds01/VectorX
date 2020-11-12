

import ui.menu.MenuMaker;

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

            //TitlePane titlePane = new TitlePane();

            var menuMaker = new MenuMaker();

            // make the menu
            var menu = menuMaker.createMenuBar();

            frame.setJMenuBar(menu);

//                frame.setUndecorated(true);
            frame.setLocationByPlatform(true);
            frame.setResizable(true);

            frame.pack();
            frame.setSize(WIDTH, HEIGHT);
            frame.setVisible(true);

            setDefaultCloseOperation(EXIT_ON_CLOSE);

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        });
    }
}
