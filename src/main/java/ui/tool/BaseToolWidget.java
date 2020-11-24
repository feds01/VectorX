package ui.tool;

import ui.input.BaseInput;

import javax.swing.JPanel;
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
    private final JPanel panel;

    /**
     *
     * */
    public BaseToolWidget(JPanel panel) {
        this.panel = panel;
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
}
