package ui.tool;

import ui.input.BaseInput;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * */
public abstract class BaseToolWidget {


    /**
     *
     * */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     *
     * */
    protected List<BaseInput<?>> tools = new ArrayList<>();

    /**
     *
     * */
    protected final JPanel panel;

    /**
     *
     * */
    public BaseToolWidget() {
        this.panel = new JPanel();

        panel.setBackground(Color.WHITE);

        // set maximum property width to 240
        panel.setPreferredSize(new Dimension(240, 100000));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     *
     * */
    public Map<String, Object> getValueMap() {
        Map<String, Object> values = new HashMap<>();


        for(BaseInput<?> tool : tools) {
            values.put(tool.getName(), tool.getValue());
        }

        return values;
    }

    public Object getValue(String name) {
        for(BaseInput<?> tool : tools) {

            if (tool.getName().equals(name)) {
                return tool.getValue();
            }
        }

        return null;
    }

    /**
     *
     * */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changes.addPropertyChangeListener(listener);
    }

    /**
     *
     * */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changes.removePropertyChangeListener(listener);
    }


    /**
     *
     * */
    public JPanel getComponent() {
        return this.panel;
    }

    /**
     *
     * */
    protected void setupInputListeners() {
        this.tools.forEach(tool -> {
            tool.addPropertyChangeListener(evt -> {
                changes.firePropertyChange(tool.getName(), evt.getOldValue(), evt.getNewValue());
            });
        });
    }
}
