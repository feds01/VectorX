package ui.widget;

import drawing.ResizeEvent;
import drawing.ToolType;
import drawing.shape.Ellipses;
import drawing.shape.ImageShape;
import drawing.shape.Line;
import drawing.shape.Rectangle;
import drawing.shape.Shape;
import drawing.shape.TextShape;
import drawing.shape.Triangle;
import history.HistoryManager;
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
public class CanvasContainer extends JPanel implements MouseMotionListener, MouseInputListener {

    /**
     *
     */
    private final HistoryManager historyManager;


    /**
     *
     */
    private final List<Shape> shapes = new ArrayList<>();

    /**
     * The temporary object that is being used to display a pre-emptive shape that
     * will be drawn when the user let's go of the mouse.
     */
    private Shape currentObject = null;

    /**
     *
     */
    private Shape selectedShape = null;

    /**
     *
     */
    private Shape highlightedShape = null;

    /**
     *
     */
    private boolean copySelectedShape = false;

    /**
     *
     */
    private final ToolController toolController;

    /**
     *
     */
    private final WidgetController widgetController;

    /**
     *
     */
    private int mouseX1;

    /**
     *
     */
    private int mouseY1;

    /**
     *
     */
    private int isResizing = -1;

    /**
     *
     */
    private boolean isDragging = false;

    /**
     *
     */
    private boolean additionalOverlay = true;

    /**
     *
     */
    public CanvasContainer(ToolController toolController, WidgetController widgetController) {
        this.toolController = toolController;
        this.widgetController = widgetController;

        // Default canvas background
        this.setBackground(Color.WHITE);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);


        // Instantiate the history manager
        this.historyManager = new HistoryManager();

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

        this.repaint();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    private void widgetPropertiesChangeListener(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("widgetPropertyChange")) {

            // don't do anything if no selected shape
            if (this.selectedShape == null) {
                return;
            }

            // Get the current shape properties.
            var shapeProperties = this.selectedShape.getPropertyMap();

            // Cast the current value retrieved from the widget and update the values of the
            // current shape.
            var updatedProperties = (Map<String, Object>) event.getNewValue();

            // check if the properties have changed, and if they have set the shape properties
            // of the current object
            for (String name : updatedProperties.keySet()) {
                var newProperty = updatedProperties.get(name);

                if (shapeProperties.get(name) != null) {


                    // if setProperty throws an IllegalArgumentException, this means
                    // that the property is invalid and should not be propagated
                    try {
                        this.selectedShape.setProperty(name, newProperty);
                    } catch (IllegalArgumentException e) {
                        return;
                    }
                }
            }

            // Update our history manager
            this.historyManager.addHistoryEntry(this.selectedShape);

            this.revalidate();
            this.repaint();
        }
    }


    /**
     * This overridden paint method will be called by Swing when Canvas.repaint() is called from
     * SimpleGuiDelegate.propertyChange().
     */
    public void paint(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        var g2 = (Graphics2D) g;

        // Enable antialias so objects look sharp
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Propagate alpha values for stroke colours
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // Draw all of the components on the canvas
        this.shapes.forEach(shape -> shape.draw(g2, false));

        // Draw the current shape to add interactivity to the canvas. The user
        // can see what they are about to draw.
        if (additionalOverlay && currentObject != null) {
            currentObject.draw(g2, true);
        }

        // Draw any highlighted shape...
        if (additionalOverlay && highlightedShape != null && !(isDragging || isResizing > -1)) {
            highlightedShape.drawBoundary(g2);
        }

        // Draw a selected bounding box if a shape has been selected...
        if (additionalOverlay && selectedShape != null && !isDragging) {
            selectedShape.drawSelectedBoundary(g2);
        }

        g.dispose();
    }


    /**
     *
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        var currentTool = this.toolController.getCurrentTool();

        // Handle a click event when 'image' tool is enabled
        if (currentTool.getType() == ToolType.IMAGE) {

            // don't re-create the image if it's already created.
            if (this.currentObject == null) {
                this.currentObject = createNewObject(mouseX1, mouseY1, 0, 0);
            } else {

                // add the current shape to the canvas and the history manager for future
                // reference...
                this.historyManager.addShape(currentObject);

                this.shapes.add(currentObject);

                this.selectedShape = this.currentObject;
                this.currentObject = null;

                this.repaint();

                // update the widget controller with the newly selected shape
                this.widgetController.setCurrentWidgetFromShape(selectedShape);
            }
        }


        // Handle a click event when 'fill' tool is enabled
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

                if (this.selectedShape != null) {
                    this.isDragging = true;
                    this.isResizing = this.selectedShape.getResizePoint(e.getPoint());
                }

                // use the isOnResizePoint method to check whether a point


                if (this.isResizing < 0 && !Objects.equals(selectedShape, this.selectedShape)) {
                    // update the cursor to represent the movement of the object
                    this.selectedShape = selectedShape;

                    this.repaint();

                    // update the widget if the selected component is not null
                    if (selectedShape != null) {
                        this.widgetController.setCurrentWidgetFromShape(this.selectedShape);
                    } else {
                        this.widgetController.setCurrentWidget(new EmptyToolWidget());
                    }
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
                this.isResizing = -1;

                // update the panel widget to reflect the newest values
                this.widgetController.updateWidget();

            }

            if (currentObject != null && currentTool.getType() != ToolType.FILL &&
                    currentTool.getType() != ToolType.IMAGE) {
                shapes.add(currentObject);

                // add the item to shape history
                this.historyManager.addShape(currentObject);

                // Update the widget controller to our newest shape on the canvas
                this.selectedShape = currentObject;
                this.widgetController.setCurrentWidgetFromShape(selectedShape);

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

        if (SwingUtilities.isLeftMouseButton(e)) {

            // Check if we need to drag or resize the current object based on selector
            // state
            if (this.selectedShape != null && currentTool.getType() == ToolType.SELECTOR) {
                var oldX = this.selectedShape.getX();
                var oldY = this.selectedShape.getY();

                var dx = (e.getX() - mouseX1);
                var dy = (e.getY() - mouseY1);

                // perform resizing operation
                if (this.isResizing > -1) {
                    System.out.println(dx);
                    System.out.println(dy);
                    this.selectedShape.resizeShape(this.isResizing, dx, dy);


                    // update the mouse values to the current point
                    mouseX1 = e.getX();
                    mouseY1 = e.getY();

                    this.repaint();
                } else if (this.isDragging) {
                    // Perform dragging operation

                    // on the event that the shape is dragged to a boundary
                    try {
                        this.selectedShape.setX(oldX + dx);
                        this.selectedShape.setY(oldY + dy);

                        // only do this for line objects
                        if (this.selectedShape instanceof Line) {
                            var oldEndX = ((Line) this.selectedShape).getEndX();
                            var oldEndY = ((Line) this.selectedShape).getEndY();

                            ((Line) (this.selectedShape)).setEndX(oldEndX + dx);
                            ((Line) (this.selectedShape)).setEndY(oldEndY + dy);
                        }

                        // update the mouse values to the current point
                        mouseX1 = e.getX();
                        mouseY1 = e.getY();

                        this.repaint();
                    } catch (IllegalArgumentException ignored) {
                        this.selectedShape.setX(oldX);
                        this.selectedShape.setY(oldY);
                    }
                }
            }


            if (currentTool.getType() != ToolType.SELECTOR &&
                    currentTool.getType() != ToolType.FILL && currentTool.getType() != ToolType.IMAGE) {
                // Perform a drawing action if the current tool is not fill
                // or an image inserter.

                var endX = e.getX();
                var endY = e.getY();


                // create the new object based on the currently selected tool
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


            // check for resize events if the selectedShape is not null
            if (this.selectedShape != null) {
                // Show a 'move' cursor when hovering over a selected object
                if (Objects.equals(this.selectedShape, newHighlightedShape)) {
                    this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }


                var onResize = this.selectedShape.getResizePoint(e.getPoint());

                // use the isOnResizePoint method to check whether a point

                if (onResize > -1) {
                    this.setCursor(ResizeEvent.getCursorFromResizeEvent(onResize));
                }
            }

            // Don't re-draw the selected object if they are the same or null, or equal
            // to the currently selected object...
            if (!Objects.equals(newHighlightedShape, this.highlightedShape) ||
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
    public List<Shape> getShapes() {
        return shapes;
    }

    /**
     *
     */
    public void setShapes(List<Shape> shapes) {
        this.shapes.clear();
        this.shapes.addAll(shapes);

        this.repaint();
    }

    /**
     *
     */
    public Shape getShapeIfHoveringShape(Point point) {
        List<Shape> shapes = new ArrayList<>(this.shapes);

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
     */
    public void clear() {
        this.shapes.clear();

        this.setBackground(Color.WHITE);

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

        // This should never happen...
        if (currentTool.getType() == ToolType.FILL || currentTool.getType() == ToolType.SELECTOR) {
            throw new IllegalStateException("Can't create new object for a selection tool");
        }


        try {
            // we need to boot up JFileChooser to select the item that
            // will be drawn on the canvas.
            if (currentTool.getType() == ToolType.IMAGE) {
                var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                // filter for image files only
                FileFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "png");
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

            // Create the object based on the current tool
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
        this.shapes.add(this.selectedShape);

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

        this.shapes.remove(this.selectedShape);
        this.resetSelectedObject();

        // clear the canvas if no shapes are present on the canvas
        if (this.shapes.size() == 0) {
            this.clear();
        }

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
