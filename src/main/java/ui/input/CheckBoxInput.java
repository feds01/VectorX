package ui.input;


import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * CheckboxInput class that is used to render a text field input which
 * is derived from the BaseInput class.
 *
 * @author 200008575
 * */
public class CheckBoxInput extends BaseInput<Boolean> {

    /**
     *
     * */
    private final JCheckBox checkbox;

    /**
     *
     * */
    public CheckBoxInput(String name, boolean initialValue, String labelText) {
        super(name, initialValue);


        this.checkbox = new JCheckBox();
        this.checkbox.setSelected(initialValue);
        this.checkbox.addActionListener(this::actionPerformed);

        // add components to the panel component
        this.panel.add(this.checkbox);


        if (!labelText.equals("")) {
            JLabel label = new JLabel(labelText);
            label.setFont(fontLoader.getFont("NotoSans"));
            label.setForeground(Color.GRAY);

            this.panel.add(label);
        }
    }

    /**
     *
     */
    public CheckBoxInput(String name, boolean initialValue) {
        this(name, initialValue, "");
    }

    /**
     *
     * */
    private void actionPerformed(ActionEvent e) {
        var checkbox = (JCheckBox) e.getSource();

        changes.firePropertyChange(name, !checkbox.isSelected(), checkbox.isSelected());
    }

    /**
     *
     *
     * @param value*/
    public void setValue(Object value) {
        this.checkbox.setSelected((Boolean) value);
    }

    /**
     *
     * */
    public Boolean getValue() {
        return this.checkbox.isSelected();
    }

    /**
     *
     * */
    public JPanel getComponent() {
        return this.panel;
    }
}
