package drawing.shape;

import java.awt.*;

public class ShapeColour {
    private int colour;
    private float alpha;


    public ShapeColour(int colour, float alpha) {
        if (0 > colour || colour > 0xFFFFFF) {
            throw new IllegalArgumentException("Invalid colour setting.");
        }

        if (alpha < 0 || alpha > 1.0) {
            throw new IllegalArgumentException("Invalid colour alpha setting");
        }

        this.colour = colour;
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        if (alpha < 0 || alpha > 1.0) {
            throw new IllegalArgumentException("Invalid colour alpha setting");
        }

        this.alpha = alpha;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        if (0 > colour || colour > 0xFFFFFF) {
            throw new IllegalArgumentException("Invalid colour setting.");
        }

        this.colour = colour;
    }

    public Color toColour() {
        int b = this.colour % 256;
        int g = (this.colour / 256) % 256;
        int r = this.colour / 65536;
        //System.out.printf("%s %s %s",b, g, r);

        return new Color(r, g, b, (int) this.alpha * 255);
    }
}
