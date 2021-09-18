package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

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
