package history;

import common.CopyUtils;
import drawing.shape.Shape;
import drawing.shape.ShapeProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class History {

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
    public History() {
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

    public List<ShapeProperties> redo() {
        return null;
    }

    public List<ShapeProperties> undo() {
        return null;
    }

    public int getCurrentFrameId() {
        return currentFrameId;
    }

    public void setCurrentFrameId(int currentFrameId) {
        this.currentFrameId = currentFrameId;
    }
}
