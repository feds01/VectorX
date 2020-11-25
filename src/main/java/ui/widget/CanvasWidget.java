package ui.widget;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class CanvasWidget extends JPanel {
    private final JPanel canvas;

    public CanvasWidget() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.canvas = new JPanel();

        this.canvas.setPreferredSize(new Dimension(300, 300));
        this.canvas.setMaximumSize(new Dimension(300, 300));

        this.canvas.setBackground(Color.white);


        this.add(canvas);
    }
}
