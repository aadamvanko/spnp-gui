package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Expected reward rete in steady state output option view model.
 */
public class ExpectedRewardRateSteadyState extends OutputOptionSteadyState {

    public ExpectedRewardRateSteadyState() {
        super("Expected reward rate in steady-state");

        initFunction();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedRewardRateSteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        result.getLines().add(String.format("pr_expected(\"Expected reward rate of %s in steady-state\", %s);", getFunction().getName(), getFunction().getName()));
    }

}
