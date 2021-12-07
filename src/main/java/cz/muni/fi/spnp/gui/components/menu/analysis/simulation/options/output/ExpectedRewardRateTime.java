package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Expected reward rate at time t output option view model.
 */
public class ExpectedRewardRateTime extends OutputOptionTime {

    public ExpectedRewardRateTime() {
        super("Expected reward rate at time t");

        initFunction();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedRewardRateTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        result.getLines().add(String.format("pr_expected(\"Expected reward rate of %s\", %s);", getFunction().getName(), getFunction().getName()));

        tryAddingClosingBracket(result);
    }

}
