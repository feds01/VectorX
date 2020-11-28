package ui.controllers;

import drawing.ToolType;
import drawing.tool.DrawingTool;
import ui.widget.ToolMenu;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;


/**
 *
 */
public class ToolController {

    /**
     *
     */
    private DrawingTool currentTool = null;

    /**
     *
     */
    private final SwingPropertyChangeSupport toolListener = new SwingPropertyChangeSupport(this);

    /**
     *
     */
    public ToolController() {
    }

    /**
     *
     */
    public DrawingTool getCurrentTool() {
        return currentTool;
    }

    /**
     *
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.toolListener.addPropertyChangeListener(listener);
    }

    /**
     *
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.toolListener.removePropertyChangeListener(listener);
    }

    /**
     *
     */
    public void setCurrentTool(ToolType type) {
        DrawingTool oldTool = this.currentTool;
        this.currentTool = ToolMenu.toolMap.get(type);

        toolListener.firePropertyChange("toolChange", oldTool, this.currentTool);
    }

    /**
     *
     */
    public void setCurrentTool(DrawingTool currentTool) {
        DrawingTool oldTool = this.currentTool;

        this.currentTool = currentTool;

        toolListener.firePropertyChange("toolChange", oldTool, this.currentTool);
    }
}
