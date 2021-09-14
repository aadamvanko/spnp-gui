package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ExpectedAccumulatedRewardTime extends OutputOptionTime {

    public ExpectedAccumulatedRewardTime() {
        super("Expected accumulated reward by time t");

        initFunction();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        result.lines.add(String.format("pr_cum_expected(\"Expected accumulated reward using %s\", %s);", getFunction().getName(), getFunction().getName()));

        tryAddingClosingBracket(result);
    }
}
