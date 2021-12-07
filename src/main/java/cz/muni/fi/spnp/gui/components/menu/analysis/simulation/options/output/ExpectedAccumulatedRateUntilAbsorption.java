package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Expected accumulated rate until absorption output option view model.
 */
public class ExpectedAccumulatedRateUntilAbsorption extends OutputOptionViewModel {

    public ExpectedAccumulatedRateUntilAbsorption() {
        super("Expected accumulated rate until absorption");

        initFunction();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedAccumulatedRateUntilAbsorption();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        addSolve(result);
        result.getLines().add(String.format("pr_cum_expected(\"Expected accumulated reward until absorption using %s\", %s);",
                getFunction().getName(), getFunction().getName()));
    }

}
