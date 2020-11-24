package ui.tool;

import ui.input.BaseInput;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * */
public abstract class BaseToolWidget {

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
    protected Map<String, Object> getValueMap() {
        Map<String, Object> values = new HashMap<>();


        for(BaseInput<?> tool : tools) {
            values.put(tool.getName(), tool.getValue());
        }

        return values;
    }

    public JPanel getComponent() {
        return this.panel;
    }
}
