package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * View class for all options including checkbox to indicate if the user wants to include it in the output.
 */
public abstract class OptionView {

    private final String name;
    protected Label labelName;
    protected CheckBox checkBoxUse;

    public OptionView(String name) {
        this.name = name;

        createView();
    }

    private void createView() {
        labelName = new Label(name);
        checkBoxUse = new CheckBox();
    }

    public Label getLabelName() {
        return labelName;
    }

    public CheckBox getCheckBoxUse() {
        return checkBoxUse;
    }

}
