package ui.common;

import common.FontLoader;
import drawing.shape.Ellipse;
import drawing.shape.ImageShape;
import drawing.shape.Line;
import drawing.shape.Rectangle;
import drawing.shape.Shape;
import drawing.shape.TextShape;
import drawing.shape.Triangle;
import ui.tool.BaseToolWidget;
import ui.tool.EmptyToolWidget;
import ui.tool.ImageToolWidget;
import ui.tool.LineToolWidget;
import ui.tool.ShapeToolWidget;
import ui.tool.TextToolWidget;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * WidgetFactory class holds utility methods to create re-usable
 * widgets across the application.
 *
 * @author 200008575
 * */
public class WidgetFactory {

    /**
     *
     */
    protected static final FontLoader fontLoader = FontLoader.getInstance();

    /**
     *
     */
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

        var separator = createSeparator();

        box.add(separator);
        box.add(Box.createVerticalStrut(5));

        return box;
    }

    /**
     *
     */
    public static JSeparator createSeparator() {
        var separator = new JSeparator();
        separator.setPreferredSize(new Dimension(240, 1));
        separator.setMaximumSize(new Dimension(240, 1));
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setBackground(Color.GRAY);

        return separator;
    }

    /**
     *
     */
    public static BaseToolWidget createWidgetFromShape(Shape shape, JFrame frame) {

        switch (shape.getToolType()) {
            case LINE:
                var line = (Line) shape;

                return new LineToolWidget(line, frame);
            case TRIANGLE:
                var triangle = (Triangle) shape;

                return new ShapeToolWidget(triangle, frame);
            case RECTANGLE:
                var rectangle = (Rectangle) shape;

                return new ShapeToolWidget(rectangle, frame);
            case ELLIPSE:
                var ellipsis = (Ellipse) shape;

                return new ShapeToolWidget(ellipsis, frame);
            case IMAGE:
                var image = (ImageShape) shape;

                return new ImageToolWidget(image);
            case TEXT:
                var text = (TextShape) shape;
                return new TextToolWidget(text, frame);
            default:
                return new EmptyToolWidget();
        }
    }
}
