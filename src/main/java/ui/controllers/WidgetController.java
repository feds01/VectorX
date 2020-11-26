package ui.controllers;

import drawing.shape.Shape;
import ui.common.WidgetFactory;
import ui.tool.BaseToolWidget;
import ui.tool.EmptyToolWidget;

import javax.swing.JFrame;
import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class WidgetController {

    private Map<String, Object> widgetProperties = new HashMap<>();

    private final JFrame frame;
    /**
     *
     */
    private BaseToolWidget widget = new EmptyToolWidget();

    /**
     *
     */
    private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this);

    /**
     *
     */
    public WidgetController(JFrame frame) {
        this.frame = frame;
    }


    /**
     *
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     *
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }


    /**
     *
     */
    public BaseToolWidget getCurrentToolWidget() {
        return this.widget;
    }

    /**
     *
     */
    public void setCurrentWidgetFromShape(Shape shape) {
        BaseToolWidget oldWidget = this.widget;

        // un-register property listener for this widget to avoid
        // memory leaks or tainted values.
        this.widget.removePropertyChangeListener(this::propertyListener);

        this.widget = WidgetFactory.createWidgetFromShape(shape, this.frame);

        this.widgetProperties = this.widget.getValueMap();
        this.widget.addPropertyChangeListener(this::propertyListener);

        pcs.firePropertyChange("widgetChange", oldWidget, this.widget);
    }

    private void propertyListener(PropertyChangeEvent propertyChangeEvent) {
        var widget = (BaseToolWidget) propertyChangeEvent.getSource();

        Map<String, Object> oldWidgetProperties = this.widgetProperties;
        this.widgetProperties = widget.getValueMap();

        // @Workaround: Weird bug where if multiple listeners are registered within lambdas,
        // events aren't being detected.
        for (PropertyChangeListener l : this.pcs.getPropertyChangeListeners()) {
            l.propertyChange(new PropertyChangeEvent(this, "widgetPropertyChange", oldWidgetProperties, this.widgetProperties));
        }
    }

    /**
     *
     */
    public void setCurrentWidget(BaseToolWidget widget) {
        BaseToolWidget oldWidget = this.widget;
        this.widget = widget;

        pcs.firePropertyChange("widgetChange", oldWidget, this.widget);
    }

    public Map<String, Object> getWidgetProperties() {
        return widgetProperties;
    }
}
