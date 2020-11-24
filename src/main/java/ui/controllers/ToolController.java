package ui.controllers;

import drawing.tool.DrawingTool;
import ui.tool.BaseToolWidget;
import ui.tool.EmptyToolWidget;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;


/**
 *
 * */
public class ToolController {

    /**
     *
     * */
    private DrawingTool currentTool = null;

    /**
     *
     * */
    private BaseToolWidget widget = new EmptyToolWidget();

    /**
     *
     * */
    private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this);

    /**
     *
     * */
    public ToolController() {}

    /**
     *
     * */
    public DrawingTool getCurrentTool() {
        return currentTool;
    }

    /**
     *
     * */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     *
     * */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
     *
     * */
    public void setCurrentTool(DrawingTool currentTool) {
        DrawingTool oldTool = this.currentTool;

        this.currentTool = currentTool;

        pcs.firePropertyChange("titleChange", oldTool, this.currentTool);
    }

    /**
     *
     * */
    public BaseToolWidget getCurrentToolWidget() {
        return this.widget;
    }

    /**
     *
     * */
    public void setCurrentWidget(BaseToolWidget widget) {
        BaseToolWidget oldWidget = this.widget;
        this.widget = widget;

        System.out.println(widget);

        pcs.firePropertyChange("widgetChange", oldWidget, this.widget);
    }
}
