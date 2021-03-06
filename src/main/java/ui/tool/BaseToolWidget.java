package ui.tool;

import drawing.shape.Shape;
import ui.input.BaseInput;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseToolWidget widget class that is used to create PropertiesMenu
 * widgets that will be rendered based on the currently selected shape.
 *
 * @author 200008575
 * */
public abstract class BaseToolWidget {

    /**
     * The reference of the currently edited shape.
     */
    Shape shape;

    /**
     *
     */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     *
     */
    protected List<BaseInput<?>> tools = new ArrayList<>();

    /**
     *
     */
    protected final JPanel panel;

    /**
     * BaseToolWidget constructor
     * */
    public BaseToolWidget(Shape shape) {
        this.shape = shape;
        this.panel = new JPanel();

        panel.setBackground(Color.WHITE);

        // set maximum property width to 240
        panel.setPreferredSize(new Dimension(240, 100000));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     *
     */
    public Map<String, Object> getValueMap() {
        Map<String, Object> values = new HashMap<>();


        for (BaseInput<?> tool : tools) {
            values.put(tool.getName(), tool.getValue());
        }

        return values;
    }

    /**
     *
     */
    public Object getValue(String name) {
        for (BaseInput<?> tool : tools) {

            if (tool.getName().equals(name)) {
                return tool.getValue();
            }
        }

        return null;
    }

    /**
     *
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changes.addPropertyChangeListener(listener);
    }

    /**
     *
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changes.removePropertyChangeListener(listener);
    }

    /**
     *
     */
    public JPanel getComponent() {
        return this.panel;
    }

    /**
     *
     */
    protected void constructUI() {
    }

    /**
     *
     */
    protected void setupInputListeners() {
        this.tools.forEach(tool -> tool.addPropertyChangeListener(evt -> {
            for (PropertyChangeListener l : changes.getPropertyChangeListeners()) {

                // fire property change for each listener
                l.propertyChange(new PropertyChangeEvent(
                        this,
                        tool.getName(),
                        evt.getOldValue(),
                        evt.getNewValue()
                ));
            }
        }));
    }

    /**
     *
     */
    public void update() {
        this.tools.forEach(tool -> {
            tool.setValue(this.shape.getProperty(tool.getName()));
        });

        this.panel.removeAll();
        this.constructUI();

        this.panel.repaint();
    }
}
