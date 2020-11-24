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
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

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

        this.panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.panel.setPreferredSize(new Dimension(120, 24));
        this.panel.setMaximumSize(new Dimension(240, 24));
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
