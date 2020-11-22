package ui.input;

import drawing.shape.ShapeColour;

import javax.swing.JToggleButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

public class ColourPickerInput extends JToggleButton {
    private ShapeColour colour;

    public ColourPickerInput(ShapeColour colour) {
        super();


        this.colour = colour;

        this.setBackground(colour.toColour());

        this.setMaximumSize(new Dimension(20, 20));


        this.addActionListener((ActionEvent e) -> {
            System.out.println(e);
        });
    }


    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);

        // this.currentColour = bg;
    }

    @Override
    protected void paintBorder(Graphics g) {
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape s = new RoundRectangle2D.Float(1, 1, 20,  20, 8, 8);


        g2.fill(s);
        super.paintChildren(g);

    }

    public ShapeColour getColour() {
        return colour;
    }

    public void setColour(ShapeColour colour) {
        this.colour = colour;
    }
}
