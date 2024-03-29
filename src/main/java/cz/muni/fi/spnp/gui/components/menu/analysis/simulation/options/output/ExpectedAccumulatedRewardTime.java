package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Expected accumulated rate reward in time output option view model.
 */
public class ExpectedAccumulatedRewardTime extends OutputOptionTime {

    public ExpectedAccumulatedRewardTime() {
        super("Expected accumulated reward by time t");

        initFunction();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedAccumulatedRewardTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        result.getLines().add(String.format("pr_cum_expected(\"Expected accumulated reward using %s\", %s);", getFunction().getName(), getFunction().getName()));

        tryAddingClosingBracket(result);
    }
}
