package ui.input;


import common.FontLoader;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * */
public abstract class BaseInput<T> {
    /**
     *
     * */
    public final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     *
     * */
    protected static final FontLoader fontLoader = FontLoader.getInstance();

    /**
     *
     * */
    protected JPanel panel = new JPanel();

    /**
     *
     * */
    protected final String name;

    /**
     *
     * */
    protected T value;

    /**
     *
     * */
    BaseInput(String name, T initialValue) {
        this.name = name;
        this.value = initialValue;

        this.panel.setBackground(Color.WHITE);
        this.panel.setFont(fontLoader.getFont("NotoSans"));


        var layout = new FlowLayout(FlowLayout.LEADING);
        layout.setHgap(0);

        this.panel.setLayout(layout);

        this.panel.setPreferredSize(new Dimension(120, 26));
        this.panel.setMaximumSize(new Dimension(240, 26));
    }

    /**
     *
     * */
    public String getName() {
        return name;
    }

    /**
     *
     * */
    public T getValue() {
        return this.value;
    }

    /**
     *
     * */
    public void setValue(T value) {
        this.value = value;
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
}
