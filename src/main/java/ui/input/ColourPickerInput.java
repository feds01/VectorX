package ui.input;

import drawing.shape.ShapeColour;

import javax.swing.JToggleButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

public class ColourPickerInput extends JToggleButton {
    private ShapeColour colour;

    public ColourPickerInput(ShapeColour colour) {
        super();


        this.colour = colour;


        //this.setOpaque(true);
        this.setBackground(this.colour.toColour());
        this.setBorderPainted(false);
        this.setMaximumSize(new Dimension(24, 24));


        this.addActionListener((ActionEvent e) -> {
            System.out.println(e);
        });
    }


    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }

    @Override
    public boolean isFocusPainted() {
        return false;
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        System.out.println(this.colour.toColour());
        g2.setColor(this.colour.toColour());

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
