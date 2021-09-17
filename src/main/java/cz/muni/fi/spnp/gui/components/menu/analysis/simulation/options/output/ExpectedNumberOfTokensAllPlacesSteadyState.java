package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

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
