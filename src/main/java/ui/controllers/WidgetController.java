package ui.controllers;

import drawing.shape.Shape;
import drawing.tool.DrawingTool;
import ui.common.WidgetFactory;
import ui.tool.BaseToolWidget;
import ui.tool.EmptyToolWidget;

import javax.swing.JFrame;
import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class WidgetController {

    private final JFrame frame;
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
    public WidgetController(JFrame frame) {
        this.frame = frame;
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
    public BaseToolWidget getCurrentToolWidget() {
        return this.widget;
    }

    /**
     *
     * */
    public void setCurrentWidgetFromShape(Shape shape) {
        BaseToolWidget oldWidget = this.widget;

        this.widget = WidgetFactory.createWidgetFromShape(shape, this.frame);


        pcs.firePropertyChange("widgetChange", oldWidget, this.widget);
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
