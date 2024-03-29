package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Expected number of tokens in place output option view model.
 */
public class ExpectedNumberOfTokensPlaceSteadyState extends OutputOptionSteadyState {

    public ExpectedNumberOfTokensPlaceSteadyState() {
        super("Expected # of tokens of a given place in steady-state");

        initPlace();
    }


    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedNumberOfTokensPlaceSteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addExpectedNumberOfTokensPlace(result, diagramViewModel, getPlace());
    }

}
