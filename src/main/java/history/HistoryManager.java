package history;

import common.CopyUtils;
import drawing.shape.Shape;
import drawing.shape.ShapeProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class HistoryManager {

    /**
     *
     */
    private List<Shape> addedCanvasShapes = new ArrayList<>();

    /**
     *
     */
    private Stack<Map<Integer, ShapeProperties>> historyFrames = new Stack<>();

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
        this.currentFrame = Map.copyOf(currentFrame);

        var propertyCopy = (ShapeProperties) CopyUtils.deepCopy(shape.getProperties());

        currentFrame.put(historyFrames.size(), propertyCopy);

    }

    /**
     *
     * */
    public void addHistoryEntry(Shape shape) {
        var copy = Map.copyOf(currentFrame);

        var propertyCopy = (ShapeProperties) CopyUtils.deepCopy(shape.getProperties());

        copy.put(addedCanvasShapes.indexOf(shape), propertyCopy);

        this.historyFrames.push(copy);
    }

    /**
     *
     * */
    public boolean canRedo() {
        return currentFrameId < this.currentFrame.size() - 1;
    }

    public boolean canUndo() {
        return currentFrameId > 0;
    }

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

    public int getCurrentFrameId() {
        return currentFrameId;
    }

    public void setCurrentFrameId(int currentFrameId) {
        this.currentFrameId = currentFrameId;
    }
}
