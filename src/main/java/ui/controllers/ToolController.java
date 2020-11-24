package ui.controllers;

import drawing.tool.DrawingTool;
import ui.tool.BaseToolWidget;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;


public class ToolController {
    private DrawingTool currentTool = null;

    private BaseToolWidget widget = null;

    private SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this);

    public ToolController() {}

    public DrawingTool getCurrentTool() {
        return currentTool;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void setCurrentTool(DrawingTool currentTool) {
        DrawingTool oldTool = this.currentTool;

        this.currentTool = currentTool;

        pcs.firePropertyChange("change", oldTool, this.currentTool);
    }

    public void setCurrentWidget(BaseToolWidget widget) {
        this.widget = widget;
    }
}
