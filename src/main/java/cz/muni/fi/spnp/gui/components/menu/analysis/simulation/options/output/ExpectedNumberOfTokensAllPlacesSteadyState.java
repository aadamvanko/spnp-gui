package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Expected number of tokens in all places output option view model.
 */
public class ExpectedNumberOfTokensAllPlacesSteadyState extends OutputOptionSteadyState {

    public ExpectedNumberOfTokensAllPlacesSteadyState() {
        super("Expected # of tokens of all places in steady-state");
    }


    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedNumberOfTokensAllPlacesSteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getPlaces().forEach(placeViewModel -> addExpectedNumberOfTokensPlace(result, diagramViewModel, placeViewModel));
    }

}
