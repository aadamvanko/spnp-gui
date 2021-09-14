package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ValueOfVariable extends OutputOptionViewModel {

    public ValueOfVariable() {
        super("Value of a variable (pr_value)");

        initVariable();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        result.lines.add(String.format("pr_value(\"Value of the variable %s\", (double) %s);", getVariable().getName(), getVariable().getName()));
    }

}
