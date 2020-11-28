package history;

import common.CopyUtils;
import drawing.shape.Shape;
import drawing.shape.ShapeProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Class that holds utility methods to copy objects and make a new
 * instance of the object.
 *
 * @author 200008575
 * */
public class HistoryManager {

    /**
     *
     */
    private final List<Shape> addedCanvasShapes = new ArrayList<>();

    /**
     *
     */
    private final Stack<Map<Integer, ShapeProperties>> historyFrames = new Stack<>();

    /**
     *
     */
    private Map<Integer, ShapeProperties> currentFrame = new HashMap<>();

    /**
     *
     */
    private int currentFrameId = 0;

    /**
     *
     */
    public HistoryManager() {
        this.historyFrames.push(currentFrame);
    }

    /**
     *
     */
    public void addShape(Shape shape) {
        this.currentFrame = new LinkedHashMap<>(currentFrame);

        var shapeCopy = (Shape) CopyUtils.deepCopy(shape);

        this.addedCanvasShapes.add(shapeCopy);

        assert shapeCopy != null;
        currentFrame.put(historyFrames.size(), shapeCopy.getProperties());

    }

    /**
     *
     * */
    public void addHistoryEntry(Shape shape) {
        var copy = new LinkedHashMap<>(currentFrame);

        var propertyCopy = (ShapeProperties) CopyUtils.deepCopy(shape.getProperties());

        if (addedCanvasShapes.contains(shape)) {
            var globalShape = addedCanvasShapes.get(addedCanvasShapes.indexOf(shape));

            // don't add it if no change was detected
            if (!globalShape.getProperties().equals(propertyCopy)) {
                copy.put(addedCanvasShapes.indexOf(shape), propertyCopy);
                this.historyFrames.push(copy);
                this.currentFrame = copy;

                currentFrameId++;
            }
        }
    }

    /**
     *
     * */
    public boolean canRedo() {
        return currentFrameId < this.currentFrame.size() - 1;
    }

    /**
     *
     */
    public boolean canUndo() {
        return currentFrameId > 0;
    }

    /**
     *
     */
    public List<Shape> redo() {
        if (!canRedo()) { return null; }

        List<Shape> shapes = new ArrayList<>();

        Map<Integer, ShapeProperties> mapOfProperties;

        // Don't do anything if we're on the most recent frame
        if (currentFrameId == this.historyFrames.size() - 1) {
            mapOfProperties = this.historyFrames.get(this.historyFrames.size() - 1);
        } else {
            mapOfProperties = this.historyFrames.get(currentFrameId++);
        }

        mapOfProperties.forEach((idx, shapeProperties) -> {
            var shape = this.addedCanvasShapes.get(idx);;

            shapes.add(shape);
        });


        return shapes;
    }

    /**
     *
     */
    public List<Shape> undo() {
        if (!canUndo()) { return null; }

        List<Shape> shapes = new ArrayList<>();

        Map<Integer, ShapeProperties> mapOfProperties;

        // Don't do anything if we're on the most recent frame
        if (currentFrameId == 0) {
            mapOfProperties = currentFrame;
        } else {
            mapOfProperties = this.historyFrames.get(currentFrameId--);
        }

        mapOfProperties.forEach((idx, shapeProperties) -> {
            var shape = this.addedCanvasShapes.get(idx);;

            shapes.add(shape);
        });


        return shapes;
    }

    /**
     *
     */
    public int getCurrentFrameId() {
        return currentFrameId;
    }

    /**
     *
     */
    public void setCurrentFrameId(int currentFrameId) {
        this.currentFrameId = currentFrameId;
    }

    /**
     *
     */
    public Map<Integer, ShapeProperties> getCurrentFrame() {
        return this.currentFrame;
    }

    /**
     *
     */
    public List<Shape> getAddedCanvasShapes() {
        return addedCanvasShapes;
    }

    /**
     *
     */
    public Stack<Map<Integer, ShapeProperties>> getHistoryFrames() {
        return historyFrames;
    }
}
