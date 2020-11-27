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
import ui.controllers.WidgetController;
import ui.tool.EmptyToolWidget;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class Canvas extends JPanel implements MouseMotionListener, MouseInputListener {
    /**
     *
     */
    private double zoomFactor = 1;

    /**
     *
     */
    private final List<Shape> objects = new ArrayList<>();

    /**
     * The temporary object that is being used to display a pre-emptive shape that
     * will be drawn when the user let's go of the mouse.
     */
    private Shape currentObject = null;

    private Shape selectedShape = null;

    private Shape highlightedShape = null;

    private boolean copySelectedShape = false;

    private final ToolController toolController;

    private final WidgetController widgetController;

    private int mouseX1;
    private int mouseY1;
    private boolean isDragging;
    private boolean additionalOverlay = true;

    /**
     *
     */
    public Canvas(ToolController toolController, WidgetController widgetController) {
        this.toolController = toolController;
        this.widgetController = widgetController;

        // Default canvas background
        this.setBackground(Color.WHITE);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // setup widget property change listener
        this.toolController.addPropertyChangeListener(this::toolChangeListener);
        this.widgetController.addPropertyChangeListener(this::widgetPropertiesChangeListener);
    }

    /**
     *
     */
    private void toolChangeListener(PropertyChangeEvent event) {

        // Propagate the cursor when the tool changes...
        this.setCursor(toolController.getCurrentTool().getCursor());

        this.selectedShape = null;
        this.repaint();
    }

    /**
     *
     */
    private void widgetPropertiesChangeListener(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("widgetPropertyChange")) {


            // don't do anything if no selected shape
            if (this.selectedShape == null) {
                return;
            }

            var shapeProperties = this.selectedShape.getProperties();

            var updatedProperties = (Map<String, Object>) event.getNewValue();

            // check if the properties have changed, and if they have set the shape properties
            // of the current object
            for (String name : updatedProperties.keySet()) {
                var newProperty = updatedProperties.get(name);

                if (shapeProperties.get(name) != null) {
                    this.selectedShape.setProperty(name, newProperty);
                }
            }

            this.revalidate();
            this.repaint();
        }
    }


    /**
     * This overridden paint method will be called by Swing when Canvas.repaint() is called from SimpleGuiDelegate.propertyChange().
     */
    public void paint(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        var g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all of the components on the canvas
        this.objects.forEach(shape -> shape.draw(g2, false));

        // Draw the current shape to add interactivity to the canvas. The user
        // can see what they are about to draw.
        if (additionalOverlay && currentObject != null) {
            currentObject.draw(g2, true);
        }

        // Draw any highlighted shape...
        if (additionalOverlay && highlightedShape != null) {
            highlightedShape.drawBoundary(g2);
        }

        // Draw a selected bounding box if a shape has been selected...
        if (additionalOverlay && selectedShape != null) {
            selectedShape.drawSelectedBoundary(g2);
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

                this.selectedShape = this.currentObject;
                this.currentObject = null;

                this.repaint();

                this.widgetController.setCurrentWidgetFromShape(selectedShape);
            }
        }

        if (currentTool.getType() == ToolType.FILL) {
            this.selectedShape = null;

            var selectedShape = getShapeIfHoveringShape(e.getPoint());

            // get the current colour from the FillWidget
            var fillValue = (Color) this.widgetController.getCurrentToolWidget().getValue("fill");

            // Fill the canvas if no shape is clicked on...
            if (selectedShape == null) {
                this.setBackground(fillValue);
            } else {
                selectedShape.setShapeFillColour(fillValue);
            }

            this.repaint();
        }
    }

    /**
     *
     */
    @Override
    public void mousePressed(MouseEvent e) {
        var currentTool = this.toolController.getCurrentTool();

        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseX1 = e.getX();
            mouseY1 = e.getY();

            if (currentTool.getType() == ToolType.SELECTOR) {
                var selectedShape = getShapeIfHoveringShape(e.getPoint());

                this.isDragging = true;

                // update the cursor to represent the movement of the object
                if (!Objects.equals(selectedShape, this.selectedShape) && selectedShape != null) {
                    this.selectedShape = selectedShape;

                    this.repaint();

                    this.widgetController.setCurrentWidgetFromShape(selectedShape);
                }
            }

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

        if (SwingUtilities.isLeftMouseButton(e)) {

            // Reset the isDragging flag since the user let go of the mouse
            if (currentTool.getType() == ToolType.SELECTOR) {
                this.setCursor(currentTool.getCursor());
                this.isDragging = false;

                // update the panel widget to reflect the newest values
                this.widgetController.updateWidget();
            }


            if (currentTool.getType() != ToolType.SELECTOR
                    && currentTool.getType() != ToolType.FILL
                    && currentTool.getType() != ToolType.IMAGE
            ) {
                if (currentObject != null) {
                    objects.add(currentObject);

                    // Update the widget controller to our newest shape on the canvas
                    this.selectedShape = currentObject;
                    this.widgetController.setCurrentWidgetFromShape(selectedShape);

                    this.repaint();
                    currentObject = null;
                }
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

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (currentTool.getType() == ToolType.SELECTOR && this.isDragging && this.selectedShape != null) {
                this.selectedShape.setX(this.selectedShape.getX() + (e.getX() - mouseX1));
                this.selectedShape.setY(this.selectedShape.getY() + (e.getY() - mouseY1));

                mouseX1 = e.getX();
                mouseY1 = e.getY();

                this.repaint();
            }


            // Perform a drawing action if the current tool is not the selector, fill
            // or an image inserter.
            if (currentTool.getType() != ToolType.SELECTOR
                    && currentTool.getType() != ToolType.FILL
                    && currentTool.getType() != ToolType.IMAGE) {
                var endX = e.getX();
                var endY = e.getY();

                this.currentObject = this.createNewObject(mouseX1, mouseY1, endX, endY);

                if (this.currentObject != null) {
                    this.repaint();
                }
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
            Shape newHighlightedShape = this.getShapeIfHoveringShape(e.getPoint());

            // Show a 'move' cursor when hovering over a selected object
            if (Objects.equals(this.selectedShape, newHighlightedShape) && this.selectedShape != null) {
                this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }

            // Don't re-draw the selected object if they are the same or null, or equal
            // to the currently selected object...
            if (!Objects.equals(newHighlightedShape, this.highlightedShape) &&
                    !Objects.equals(this.selectedShape, newHighlightedShape)) {

                this.setCursor(currentTool.getCursor());

                // set the 'new' shape to the currently highlighted one...
                this.highlightedShape = newHighlightedShape;

                this.repaint();
            }
        }
    }

    /**
     *
     */
    public Shape getShapeIfHoveringShape(Point point) {
        List<Shape> shapes = this.objects;

        // Because we care about the items that are on the top
        // of the stack first, we will inspect items that are on
        // top first and then move to the bottom.
        Collections.reverse(shapes);

        for (Shape shape : shapes) {
            if (shape.isPointWithinBounds(point)) {

                return shape;
            }
        }

        return null;
    }

    /**
     *
     * */
    public void clear() {
        this.objects.clear();

        // clear all the temporary objects
        this.resetSelectedObject();
        this.highlightedShape = null;
        this.currentObject = null;

        // set the current tool to the selector and set the property editor
        // widget to an empty widget since we're totally resetting the editor
        this.toolController.setCurrentTool(ToolType.SELECTOR);

        this.widgetController.setCurrentWidget(new EmptyToolWidget());

        // finally repaint
        this.repaint();
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
                var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                // filter for image files only
                FileFilter imageFilter = new FileNameExtensionFilter(
                        "Image files", "jpg", "png");

                fileChooser.setFileFilter(imageFilter);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    BufferedImage selectedImage;

                    try {
                        selectedImage = ImageIO.read(fileChooser.getSelectedFile());


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

    /**
     *
     */
    private void resetSelectedObject() {
        this.selectedShape = null;
        copySelectedShape = false;
    }

    /**
     *
     */
    public void setCopyOnSelectedShape(boolean flag) {
        this.copySelectedShape = flag;
    }

    /**
     *
     */
    public void copySelectedShape() {

        // Don't do anything if 'copy' hasn't been set on the current shape
        // and if there is no selected shape...
        if (!this.copySelectedShape || this.selectedShape == null) {
            return;
        }


        // We want to create a new copy of the current selected shape
        // To differentiate between the two objects, we'll offset the
        // new object by 10px down and 10px to the left.
        //
        // @Improve: what if the object is at the end of the canvas
        var newObject = this.selectedShape.copy();

        newObject.setX(newObject.getX() + 10);
        newObject.setY(newObject.getY() + 10);

        this.resetSelectedObject();
        this.repaint();

        this.selectedShape = newObject;
        this.objects.add(this.selectedShape);

        this.repaint();
    }

    /**
     *
     */
    public void deleteSelectedShape() {
        // Don't do anything if there is no selected shape
        if (selectedShape == null) {
            return;
        }

        this.objects.remove(this.selectedShape);
        selectedShape = null;

        this.repaint();
    }


    /**
     *
     */
    public void export(File to, String extension) {
        if (!extension.equals("jpg") && !extension.equals("png")) {
            throw new UnsupportedOperationException("Can't export to specified extension");
        }

        BufferedImage image;

        // Determine the colour mode based on file extension
        if (extension.equals("png")) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        } else {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }


        var g = image.createGraphics();

        // we need to temporarily disable any kind of 'currentObject', 'selectedObject', and
        // 'highlighted' shape. We can disable the additionalOverlay flag to prevent the
        // paint method from paint those additional components. Once the graphic is pained,
        // we can re-enable the painting of the additional components.
        this.additionalOverlay = false;
        this.paint(g);
        this.additionalOverlay = true;

        g.dispose();

        try (
                var out = new FileOutputStream(to)
        ) {

            // Create the file before export if we need to
            if (!to.exists()) {
                boolean createdFile = to.createNewFile();

                if (createdFile) {
                    System.out.println("LOG: created file...");
                }
            }

            System.out.println("LOG: Exporting image to " + to.toString());
            ImageIO.write(image, extension, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
