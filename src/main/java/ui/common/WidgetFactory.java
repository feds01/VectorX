package ui.common;

import common.FontLoader;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetFactory {

    /**
     *
     * */
    protected static final FontLoader fontLoader = FontLoader.getInstance();

    public static JPanel createTitleWidget(String name) {
        JPanel box = new JPanel();

        box.setBackground(Color.WHITE);
        box.setFont(fontLoader.getFont("NotoSans"));

        var layout = new FlowLayout(FlowLayout.LEADING);
        layout.setHgap(0);

        box.setLayout(layout);

        box.setMaximumSize(new Dimension(240, 30));

        box.add(Box.createVerticalStrut(5));

        var title = new JLabel(name);

        Font font = fontLoader.getFont("NotoSans");

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.15);

        title.setFont(font.deriveFont(attributes));
        title.setForeground(Color.GRAY);


        box.add(title);

        var separator = new JSeparator();
        separator.setPreferredSize(new Dimension(240, 1));
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setBackground(Color.GRAY);

        box.add(separator);
        box.add(Box.createVerticalStrut(5));

        return box;
    }
}
