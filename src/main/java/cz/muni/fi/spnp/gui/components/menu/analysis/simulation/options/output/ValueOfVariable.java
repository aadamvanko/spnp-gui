package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Value of variable output option view model.
 */
public class ValueOfVariable extends OutputOptionViewModel {

    public ValueOfVariable() {
        super("Value of a variable (pr_value)");

        initVariable();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ValueOfVariable();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        result.getLines().add(String.format("pr_value(\"Value of the variable %s\", (double) %s);", getVariable().getName(), getVariable().getName()));
    }

}
