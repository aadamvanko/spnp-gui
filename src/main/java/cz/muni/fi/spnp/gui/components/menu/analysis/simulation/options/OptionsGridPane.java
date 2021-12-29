package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Grid pane for options only.
 */
public class OptionsGridPane extends GridPane {

    public OptionsGridPane() {
        setHgap(5);
        setVgap(5);
        addRow(0, new Label("Option"), new Label("Value"), new Label("Use"));
    }

    public void addRow(ChoiceOptionView choiceOptionView) {
        addRow(getRowCount(), choiceOptionView.getLabelName(), choiceOptionView.getChoiceBoxValue(), choiceOptionView.getCheckBoxUse());
    }

    public void addRow(IntegerOptionView integerOptionView) {
        addRow(getRowCount(), integerOptionView.getLabelName(), integerOptionView.getIntegerTextFieldValue().getTextField(), integerOptionView.getCheckBoxUse());
    }

    public void addRow(DoubleOptionView doubleOptionView) {
        addRow(getRowCount(), doubleOptionView.getLabelName(), doubleOptionView.getDoubleTextFieldValue().getTextField(), doubleOptionView.getCheckBoxUse());
    }

}
