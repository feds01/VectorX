package ui.menus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class PropertiesMenu  {
    private final JFrame frame;

    public PropertiesMenu(JFrame frame) {
        this.frame = frame;
    }



    public JToolBar createMenu() {
        JToolBar propertyBar = new JToolBar(SwingConstants.VERTICAL);

        // prevent it from being floated
        propertyBar.setFloatable(false);


        // add a border to the toolbar
        propertyBar.setBorderPainted(true);

//        var margin = new EmptyBorder(new Insets(0, 5, 0, 5));
//        var lineBorder = new LineBorder(Color.BLACK);

        propertyBar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.BLACK),
                new EmptyBorder(new Insets(0, 5, 0, 5))
        ));

        var panel = new JPanel();

        panel.add(new JTextArea("Properties"));

        propertyBar.add(panel);
        propertyBar.add(Box.createVerticalGlue());

        return propertyBar;
    }
}
