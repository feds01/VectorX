package ui.widget;

import drawing.ToolType;
import drawing.shape.Ellipses;
import drawing.shape.ImageShape;
import drawing.shape.Line;
import drawing.shape.Rectangle;
import drawing.shape.Shape;
import drawing.shape.TextShape;
import drawing.shape.Triangle;
import ui.controllers.ToolController;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Canvas extends JPanel implements MouseMotionListener, MouseInputListener {
    private static final Color DEFAULT_BG_COLOUR = Color.WHITE;

    private double zoomFactor = 1;

    /**
     *
     */
    private List<Shape> objects = new ArrayList<>();

    /**
     * The temporary object that is being used to display a pre-emptive shape that
     * will be drawn when the user let's go of the mouse.
     */
    private Shape currentObject = null;

    private Shape highlightedShape = null;

    private final ToolController toolController;

    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;

    /**
     *
     */
    public Canvas(ToolController toolController) {
        this.toolController = toolController;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * This overridden paint method will be called by Swing when Canvas.repaint() is called from SimpleGuiDelegate.propertyChange().
     */
    public void paint(Graphics g) {
        g.setColor(DEFAULT_BG_COLOUR);
        g.fillRect(0, 0, getWidth(), getHeight());

        var g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all of the components on the canvas
        this.objects.forEach(shape -> shape.draw(g2, false));

        // Draw the current shape to add interactivity to the canvas. The user
        // can see what they are about to draw.
        if (currentObject != null) {
            currentObject.draw(g2, true);
        }


        // Draw any highlighted shape
        if (highlightedShape != null) {
            highlightedShape.drawBoundary(g2);
        }

        g.dispose();
    }


    /**
     *
     */
    public double getZoomFactor() {
        return zoomFactor;
    }

    /**
     *
     */
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     *
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        var currentTool = this.toolController.getCurrentTool();

        if (currentTool.getType() == ToolType.IMAGE) {

            // don't re-create the image if it's already created.
            if (this.currentObject == null) {
                this.currentObject = createNewObject(mouseX1, mouseY1, 0, 0);
            } else {
                this.objects.add(currentObject);
                this.repaint();

                currentObject = null;
            }

        }
    }

    /**
     *
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseX1 = e.getX();
            mouseY1 = e.getY();

            System.out.println("x1 = " + mouseX1 + ", y1 = " + mouseY1);
        } else {
            this.getParent().dispatchEvent(e);
        }
    }

    /**
     *
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        var currentTool = toolController.getCurrentTool();

        if (SwingUtilities.isLeftMouseButton(e)
                && currentTool.getType() != ToolType.SELECTOR
                && currentTool.getType() != ToolType.FILL
                && currentTool.getType() != ToolType.IMAGE
        ) {
            mouseX2 = e.getX();
            mouseY2 = e.getY();
            System.out.println("x2 = " + mouseX2 + ", y2 = " + mouseY2);

            if (currentObject != null) {
                objects.add(currentObject);
                this.repaint();

                currentObject = null;
            }
        } else {
            this.getParent().dispatchEvent(e);
        }
    }

    /**
     *
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     *
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     *
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        var currentTool = toolController.getCurrentTool();

        if (SwingUtilities.isLeftMouseButton(e)
                && currentTool.getType() != ToolType.SELECTOR
                && currentTool.getType() != ToolType.FILL
                && currentTool.getType() != ToolType.IMAGE
        ) {
            var endX = e.getX();
            var endY = e.getY();

            this.currentObject = this.createNewObject(mouseX1, mouseY1, endX, endY);

            if (this.currentObject != null) {
                this.repaint();
            }


        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            this.getParent().dispatchEvent(e);
        }
    }

    /**
     *
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        var currentTool = toolController.getCurrentTool();

        // If the image tool is selected, and already created. The x and y
        // coordinates of the shape should be updated so it can be re-drawn.
        if (currentTool.getType() == ToolType.IMAGE && currentObject != null) {
            currentObject.setX(e.getX());
            currentObject.setY(e.getY());

            this.repaint();
        }

        if (currentTool.getType() == ToolType.SELECTOR) {
            List<Shape> shapes = this.objects;

            // Because we care about the items that are on the top
            // of the stack first, we will inspect items that are on
            // top first and then move to the bottom.
            Collections.reverse(shapes);

            Shape newHighlightedShape = null;

            for (Shape shape : shapes) {
                if (shape.isPointWithinBounds(e.getPoint())) {

                    System.out.println(shape);
                    newHighlightedShape = shape;
                    break;
                }
            }

            // Don't re-draw the selected object if they are the same or null
            if (!Objects.equals(newHighlightedShape, this.highlightedShape)) {

                // set the 'new' shape to the currently highlighted one...
                this.highlightedShape = newHighlightedShape;

                this.repaint();
            }
        }

    }

    /**
     *
     */
    private Shape createNewObject(int x1, int y1, int x2, int y2) {
        var currentTool = toolController.getCurrentTool();

        try {

            // This should never happen...
            if (currentTool.getType() == ToolType.FILL ||
                    currentTool.getType() == ToolType.SELECTOR
            ) {
                throw new IllegalStateException("Can't create new object for a selection tool");
            }

            // we need to boot up JFileChooser to select the item that
            // will be drawn on the canvas.
            if (currentTool.getType() == ToolType.IMAGE) {
                var jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                // filter for image files only
                FileFilter imageFilter = new FileNameExtensionFilter(
                        "Image files", "jpg", "png");

                jfc.setFileFilter(imageFilter);

                int result = jfc.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    BufferedImage selectedImage;

                    try {
                        selectedImage = ImageIO.read(jfc.getSelectedFile());


                        return new ImageShape(x1, y1, selectedImage);
                    } catch (IOException e) {
                        System.out.println("Failed to read selected image.");
                    }
                }
            }

            switch (currentTool.getType()) {
                case LINE:
                    return new Line(x1, y1, x2, y2);
                case TRIANGLE:
                    return new Triangle(x1, y1, x2, y2);
                case RECTANGLE:
                    return new Rectangle(x1, y1, x2, y2);
                case ELLIPSIS:
                    return new Ellipses(x1, y1, x2, y2);
                case TEXT:
                    return new TextShape(x1, y1, x2, y2);
            }

        } catch (IllegalArgumentException ignored) {
            return null;
        }

        return null;
    }
}
